package dev.matheus.entitys.user;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity(name = "users")
@Table(name = "tb_users")
public class User extends PanacheEntityBase {

    // Id do usuário no banco de dados
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    // Primeiro nome do usuário
    public String firstName;

    // Segundo nome do usuário
    public String lastName;

    // CPF do usuário com regra para cadastro único
    @Column(unique = true)
    public String  document;

    // E-mail do usuário com regra para cadastro único
    @Column(unique = true)
    public String email;

    // Senha do usuário
    public String password;

    // Saldo do usuário
    public BigDecimal balance;

    // Tipo de usuário
    @Enumerated(EnumType.STRING)
    public UserType userType;

}
