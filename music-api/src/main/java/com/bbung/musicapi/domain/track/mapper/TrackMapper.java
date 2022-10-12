package com.bbung.musicapi.domain.track.mapper;

import com.bbung.musicapi.entity.Track;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TrackMapper {

    int insert(List<Track> tracks);

    int update(List<Track> tracks);

    List<Track> findList(Long albumId);

    int delete(List<Track> list);
}
