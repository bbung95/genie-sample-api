package com.bbung.musicapi.domain.artist.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Setter
@Getter
@ToString
public class ArtistDto {

    private Long id;
    private String name;
    private String agency;
    private String nationality;
    private String contents;
    private String registrant;
    private LocalDate birthday;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;

}
