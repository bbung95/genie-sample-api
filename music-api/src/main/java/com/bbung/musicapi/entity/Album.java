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
public class Album {

    private Long id;
    private String title;
    private String contents;
    private String registrant;
    private LocalDate releaseDate;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;

    private Long artistId;
    private Long genreId;
}
