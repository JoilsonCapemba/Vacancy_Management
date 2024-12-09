package br.com.rocketseat.Vacancy_Management.modules.vacancy;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface VacancyRepository extends JpaRepository<Vacancy, UUID> {

    Optional<List<Vacancy>> findByCompanyId(UUID companyId);
}
