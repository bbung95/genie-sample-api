package com.bbung.musicapi.domain.track.exception;

public class TrackValidationException extends RuntimeException {

    public TrackValidationException(String message) {
        super(message + "를 입력해주세요.");
    }
}
