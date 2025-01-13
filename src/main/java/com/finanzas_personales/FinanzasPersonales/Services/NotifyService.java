package com.finanzas_personales.FinanzasPersonales.Services;

import com.finanzas_personales.FinanzasPersonales.ENUMS.notificationTypeENUM;
import com.finanzas_personales.FinanzasPersonales.Models.NotificationsModel;
import com.finanzas_personales.FinanzasPersonales.Models.RecurringPaymentModel;
import com.finanzas_personales.FinanzasPersonales.Models.SavingsGoalModel;
import com.finanzas_personales.FinanzasPersonales.Repositories.INotificationRepository;
import com.finanzas_personales.FinanzasPersonales.Repositories.IRecurringPaymentsRepository;
import com.finanzas_personales.FinanzasPersonales.Repositories.ISavingsGoalsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Random;

@Service
public class NotifyService {
    @Autowired
    private  IRecurringPaymentsRepository recurringPaymentRepository;
    @Autowired
    private  ISavingsGoalsRepository savingsGoalsRepository;
    @Autowired
    private  INotificationRepository notificationRepository;

   

    @Scheduled(fixedRate = 60000) // Ejecutar cada minuto
    public void createRecurringPaymentNotifications() {
        // Obtener la fecha y hora actuales
        LocalDateTime now = LocalDateTime.now();
        System.out.println("Ejecución de tarea programada: " + now);

        try {
            // Obtener todos los pagos recurrentes desde el repositorio
            List<RecurringPaymentModel> allPayments = recurringPaymentRepository.findAll();

            if (allPayments == null || allPayments.isEmpty()) {
                System.out.println("No se encontraron pagos recurrentes en la base de datos.");
                return;
            }

            System.out.println("Total de pagos recurrentes encontrados: " + allPayments.size());

            for (RecurringPaymentModel payment : allPayments) {
                // Validación básica del objeto
                if (payment == null || payment.getFrequency() == null || payment.getReminderTime() == null) {
                    System.out.println("Pago recurrente inválido encontrado. Saltando...");
                    continue;
                }

                System.out.println("Procesando pago recurrente: " + payment.getName() + ", Frecuencia: " + payment.getFrequency());

                // Lógica para determinar si se debe notificar según la frecuencia
                boolean shouldNotify = false;

                switch (payment.getFrequency()) {
                    case Diariamente:
                        shouldNotify = true;
                        break;

                    case Semanalmente:
                        shouldNotify = now.getDayOfWeek() == payment.getCreatedAt().getDayOfWeek();
                        break;

                    case Mensualmente:
                        shouldNotify = now.getDayOfMonth() == payment.getCreatedAt().getDayOfMonth();
                        break;

                    case Anualmente:
                        shouldNotify = now.getDayOfYear() == payment.getCreatedAt().getDayOfYear();
                        break;

                    default:
                        System.out.println("Frecuencia desconocida: " + payment.getFrequency());
                        break;
                }

                // Validar la hora actual con el tiempo de recordatorio (tolerancia de un minuto)
                if (shouldNotify && isTimeClose(now.toLocalTime(), payment.getReminderTime())) {
                    System.out.println("Se debe generar una notificación para: " + payment.getName());

                    // Generar un mensaje aleatorio
                    String randomMessage = getRandomPaymentMessage(payment);

                    // Crear y guardar la notificación
                    NotificationsModel notification = new NotificationsModel();
                    notification.setUser(payment.getUser());
                    notification.setMessage(randomMessage);
                    notification.setType(notificationTypeENUM.pago_recurrente);
                    notification.setReadStatus(false);
                    notification.setCreationDate(now);
                    notification.setRequiresResponse(false);

                    // Guardar la notificación
                    notificationRepository.save(notification);
                    System.out.println("Notificación guardada exitosamente para: " + payment.getName());
                } else {
                    System.out.println("No es el momento de notificar para: " + payment.getName());
                }
            }
        } catch (Exception e) {
            System.err.println("Error al procesar las notificaciones: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Scheduled(fixedRate = 60000) // Ejecutar cada minuto
    public void createSavingsNotifications() {
        // Obtener la fecha y hora actuales
        LocalDateTime now = LocalDateTime.now();
        System.out.println("Ejecución de tarea programada: " + now);

        try {
            // Obtener todos los ahorros desde el repositorio
            List<SavingsGoalModel> allSaving = savingsGoalsRepository.findAll();

            if (allSaving == null || allSaving.isEmpty()) {
                System.out.println("No se encontraron ahorros en la base de datos.");
                return;
            }

            System.out.println("Total de ahorros encontrados: " + allSaving.size());

            for (SavingsGoalModel saving : allSaving) {
                // Validación básica del objeto
                if (saving == null || saving.getFrequency() == null || saving.getGoalTime() == null) {
                    System.out.println("Ahorro inválido encontrado. Saltando...");
                    continue;
                }

                System.out.println("Procesando Ahorro: " + saving.getName() + ", Frecuencia: " + saving.getFrequency());

                // Lógica para determinar si se debe notificar según la frecuencia
                boolean shouldNotify = false;

                switch (saving.getFrequency()) {
                    case Diariamente:
                        shouldNotify = true;
                        break;

                    case Semanalmente:
                        shouldNotify = now.getDayOfWeek() == saving.getCreatedAt().getDayOfWeek();
                        break;

                    case Mensualmente:
                        shouldNotify = now.getDayOfMonth() == saving.getCreatedAt().getDayOfMonth();
                        break;

                    case Anualmente:
                        shouldNotify = now.getDayOfYear() == saving.getCreatedAt().getDayOfYear();
                        break;

                    default:
                        System.out.println("Frecuencia desconocida: " + saving.getFrequency());
                        break;
                }

                // Validar la hora actual con el tiempo de recordatorio (tolerancia de un minuto)
                if (shouldNotify && isTimeClose(now.toLocalTime(), saving.getGoalTime())) {
                    System.out.println("Se debe generar una notificación para: " + saving.getName());

                    // Generar un mensaje aleatorio
                    String randomMessage = getRandomSavingMessage(saving);

                    // Crear y guardar la notificación
                    NotificationsModel notification = new NotificationsModel();
                    notification.setUser(saving.getUser());
                    notification.setMessage(randomMessage);
                    notification.setType(notificationTypeENUM.ahorro);
                    notification.setReadStatus(false);
                    notification.setCreationDate(now);
                    notification.setRequiresResponse(false);

                    // Guardar la notificación
                    notificationRepository.save(notification);
                    System.out.println("Notificación guardada exitosamente para: " + saving.getName());
                } else {
                    System.out.println("No es el momento de notificar para: " + saving.getName());
                }
            }
        } catch (Exception e) {
            System.err.println("Error al procesar las notificaciones: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private boolean isTimeClose(LocalTime currentTime, LocalTime reminderTime) {
        // Calcula la diferencia en segundos
        long secondsDifference = Math.abs(currentTime.toSecondOfDay() - reminderTime.toSecondOfDay());
        // Devuelve true si la diferencia es menor o igual a 60 segundos
        return secondsDifference <= 60;
    }

    private String getRandomPaymentMessage(RecurringPaymentModel payment) {
        Random randomGenerator = new Random();
        int random = randomGenerator.nextInt(3) + 1;

        // Usar el número aleatorio en el switch
        return switch (random) {
            case 1 -> "¡Recordatorio de pago! \uD83D\uDCB3\uD83D\uDD14<br>" +
                    "<br>" +
                    "¡Hola "+ payment.getUser().getName()+"!<br>" +
                    "Este es un recordatorio para el pago recurrente de <b>"+ payment.getName() +"</b>. El pago de <b>$" + payment.getAmount()+" MXN</b> se realiza <b>"+payment.getFrequency() +"</b> y está programado para hoy a las <b>"+payment.getReminderTime()+ "</b> horas. \uD83D\uDCC5<br>" +
                    "<br>" +
                    "<b>Comentario:</b> "+ payment.getComment() + " \uD83D\uDCBB<br>" +
                    "<br>" +
                    "No olvides hacerlo para mantener tu servicio activo. ¡Gracias! \uD83D\uDE0A";
            case 2 -> "¡Atención, recordatorio de pago! 💡⏰<br>" +
                    "<br>" +
                    "¡Hola "+ payment.getUser().getName() +"!<br>" +
                    "Tu pago recurrente de <b>"+ payment.getName() +"</b> de <b>$" + payment.getAmount() + " MXN</b> es <b>" + payment.getFrequency() + "</b>. El próximo pago se realiza hoy a las <b>" + payment.getReminderTime() + "</b> horas. 🕓<br>" +
                    "<br>" +
                    "<b>Comentario:</b> " + payment.getComment() + " 🔌<br>" +
                    "<br>" +
                    "¡Asegúrate de hacerlo para evitar cortes de servicio! 😊";
            default -> "Recordatorio de pago programado 🕓💰<br>" +
                    "<br>" +
                    "¡Hola "+ payment.getUser().getName() +"!<br>" +
                    "Recuerda que el pago recurrente de <b>" + payment.getName() + "</b> de <b>$" + payment.getAmount() + " MXN</b> es <b>" + payment.getFrequency() + "</b> y debe realizarse a las <b>" + payment.getReminderTime() + "</b> horas hoy. 📅<br>" +
                    "<br>" +
                    "<b>Comentario:</b> " + payment.getComment() + " 🌐<br>" +
                    "<br>" +
                    "¡Gracias por mantenerte al día con tus pagos! 😊";
        };
    }

    private String getRandomSavingMessage(SavingsGoalModel saving) {
        Random randomGenerator = new Random();
        int random = randomGenerator.nextInt(3) + 1;

        // Usar el número aleatorio en el switch
        return switch (random) {
            case 1 -> "¡Recordatorio de ahorro! 💰🔔<br>" +
                    "<br>" +
                    "¡Hola " + saving.getUser().getName() + "!<br>" +
                    "Este es un recordatorio para tu meta de ahorro de <b>" + saving.getName() + "</b>. ¡Sigue avanzando! 🚀<br>" +
                    "<br>" +
                    "Tu meta total es de <b>$" + saving.getGoalAmount() + " MXN</b> y hasta ahora has ahorrado <b>$" + saving.getSavedAmount() + " MXN</b>. ¡No te detengas! 💪<br>" +
                    "<br>" +
                    "El monto objetivo para hoy es <b>$" + saving.getPeriodicTarget() + " MXN</b>. 🗓️<br>" +
                    "<br>" +
                    "<b>Comentario:</b> " + saving.getComment() + " 🌟<br>" +
                    "<br>" +
                    "¡Recuerda, cada paso cuenta! 😊";
            case 2 -> "¡Atención, recordatorio de ahorro! 💡💸<br>" +
                    "<br>" +
                    "¡Hola " + saving.getUser().getName() + "!<br>" +
                    "Tu meta de ahorro de <b>" + saving.getName() + "</b> está en marcha. Actualmente has ahorrado <b>$" + saving.getSavedAmount() + " MXN</b>. ¡Vamos por más! 💪<br>" +
                    "<br>" +
                    "El monto objetivo para hoy es de <b>$" + saving.getPeriodicTarget() + " MXN</b> y tienes hasta las <b>" + saving.getGoalTime() + "</b> para alcanzarlo. 🕓<br>" +
                    "<br>" +
                    "<b>Comentario:</b> " + saving.getComment() + " 🔑<br>" +
                    "<br>" +
                    "¡No pierdas el ritmo, estás en el camino correcto! 😊";
            default -> "Recordatorio de ahorro programado 🕓💰<br>" +
                    "<br>" +
                    "¡Hola " + saving.getUser().getName() + "!<br>" +
                    "Recuerda que tu meta de ahorro de <b>" + saving.getName() + "</b> sigue en progreso. ¡Sigue así! 💪<br>" +
                    "<br>" +
                    "Tu meta total es de <b>$" + saving.getGoalAmount() + " MXN</b> y hasta el momento has ahorrado <b>$" + saving.getSavedAmount() + " MXN</b>. ¡Vamos por más! 🏅<br>" +
                    "<br>" +
                    "El objetivo de ahorro para hoy es de <b>$" + saving.getPeriodicTarget() + " MXN</b> y lo puedes lograr. ¡Ánimo! 🌟<br>" +
                    "<br>" +
                    "<b>Comentario:</b> " + saving.getComment() + " 💡<br>" +
                    "<br>" +
                    "¡Sigue avanzando, tu objetivo está más cerca de lo que piensas! 😊";
        };
    }

}
