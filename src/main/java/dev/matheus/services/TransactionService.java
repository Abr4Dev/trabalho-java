package dev.matheus.services;

import dev.matheus.entitys.transaction.Transaction;
import dev.matheus.entitys.user.User;
import org.jboss.resteasy.reactive.ClientWebApplicationException;
import dev.matheus.mock.AuthorizationTransaction.AuthorizationTransactionResponse;
import dev.matheus.mock.AuthorizationTransaction.AuthorizationTransaction;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.eclipse.microprofile.rest.client.inject.RestClient;

@ApplicationScoped
public class TransactionService {

    @Inject
    @RestClient
    AuthorizationTransaction authorizationTransaction;

    public boolean authorizeTransaction() throws Exception {
        try {
            AuthorizationTransactionResponse response = authorizationTransaction.authorize();
            return response != null && response.data != null && response.data.authorization;
        } catch (ClientWebApplicationException err) {
            throw new Exception("Transação recusada!");
        }
    }

    @Inject
    private UserService userService;

    @Transactional
    public Transaction createTransaction(Transaction newTransaction) throws  Exception{

        User sender = userService.findUserById(newTransaction.sender.id);
        User receiver = userService.findUserById(newTransaction.receiver.id);
        userService.validadeTransaction(sender, newTransaction.amount);

        // MOCK de Autorização da transação
        if (!authorizeTransaction())
            throw new Exception("Transação recusada!");

         sender.balance = sender.balance.subtract(newTransaction.amount);
         receiver.balance = receiver.balance.add(newTransaction.amount);

         newTransaction.sender = userService.saveUser(sender);
         newTransaction.receiver = userService.saveUser(receiver);
         newTransaction.persist();

         // ADD MOCK

         return newTransaction;
    }
}
