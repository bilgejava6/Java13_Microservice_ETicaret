package com.muhammet.service;

import com.muhammet.domain.User;
import com.muhammet.dto.request.UserSaveRequestDto;
import com.muhammet.dto.request.UserUpdateRequestDto;
import com.muhammet.exception.ErrorType;
import com.muhammet.exception.UserServiceException;
import com.muhammet.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
    private final UserRepository userRepository;
    private final CacheManager cacheManager;
    public void save(UserSaveRequestDto dto){
        userRepository.save(User.builder()
                        .userName(dto.getUserName())
                        .email(dto.getEmail())
                        .authId(dto.getAuthId())
                .build());
        /**
         * Bu işlem exception fırlatabilir, bu nedenle ya try..catch yaparsınız
         * ya da Object null kontrolu yaparsınız.
         */
        Objects.requireNonNull(cacheManager.getCache("user-find-all")).clear();
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
        Objects.requireNonNull(cacheManager.getCache("user-find-all")).clear();
    }

    @Cacheable(value = "user-find-all")
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Cacheable(value = "get-string")
    public String getString(String ad){
        String createString = ad.toUpperCase().concat(" - Hoş geldiniz");
        /**
         * DİKKAT!!!
         * Aşağıya yazılan kod bloğu bir işlemin uzun sürmesi durumunu simüle etmek
         * için eklenmiştir.
         */
        try{
            Thread.sleep(3000L);
        }catch (InterruptedException exception){
            log.error("Beklenmeyen thread hatası");
        }
        return createString;
    }

}
