package com.bbung.musicapi.entity;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Track {

    private Long id;
    private String title;
    private String playTime;
    private String exposure;
    private int orders;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;

    private Long albumId;
}
