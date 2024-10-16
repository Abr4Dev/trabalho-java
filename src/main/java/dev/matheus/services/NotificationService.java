package dev.matheus.services;

import dev.matheus.entitys.user.User;
import dev.matheus.mock.SendNotification.SendNotificationRequest;
import dev.matheus.mock.SendNotification.SendNotificationResponse;
import dev.matheus.mock.SendNotification.SendNotification;
import jakarta.inject.Inject;
import org.eclipse.microprofile.rest.client.inject.RestClient;

public class NotificationService {

    @Inject
    @RestClient
    SendNotification sendNotification;

    public void sendNotificationEmail(User user, String message) throws Exception {
        SendNotificationRequest request = new SendNotificationRequest();
        request.email = user.email;
        request.mensagem = message;

        SendNotificationResponse response = sendNotification.sendNotification(request);

        if((!"success".equals(response.status))) {
            System.out.println("[DEBUG INFO] - Erro ao enviar notificação");
            throw new Exception("Serviço de notificação está fora do ar");
        }
    }
}

