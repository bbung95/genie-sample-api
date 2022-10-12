package com.bbung.musicapi.domain.track.mapper;

import com.bbung.musicapi.entity.Track;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TrackMapper {

    int insert(Track track);
}
