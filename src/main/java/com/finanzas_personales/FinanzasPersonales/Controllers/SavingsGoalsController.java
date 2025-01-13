package com.finanzas_personales.FinanzasPersonales.Controllers;

import com.finanzas_personales.FinanzasPersonales.Models.SavingsGoalModel;
import com.finanzas_personales.FinanzasPersonales.Services.SavingsGoalsService;
import com.finanzas_personales.FinanzasPersonales.dto.SavingGoalDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/savings")
public class SavingsGoalsController {
    @Autowired
    private SavingsGoalsService savingsGoalService;

    @GetMapping("/user/{userID}")
    public ResponseEntity<?> getSavingsGoalsByUserId(@PathVariable Long userID) {
        try {
            List<SavingsGoalModel> savingsGoals = savingsGoalService.getSavingsGoalsByUserId(userID);
            return ResponseEntity.ok(savingsGoals);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }

    @PostMapping("/create")
    public SavingsGoalModel createSavingsGoal(@RequestBody SavingGoalDto savingsGoalDto) {
        return savingsGoalService.createSavingGoal(savingsGoalDto);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteSavingsGoalById(@PathVariable Long id) {
        savingsGoalService.deleteSavingGoalById(id);
    }

    @PutMapping("/update/{id}")
    public void updateSavingsGoal(@PathVariable Long id, @RequestBody SavingGoalDto savingsGoalDto) {
        savingsGoalService.updateSavingGoal(id, savingsGoalDto);
    }

    @PutMapping("/status/{id}")
    public void updateSavingsGoalStatus(@PathVariable Long id, @RequestBody Boolean newStatus) {
        savingsGoalService.updateSavingGoalStatus(id, newStatus);
    }

    @PutMapping("/saved/{id}")
    public void updateSavedAmount(@PathVariable Long id, @RequestBody BigDecimal newAmount) {
        savingsGoalService.updateSavedAmount(id, newAmount);
    }
}
