package com.aykhan.controller;

import com.aykhan.dto.LoginRequest;
import com.aykhan.dto.LoginResponse;
import com.aykhan.dto.RegisterRequest;
import com.aykhan.dto.RegisterResponse;
import com.aykhan.services.MyAuthService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class AuthController {

  private final MyAuthService authService;

  public AuthController(MyAuthService authService) {
    this.authService = authService;
  }

  @PostMapping("/register")
  public RegisterResponse register_new(@RequestBody RegisterRequest req) {
    log.debug(String.format("request received %s", req));
    boolean res = authService.register(req.getName(),
            req.getEmail(),
            req.getPass(),
            req.getIsMaker());
    return res ? RegisterResponse.ok() : RegisterResponse.alreadyRegistered();
  }

  @PostMapping("/login")
  public LoginResponse login(@RequestBody LoginRequest req) {
    return authService.login(req.getName(), req.getPass(), req.isRemember())
        .map(token -> new LoginResponse("logged in", token))
        .orElse(new LoginResponse("wrong credentials", null));
  }
}
