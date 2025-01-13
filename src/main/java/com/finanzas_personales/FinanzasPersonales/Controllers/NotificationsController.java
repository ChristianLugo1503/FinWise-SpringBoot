package com.finanzas_personales.FinanzasPersonales.Controllers;

import com.finanzas_personales.FinanzasPersonales.Models.NotificationsModel;
import com.finanzas_personales.FinanzasPersonales.Models.SavingsGoalModel;
import com.finanzas_personales.FinanzasPersonales.Services.NotificationService;
import com.finanzas_personales.FinanzasPersonales.dto.NotificationDto;
import com.finanzas_personales.FinanzasPersonales.dto.SavingGoalDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/notifications")
public class NotificationsController {
    @Autowired
    private NotificationService notificationService;

    @PostMapping("/create")
    public NotificationsModel createNotification(@RequestBody NotificationDto notificationDto) {
        return notificationService.createNotification(notificationDto);
    }

    @GetMapping("/user/{userID}")
    public ResponseEntity<?> getNotificationsByUserId(@PathVariable Long userID) {
        try {
            List<NotificationsModel> notifications = notificationService.getNotificationsByUserId(userID);
            return ResponseEntity.ok(notifications);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }

    @DeleteMapping("/delete/{id}")
    public void deleteSavingsGoalById(@PathVariable Long id) {
        notificationService.deleteNotificationById(id);
    }

    @PutMapping("/readStatus/{id}")
    public void updateReadStatus(@PathVariable Long id, @RequestBody Boolean newStatus) {
        notificationService.updateNotificationReadStatus(id, newStatus);
    }

}
