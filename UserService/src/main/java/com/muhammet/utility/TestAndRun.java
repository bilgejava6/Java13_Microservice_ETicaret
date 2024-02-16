package com.muhammet.utility;

import com.muhammet.dto.request.UserRequestDto;
import com.muhammet.manager.ElasticUserManager;
import com.muhammet.service.UserService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TestAndRun {

    private final UserService userService;
    private final ElasticUserManager userManager;

    //@PostConstruct
    public void start(){
        userService.findAll().forEach(user->{
            UserRequestDto dto = new UserRequestDto(
                    user.getId(),
                    user.getAuthId(),
                    user.getUserName(),
                    user.getEmail(),
                    user.getPhoto(),
                    user.getName(),
                    user.getPhone(),
                    user.getAbout()
            );
            userManager.save(dto);
        });

    }

}
