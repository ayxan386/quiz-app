package com.aykhan.services.additions;

import com.aykhan.entities.Question;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class RemoveCorrectAnswers {

  public static List<Question> removeCorrectAnswer(List<Question> init) {
    return init.stream()
        .map(q -> new Question(q, -1))
        .collect(Collectors.toList());
  }

  public static Question removeCorrectAnswer(Question q) {
    return new Question(q, -1);
  }

  public static List<Question> removeCorrectAnswer(Iterable<Question> all) {
    return StreamSupport.stream(all.spliterator(),false)
        .map(q -> new Question(q,-1))
        .collect(Collectors.toList());
  }
}
