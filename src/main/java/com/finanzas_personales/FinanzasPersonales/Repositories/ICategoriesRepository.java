package com.finanzas_personales.FinanzasPersonales.Repositories;

import com.finanzas_personales.FinanzasPersonales.Models.CategoriesModel;
import com.finanzas_personales.FinanzasPersonales.Models.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ICategoriesRepository extends JpaRepository<CategoriesModel, Long> {
    List<CategoriesModel> findByType(String type);
    List<CategoriesModel> findByUserId(UserModel userId);
}
