package com.bbung.musicapi.domain.track.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class TrackFormDto {

    private Long id;
    private int orders;
    private String title;
    private String playTime;
    private String exposure;
}
