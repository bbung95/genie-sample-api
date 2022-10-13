package com.bbung.musicapi.domain.album.dto;

import com.bbung.musicapi.domain.track.dto.TrackDto;
import com.bbung.musicapi.domain.track.dto.TrackFormDto;
import lombok.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AlbumFormDto {

    @Min(value = 1, message = "아티스트를 선택해주세요.")
    @NotNull(message = "아티스트를 선택해주세요.")
    private Long artistId = 0L;
    @NotBlank(message = "앨범명을 입력해주세요.")
    private String title;
    @NotNull(message = "발매일을 입력해주세요.")
    private LocalDate releaseDate;
    private Long genreId = 0L;
    private String contents;

    @Builder.Default
    private List<TrackFormDto> tracks = new ArrayList<>();
}
