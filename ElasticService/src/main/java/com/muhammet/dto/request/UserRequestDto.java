package com.muhammet.dto.request;

public record UserRequestDto(
        String id,
        Long authId,
        String userName,
        String email,
        String photo,
        String name,
        String phone,
        String about
) {
}
