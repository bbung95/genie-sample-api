package com.bbung.genieapi.domain.artist.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Setter
@Getter
@ToString
public class ArtistListDto {

    private Long id;
    private String name;
    private String agency;
    private String nationality;
    private String contents;
    private String registrant;
    private LocalDate birthday;
    private LocalDateTime created_date;

}
