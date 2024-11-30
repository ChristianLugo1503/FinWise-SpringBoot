package com.finanzas_personales.FinanzasPersonales.Controllers;

import com.finanzas_personales.FinanzasPersonales.Models.TransactionsModel;
import com.finanzas_personales.FinanzasPersonales.Services.TransactionsSevice;
import com.finanzas_personales.FinanzasPersonales.dto.TransactionsDto;
import org.springframework.beans.factory.annotation.Autowired;
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
}
