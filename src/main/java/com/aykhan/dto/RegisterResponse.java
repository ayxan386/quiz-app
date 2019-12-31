package com.aykhan.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterResponse {

  private String message;

  public static RegisterResponse ok() {
    return new RegisterResponse("OK");
  }

  public static RegisterResponse alreadyRegistered() {
    return new RegisterResponse("user already exists");
  }
}
