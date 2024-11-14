package com.finanzas_personales.FinanzasPersonales.Repositories;


import com.finanzas_personales.FinanzasPersonales.Models.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IUserRepository {
    public UserModel findByEmail(String email);
}
