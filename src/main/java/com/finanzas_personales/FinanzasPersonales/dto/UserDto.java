package com.finanzas_personales.FinanzasPersonales.dto;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class UserDto {
    private String name;
    private String lastname;
    private String email;
    private String password;
    private String newPassword;
    private MultipartFile image;
}
