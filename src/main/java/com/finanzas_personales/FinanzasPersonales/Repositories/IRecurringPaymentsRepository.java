package com.finanzas_personales.FinanzasPersonales.Repositories;

import com.finanzas_personales.FinanzasPersonales.Models.CategoriesModel;
import com.finanzas_personales.FinanzasPersonales.Models.RecurringPaymentModel;
import com.finanzas_personales.FinanzasPersonales.Models.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalTime;
import java.util.List;

public interface IRecurringPaymentsRepository extends JpaRepository<RecurringPaymentModel, Long> {
    List<RecurringPaymentModel> findByUserId(@Param("userId") Long userId);
    List<RecurringPaymentModel> findAllByReminderTime(LocalTime reminderTime);
    @Query("SELECT r FROM recurring_payments r WHERE r.reminderTime = :reminderTime")
    List<RecurringPaymentModel> findByReminderTime(@Param("reminderTime") LocalTime reminderTime);

}
