package com.finanzas_personales.FinanzasPersonales.Models;

import com.finanzas_personales.FinanzasPersonales.ENUMS.typeENUM;
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
    private typeENUM type;

    // Almacena el contenido de la imagen como BLOB
    @Lob
    @Column(columnDefinition = "LONGBLOB")
    private byte[] image;

    @ManyToOne // Muchos a uno
    @JoinColumn(name = "user_id", nullable = false)
    private UserModel userId;

    @Column(nullable = false)
    private String color;

    // Constructor sin el campo ID
    public CategoriesModel(String name, typeENUM type, byte[] image, UserModel user, String color) {
        this.name = name;
        this.type = type;
        this.image = image;
        this.userId = user;
        this.color = color;
    }
};
