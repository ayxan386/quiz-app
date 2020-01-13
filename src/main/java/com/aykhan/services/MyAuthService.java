package com.aykhan.services;

import com.aykhan.entities.MyUser;
import com.aykhan.repository.MyUserRepository;
import com.aykhan.security.Const;
import com.aykhan.security.JwtTokenService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MyAuthService {
  private final MyUserRepository userRepository;
  private final PasswordEncoder encoder;
  private final AuthenticationManager am;
  private final JwtTokenService jwtTokenService;

  public MyAuthService(MyUserRepository userRepository,
                       @Qualifier("encoder") PasswordEncoder encoder,
                       AuthenticationManager am, JwtTokenService jwtTokenService) {
    this.userRepository = userRepository;
    this.encoder = encoder;
    this.am = am;
    this.jwtTokenService = jwtTokenService;
  }

  public boolean register(String username, String email, String pass, boolean is_maker) {
    boolean isPresent = userRepository.getByName(username).isPresent();
    if (!isPresent)
      userRepository.save(new MyUser(username,
          email,
          encoder.encode(pass),
          is_maker ? "MAKER" : "USER"));
    return !isPresent;
  }

  public Optional<String> login(String name, String pass, boolean remember) {
    return Optional.of(am.authenticate(new UsernamePasswordAuthenticationToken(name, pass)))
        .filter(Authentication::isAuthenticated)
        .map(a -> {
          SecurityContextHolder.getContext().setAuthentication(a);
          return a;
        })
        .map(
            a -> {
              User u = (User) a.getPrincipal();
              Optional<MyUser> byName = userRepository.getByName(u.getUsername());
              return byName.orElse(null);
            }
        )
        .map(ud -> jwtTokenService.generateToken((long) ud.getId(), remember))
        .map(t -> Const.AUTH_BEARER + t);
  }
}
