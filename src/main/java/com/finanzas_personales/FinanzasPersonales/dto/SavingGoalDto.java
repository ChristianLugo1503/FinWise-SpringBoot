package com.finanzas_personales.FinanzasPersonales.dto;

import com.finanzas_personales.FinanzasPersonales.ENUMS.frequencyENUM;
import jakarta.persistence.Column;
import jakarta.persistence.Lob;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
public class SavingGoalDto {
    private Long user;
    private String name;
    private BigDecimal goalAmount;
    private BigDecimal savedAmount;
    private frequencyENUM frequency;
    private BigDecimal periodicTarget;
    private String comment;
    private LocalTime goalTime;
    private LocalDateTime createdAt;
    private Boolean status;
}
