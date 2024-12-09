package br.com.rocketseat.Vacancy_Management.modules.candidate.controllers;

import br.com.rocketseat.Vacancy_Management.modules.candidate.dto.AuthCandidateForm;
import br.com.rocketseat.Vacancy_Management.modules.candidate.CandidateServices;
import br.com.rocketseat.Vacancy_Management.modules.candidate.dto.CandidateCreateRequestDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.naming.AuthenticationException;
import java.util.UUID;

@RestController
@RequestMapping("/candidates")
public class CandidateController {

    @Autowired
    CandidateServices candidateServices;

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

}
