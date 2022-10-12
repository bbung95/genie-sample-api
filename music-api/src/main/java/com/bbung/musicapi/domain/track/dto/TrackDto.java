package com.bbung.musicapi.domain.track.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class TrackDto {

    private Long id;
    private String title;
    private String playTime;
    private String exposure;
    private int orders;
}
