package com.bbung.musicapi.domain.artist.exception;

public class ParamValidationException extends RuntimeException {
    public ParamValidationException() {
        super("요청을 확인해주세요.");
    }
}
