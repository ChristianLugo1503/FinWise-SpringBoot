package com.finanzas_personales.FinanzasPersonales.Models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "categories")
@Data // Generar automaticamente getters y setters
@AllArgsConstructor //generar un constructor con argumentos
@NoArgsConstructor //generar un constructor sin argumentos
public class CategoriesModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String type;

    // Almacena el contenido de la imagen como BLOB
    @Lob
    @Column(columnDefinition = "LONGBLOB")
    private byte[] image;

    @ManyToOne // Muchos a uno
    @JoinColumn(name = "user_id", nullable = false)
    private UserModel userId;

    // Constructor sin el campo ID
    public CategoriesModel(String name, String type, byte[] image, UserModel user) {
        this.name = name;
        this.type = type;
        this.image = image;
        this.userId = user;
    }
};
