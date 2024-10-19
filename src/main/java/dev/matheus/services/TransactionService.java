package dev.matheus.services;

import dev.matheus.dtos.requests.RequestSendNotification;
import dev.matheus.dtos.requests.RequestTransaction;
import dev.matheus.dtos.responses.ResponseSendNotification;
import dev.matheus.entitys.transaction.Transaction;
import dev.matheus.entitys.user.User;
import dev.matheus.mocks.SendNotification.SendNotification;
import org.jboss.resteasy.reactive.ClientWebApplicationException;
import dev.matheus.dtos.responses.ResponseAuthorizationTransaction;
import dev.matheus.mocks.AuthorizationTransaction.AuthorizationTransaction;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.eclipse.microprofile.rest.client.inject.RestClient;

@ApplicationScoped
public class TransactionService {

    // Injeta dependências
    // O Quarkus, durante a execução, se encarrega de fornecer uma instância de UserService pronta para uso.
    @Inject
    UserService userService;

    // Transactional pois o methode altera dados no banco de dados!
    @Transactional
    public Transaction createTransaction(RequestTransaction requestTransaction) throws  Exception{

        User sender = userService.findUserById(requestTransaction.senderId);
        User receiver = userService.findUserById(requestTransaction.receiverId);
        userService.validadeTransaction(sender, requestTransaction.amount);

        // MOCK de Autorização da transação
        // Se a autorização falar uma Exception é lançada
        if (!authorizeTransaction())  throw new Exception("Transação recusada!");

        sender.balance = sender.balance.subtract(requestTransaction.amount);
        receiver.balance = receiver.balance.add(requestTransaction.amount);

        // Criando a transferência
        Transaction transaction = new Transaction();

        transaction.amount = requestTransaction.amount;
        transaction.sender = userService.saveUser(sender);
        transaction.receiver = userService.saveUser(receiver);
        transaction.notifySender = sendNotificationEmail();
        transaction.notifyReceiver = sendNotificationEmail();
        transaction.persist();

        return transaction;
    }

    // Injeta dependências
    // O Quarkus, durante a execução, se encarrega de fornecer uma instância de AuthorizationTransaction pronta para uso.
    @Inject
    @RestClient
    AuthorizationTransaction authorizationTransaction;

    public boolean authorizeTransaction() throws Exception {
        try {
            ResponseAuthorizationTransaction response = authorizationTransaction.authorize();
            return response != null && response.data != null && response.data.authorization;
        } catch (ClientWebApplicationException err) {
            throw new Exception("Transação recusada!");
        }
    }

    @Inject
    @RestClient
    SendNotification sendNotification;

    public String sendNotificationEmail() {
        ResponseSendNotification response = new ResponseSendNotification();
        RequestSendNotification requestSendNotification = new RequestSendNotification();
        String notifyStatus;

        try {
            response = sendNotification.sendNotification(requestSendNotification);
            notifyStatus = "Notificação enviada com sucesso";
            return notifyStatus;
        } catch (ClientWebApplicationException err) {
            notifyStatus = "Falha ao enviar a notificação";
            return notifyStatus;
        }
    }
}
