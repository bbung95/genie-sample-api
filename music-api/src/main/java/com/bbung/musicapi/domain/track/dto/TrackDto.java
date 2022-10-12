package com.bbung.musicapi.domain.track.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Setter
@Getter
@ToString
public class TrackDto {

    private Long id;
    private String title;
    private String playTime;
    private String exposure;
    private int orders;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;

}
