package com.aykhan.repository;

import com.aykhan.entities.Question;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepository extends CrudRepository<Question, Integer> {
  List<Question> findBySubject(@Param("subject") String subject);

  Integer getCorrectById(Integer id);
}
