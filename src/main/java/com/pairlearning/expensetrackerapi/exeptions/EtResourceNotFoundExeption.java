package com.pairlearning.expensetrackerapi.exeptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class EtResourceNotFoundExeption extends RuntimeException{

    public EtResourceNotFoundExeption(final String message){
        super(message);
    }
}
