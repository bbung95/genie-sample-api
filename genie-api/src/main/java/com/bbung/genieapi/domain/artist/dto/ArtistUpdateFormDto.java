package com.bbung.genieapi.domain.artist.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Setter
@Getter
@ToString
public class ArtistUpdateFormDto {

    private String name;
    private LocalDate birthday;
    private String agency;
    private String nationality;
    private String contents;
}
