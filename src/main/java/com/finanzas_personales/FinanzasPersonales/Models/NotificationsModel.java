package com.finanzas_personales.FinanzasPersonales.Models;

import com.finanzas_personales.FinanzasPersonales.ENUMS.notificationTypeENUM;
import com.finanzas_personales.FinanzasPersonales.ENUMS.statusENUM;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity(name = "notifications")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class NotificationsModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserModel user;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private notificationTypeENUM type;

    @Lob
    @Column(name="message", length=512)
    private String message;

    @Column(name = "creation_date", nullable = false)
    private LocalDateTime creationDate;

    @Column(name = "read_status", nullable = false)
    private Boolean readStatus;

    @Column(name = "requires_response", nullable = false)
    private Boolean requiresResponse;

    @Enumerated(EnumType.STRING)
    private statusENUM responseStatus;

    @ManyToOne
    @JoinColumn(name = "contribution_id")
    private GroupContributionsModel contribution;
}
