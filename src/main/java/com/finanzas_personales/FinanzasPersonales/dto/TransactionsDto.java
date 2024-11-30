package com.finanzas_personales.FinanzasPersonales.dto;

import com.finanzas_personales.FinanzasPersonales.ENUMS.typeENUM;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class TransactionsDto {
    private Long userId;
    private Long categoryID;
    private BigDecimal amount;
    private Date date;
    private String description;
    private typeENUM type;
}
