package com.bbung.musicapi.domain.artist.exception;

import com.bbung.musicapi.util.ExceptionMessageUtil;
import org.springframework.validation.BindingResult;

public class ArtistValidationException extends RuntimeException {
    public ArtistValidationException(BindingResult result) {
        super(ExceptionMessageUtil.messageParse(result));
    }
}
