package com.bbung.genieapi.domain.artist.exception;

public class ArtistParamValidationException extends RuntimeException {
    public ArtistParamValidationException() {
        super("요청을 확인해주세요.");
    }
}
