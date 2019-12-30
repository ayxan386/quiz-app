package com.aykhan.controller;

import com.aykhan.dto.UserAnswer;
import com.aykhan.dto.UserSubmission;
import com.aykhan.services.implementations.QuestionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/check")
@Slf4j
public class AnswerController {

  private final QuestionService questionService;
  private int current_score = 0;

  @Autowired
  public AnswerController(QuestionService questionService) {
    this.questionService = questionService;
  }

  @PostMapping("")
  Integer checkMyAnswers(@RequestBody UserSubmission submission) {
    current_score = 0;
    log.debug(String.format("User submission %s", submission));
    submission.getAnws()
        .forEach(
            userAnswer -> check(userAnswer, submission.getAns_weight())
        );
    return current_score;
  }


  private void check(UserAnswer ua, int weight) {
    log.debug(String.format("UserAnswer passed to method: %s", ua.toString()));
    Integer correctAnswer = questionService.getCorrectAnswer(ua.getQuestion_id());
    log.debug(String.format("correct answer according to database is: %d", correctAnswer));

    current_score +=
        ua.getAnswer_number() == correctAnswer ?
            weight
            : 0;
  }
}