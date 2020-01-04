package com.aykhan.services.implementations;

import com.aykhan.dto.QuestionToAdd;
import com.aykhan.entities.Answer;
import com.aykhan.entities.MyUser;
import com.aykhan.entities.Question;
import com.aykhan.entities.Subject;
import com.aykhan.repository.QuestionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class QuestionService {

  private final QuestionRepository questionRepository;
  private final AnswerService answerService;
  private final SubjectService subjectService;
  private final MyUserDetailsService userDetailsService;

  @Autowired
  public QuestionService(QuestionRepository questionRepository,
                         AnswerService answerService,
                         SubjectService subjectService,
                         MyUserDetailsService userDetailsService) {
    this.questionRepository = questionRepository;
    this.answerService = answerService;
    this.subjectService = subjectService;
    this.userDetailsService = userDetailsService;
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
//    //Adding subject
    if (!subjectService.isPresent(q.getSubject())) {
      User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
      String username = user.getUsername();
      MyUser myUser = userDetailsService.getByUsername(username);
      subjectService.saveOne(new Subject(-1, q.getSubject(), myUser));
    }
    //adding question
    Question added_q = questionRepository.save(
        new Question(q.getId(), q.getQuestion(), q.getSubject(), null, q.getAnswer())
    );

    //adding answers
    for (int i = 0; i < q.getOptions().size(); i++) {
      Answer answer =
          answerService.saveOne(new Answer(added_q.getId(), q.getOptions().get(i)));
      if (i == q.getAnswer()) {
        added_q.setAnswer(answer.getId());
      }

      //updating the question
      questionRepository.save(added_q);
    }
  }
}
