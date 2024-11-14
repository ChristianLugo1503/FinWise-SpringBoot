package com.finanzas_personales.FinanzasPersonales.Repositories;

import com.finanzas_personales.FinanzasPersonales.Models.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepository implements IUserRepository{

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public UserModel findByEmail(String email) {
        String SQL = "SELECT * FROM users WHERE email = ?";
        return jdbcTemplate.queryForObject(SQL, new Object[]{email},
                new BeanPropertyRowMapper<>(UserModel.class));
    }
}