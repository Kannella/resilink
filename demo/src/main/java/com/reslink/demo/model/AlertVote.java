package com.reslink.demo.model;

import com.reslink.demo.model.enums.VoteType;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import java.time.LocalDateTime;

@Data
@Entity
// Garante que um usuário só pode votar uma vez por alerta
@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames = {"user_id", "alert_id"})
})
public class AlertVote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private VoteType voteType;

    @CreationTimestamp
    private LocalDateTime votedAt;

    // Relacionamento: Muitos votos para UM usuário
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // Relacionamento: Muitos votos para UM alerta
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "alert_id", nullable = false)
    private UserSubmittedAlert alert;
}