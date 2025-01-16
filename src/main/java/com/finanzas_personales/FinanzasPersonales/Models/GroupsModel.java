package com.finanzas_personales.FinanzasPersonales.Models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity(name = "user_groups")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GroupsModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column()
    private String description;

    @Column(name = "goal_amount", nullable = false)
    private BigDecimal goalAmount;

    @Column(name = "saved_amount", nullable = false, precision = 10, scale = 2)
    private BigDecimal savedAmount;

    @ManyToOne //muchos a uno
    @JoinColumn(name = "created_by", nullable = true)
    private UserModel createdBy;

    @Column(name = "created_at", updatable = false)
    @org.hibernate.annotations.CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    @org.hibernate.annotations.UpdateTimestamp
    private LocalDateTime updatedAt;
}
