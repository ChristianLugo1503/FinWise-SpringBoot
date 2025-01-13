package com.finanzas_personales.FinanzasPersonales.Services;

import com.finanzas_personales.FinanzasPersonales.Models.SavingsGoalModel;
import com.finanzas_personales.FinanzasPersonales.Repositories.ICategoriesRepository;
import com.finanzas_personales.FinanzasPersonales.Repositories.ISavingsGoalsRepository;
import com.finanzas_personales.FinanzasPersonales.Repositories.IUserRepository;
import com.finanzas_personales.FinanzasPersonales.dto.SavingGoalDto;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class SavingsGoalsService {

    @Autowired
    private ISavingsGoalsRepository savingsGoalsRepository;

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private ICategoriesRepository categoriesRepository;

    // Obtener metas de ahorro por ID de usuario
    public List<SavingsGoalModel> getSavingsGoalsByUserId(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new RuntimeException("Usuario no encontrado");
        }
        return savingsGoalsRepository.findByUserId(userId);
    }

    // Crear una meta de ahorro
    public SavingsGoalModel createSavingGoal(SavingGoalDto dto) {
        // Mapeo manual de DTO a modelo
        SavingsGoalModel savingGoal = new SavingsGoalModel();

        savingGoal.setUser(userRepository.findById(dto.getUser()).orElseThrow(
                () -> new EntityNotFoundException("Usuario no encontrado")
        ));
        savingGoal.setName(dto.getName());
        savingGoal.setGoalAmount(dto.getGoalAmount());
        savingGoal.setSavedAmount(dto.getSavedAmount());
        savingGoal.setFrequency(dto.getFrequency());
        savingGoal.setPeriodicTarget(dto.getPeriodicTarget());
        savingGoal.setComment(dto.getComment());
        savingGoal.setGoalTime(dto.getGoalTime());
        savingGoal.setCreatedAt(dto.getCreatedAt());
        savingGoal.setStatus(dto.getStatus());

        // Guardar y devolver el modelo persistido
        return savingsGoalsRepository.save(savingGoal);
    }

    // Eliminar una meta de ahorro por ID
    public void deleteSavingGoalById(Long id) {
        if (!savingsGoalsRepository.existsById(id)) {
            throw new EntityNotFoundException("Meta de ahorro no encontrada");
        }
        savingsGoalsRepository.deleteById(id);
    }

    // Actualizar una meta de ahorro
    public SavingsGoalModel updateSavingGoal(Long id, SavingGoalDto dto) {
        // Verificar si la meta de ahorro existe
        SavingsGoalModel savingGoal = savingsGoalsRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Meta de ahorro no encontrada"));

        // Actualizar los campos con los datos del DTO
        savingGoal.setUser(userRepository.findById(dto.getUser()).orElseThrow(
                () -> new EntityNotFoundException("Usuario no encontrado")
        ));
        savingGoal.setName(dto.getName());
        savingGoal.setGoalAmount(dto.getGoalAmount());
        savingGoal.setSavedAmount(dto.getSavedAmount());
        savingGoal.setFrequency(dto.getFrequency());
        savingGoal.setPeriodicTarget(dto.getPeriodicTarget());
        savingGoal.setComment(dto.getComment());
        savingGoal.setGoalTime(dto.getGoalTime());
        savingGoal.setCreatedAt(dto.getCreatedAt());

        // Guardar y devolver el modelo persistido
        return savingsGoalsRepository.save(savingGoal);
    }

    // Cambiar el estado o un atributo específico de una meta de ahorro
    public void updateSavingGoalStatus(Long id, Boolean newStatus) {
        // Verificar si la meta de ahorro existe
        SavingsGoalModel savingGoal = savingsGoalsRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Meta de ahorro no encontrada"));

        // Actualizar el estado
        savingGoal.setStatus(newStatus);

        // Guardar los cambios en la base de datos
        savingsGoalsRepository.save(savingGoal);
    }

    //Actualizar el dinero ahorrado
    public void updateSavedAmount(Long id, BigDecimal newAmount) {
        // Verificar si la meta de ahorro existe
        SavingsGoalModel savingGoal = savingsGoalsRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Meta de ahorro no encontrada"));

        // Actualizar el estado
        savingGoal.setSavedAmount(newAmount);

        // Guardar los cambios en la base de datos
        savingsGoalsRepository.save(savingGoal);
    }
}
