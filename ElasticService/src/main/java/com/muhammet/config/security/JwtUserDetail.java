package com.muhammet.config.security;

import com.muhammet.domain.User;
import com.muhammet.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class JwtUserDetail implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }
    /**
     * TBMM ye giriş yapcaksınız.
     * 1- Kimlik kartınızı(Nüfus Cüzdanı) uzatırsınız. (Spring uygulamamıza gönderidiğimiz Bearer Token)
     * 2- Yetkli kişi Kimlik kartında yazak TC numarasını okur.(token bilgisini alarak jwt kontrolü sağlarız.)
     * 3- yetkili TC kimlik numarasını sorgular.
     * 4- Herşey yolunda ise giriş yapacak olana içeride kullanması için kimlik tanımlamalıdır.
     * 5- Yetkili İçeride kişinin rahat gezebileceği belirli yetkileri olan geçici bir giriş kartı verir.
     */

    public UserDetails getUserByAuthId(Long authId){
        Optional<User> authUser = userRepository.findOptionalByAuthId(authId);
        if(authUser.isEmpty()) return null;
        List<GrantedAuthority> yetkiListesi = new ArrayList<>();
        yetkiListesi.add(new SimpleGrantedAuthority("ROLE_USER"));
        yetkiListesi.add(new SimpleGrantedAuthority("ADMIN"));
        yetkiListesi.add(new SimpleGrantedAuthority("AHMET_AMCA"));
        yetkiListesi.add(new SimpleGrantedAuthority("NABER_NASILSIN"));


        return org.springframework.security.core.userdetails.User.builder()
                .username(authUser.get().getUserName())
                .password("")
                .accountLocked(false)
                .accountExpired(false)
                .authorities(yetkiListesi) // kullanıcının yetkilerini yazıyoruz.
                .build();


    }
}
