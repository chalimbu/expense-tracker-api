package com.pairlearning.expensetrackerapi.exeptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class EtBadRequestExeption extends RuntimeException{

    public EtBadRequestExeption(final String message){
        super(message);
    }
}
