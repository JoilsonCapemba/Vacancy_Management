package br.com.rocketseat.Vacancy_Management.security.dto;

import java.time.Instant;

public record TokenDTO(String access_token, String token_type, Instant expires_in /* String refresh_token*/) {
}
