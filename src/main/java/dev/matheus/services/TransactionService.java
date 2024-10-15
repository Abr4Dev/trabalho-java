package dev.matheus.services;

import dev.matheus.entitys.transaction.Transaction;
import dev.matheus.entitys.user.User;
import dev.matheus.mock.AuthorizationTransaction.AuthorizationTransactionResponse;
import dev.matheus.mock.AuthorizationTransaction.AuthorizationTransaction;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class TransactionService {

    AuthorizationTransaction authorizationTransaction;

    public boolean authorizeTransaction() {
        AuthorizationTransactionResponse response = this.authorizationTransaction.authorize();
        if (response != null && response.data != null) {
            return response.data.authorization;
        } else {
            return false;
        }
    }

    private UserService userService;

    public void createTransaction(Transaction newTransaction) throws  Exception{

        User sender = this.userService.findUserById(newTransaction.sender.id);
        User receiver = this.userService.findUserById(newTransaction.receiver.id);
        this.userService.validadeTransaction(sender, newTransaction.amount);

        boolean isAuthorized = this.authorizeTransaction();
         if (!isAuthorized){
            throw new Exception("Transação não autorizada");
         }

         sender.balance = sender.balance.subtract(newTransaction.amount);
         receiver.balance = receiver.balance.add(newTransaction.amount);

         this.userService.saveUser(sender);
         this.userService.saveUser(receiver);
         newTransaction.persist();
    }
}
