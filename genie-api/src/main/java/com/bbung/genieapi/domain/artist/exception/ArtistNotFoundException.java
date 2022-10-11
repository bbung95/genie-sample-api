package com.bbung.genieapi.domain.artist.exception;

public class ArtistNotFoundException extends RuntimeException {
    public ArtistNotFoundException(String message) {
        super("해당 ID " + message + "가 존재하지 않습니다.");
    }
}
