package br.com.rocketseat.Vacancy_Management.exceptions;

public class UserNotFoundException extends RuntimeException{
    public UserNotFoundException(){
        super("User not found");
    }
}
