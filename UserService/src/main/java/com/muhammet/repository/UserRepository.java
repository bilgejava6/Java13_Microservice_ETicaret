package com.muhammet.repository;

import com.muhammet.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface UserRepository extends MongoRepository<User,String> {

    Page<User> findAllByUserNameContaining(String userName, Pageable pageable);
}
