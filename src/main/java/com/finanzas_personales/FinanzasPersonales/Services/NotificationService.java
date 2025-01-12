package com.finanzas_personales.FinanzasPersonales.Services;

import com.finanzas_personales.FinanzasPersonales.Models.NotificationsModel;
import com.finanzas_personales.FinanzasPersonales.Models.SavingsGoalModel;
import com.finanzas_personales.FinanzasPersonales.Repositories.INotificationRepository;
import com.finanzas_personales.FinanzasPersonales.Repositories.IUserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.management.Notification;
import java.util.List;

@Service
public class NotificationService {
    @Autowired
    private INotificationRepository NotificationRepository;

    @Autowired
    private IUserRepository userRepository;

    //Obtener notificaciones por id de usuario
    public List<NotificationsModel> getNotificationsByUserId(Long userId){
        if (!userRepository.existsById(userId)) {
            throw new RuntimeException("Usuario no encontrado");
        }
        return  NotificationRepository.findByUserId(userId);
    }

    // Eliminar una notificacion
    public void deleteNotificationById(Long id) {
        if (!NotificationRepository.existsById(id)) {
            throw new EntityNotFoundException("Meta de ahorro no encontrada");
        }
        NotificationRepository.deleteById(id);
    }

    // Cambiar el estado leido a una notificacion
    public void updateNotificationReadStatus(Long id, Boolean newStatus) {
        NotificationsModel notification = NotificationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Notificacion no encontrada"));

        // Actualizar el estado
        notification.setReadStatus(newStatus);

        // Guardar los cambios en la base de datos
        NotificationRepository.save(notification);
    }
}
