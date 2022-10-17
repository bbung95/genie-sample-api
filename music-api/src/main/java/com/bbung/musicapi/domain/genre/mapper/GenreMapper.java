package com.bbung.musicapi.domain.genre.mapper;

import com.bbung.musicapi.domain.genre.dto.Genre;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface GenreMapper {

    List<Genre> findList();
}
