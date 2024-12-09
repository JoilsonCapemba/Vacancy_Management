package br.com.rocketseat.Vacancy_Management.modules.candidate;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;
import java.util.UUID;

@RestController
public interface CandidateRepository extends JpaRepository<Candidate, UUID> {
   Optional<Candidate> findByUserNameOrEmail(String userName, String email);
   Optional<Candidate> findByUserName(String userName);
}
