package com.finanzas_personales.FinanzasPersonales.dto;

import lombok.Data;

@Data
public class AuthResponseDto {
    String token;
    String refreshToken;
}