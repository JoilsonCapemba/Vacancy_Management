package br.com.rocketseat.Vacancy_Management.modules.candidate.controllers;

import br.com.rocketseat.Vacancy_Management.modules.candidate.dto.AuthCandidateForm;
import br.com.rocketseat.Vacancy_Management.modules.candidate.CandidateServices;
import br.com.rocketseat.Vacancy_Management.modules.candidate.dto.CandidateCreateRequestDTO;
import br.com.rocketseat.Vacancy_Management.modules.vacancy.Vacancy;
import br.com.rocketseat.Vacancy_Management.modules.vacancy.VacancyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.naming.AuthenticationException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/candidates")
@Tag(name = "Candidate", description = "Informacoes do candidato")
public class CandidateController {

    @Autowired
    CandidateServices candidateServices;

    @Autowired
    VacancyService vacancyService;

    @PostMapping("/create")
    public ResponseEntity<Object> createcandidate(@RequestBody @Valid CandidateCreateRequestDTO candidateRequest){
        try {
            var newUser = this.candidateServices.createCandidate(candidateRequest);
            return  ResponseEntity.status(HttpStatus.CREATED).body(newUser);
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/auth")
    public ResponseEntity<Object> loginCandidate(@RequestBody AuthCandidateForm authCandidateForm) throws AuthenticationException {
        try {
            var token = this.candidateServices.login(authCandidateForm);
            return ResponseEntity.ok(token);
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }

    }

    @GetMapping("/profile")
    @PreAuthorize("hasRole('CANDIDATE')")
    public ResponseEntity<Object> getProfile(HttpServletRequest request){
        var id = request.getAttribute("candidate_id").toString();

        try {
            var candidate = this.candidateServices.profile(UUID.fromString(id));
            return  ResponseEntity.ok(candidate);
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @GetMapping("/vacancies")
    @PreAuthorize("hasRole('CANDIDATE')")
    @Operation(summary = "Listagem de vagas para o Candidato por filtro", description = "Esta function lista as vagas por filtro")
    @SecurityRequirement(name = "jwt")
    public ResponseEntity<List<Vacancy>> findVacanciesByFilter(@RequestParam String filter){
        return ResponseEntity.ok(this.vacancyService.listVacanciesByFilter(filter));
    }

}
