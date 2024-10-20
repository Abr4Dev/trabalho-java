package dev.matheus.services;

import dev.matheus.entitys.user.User;
import dev.matheus.entitys.user.UserType;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.Path;

import java.math.BigDecimal;
import java.util.List;

@Path("/users")
public class UserService {

    // Vem de um Controller @GET
    // Lista toda a lista de usuários
    public List<User> listUsers() {
        return User.listAll();
    }

    // Vem de um Controller @POST
    // Persist (Cria usuário) no banco de dados
    // Transactional é usado, pois o methode altera dados no banco de dados
    @Transactional
    public User saveUser(User user) {
        user.persist();
        return user;
    }

    // Regra de negócia que valida se a transferência pode ser realizada
    // Se o usuário remetente for um de conta logista, uma Exception é lançada
    // Se o usuário remetente não possuir saldo para transferência, uma Exception é lançada
    public void validadeTransaction(User sender, BigDecimal amount) throws Exception {
        if(sender.userType == UserType.MERCHANT) {
            throw new Exception("Usuário do tipo Lojista não está autorizado a realizar transferência");
        }
        if (sender.balance.compareTo(amount) < 0) {
            throw new Exception("Saldo insuficiente");
        }
    }

    // Regra de negócio que busca usuário remetente no banco, uma Exception é lançada se o usuário não existir
    public User findUserSenderById(Long id) throws Exception {
        if (User.findById(id) == null) {
            throw new Exception("Usuário remetente não encontrado");
        }
        return User.findById(id);
    }

    // Regra de negócio que busca usuário Destinatário no banco, uma Exception é lançada se o usuário não existir
    public User findUserReceiverById(Long id) throws Exception {
        if (User.findById(id) == null) {
            throw new Exception("Usuário Destinatário não encontrado");
        }
        return User.findById(id);
    }
}
