package dev.matheus.services;

import dev.matheus.dtos.requests.RequestTransaction;
import dev.matheus.entitys.transaction.Transaction;
import dev.matheus.entitys.user.User;
import org.jboss.resteasy.reactive.ClientWebApplicationException;
import dev.matheus.dtos.responses.ResponseAuthorizationTransaction;
import dev.matheus.mocks.AuthorizationTransaction.AuthorizationTransaction;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.eclipse.microprofile.rest.client.inject.RestClient;

@ApplicationScoped
public class TransactionService {

    @Inject
    private UserService userService;

    @Transactional
    public Transaction createTransaction(RequestTransaction requestTransaction) throws  Exception{

        User sender = userService.findUserById(requestTransaction.senderId);
        User receiver = userService.findUserById(requestTransaction.receiverId);
        userService.validadeTransaction(sender, requestTransaction.amount);

        // MOCK de Autorização da transação
        if (!authorizeTransaction())  throw new Exception("Transação recusada!");

        sender.balance = sender.balance.subtract(requestTransaction.amount);
        receiver.balance = receiver.balance.add(requestTransaction.amount);

        Transaction transaction = new Transaction();

        transaction.sender = userService.saveUser(sender);
        transaction.receiver = userService.saveUser(receiver);
        transaction.persist();

         // ADD MOCK

         return transaction;
    }

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
}
