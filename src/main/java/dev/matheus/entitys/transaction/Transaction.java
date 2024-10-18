package dev.matheus.entitys.transaction;

import dev.matheus.entitys.user.User;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity(name = "transactions")
@Table(name = "tb_transactions")
public class Transaction extends PanacheEntityBase {

    // Id da transação
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    // Valor da transferência
    public BigDecimal amount;

    // Usuário que envia na transferência
    // Muitos-para-um: várias transações podem ser feitas por um mesmo usuário
    @ManyToOne
    @JoinColumn(name = "sender_id")
    public User sender;

    // Usuário que recebe na transferência
    // Muitos-para-um: várias transações podem ser feitas por um mesmo usuário
    @ManyToOne
    @JoinColumn(name = "receiver_id")
    public User receiver;

    // Timestamp da transação
    @CreationTimestamp
    public LocalDateTime timestamp;
}
