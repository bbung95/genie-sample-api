package com.bbung.musicapi.domain.track.mapper;

import com.bbung.musicapi.domain.track.dto.TrackDto;
import com.bbung.musicapi.domain.track.dto.TrackFormDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TrackMapper {

    int insert(List<TrackFormDto> tracks);

    int update(List<TrackFormDto> tracks);

    List<TrackDto> findList(Long albumId);

    int delete(List<TrackDto> list);

    void deleteAll(Long albumId);
}
