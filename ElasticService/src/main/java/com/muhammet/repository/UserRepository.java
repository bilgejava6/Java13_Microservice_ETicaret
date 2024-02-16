package com.muhammet.repository;

import com.muhammet.domain.User;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface UserRepository extends ElasticsearchRepository<User,String> {

}
