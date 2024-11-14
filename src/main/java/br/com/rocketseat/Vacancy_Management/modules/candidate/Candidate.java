package br.com.rocketseat.Vacancy_Management.modules.candidate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Candidate {
    private UUID id;
    private String name;
    private String userName;
    private String email;
    private String password;
    private String description;
    private String curriculum;

}
