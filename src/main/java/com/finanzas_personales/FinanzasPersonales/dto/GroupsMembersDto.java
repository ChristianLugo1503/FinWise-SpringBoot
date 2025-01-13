package com.finanzas_personales.FinanzasPersonales.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class GroupsMembersDto {
    private Long groupId;
    private Long userId;
}
