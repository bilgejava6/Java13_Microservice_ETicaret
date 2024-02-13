package com.muhammet.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum ErrorType {

    INTERNAL_ERROR(1000, "Sunucuda beklenmeye hata oluştu, lütfen terar deneyiniz",HttpStatus.INTERNAL_SERVER_ERROR),
    INVALID_TOKEN_ERROR(1002, "Geçersiz Token bilgisi",HttpStatus.BAD_REQUEST),
    ERROR_DUPLICATE_USERNAME(2000,"Bu Kullanıcı adı zaten kayıtlıdır. Lütfen değiştirerek tekrar deneyiniz.",HttpStatus.BAD_REQUEST),
    ERROR_INVALID_LOGIN_PARAMETER(2001,"Kullanıcı adı ya da Şifre hatalıdır. Lütfen tekrar deneyiniz.t",HttpStatus.BAD_REQUEST),
    ERROR_INVALID_USER_ID(2003,"Kullanıcı id geçersiz. Böyle bir id li kullanıcı olmadığı için güncelleme yaplılamamıştır.",HttpStatus.BAD_REQUEST),

    BAD_REQUEST_ERROR(1001, "Girilen parametreler hatalıdır. Lütfen düzelterek tekrar deneyiniz", HttpStatus.BAD_REQUEST);

    int code;
    String message;
    HttpStatus httpStatus;
}
