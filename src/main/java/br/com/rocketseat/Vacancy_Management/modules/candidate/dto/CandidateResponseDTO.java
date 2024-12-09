package br.com.rocketseat.Vacancy_Management.modules.candidate.dto;

import java.util.UUID;

public record CandidateResponseDTO(String name, String userName, String email, String description, UUID candidateId) {
}
