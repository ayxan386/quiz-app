package com.aykhan.services.implementations;

import com.aykhan.repository.MyUserRepository;
import lombok.SneakyThrows;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.function.Supplier;

@Service
public class MyUserDetailsService implements UserDetailsService {
  private final MyUserRepository userRepository;

  public MyUserDetailsService(MyUserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @SneakyThrows
  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    return userRepository.getByName(username).flatMap(user ->
        Optional.of(User.withUsername(user.getName())
            .passwordEncoder(pass -> pass)//BCrypt.hashpw(pass,"SALT")
            .password(user.getPassword())
            .roles(user.getRole())
            .build()))
        .orElseThrow((Supplier<Throwable>) () -> new UsernameNotFoundException("no such user exists"));
  }

  public UserDetails getById(Integer id) {
    return userRepository
        .findById(id).flatMap(user ->
            Optional.of(User.withUsername(user.getName())
                .passwordEncoder(pass -> pass)//BCrypt.hashpw(pass,"SALT")
                .password(user.getPassword())
                .roles(user.getRole())
                .authorities("ROLE_" + user.getRole())
                .build())
        ).get();
  }
}
