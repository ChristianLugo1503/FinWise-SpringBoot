package com.finanzas_personales.FinanzasPersonales.Services;

import com.finanzas_personales.FinanzasPersonales.Models.CategoriesModel;
import com.finanzas_personales.FinanzasPersonales.Models.RecurringPaymentModel;
import com.finanzas_personales.FinanzasPersonales.Models.UserModel;
import com.finanzas_personales.FinanzasPersonales.Repositories.ICategoriesRepository;
import com.finanzas_personales.FinanzasPersonales.Repositories.IRecurringPaymentsRepository;
import com.finanzas_personales.FinanzasPersonales.Repositories.IUserRepository;
import com.finanzas_personales.FinanzasPersonales.dto.RecurringPaymentDto;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RecurringPaymentService {
    @Autowired
    private IRecurringPaymentsRepository recurringPaymentsRepository;

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private ICategoriesRepository categoriesRepository;

    //GET RECURRING PAYMENTS BY USER ID
    public List<RecurringPaymentModel> getRecurringPaymentsByUserId(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new RuntimeException("User not found");
        }
        return recurringPaymentsRepository.findByUserId(userId);
    }

    public RecurringPaymentModel createRecurringPayment(RecurringPaymentDto dto){
        // Mapeo manual de DTO a modelo
        RecurringPaymentModel recurringPayment = new RecurringPaymentModel();

        recurringPayment.setUser(userRepository.findById(dto.getUser()).orElseThrow(
                () -> new EntityNotFoundException("Usuario no encontrado")
        ));
        recurringPayment.setName(dto.getName());
        recurringPayment.setFrequency(dto.getFrequency());
        recurringPayment.setReminderTime(dto.getReminderTime());
        recurringPayment.setComment(dto.getComment());
        recurringPayment.setAmount(dto.getAmount());
        recurringPayment.setStatus(dto.getStatus());
        recurringPayment.setType(dto.getType());
        recurringPayment.setCreatedAt(dto.getCreatedAt());
        recurringPayment.setUpdatedAt(dto.getUpdatedAt());
        recurringPayment.setCategoryID(categoriesRepository.findById(dto.getCategoryID()).orElseThrow(
                () -> new EntityNotFoundException("Categoría no encontrada")
        ));

        // Guardar y devolver el modelo persistido
        return recurringPaymentsRepository.save(recurringPayment);
    }

    //Eliminar pago recurrente
    public void deleteRecurringPaymentByID(Long id){
        recurringPaymentsRepository.deleteById(id);
    }

    public RecurringPaymentModel updateRecurringPayment(Long id, RecurringPaymentDto dto){
        // Verificar si el pago recurrente existe
        RecurringPaymentModel recurringPayment = recurringPaymentsRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Pago recurrente no encontrado"));
        // Mapeo manual de DTO a modelo

        recurringPayment.setUser(userRepository.findById(dto.getUser()).orElseThrow(
                () -> new EntityNotFoundException("Usuario no encontrado")
        ));
        recurringPayment.setName(dto.getName());
        recurringPayment.setFrequency(dto.getFrequency());
        recurringPayment.setReminderTime(dto.getReminderTime());
        recurringPayment.setComment(dto.getComment());
        recurringPayment.setAmount(dto.getAmount());
        recurringPayment.setStatus(dto.getStatus());
        recurringPayment.setType(dto.getType());
        recurringPayment.setCreatedAt(dto.getCreatedAt());
        recurringPayment.setUpdatedAt(dto.getUpdatedAt());
        recurringPayment.setCategoryID(categoriesRepository.findById(dto.getCategoryID()).orElseThrow(
                () -> new EntityNotFoundException("Categoría no encontrada")
        ));

        // Guardar y devolver el modelo persistido
        return recurringPaymentsRepository.save(recurringPayment);
    }

    // Cambiar status de un pago recurrente por ID
    public void updateRecurringPaymentStatus(Long id, Boolean newStatus) {
        // Verificar si el pago recurrente existe
        RecurringPaymentModel recurringPayment = recurringPaymentsRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Pago recurrente no encontrado"));

        // Actualizar el estado
        recurringPayment.setStatus(newStatus);

        // Guardar los cambios en la base de datos
        recurringPaymentsRepository.save(recurringPayment);
    }

}
