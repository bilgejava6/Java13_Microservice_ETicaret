package com.muhammet.manager;

import com.muhammet.dto.request.UserRequestDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import static com.muhammet.constants.RestApiUrls.ADD;

@FeignClient(name = "elastic-user-service", url = "http://localhost:9091/dev/v1/elastic/user")
public interface ElasticUserManager {

    @PostMapping(ADD)
    ResponseEntity<Void> save(@RequestBody UserRequestDto dto);
}
