package com.muhammet.repository;

import com.muhammet.domain.User;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.Optional;

public interface UserRepository extends ElasticsearchRepository<User,String> {

    Optional<User> findOptionalByAuthId(Long authId);

}
