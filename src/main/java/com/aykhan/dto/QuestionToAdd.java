package com.aykhan.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuestionToAdd {
  private int id;
  private String question;
  private String subject;
  private List<String> options;
  private int answer;
}
