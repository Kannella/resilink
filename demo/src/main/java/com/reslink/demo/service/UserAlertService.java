package com.reslink.demo.service;

import com.reslink.demo.dto.useralerts.AlertCreateRequestDto;
import com.reslink.demo.dto.useralerts.AlertResponseDto; // IMPORTAR
import com.reslink.demo.model.User;
import com.reslink.demo.model.UserSubmittedAlert;
import com.reslink.demo.model.AlertVote;
import com.reslink.demo.model.enums.AlertStatus;
import com.reslink.demo.model.enums.VoteType;
import com.reslink.demo.repository.AlertVoteRepository;
import com.reslink.demo.repository.UserRepository;
import com.reslink.demo.repository.UserSubmittedAlertRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserAlertService {

    private static final int VOTE_THRESHOLD = 5;

    @Autowired private UserRepository userRepository;
    @Autowired private UserSubmittedAlertRepository alertRepository;
    @Autowired private AlertVoteRepository voteRepository;

    @Transactional
    // Mude o tipo de retorno
    public AlertResponseDto createAlert(AlertCreateRequestDto dto, UserDetails userDetails) {
        User user = userRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado."));

        UserSubmittedAlert newAlert = new UserSubmittedAlert();
        newAlert.setDescription(dto.getDescription());
        newAlert.setCategory(dto.getCategory());
        newAlert.setLatitude(dto.getLatitude());
        newAlert.setLongitude(dto.getLongitude());
        newAlert.setSubmittedBy(user);
        newAlert.setStatus(AlertStatus.PENDING);

        UserSubmittedAlert savedAlert = alertRepository.save(newAlert);
        return toResponseDto(savedAlert); // Converte para DTO
    }

    @Transactional
    // Mude o tipo de retorno
    public AlertResponseDto voteOnAlert(Long alertId, VoteType voteType, UserDetails userDetails) {
        User user = userRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado."));

        UserSubmittedAlert alert = alertRepository.findById(alertId)
                .orElseThrow(() -> new RuntimeException("Alerta não encontrado."));

        if (alert.getSubmittedBy().equals(user)) {
            throw new RuntimeException("Você não pode votar no seu próprio alerta.");
        }

        if (voteRepository.findByUserAndAlert(user, alert).isPresent()) {
            throw new RuntimeException("Você já votou neste alerta.");
        }

        AlertVote newVote = new AlertVote();
        newVote.setAlert(alert);
        newVote.setUser(user);
        newVote.setVoteType(voteType);
        voteRepository.save(newVote);

        updateAlertStatus(alert);

        // Retorna o DTO do alerta atualizado
        return toResponseDto(alert);
    }

    private void updateAlertStatus(UserSubmittedAlert alert) {
        long confirmations = voteRepository.countByAlertAndVoteType(alert, VoteType.CONFIRMATION);
        long denials = voteRepository.countByAlertAndVoteType(alert, VoteType.DENIAL);

        if (confirmations >= VOTE_THRESHOLD) {
            alert.setStatus(AlertStatus.CONFIRMED);
        } else if (denials >= VOTE_THRESHOLD) {
            alert.setStatus(AlertStatus.REJECTED);
        }

        alertRepository.save(alert);
    }

    // MÉTODO AUXILIAR PARA CONVERTER ENTIDADE EM DTO
    private AlertResponseDto toResponseDto(UserSubmittedAlert alert) {
        return AlertResponseDto.builder()
                .id(alert.getId())
                .description(alert.getDescription())
                .category(alert.getCategory())
                .latitude(alert.getLatitude())
                .longitude(alert.getLongitude())
                .status(alert.getStatus())
                .createdAt(alert.getCreatedAt())
                .submitterEmail(alert.getSubmittedBy().getEmail()) // Pega apenas o email
                .confirmationCount(voteRepository.countByAlertAndVoteType(alert, VoteType.CONFIRMATION))
                .denialCount(voteRepository.countByAlertAndVoteType(alert, VoteType.DENIAL))
                .build();
    }
}