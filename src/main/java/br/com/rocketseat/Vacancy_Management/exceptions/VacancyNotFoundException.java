package br.com.rocketseat.Vacancy_Management.exceptions;

public class VacancyNotFoundException extends RuntimeException{
    public VacancyNotFoundException(){
        super("Vacancy Not Found");
    }
}
