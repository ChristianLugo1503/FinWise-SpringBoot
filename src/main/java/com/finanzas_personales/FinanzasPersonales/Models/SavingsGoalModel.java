package com.finanzas_personales.FinanzasPersonales.Models;

import com.finanzas_personales.FinanzasPersonales.ENUMS.frequencyENUM;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.catalina.User;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity(name = "savings_goals")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SavingsGoalModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserModel user;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(name = "goal_amount", nullable = false, precision = 10, scale = 2)
    private BigDecimal goalAmount;

    @Column(name = "saved_amount", nullable = false, precision = 10, scale = 2)
    private BigDecimal savedAmount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private frequencyENUM frequency;

    @Column(name = "periodic_target", precision = 10, scale = 2)
    private BigDecimal periodicTarget;

    private String comment;

    @Column(name = "goal_time", nullable = false)
    private LocalTime goalTime;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
}
