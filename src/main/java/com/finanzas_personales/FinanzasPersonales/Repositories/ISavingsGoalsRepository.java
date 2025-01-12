package com.finanzas_personales.FinanzasPersonales.Repositories;

import com.finanzas_personales.FinanzasPersonales.Models.SavingsGoalModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ISavingsGoalsRepository extends JpaRepository<SavingsGoalModel, Long> {
    List<SavingsGoalModel> findByUserId(@Param("userId") Long userId);
}
