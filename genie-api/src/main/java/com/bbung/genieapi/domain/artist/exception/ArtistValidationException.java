package com.bbung.genieapi.domain.artist.exception;

import com.bbung.genieapi.util.ExceptionMessageUtil;
import org.springframework.validation.BindingResult;

public class ArtistValidationException extends RuntimeException {
    public ArtistValidationException(BindingResult result) {
        super(ExceptionMessageUtil.messageParse(result));
    }
}
