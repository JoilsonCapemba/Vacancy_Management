package br.com.rocketseat.Vacancy_Management.modules.candidate.controllers;

import br.com.rocketseat.Vacancy_Management.modules.candidate.dto.CreateCandidateDTO;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/candidates")
public class CandidateController {

    @PostMapping("/create")
    public ResponseEntity<String> createcandidate(@RequestBody @Valid CreateCandidateDTO candidate){
        return  ResponseEntity.ok("Candidato"+candidate.name()+" criado com sucesso");
    }

}
