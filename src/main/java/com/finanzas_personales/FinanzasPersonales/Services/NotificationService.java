package com.finanzas_personales.FinanzasPersonales.Services;

import com.finanzas_personales.FinanzasPersonales.ENUMS.statusENUM;
import com.finanzas_personales.FinanzasPersonales.Models.NotificationsModel;
import com.finanzas_personales.FinanzasPersonales.Models.SavingsGoalModel;
import com.finanzas_personales.FinanzasPersonales.Repositories.INotificationRepository;
import com.finanzas_personales.FinanzasPersonales.Repositories.IUserRepository;
import com.finanzas_personales.FinanzasPersonales.dto.NotificationDto;
import com.finanzas_personales.FinanzasPersonales.dto.SavingGoalDto;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.management.Notification;
import java.time.LocalDateTime;
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

    // Crear una meta de ahorro
    // Crear una notificación
    public NotificationsModel createNotification(NotificationDto dto) {
        // Mapeo manual de DTO a modelo
        NotificationsModel notifications = new NotificationsModel();

        // Establecer los datos del usuario
        notifications.setUser(userRepository.findById(dto.getUser()).orElseThrow(
                () -> new EntityNotFoundException("Usuario no encontrado")
        ));
        notifications.setType(dto.getType());
        notifications.setMessage(dto.getMessage());
        notifications.setCreationDate(LocalDateTime.now());
        notifications.setReadStatus(false);
        notifications.setRequiresResponse(false);
        notifications.setResponseStatus(statusENUM.Pendiente);

        return NotificationRepository.save(notifications);
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
