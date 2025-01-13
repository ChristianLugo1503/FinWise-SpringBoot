package com.finanzas_personales.FinanzasPersonales.dto;

import com.finanzas_personales.FinanzasPersonales.ENUMS.statusENUM;
import com.finanzas_personales.FinanzasPersonales.Models.GroupsModel;
import com.finanzas_personales.FinanzasPersonales.Models.UserModel;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class GroupContributionsDto {
    private Long group;
    private Long user;
    private BigDecimal amount;
    private statusENUM status;
    private String note;
    private LocalDateTime createdAt;
}
