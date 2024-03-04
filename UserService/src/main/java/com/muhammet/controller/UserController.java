package com.muhammet.controller;


import com.muhammet.domain.User;
import com.muhammet.dto.request.DefaultRequestDto;
import com.muhammet.dto.request.UserSaveRequestDto;
import com.muhammet.dto.request.UserUpdateRequestDto;
import com.muhammet.exception.ErrorType;
import com.muhammet.exception.UserServiceException;
import com.muhammet.service.UserService;
import com.muhammet.utility.JwtTokenManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static com.muhammet.constants.RestApiUrls.*;

/**
 * http://localhost:9094/dev/v1/user
 */
@RestController
@RequestMapping(USER)
@RequiredArgsConstructor
/**
 * Loglama işlemleri için kullanıyoruzç
 */
@Slf4j
public class UserController {
    private final UserService userService;
    private final JwtTokenManager jwtTokenManager;

    @Value("${userservice.deger2}")
    private String deger2;
    @Value("${userservice.listem.iki}")
    private String iki;
    @GetMapping("/get-application-properties")
    public String getApplicationProperties(){
        log.trace("Properties Bilgisi...: "+ iki);
        log.debug("Properties Bilgisi...: "+ iki);
        log.info("Properties Bilgisi...: "+ iki);
        log.warn("Properties Bilgisi...: "+ iki);
        log.error("Properties Bilgisi...: "+ iki);


        System.out.println("Console çıktı...: "+ iki);
        return deger2;
    }

    /**
     * /add
     * @param dto
     * @return
     */
    @PostMapping(ADD)
    public ResponseEntity<Void> save(@RequestBody UserSaveRequestDto dto){
        userService.save(dto);
        return ResponseEntity.ok().build();
    }

    @PutMapping(UPDATE)
    public ResponseEntity<Void> update(UserUpdateRequestDto dto){
        userService.update(dto);
        return ResponseEntity.ok().build();
    }

    @GetMapping(GET_ALL)
    public ResponseEntity<List<User>> getAll(DefaultRequestDto dto){
        Optional<Long> authId = jwtTokenManager.validateToken(dto.getToken());
        if (authId.isEmpty())
            throw new UserServiceException(ErrorType.INVALID_TOKEN_ERROR);
        return ResponseEntity.ok(userService.findAll());
    }


    @GetMapping("/get-string")
    public ResponseEntity<String> getString(String ad){
        return ResponseEntity.ok(userService.getString(ad));
    }


    @GetMapping("/get-all-by-name")
    public ResponseEntity<Page<User>> getAllByName(String userName, int page, int size, String sortParameter, String sortDirection){
        return ResponseEntity.ok(userService.findAllByUserName(userName, page, size, sortParameter, sortDirection));
    }

    @GetMapping("/get-service-name")
    public ResponseEntity<String> getServiceName(){
        return ResponseEntity.ok("UserService is running...");
    }
}
