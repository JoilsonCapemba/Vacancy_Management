package br.com.rocketseat.Vacancy_Management.modules.candidate.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

public record CandidateCreateRequestDTO(
        @NotNull
        String name,
        @NotNull
        String userName,
        @Email(message = "Preencha com um email valido!")
        String email,
        @NotNull @Length(min = 6)
        String password,
        String description,
        String curriculum) {
}
