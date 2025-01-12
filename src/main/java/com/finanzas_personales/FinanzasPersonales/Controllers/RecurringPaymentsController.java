package com.finanzas_personales.FinanzasPersonales.Controllers;

import com.finanzas_personales.FinanzasPersonales.Models.CategoriesModel;
import com.finanzas_personales.FinanzasPersonales.Models.RecurringPaymentModel;
import com.finanzas_personales.FinanzasPersonales.Services.RecurringPaymentService;
import com.finanzas_personales.FinanzasPersonales.dto.RecurringPaymentDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/recurringPayments")
public class RecurringPaymentsController {

    @Autowired
    private RecurringPaymentService recurringPaymentService;

    @GetMapping("/user/{userID}")
    public ResponseEntity<?> getRecurringPaymentsByUserId(@PathVariable Long userID) {
        try {
            List<RecurringPaymentModel> recurringPayments = recurringPaymentService.getRecurringPaymentsByUserId(userID);
            return ResponseEntity.ok(recurringPayments);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }

    @PostMapping("/create")
    public  RecurringPaymentModel createRecurringPayment(@RequestBody RecurringPaymentDto recurringPaymentDto){
        return recurringPaymentService.createRecurringPayment(recurringPaymentDto);
    }

    @DeleteMapping("delete/{id}")
    public void deleteRecurringPaymentByID(@PathVariable Long id){
        recurringPaymentService.deleteRecurringPaymentByID(id);
    }

    @PutMapping("update/{id}")
    public void updateRecurringPayment(@PathVariable Long id, @RequestBody RecurringPaymentDto recurringPaymentDto) {
        recurringPaymentService.updateRecurringPayment(id, recurringPaymentDto);
    }

    @PutMapping("status/{id}")
    public void updateStatus(@PathVariable Long id, @RequestBody Boolean newStatus) {
        recurringPaymentService.updateRecurringPaymentStatus(id, newStatus);
    }

}
