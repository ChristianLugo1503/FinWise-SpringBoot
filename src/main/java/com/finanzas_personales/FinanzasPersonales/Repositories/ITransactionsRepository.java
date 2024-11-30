package com.finanzas_personales.FinanzasPersonales.Repositories;

import com.finanzas_personales.FinanzasPersonales.Models.CategoriesModel;
import com.finanzas_personales.FinanzasPersonales.Models.TransactionsModel;
import com.finanzas_personales.FinanzasPersonales.Models.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ITransactionsRepository extends JpaRepository<TransactionsModel, Long> {
    List<TransactionsModel> findByUserId(UserModel userId);
}
