package com.bbung.musicapi.domain.album.mapper;

import com.bbung.musicapi.domain.album.dto.AlbumDto;
import com.bbung.musicapi.domain.album.dto.AlbumFormDto;
import com.bbung.musicapi.domain.album.dto.AlbumSearchParam;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Optional;

@Mapper
public interface AlbumMapper {

    int insert(AlbumFormDto album);

    Optional<AlbumDto> findById(Long id);

    List<AlbumDto> findList(AlbumSearchParam param);

    int update(@Param("id") Long id, @Param("album") AlbumFormDto albumFormDto);

    int delete(Long id);

    List<Long> artistAlbumList(Long artistId);
}
