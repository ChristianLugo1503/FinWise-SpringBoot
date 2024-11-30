package com.finanzas_personales.FinanzasPersonales.Models;

import com.finanzas_personales.FinanzasPersonales.ENUMS.typeENUM;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Entity(name="transactions")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionsModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne // Muchos a uno
    @JoinColumn(name = "user_id", nullable = false)
    private UserModel userId;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false) // Aquí `nullable = false` significa que es obligatorio.
    private CategoriesModel categoryID;

    @Column
    private BigDecimal amount;

    @Column
    private Date date;

    @Column
    private String description;

    @Enumerated(EnumType.STRING)
    private typeENUM type;

}
