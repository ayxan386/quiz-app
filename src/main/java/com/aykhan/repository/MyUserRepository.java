package com.aykhan.repository;

import com.aykhan.entities.MyUser;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface MyUserRepository extends CrudRepository<MyUser, Integer> {
  Optional<MyUser> getByName(@Param("name") String name);
}
