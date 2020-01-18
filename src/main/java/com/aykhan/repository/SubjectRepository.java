package com.aykhan.repository;

import com.aykhan.entities.MyUser;
import com.aykhan.entities.Subject;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubjectRepository extends CrudRepository<Subject, Integer> {

    Subject getByName(@Param("name") String name);

    boolean existsByName(@Param("name") String name);

    List<Subject> getAllByOwner(@Param("owner") MyUser user);
}
