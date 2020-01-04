package com.aykhan.repository;

import com.aykhan.entities.Subject;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SubjectRepository extends CrudRepository<Subject, Integer> {

  Subject getByName(@Param("name") String name);

  boolean existsByName(@Param("name") String name);

}
