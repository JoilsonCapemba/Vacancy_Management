package br.com.rocketseat.Vacancy_Management.exceptions;

public class UserFoudException extends RuntimeException{
    public UserFoudException(){
        super("Usuario ja existe");
    }
}
