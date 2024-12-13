package com.finanzas_personales.FinanzasPersonales.Services;

import com.finanzas_personales.FinanzasPersonales.ENUMS.typeENUM;
import com.finanzas_personales.FinanzasPersonales.Models.TransactionsModel;
import com.finanzas_personales.FinanzasPersonales.Models.UserModel;
import com.finanzas_personales.FinanzasPersonales.Repositories.ICategoriesRepository;
import com.finanzas_personales.FinanzasPersonales.Repositories.ITransactionsRepository;
import com.finanzas_personales.FinanzasPersonales.Repositories.IUserRepository;
import com.finanzas_personales.FinanzasPersonales.dto.TransactionsDto;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.yaml.snakeyaml.events.Event;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class TransactionsSevice {
    @Autowired
    private ITransactionsRepository transactionsRepository;

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private ICategoriesRepository categoriesRepository;

    //Crear nueva Transaction
    public TransactionsModel createTransaction(TransactionsDto dto) {
        TransactionsModel transactionsModel = new TransactionsModel();
        transactionsModel.setUserId(userRepository.findById(dto.getUserId()).orElseThrow(
                () -> new EntityNotFoundException("Usuario no encontrado")
        ));
        transactionsModel.setCategoryID(categoriesRepository.findById(dto.getCategoryID()).orElseThrow(
                () -> new EntityNotFoundException("Categoría no encontrada")
        ));
        transactionsModel.setAmount(dto.getAmount());
        transactionsModel.setDate(dto.getDate());
        transactionsModel.setDescription(dto.getDescription());
        transactionsModel.setType(dto.getType());

        return transactionsRepository.save(transactionsModel);
    }

    //Eliminar transaccion por medio del id
    public void deleteTransactionById(Long id){
        transactionsRepository.deleteById(id);
    }

    //Optener todas las transacciones por medio del id del usuario
    public List<TransactionsModel> getTransactionsByUserID(Long userId){
        try {
            Optional<UserModel> userOpt = userRepository.findById(userId);

            if (userOpt.isEmpty()) {
                throw new RuntimeException("User not found");
            }

            UserModel user = userOpt.get();
            return transactionsRepository.findByUserId(user);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid user ID format");
        }
    }

    // Actualizar una transacción existente
    public TransactionsModel updateTransaction(Long id, TransactionsDto dto) {
        TransactionsModel existingTransaction = transactionsRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Transacción no encontrada")
        );

        existingTransaction.setCategoryID(categoriesRepository.findById(dto.getCategoryID()).orElseThrow(
                () -> new EntityNotFoundException("Categoría no encontrada")
        ));
        existingTransaction.setAmount(dto.getAmount());
        existingTransaction.setDate(dto.getDate());
        existingTransaction.setDescription(dto.getDescription());
        existingTransaction.setType(dto.getType());

        return transactionsRepository.save(existingTransaction);
    }
}
