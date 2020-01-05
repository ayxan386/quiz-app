package com.aykhan.services.implementations;

import com.aykhan.entities.MyUser;
import com.aykhan.entities.Subject;
import com.aykhan.entities.UserResults;
import com.aykhan.repository.UserResultsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

@Service
public class UserResultsService {
  private final UserResultsRepository userResultsRepository;
  private final MyUserDetailsService userDetailsService;

  @Autowired
  public UserResultsService(UserResultsRepository userResultsRepository,
                            MyUserDetailsService userDetailsService) {
    this.userResultsRepository = userResultsRepository;
    this.userDetailsService = userDetailsService;
  }

  public UserResults saveScoreOfAuth(int score, Subject sub) {
    User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    String username = user.getUsername();
    MyUser myUser = userDetailsService.getByUsername(username);
    return userResultsRepository.save(new UserResults(-1, myUser, sub, score));
  }
}
