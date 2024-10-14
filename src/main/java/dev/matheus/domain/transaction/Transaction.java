package dev.matheus.domain.transaction;

import dev.matheus.domain.user.User;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity(name = "transactions")
@Table(name = "tb_transactions")
public class Transaction extends PanacheEntityBase {

    // Id da transação no banco de dados
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    // Valor da transação
    public BigDecimal amount;

    // Usuário que envia (Uma transação só pode ter um usuário que envia!)
    // Modelando a relação das tabelas
    @ManyToOne
    @JoinColumn(name = "sender_id")
    public User sender;

    // Usuário que recebe (Uma transação só pode ter um usuário que recebe!)
    // Modelando a relação das tabelas
    @ManyToOne
    @JoinColumn(name = "receiver_id")
    public User receiver;

    // Timestamp da transação
    public LocalDateTime timestamp;
}
