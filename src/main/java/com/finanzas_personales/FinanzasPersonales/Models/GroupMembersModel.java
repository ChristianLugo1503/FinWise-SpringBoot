package com.finanzas_personales.FinanzasPersonales.Models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity(name = "group_members")
@Data //generar automaticamente setters y getters
@AllArgsConstructor //generar constructor con todos los argumentos
@NoArgsConstructor //generar constructor sin argumentos
public class GroupMembersModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne //muchos miembros pueden ser de un grupo
    @JoinColumn(name = "group_id", nullable = false)
    private GroupsModel groupId;

    @ManyToOne // Un usuario puede ser miembro de muchos grupos
    @JoinColumn(name = "user_id", nullable = false)
    private UserModel userId;

    @Column(name = "joined_at", updatable = false)
    @org.hibernate.annotations.CreationTimestamp
    private LocalDateTime joinedAt;
}
