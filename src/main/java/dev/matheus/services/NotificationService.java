package dev.matheus.services;

import dev.matheus.entitys.user.User;
import dev.matheus.dtos.requests.RequestSendNotification;
import dev.matheus.dtos.responses.ResponseSendNotification;
import dev.matheus.mocks.SendNotification.SendNotification;
import jakarta.inject.Inject;
import org.eclipse.microprofile.rest.client.inject.RestClient;

public class NotificationService {

    @Inject
    @RestClient
    SendNotification sendNotification;

    public void sendNotificationEmail(User user, String message) throws Exception {
        RequestSendNotification request = new RequestSendNotification();
        request.email = user.email;
        request.mensagem = message;

        ResponseSendNotification response = sendNotification.sendNotification(request);

        if((!"success".equals(response.status))) {
            System.out.println("[DEBUG INFO] - Erro ao enviar notificação");
            throw new Exception("Serviço de notificação está fora do ar");
        }
    }
}

