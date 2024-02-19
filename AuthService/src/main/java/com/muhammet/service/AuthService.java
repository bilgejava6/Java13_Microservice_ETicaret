package com.muhammet.service;

import com.muhammet.dto.request.LoginRequestDto;
import com.muhammet.dto.request.RegisterRequestDto;
import com.muhammet.dto.request.UserSaveRequestDto;
import com.muhammet.entity.Auth;
import com.muhammet.manager.UserProfileManager;
import com.muhammet.rabbitmq.model.CreateUserModel;
import com.muhammet.rabbitmq.producer.CreateUserProducer;
import com.muhammet.repository.AuthRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final AuthRepository authRepository;
    private final CreateUserProducer createUserProducer;
    public Boolean register(RegisterRequestDto dto){
        Auth auth = Auth.builder()
                .password(dto.getPassword())
                .email(dto.getEmail())
                .userName(dto.getUserName())
                .createAt(System.currentTimeMillis())
                .updateAt(System.currentTimeMillis())
                .isActive(true)
                .build();
        authRepository.save(auth);
        /**
         * Burada kullanıcıyı authDB ye kaydettikten sonra UserService e Profil oluşturması
         * için bilgilerini iletmemiz gereklidir.
         */
//        UserSaveRequestDto userSaveRequestDto = UserSaveRequestDto.builder()
//                .authId(auth.getId())
//                .userName(auth.getUserName())
//                .email(auth.getEmail())
//                .build();
//        manager.save(userSaveRequestDto);
        /**
         * DİKKAT!!!
         * Burada userService e kayıt işlemi FeignClient ile yapııyordu ancak bu işlem büyük riskler barındırır.
         * bu nedenle rabbitMQ ile sistemin sorunsuz ve kayıpsız bir şekilde ilerlemesini sağlıyoruz.
         */
        createUserProducer.sendMessage(CreateUserModel.builder()
                        .authId(auth.getId())
                        .email(auth.getEmail())
                        .userName(auth.getUserName())
                .build());
        return true;
    }

    public Optional<Auth> doLogin(LoginRequestDto dto){
        Optional<Auth> auth = authRepository.findOptionalByUserNameAndPassword(dto.getUserName(),dto.getPassword());
        return auth;
    }
}
