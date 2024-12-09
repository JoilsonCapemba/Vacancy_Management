package br.com.rocketseat.Vacancy_Management.modules.company.controllers;

import br.com.rocketseat.Vacancy_Management.modules.company.AuthCompanyForm;
import br.com.rocketseat.Vacancy_Management.modules.company.CompanyCreateForm;
import br.com.rocketseat.Vacancy_Management.modules.company.CompanyService;
import br.com.rocketseat.Vacancy_Management.modules.vacancy.Vacancy;
import br.com.rocketseat.Vacancy_Management.modules.vacancy.VacancyService;
import br.com.rocketseat.Vacancy_Management.security.dto.TokenDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.naming.AuthenticationException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/companies")
public class CompanyController {

    @Autowired
    CompanyService companyService;

    @Autowired
    VacancyService vacancyService;

    @PostMapping("/create")
    public ResponseEntity<Object> create(@Valid @RequestBody CompanyCreateForm company){
        try {
            var newCompany =  this.companyService.createCompany(company);
            return ResponseEntity.ok(newCompany);
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }


    @PostMapping("/auth")
    public ResponseEntity<Object> login(@RequestBody AuthCompanyForm company) throws AuthenticationException {
        try{
            var token = this.companyService.authenticateCompany(company);
            return ResponseEntity.ok(token);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }

    @GetMapping("/vacancies/")
    @PreAuthorize("hasRole('COMPANY')")
    public ResponseEntity<Optional<List<Vacancy>>> vacanciesOfCompany(HttpServletRequest request){
        var id = request.getAttribute("company_id").toString();

        System.out.println(id);
        Optional<List<Vacancy>> listVacanciesofCompany = this.vacancyService.findByCompanyId(UUID.fromString(id));
        return ResponseEntity.ok(listVacanciesofCompany);
    }

}
