package dev.matheus.services;

import dev.matheus.entitys.user.User;
import dev.matheus.entitys.user.UserType;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.Path;

import java.math.BigDecimal;
import java.util.List;

@Path("/users")
public class UserService {

    public List<User> listUsers() {
        return User.listAll();
    }

    @Transactional
    public User saveUser(User user) {
        user.persist();
        return user;
    }

    public void validadeTransaction(User sender, BigDecimal amount) throws Exception {
        if(sender.userType == UserType.MERCHANT) {
            throw new Exception("Usuário do tipo Lojista não está autorizado a realizar transação");
        }
        if (sender.balance.compareTo(amount) < 0) {
            throw new Exception("Saldo insuficiente");
        }
    }

    public User findUserById(Long id) throws Exception {
        if (User.findById(id) == null) {
            throw new Exception("Usuário não encontrado");
        }
        return User.findById(id);
    }
}
