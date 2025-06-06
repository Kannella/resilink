package com.reslink.demo.repository;

import com.reslink.demo.model.AlertVote;
import com.reslink.demo.model.User;
import com.reslink.demo.model.UserSubmittedAlert;
import com.reslink.demo.model.enums.VoteType;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface AlertVoteRepository extends JpaRepository<AlertVote, Long> {

    // Procura se um usuário específico já votou em um alerta específico
    Optional<AlertVote> findByUserAndAlert(User user, UserSubmittedAlert alert);

    // Conta quantos votos de um certo tipo um alerta recebeu
    long countByAlertAndVoteType(UserSubmittedAlert alert, VoteType voteType);
}