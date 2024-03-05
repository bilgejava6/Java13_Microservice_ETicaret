package com.muhammet.controller;

import com.muhammet.domain.User;
import com.muhammet.dto.request.UserRequestDto;
import com.muhammet.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


import static com.muhammet.constants.RestApiUrls.*;
@RequiredArgsConstructor
@RestController
@RequestMapping(USER)
public class UserController {
    private final UserService userService;
    @PostMapping(ADD)
    @CrossOrigin("*")
    public ResponseEntity<Void> save(@RequestBody UserRequestDto dto){
        userService.save(dto);
        return ResponseEntity.ok().build();
    }


    @PostMapping(UPDATE)
    @CrossOrigin("*")
    public ResponseEntity<Void> update(@RequestBody UserRequestDto dto){
        userService.update(dto);
        return ResponseEntity.ok().build();
    }

    @GetMapping(GET_ALL)
    @PreAuthorize("hasAnyAuthority('ADMIN','ALI_DAYI','SUPER_USER')")
    public ResponseEntity<Iterable<User>> getAll(){
        return  ResponseEntity.ok(userService.getAll());
    }
}
