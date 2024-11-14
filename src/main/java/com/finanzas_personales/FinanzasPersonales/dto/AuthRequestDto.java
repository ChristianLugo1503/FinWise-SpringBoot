package com.finanzas_personales.FinanzasPersonales.dto;
import lombok.Data;

@Data
public class AuthRequestDto {
    String email;
    String password;
}