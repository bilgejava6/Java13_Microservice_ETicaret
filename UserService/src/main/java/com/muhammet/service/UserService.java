package com.muhammet.service;

import com.muhammet.domain.User;
import com.muhammet.dto.request.UserSaveRequestDto;
import com.muhammet.dto.request.UserUpdateRequestDto;
import com.muhammet.exception.ErrorType;
import com.muhammet.exception.UserServiceException;
import com.muhammet.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public void save(UserSaveRequestDto dto){
        userRepository.save(User.builder()
                        .userName(dto.getUserName())
                        .email(dto.getEmail())
                        .authId(dto.getAuthId())
                .build());
    }

    public void update(UserUpdateRequestDto dto) {
        /**
         * dto içinde gelen user id bilgisi ile repository de parametre geçerek,
         * bu id ye sahip bir kullacı olup olmadığı bilgisini çektik.
         *
         */
        Optional<User> user = userRepository.findById(dto.getId());
        /**
         * Eğer id si verilen kullanıcı bulunamaz ise hata furlatırız.
         */
        if(user.isEmpty())
            throw new UserServiceException(ErrorType.ERROR_INVALID_USER_ID);
        /**
         * Herşey yolunda ise kullanıcıyı ncelikle optional içinde çıkartırız
         * ve dto içinden gelen parametreleri güncellenecek kullanıcı bilgileri
         * ile değiştiririz.
         */
        User updateUser = user.get();
        updateUser.setAbout(dto.getAbout());
        updateUser.setName(dto.getName());
        updateUser.setPhone(dto.getPhone());
        updateUser.setPhoto(dto.getPhoto());
        userRepository.save(updateUser);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }
}
