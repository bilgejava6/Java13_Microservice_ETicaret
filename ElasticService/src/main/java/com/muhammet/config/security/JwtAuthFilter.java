package com.muhammet.config.security;

import com.muhammet.utility.JwtTokenManager;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Objects;
import java.util.Optional;

@Slf4j
public class JwtAuthFilter extends OncePerRequestFilter {
    @Autowired
    private JwtTokenManager jwtTokenManager;
    @Autowired
    private JwtUserDetail userDetail;
    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {
        /**
         * Tüm istekler buraya düşecek ve burada auth kontrol yapılacak.
         */
        final String authHeader = request.getHeader("Authorization");
        /**
         * Bearer [token]
         * Burada yapılacak ilk işlem token bilgisi alınır ve authHeader kontrol edilir.
         */
        log.info("Genel token....: "+ authHeader);
        log.info("Tüm istekler buraya düşecek ve burada auth kontrol yapılacak.");
        if(Objects.nonNull(authHeader) && authHeader.startsWith("Bearer ")){
            /**
             * Gelen auth bilgisin içinden "Bearer token" bilgisi alınır. Bu String içinden
             * sadece token bilgisini almaka için substring ile işlem yaparız.
             */
            String token = authHeader.substring(7);
            log.info("Token: "+ token);
            /**
             * token bilgisi geçerli mi değil mi kontrol ederiz.
             */
            Optional<Long> authId = jwtTokenManager.validateToken(token);
            if(authId.isPresent()){
                UserDetails userDetails = userDetail.getUserByAuthId(authId.get());
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                  userDetails,null,userDetails.getAuthorities()
                );
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }

        filterChain.doFilter(request,response);
    }
}
