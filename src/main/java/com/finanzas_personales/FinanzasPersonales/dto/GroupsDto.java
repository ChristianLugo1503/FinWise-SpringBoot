package com.finanzas_personales.FinanzasPersonales.dto;

import com.finanzas_personales.FinanzasPersonales.Models.UserModel;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class GroupsDto {
    private String name;
    private String description;
    private BigDecimal goalAmount;
    private BigDecimal savedAmount;
    private Long createdBy;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
