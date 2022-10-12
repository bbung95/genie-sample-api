package com.bbung.musicapi.domain.album.exception;

import org.modelmapper.convention.MatchingStrategies;

public class AlbumNotFoundException extends RuntimeException {
    public AlbumNotFoundException(String message) {
        super("해당 ID " + message + "가 존재하지 않습니다.");
    }
}
