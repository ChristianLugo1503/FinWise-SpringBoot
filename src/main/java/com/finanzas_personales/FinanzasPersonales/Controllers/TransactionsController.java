package com.finanzas_personales.FinanzasPersonales.Controllers;

import com.finanzas_personales.FinanzasPersonales.Models.TransactionsModel;
import com.finanzas_personales.FinanzasPersonales.Services.TransactionsSevice;
import com.finanzas_personales.FinanzasPersonales.dto.TransactionsDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/transactions")
public class TransactionsController {
    @Autowired
    private TransactionsSevice transactionsSevice;

    @PostMapping("/create")
    public TransactionsModel createTransaction(@RequestBody TransactionsDto transactionsDto){
        return transactionsSevice.createTransaction(transactionsDto);
    }

    @GetMapping("/user/{userID}")
    public List<TransactionsModel> getTransactionsByUserId(@PathVariable Long userID){
        return transactionsSevice.getTransactionsByUserID(userID);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteTransactionsById(@PathVariable Long id){
        transactionsSevice.deleteTransactionById(id);
    }

    // Actualizar una transacción existente
    @PutMapping("/update/{id}")
    public ResponseEntity<TransactionsModel> updateTransaction(@PathVariable Long id, @RequestBody TransactionsDto dto) {
        TransactionsModel updatedTransaction = transactionsSevice.updateTransaction(id, dto);
        return ResponseEntity.ok(updatedTransaction);
    }
}
