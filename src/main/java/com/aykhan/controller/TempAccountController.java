package com.aykhan.controller;

import com.aykhan.dto.LoginResponse;
import com.aykhan.entities.SimpleUser;
import com.aykhan.services.MyAuthService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/temp**")
public class TempAccountController {
    private final MyAuthService userService;


    public TempAccountController(MyAuthService userService) {
        this.userService = userService;
    }

    @PostMapping("")
    LoginResponse register(@RequestBody SimpleUser user) {
        return Optional.of(userService.register(user.getName(), user.getEmail(), user.getEmail(), false))
                .filter(isRegistered -> isRegistered)
                .flatMap(n -> userService.login(user.getName(), user.getEmail(), true))
                .map(token -> new LoginResponse("success", token))
                .orElse(new LoginResponse("Error occured", null));
    }
}
