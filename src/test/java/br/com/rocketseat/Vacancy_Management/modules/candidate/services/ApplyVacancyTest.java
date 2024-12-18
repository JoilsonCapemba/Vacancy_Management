package br.com.rocketseat.Vacancy_Management.modules.candidate.services;

import br.com.rocketseat.Vacancy_Management.exceptions.UserNotFoundException;
import br.com.rocketseat.Vacancy_Management.exceptions.VacancyNotFoundException;
import br.com.rocketseat.Vacancy_Management.modules.candidate.Candidate;
import br.com.rocketseat.Vacancy_Management.modules.candidate.CandidateRepository;
import br.com.rocketseat.Vacancy_Management.modules.candidate.CandidateServices;
import br.com.rocketseat.Vacancy_Management.modules.vacancy.VacancyRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mock.*;

import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mock.Strictness.STRICT_STUBS;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ApplyVacancyTest {

    @InjectMocks
    private CandidateServices candidateServices;

    @Mock
    private VacancyRepository vacancyRepository;
    @Mock
    private CandidateRepository candidateRepository;

    @Test
    @DisplayName("Shoud not be apply job with candidate not found")
    public void shouldNotBeToApplyVacancyWithVacandidateNotFound(){
        try{
            candidateServices.applyVacancy(null, null);
        }catch (Exception e){
            Assertions.assertThat(e).isInstanceOf(UserNotFoundException.class);
        }

    }

    @Test
    @DisplayName("Shoud not be to apply job with vacancy not found")
    public void shouldNotBeToApplyVacancyWithVacancyNotFound() {

        var candidateId = UUID.randomUUID();
        var candidate = new Candidate();
        candidate.setId(candidateId);

        when(candidateRepository.findById(candidateId)).thenReturn(Optional.of(candidate));

        try{
            candidateServices.applyVacancy(candidateId, null);
        }catch (Exception e){
            Assertions.assertThat(e).isInstanceOf(VacancyNotFoundException.class);
        }

    }
}
