package com.aykhan.controller;

import com.aykhan.dto.QuestionToAdd;
import com.aykhan.entities.Question;
import com.aykhan.services.additions.RemoveCorrectAnswers;
import com.aykhan.services.implementations.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/question**")
public class TestController {
  private final QuestionService questionService;

  @Autowired
  public TestController(
      QuestionService questionService) {
    this.questionService = questionService;
  }

  @PostMapping("")
  String add(@RequestBody QuestionToAdd q) {
    questionService.saveOne(q);
    return "success";
  }

  @GetMapping("")
  Iterable<Question> getAllNoSubject() {
    return RemoveCorrectAnswers.removeCorrectAnswer(questionService.getAll());
  }

  @GetMapping("/{subject}")
  List<Question> getAll(@PathVariable("subject") String subject) {
    return RemoveCorrectAnswers.removeCorrectAnswer(questionService.getAll(subject));
  }

  @GetMapping("/{id}")
  Question getId( @PathVariable("id") int id) {
    return RemoveCorrectAnswers.removeCorrectAnswer(questionService.get(id));
  }

}
