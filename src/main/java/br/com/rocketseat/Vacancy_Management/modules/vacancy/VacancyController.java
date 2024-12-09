package br.com.rocketseat.Vacancy_Management.modules.vacancy;

import br.com.rocketseat.Vacancy_Management.modules.company.CompanyService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/companies/vacancies")
public class VacancyController {

    @Autowired
    VacancyService vacancyService;

    @Autowired
    CompanyService companyService;


    @PostMapping("/create")
    @PreAuthorize("hasRole('COMPANY')")
    public ResponseEntity<?> createVacancy(@RequestBody @Valid  VacancyCreateForm vacancyRequest, HttpServletRequest request){

        var companyID = request.getAttribute("company_id").toString();

        var newVacancy = Vacancy.builder()
                .level(vacancyRequest.level())
                .benefits(vacancyRequest.benefits())
                .description(vacancyRequest.description())
                .companyId(UUID.fromString(companyID))
                .build();

        return this.vacancyService.createVacancy(newVacancy);
    }
}
