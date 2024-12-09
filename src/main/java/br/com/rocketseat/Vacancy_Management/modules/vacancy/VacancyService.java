package br.com.rocketseat.Vacancy_Management.modules.vacancy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class VacancyService {

    @Autowired
    VacancyRepository vacancyRepository;

    public ResponseEntity<Vacancy> createVacancy(Vacancy vacancy){
        return ResponseEntity.ok(this.vacancyRepository.save(vacancy));
    }

    public Optional<List<Vacancy>> findByCompanyId(UUID companyId){
        return this.vacancyRepository.findByCompanyId(companyId);
    }

    public List<Vacancy> listVacanciesByFilter(String filter){
        return this.vacancyRepository.findByDescriptionContainingIgnoreCase(filter);
    }
}
