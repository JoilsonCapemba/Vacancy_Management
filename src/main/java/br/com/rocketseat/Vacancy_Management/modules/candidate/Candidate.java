package br.com.rocketseat.Vacancy_Management.modules.candidate;

import br.com.rocketseat.Vacancy_Management.modules.candidate.dto.CandidateCreateRequestDTO;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity(name = "candidates")
public class Candidate {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotBlank
    private String name;

    @Column(name = "user_name", unique = true)
    private String userName;

    @Email
    private String email;

    @Length(min = 6)
    private String password;
    private String description;
    private String curriculum;

    public Candidate(CandidateCreateRequestDTO candidateRequest){
        this.name = candidateRequest.name();
        this.userName = candidateRequest.userName();
        this.email = candidateRequest.email();
        this.password = candidateRequest.password();
        this.description = candidateRequest.description();
        this.curriculum = candidateRequest.description();
    }

}
