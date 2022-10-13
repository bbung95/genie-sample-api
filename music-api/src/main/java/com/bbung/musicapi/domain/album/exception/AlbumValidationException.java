package com.bbung.musicapi.domain.album.exception;

import com.bbung.musicapi.util.ExceptionMessageUtil;
import org.springframework.validation.BindingResult;

public class AlbumValidationException extends RuntimeException {
    public AlbumValidationException(BindingResult bindingResult) {
        super(ExceptionMessageUtil.messageParse(bindingResult));
    }
}
