package com.finanzas_personales.FinanzasPersonales.dto;

import com.finanzas_personales.FinanzasPersonales.ENUMS.frequencyENUM;
import com.finanzas_personales.FinanzasPersonales.ENUMS.typeENUM;
import com.finanzas_personales.FinanzasPersonales.Models.CategoriesModel;
import com.finanzas_personales.FinanzasPersonales.Models.UserModel;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
public class RecurringPaymentDto {
    private Long user;
    private String name;
    private frequencyENUM frequency;
    private LocalTime reminderTime;
    private String comment;
    private BigDecimal amount;
    private Boolean status;
    private typeENUM type;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Long categoryID;
}
