package com.aykhan.services.implementations;

import com.aykhan.entities.Answer;
import com.aykhan.repository.AnswerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AnswerService {
  private final AnswerRepository answerRepository;

  @Autowired
  public AnswerService(AnswerRepository answerRepository) {
    this.answerRepository = answerRepository;
  }

  Answer saveOne(Answer q) {
    return answerRepository.save(q);
  }

}
