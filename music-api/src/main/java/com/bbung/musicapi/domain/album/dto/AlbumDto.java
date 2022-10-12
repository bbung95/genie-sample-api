package com.bbung.musicapi.domain.album.dto;

import com.bbung.musicapi.entity.Track;
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

    private Long id;
    private String artistName;
    private String title;
    private String registrant;
    private String genreTitle;
    private String contents;
    private LocalDate releaseDate;
    private LocalDateTime createdDate;

    @JsonView(AlbumDetailView.class)
    public List<Track> tracks = new ArrayList<>();
}
