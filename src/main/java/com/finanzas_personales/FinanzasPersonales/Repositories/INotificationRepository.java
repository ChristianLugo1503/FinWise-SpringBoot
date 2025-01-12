package com.finanzas_personales.FinanzasPersonales.Repositories;

import com.finanzas_personales.FinanzasPersonales.Models.NotificationsModel;
import com.finanzas_personales.FinanzasPersonales.Models.SavingsGoalModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface INotificationRepository extends JpaRepository<NotificationsModel, Long> {
    List<NotificationsModel> findByUserId(@Param("userId") Long userId);
}
