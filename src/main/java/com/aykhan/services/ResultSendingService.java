package com.aykhan.services;

import com.aykhan.entities.Subject;
import com.aykhan.services.implementations.MyUserDetailsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

@Slf4j
@Component
public class ResultSendingService {
  private final JavaMailSender mailSender;
  private final MyUserDetailsService userDetailsService;

  @Autowired
  public ResultSendingService(JavaMailSender mailSender,
                              MyUserDetailsService userDetailsService) {
    this.mailSender = mailSender;
    this.userDetailsService = userDetailsService;
  }

  public void sendResultMail(Subject subject, int score) {
    SimpleMailMessage msg = new SimpleMailMessage();
    msg.setTo(subject.getOwner().getEmail());

    User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    String username = user.getUsername();

    String date = new SimpleDateFormat("yyyy/MM/DD").format(new Date());

    msg.setText(String.format("User %s took your test %s at %s and got score %d\n",
        username, subject.getName(), date, score
    ));
    msg.setSubject("Test submission");

    try {
      this.mailSender.send(msg);
    } catch (Exception e) {
      log.error(e.getMessage());
    }
  }
}
