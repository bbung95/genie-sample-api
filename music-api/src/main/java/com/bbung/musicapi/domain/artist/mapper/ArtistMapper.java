package com.bbung.musicapi.domain.artist.mapper;

import com.bbung.musicapi.common.SearchParam;
import com.bbung.musicapi.domain.artist.dto.ArtistDto;
import com.bbung.musicapi.domain.artist.dto.ArtistListDto;
import com.bbung.musicapi.domain.artist.dto.ArtistUpdateFormDto;
import com.bbung.musicapi.entity.Artist;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Optional;

@Mapper
public interface ArtistMapper {

    Long insert(Artist artist);

    Optional<ArtistDto> findById(Long id);

    List<ArtistListDto> findList(SearchParam param);

    int update(@Param("id") Long id, @Param("artist") ArtistUpdateFormDto artistUpdateFormDto);

    int delete(Long id);
}
