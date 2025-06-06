package com.reslink.demo.model;
import com.reslink.demo.model.enums.AlertStatus;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
public class UserSubmittedAlert {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private String category;

    @Column(nullable = false)
    private Double latitude;

    @Column(nullable = false)
    private Double longitude;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AlertStatus status;

    @CreationTimestamp // Define a data/hora de criação automaticamente
    private LocalDateTime createdAt;

    // Relacionamento: Muitos alertas podem ser submetidos por UM usuário
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User submittedBy;

    // Relacionamento: Um alerta pode ter muitos votos
    @OneToMany(mappedBy = "alert", cascade = CascadeType.ALL)
    private List<AlertVote> votes;
}