package com.bbung.musicapi.domain.album.service;

import com.bbung.musicapi.common.MemberInfo;
import com.bbung.musicapi.common.PageResponse;
import com.bbung.musicapi.domain.album.dto.AlbumDto;
import com.bbung.musicapi.domain.album.dto.AlbumFormDto;
import com.bbung.musicapi.domain.album.dto.AlbumSearchParam;
import com.bbung.musicapi.domain.album.exception.AlbumNotFoundException;
import com.bbung.musicapi.domain.album.mapper.AlbumMapper;
import com.bbung.musicapi.domain.artist.service.ArtistService;
import com.bbung.musicapi.domain.track.service.TrackService;
import com.bbung.musicapi.entity.Album;
import com.bbung.musicapi.util.AuthUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AlbumService {

    private final AlbumMapper albumMapper;

    private final ArtistService artistService;

    private final TrackService trackService;

    private final ModelMapper modelMapper;

    private final AuthUtil authUtil;

    @Transactional
    public Long saveAlbum(AlbumFormDto albumFormDto){

        artistService.findById(albumFormDto.getArtistId());

        MemberInfo memberInfo = authUtil.getAuth();

        Album album = modelMapper.map(albumFormDto, Album.class);
        album.setRegistrant(memberInfo.getName());

        albumMapper.insert(album);

        trackService.saveTracks(album.getId(), albumFormDto.getTracks());

        return album.getId();
    }

    public AlbumDto findById(Long id) {

        Optional<AlbumDto> albumDto = albumMapper.findById(id);

        if(!albumDto.isPresent()){
            throw new AlbumNotFoundException(Long.toString(id));
        }

        return albumDto.get();
    }

    public PageResponse<AlbumDto> findList(AlbumSearchParam param) {

        param.searchParamValidate();

        PageHelper.startPage(param.getPageNum(), param.getPageSize());
        PageInfo<AlbumDto> page = PageInfo.of(albumMapper.findList(param));

        return PageResponse.<AlbumDto>builder()
                .page(page)
                .param(param)
                .build();
    }

    public int updateAlbum(Long id, AlbumFormDto albumFormDto) {

        findById(id);
        artistService.findById(albumFormDto.getArtistId());

        int result = albumMapper.update(id, albumFormDto);

        trackService.updateTrack(id, albumFormDto.getTracks());

        return result;
    }

    public int deleteAlbum(Long id){

        findById(id);

        int result = albumMapper.delete(id);

        return result;
    }
}