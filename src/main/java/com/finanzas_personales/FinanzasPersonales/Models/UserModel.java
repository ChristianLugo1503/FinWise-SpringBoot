package com.finanzas_personales.FinanzasPersonales.Models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;

@Entity(name = "users")
@Data //Lombok genera automáticamente getters y setters y constructores
@AllArgsConstructor //genera un constructor que acepta un argumento para cada campo de la clase.
@NoArgsConstructor //genera automáticamente un constructor sin argumentos
public class UserModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String lastname;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column
    private String role = "admin";


}

