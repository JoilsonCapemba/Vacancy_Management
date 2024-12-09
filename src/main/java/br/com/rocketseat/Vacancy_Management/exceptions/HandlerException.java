package br.com.rocketseat.Vacancy_Management.exceptions;

import br.com.rocketseat.Vacancy_Management.exceptions.dto.ErrorMessageDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class HandlerException {

    @Autowired
    MessageSource messageSource;

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<ErrorMessageDTO>>
    handleMethodArgumentNotValidException(MethodArgumentNotValidException exceptions){

        List<ErrorMessageDTO> listError = new ArrayList<>();

        exceptions.getBindingResult().getFieldErrors().forEach(err ->{
                    String message = messageSource.getMessage(err, LocaleContextHolder.getLocale());
                    listError.add(new ErrorMessageDTO(message, err.getField()));
                }
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(listError);
    }
}
