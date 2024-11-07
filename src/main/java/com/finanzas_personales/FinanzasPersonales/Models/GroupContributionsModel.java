package com.finanzas_personales.FinanzasPersonales.Models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity(name = "group_contributions")
@Data //genera automaticamente getters y setters
@AllArgsConstructor
@NoArgsConstructor
public class GroupContributionsModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne //Un grupo puede tener muchas contribuciones pero cada contribución pertence a un grupo.
    @JoinColumn(name = "group_id", nullable = false)
    private GroupsModel groupId;

    @ManyToOne //Un usuario puede hacer muchas contribuciones pero cada contrubucion pertenece a un usuario.
    @JoinColumn(name = "user_id", nullable = false)
    private UserModel userId;

    @Column(name = "amount", nullable = false)
    private BigDecimal amout;

    @Column(name = "date", updatable = false)
    @org.hibernate.annotations.CreationTimestamp
    private LocalDateTime date;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private enumStatus status;

    @Column(name = "note", length = 255)
    private String note;

    @Column(name = "created_at", updatable = false)
    @org.hibernate.annotations.CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    @org.hibernate.annotations.UpdateTimestamp
    private LocalDateTime updatedAt;
}
