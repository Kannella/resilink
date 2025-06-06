package com.reslink.demo.service;

import com.reslink.demo.dto.helprequest.HelpRequestCreateDto;
import com.reslink.demo.dto.helprequest.HelpRequestResponseDto;
import com.reslink.demo.model.HelpRequest;
import com.reslink.demo.model.User;
import com.reslink.demo.repository.HelpRequestRepository;
import com.reslink.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class HelpRequestService {

    @Autowired private HelpRequestRepository helpRequestRepository;
    @Autowired private UserRepository userRepository;

    @Transactional
    public HelpRequestResponseDto createRequest(HelpRequestCreateDto dto, UserDetails userDetails) {
        User requester = userRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("Usuário solicitante não encontrado."));

        HelpRequest newRequest = new HelpRequest();
        newRequest.setDescription(dto.getDescription());
        newRequest.setRequester(requester);

        HelpRequest savedRequest = helpRequestRepository.save(newRequest);
        return toResponseDto(savedRequest);
    }

    @Transactional(readOnly = true)
    public List<HelpRequestResponseDto> getAllRequests() {
        return helpRequestRepository.findAll().stream()
                .map(this::toResponseDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public void deleteRequest(Long requestId, UserDetails userDetails) {
        User currentUser = userRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado."));

        HelpRequest requestToDelete = helpRequestRepository.findById(requestId)
                .orElseThrow(() -> new RuntimeException("Pedido de ajuda não encontrado com o ID: " + requestId));

        // --- VERIFICAÇÃO DE PROPRIEDADE (LÓGICA CRÍTICA) ---
        // Compara o ID do usuário logado com o ID do usuário que criou o pedido.
        if (!requestToDelete.getRequester().getId().equals(currentUser.getId())) {
            throw new AccessDeniedException("Acesso negado. Você só pode excluir seus próprios pedidos de ajuda.");
        }

        helpRequestRepository.delete(requestToDelete);
    }

    // Método auxiliar para converter a Entidade para o DTO de resposta
    private HelpRequestResponseDto toResponseDto(HelpRequest helpRequest) {
        return HelpRequestResponseDto.builder()
                .id(helpRequest.getId())
                .description(helpRequest.getDescription())
                .createdAt(helpRequest.getCreatedAt())
                .requesterEmail(helpRequest.getRequester().getEmail())
                .build();
    }
}