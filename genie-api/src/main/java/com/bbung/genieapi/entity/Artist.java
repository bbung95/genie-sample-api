package com.bbung.genieapi.entity;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Artist {

    private Long id;
    private String name;
    private String agency;
    private String nationality;
    private String contents;
    private String registrant;
    private LocalDate birthday;
    private LocalDateTime created_date;
    private LocalDateTime modified_date;
}
