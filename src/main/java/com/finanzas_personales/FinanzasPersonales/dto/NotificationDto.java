package com.finanzas_personales.FinanzasPersonales.dto;

import com.finanzas_personales.FinanzasPersonales.ENUMS.notificationTypeENUM;
import com.finanzas_personales.FinanzasPersonales.ENUMS.statusENUM;
import com.finanzas_personales.FinanzasPersonales.Models.UserModel;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class NotificationDto {
    private Long user;
    private notificationTypeENUM type;
    private String message;
}