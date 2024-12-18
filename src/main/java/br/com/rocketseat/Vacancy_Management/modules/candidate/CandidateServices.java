package br.com.rocketseat.Vacancy_Management.modules.candidate;

import br.com.rocketseat.Vacancy_Management.exceptions.UserFoudException;
import br.com.rocketseat.Vacancy_Management.exceptions.UserNotFoundException;
import br.com.rocketseat.Vacancy_Management.exceptions.VacancyNotFoundException;
import br.com.rocketseat.Vacancy_Management.modules.candidate.dto.CandidateResponseDTO;
import br.com.rocketseat.Vacancy_Management.modules.candidate.dto.AuthCandidateForm;
import br.com.rocketseat.Vacancy_Management.modules.vacancy.VacancyRepository;
import br.com.rocketseat.Vacancy_Management.modules.vacancy.VacancyService;
import br.com.rocketseat.Vacancy_Management.security.TokenCandidateService;
import br.com.rocketseat.Vacancy_Management.security.dto.TokenDTO;
import br.com.rocketseat.Vacancy_Management.modules.candidate.dto.CandidateCreateRequestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import javax.naming.AuthenticationException;
import java.util.UUID;

@Service
public class CandidateServices {
    @Autowired
    CandidateRepository candidateRepository;

    @Autowired
    VacancyRepository vacancyRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    TokenCandidateService tokenCandidateService;

    public Candidate createCandidate(CandidateCreateRequestDTO candidateRequest){
        this.candidateRepository.findByUserNameOrEmail(candidateRequest.userName(), candidateRequest.email())
                .ifPresent((user)-> {
                    throw new UserFoudException();
                });
        Candidate newCandidate = new Candidate(candidateRequest);
        var passwordHash = passwordEncoder.encode(newCandidate.getPassword());
        newCandidate.setPassword(passwordHash);
        return this.candidateRepository.save(newCandidate);
    }


    public TokenDTO login(@RequestBody AuthCandidateForm authCandidateForm) throws AuthenticationException {
        var candidate = this.candidateRepository.findByUserName(authCandidateForm.userName())
                .orElseThrow(()->{
                    throw new UsernameNotFoundException("candidate/password does not exist");
                });

        var passwordMatches = passwordEncoder.matches(authCandidateForm.password(), candidate.getPassword());
         if (!passwordMatches){
             throw new AuthenticationException();
         }

         var token = this.tokenCandidateService.generateToken(candidate);
         return token;
    }


    public CandidateResponseDTO profile(UUID candidateId){
        var candidate = this.candidateRepository.findById(candidateId)
                .orElseThrow(()->{
                    throw new UsernameNotFoundException("Candidate not found");
                });
        return new CandidateResponseDTO(candidate.getName(), candidate.getUserName(), candidate.getEmail(), candidate.getDescription(), candidate.getId());
    }


    public void applyVacancy(UUID candidateId, UUID vacancyId){

        var candidate = this.candidateRepository.findById(candidateId).orElseThrow(()->{
            throw new UserNotFoundException();
        });

        var vacancy = this.vacancyRepository.findById(vacancyId).orElseThrow(()->{
            throw new VacancyNotFoundException();
        });



    }

}
