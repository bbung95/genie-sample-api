package com.bbung.musicapi.domain.album.dto;

import com.bbung.musicapi.domain.track.dto.TrackDto;
import com.bbung.musicapi.domain.track.dto.TrackFormDto;
import lombok.*;

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

    private Long artistId;
    private String title;
    private LocalDate releaseDate;
    private Long genreId;
    private String contents;

    public List<TrackFormDto> tracks = new ArrayList<>();
}
