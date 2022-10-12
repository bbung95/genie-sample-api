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
public class Genre {

    private Long id;
    private String title;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;
}
