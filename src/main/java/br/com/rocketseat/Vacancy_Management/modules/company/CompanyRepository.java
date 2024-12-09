package br.com.rocketseat.Vacancy_Management.modules.company;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CompanyRepository extends JpaRepository<Company, UUID> {
    Optional<Company> findByName(String name);

    Optional<Company> findByNameOrEmail(String name, String email);

    Optional<Company> findByUsername(String username);
}
