package com.aykhan.services.implementations;

import com.aykhan.dto.QuestionToAdd;
import com.aykhan.entities.Answer;
import com.aykhan.entities.Question;
import com.aykhan.repository.QuestionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class QuestionService {
  private final QuestionRepository questionRepository;
  private final AnswerService answerService;

  @Autowired
  public QuestionService(QuestionRepository questionRepository, AnswerService answerService) {
    this.questionRepository = questionRepository;
    this.answerService = answerService;
  }

  public void saveOne(Question q) {
    questionRepository.save(q);
  }

  public Iterable<Question> getAll() {
    return (questionRepository.findAll());
  }

  public List<Question> getAll(String subject) {
    return questionRepository.findBySubject(subject);
  }

  public Question get(int id) {
    return questionRepository.findById(id).orElse(null);
  }

  public Integer getCorrectAnswer(int question_id) {
    return get(question_id).getAnswer();
  }

  public void saveOne(QuestionToAdd q) {
    log.debug(q.toString());
    Question added_q = questionRepository.save(
        new Question(q.getId(), q.getQuestion(), q.getSubject(), null, q.getAnswer()));
    for (int i = 0; i < q.getOptions().size(); i++) {
      Answer answer =
          answerService.saveOne(new Answer(added_q.getId(), q.getOptions().get(i)));
      if (i == q.getAnswer()) {
        added_q.setAnswer(answer.getId());
      }
      questionRepository.save(added_q);
    }
  }
}
