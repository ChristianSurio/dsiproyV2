package com.sigsaaqyf.demo.repository;


import java.util.Optional;

import com.sigsaaqyf.demo.entity.User;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Long>{
    
    public Optional<User> findByCarnet(String carnet);
    public Iterable<User> findAllByLocked(Boolean locked);
}