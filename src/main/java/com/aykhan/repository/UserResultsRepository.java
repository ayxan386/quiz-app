package com.aykhan.repository;

import com.aykhan.entities.UserResults;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserResultsRepository extends CrudRepository<UserResults, Integer> {

}
