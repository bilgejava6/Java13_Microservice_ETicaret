package com.muhammet.controller;


import com.muhammet.dto.request.UserSaveRequestDto;
import com.muhammet.dto.request.UserUpdateRequestDto;
import com.muhammet.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
}
