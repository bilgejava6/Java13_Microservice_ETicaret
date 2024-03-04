package com.muhammet.controller;

import com.muhammet.dto.request.LoginRequestDto;
import com.muhammet.dto.request.RegisterRequestDto;
import com.muhammet.entity.Auth;
import com.muhammet.exception.AuthServiceException;
import com.muhammet.exception.ErrorType;
import com.muhammet.service.AuthService;
import com.muhammet.utility.JwtTokenManager;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

import static com.muhammet.constants.RestApiUrls.*;

@RestController
@RequestMapping(AUTH)
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    private final JwtTokenManager jwtTokenManager;

    @PostMapping(REGISTER)
    @CrossOrigin("*")
    public ResponseEntity<Boolean> register(@RequestBody @Valid RegisterRequestDto dto){
        return ResponseEntity.ok(authService.register(dto));
    }

    @PostMapping(LOGIN)
    @CrossOrigin("*")
    public ResponseEntity<String> doLogin(@RequestBody @Valid LoginRequestDto dto){
        Optional<Auth> auth = authService.doLogin(dto);
        if (auth.isEmpty())
            throw new AuthServiceException(ErrorType.ERROR_INVALID_LOGIN_PARAMETER);
        Optional<String> token = jwtTokenManager.createToken(auth.get().getId());
        if (token.isEmpty())
            throw new AuthServiceException(ErrorType.ERROR_CREATE_TOKEN);
        return ResponseEntity.ok(token.get());
    }

    @GetMapping("/get-service-name")
    public ResponseEntity<String> getServiceName(){
        return ResponseEntity.ok("AuthService is running...");
    }

}
