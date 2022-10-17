package com.bbung.musicapi.domain.artist.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ArtistFormDto {

    @NotBlank(message = "아티스트명을 입력해주세요.")
    private String name;
    @JsonFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "출생일을 입력해주세요.")
    private LocalDate birthday;
    private String agency;
    private String nationality;
    private String contents;

    private String registrant;
    private Long id;
}
