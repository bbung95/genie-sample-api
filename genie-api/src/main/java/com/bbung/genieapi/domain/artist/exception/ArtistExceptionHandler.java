package com.bbung.genieapi.domain.artist.exception;

import com.bbung.genieapi.util.ExceptionMessageUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ArtistExceptionHandler {

    @ExceptionHandler({
            ArtistNotFoundException.class,
            ArtistValidationException.class
    })
    public ResponseEntity badRequestException(RuntimeException e){

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ExceptionMessageUtil.customErrorMessage(HttpStatus.BAD_REQUEST.value(), e.getMessage()));
    }
}
