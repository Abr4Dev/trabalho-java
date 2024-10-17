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

    // Id da transação no banco de dados
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    // Valor da transação
    public BigDecimal amount;

    // Usuário que envia (Em uma transação, só há um usuário que envia)
    @ManyToOne
    @JoinColumn(name = "sender_id")
    public User sender;

    // Usuário que recebe (Em uma transação, só há um usuário que recebe)
    @ManyToOne
    @JoinColumn(name = "receiver_id")
    public User receiver;

    // Timestamp da transação
    @CreationTimestamp
    public LocalDateTime timestamp;
}
