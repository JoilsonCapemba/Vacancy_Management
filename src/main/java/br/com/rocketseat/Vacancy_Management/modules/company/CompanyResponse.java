package br.com.rocketseat.Vacancy_Management.modules.company;

import java.time.LocalDateTime;
import java.util.UUID;

public record CompanyResponse(UUID id, String username, String name, String email, String password, String website, String description, LocalDateTime createdAt) {
}
