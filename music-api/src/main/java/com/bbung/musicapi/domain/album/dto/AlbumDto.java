package com.bbung.musicapi.domain.album.dto;

import com.bbung.musicapi.domain.track.dto.TrackDto;
import com.bbung.musicapi.domain.track.dto.TrackFormDto;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@ToString
public class AlbumDto {

    public interface AlbumDetailView{};
    public interface AlbumListView{};

    private Long id;
    private String artistName;
    private String title;
    private String registrant;
    private String genreTitle;
    private String contents;
    private LocalDate releaseDate;
    private LocalDateTime createdDate;

    @JsonView(AlbumDetailView.class)
    public List<TrackFormDto> tracks = new ArrayList<>();
}
