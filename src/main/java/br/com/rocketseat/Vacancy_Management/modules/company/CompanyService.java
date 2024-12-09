package br.com.rocketseat.Vacancy_Management.modules.company;

import br.com.rocketseat.Vacancy_Management.exceptions.UserFoudException;
import br.com.rocketseat.Vacancy_Management.security.TokenService;
import br.com.rocketseat.Vacancy_Management.security.dto.TokenDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.method.annotation.HandlerMethodValidationException;

import javax.naming.AuthenticationException;
import java.util.Optional;

@Service
public class CompanyService {

    @Autowired
    CompanyRepository companyRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    TokenService tokenService;

    public Company createCompany(CompanyCreateForm company){
        this.companyRepository.findByNameOrEmail(company.name(), company.email())
                .ifPresent((companyFinded)->{
                    throw new UserFoudException();
                });

        String passwordEncrypted = passwordEncoder.encode(company.password());
        Company newCompany = new Company(company.username(), company.name(), company.email(), passwordEncrypted, company.website(), company.description());
        return this.companyRepository.save(newCompany);
    }



    public TokenDTO authenticateCompany(AuthCompanyForm authCompanyForm) throws AuthenticationException {
        var company = this.companyRepository.findByUsername(authCompanyForm.username()).orElseThrow(
                ()-> {
                    throw new UsernameNotFoundException("username/password incorrect");
                }
        );

        // Verificar se as senhas são iguais
        //se forem iguais gerar o token Se não gera erro
        var passwordMatches = this.passwordEncoder.matches(authCompanyForm.password(), company.getPassword());

        if(!passwordMatches){
            throw new AuthenticationException();
        }
        return this.tokenService.generateToken(company);
    }
}
