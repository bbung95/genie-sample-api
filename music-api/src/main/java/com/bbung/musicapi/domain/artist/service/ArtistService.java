package com.bbung.musicapi.domain.artist.service;

import com.bbung.musicapi.common.MemberInfo;
import com.bbung.musicapi.common.PageResponse;
import com.bbung.musicapi.domain.album.listner.AlbumEventHandler;
import com.bbung.musicapi.domain.artist.dto.*;
import com.bbung.musicapi.domain.artist.exception.ArtistNotFoundException;
import com.bbung.musicapi.domain.artist.listner.ArtistDeleteEvent;
import com.bbung.musicapi.domain.artist.mapper.ArtistMapper;
import com.bbung.musicapi.entity.Artist;
import com.bbung.musicapi.util.AuthUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ArtistService {

    private final ArtistMapper artistMapper;

    private final ModelMapper modelMapper;

    private final AuthUtil authUtil;

    private final ApplicationEventPublisher applicationEventPublisher;

    public Long saveArtist(ArtistFormDto artistFormDto){

        MemberInfo memberInfo = authUtil.getAuth();

        Artist artist = modelMapper.map(artistFormDto, Artist.class);
        artist.setRegistrant(memberInfo.getName());

        artistMapper.insert(artist);

        return artist.getId();
    }

    public ArtistDto findById(Long id){

        Optional<ArtistDto> findArtist = artistMapper.findById(id);

        if(!findArtist.isPresent()){
            throw new ArtistNotFoundException(Long.toString(id));
        }

        return findArtist.get();
    }

    public PageResponse<ArtistListDto> findList(ArtistSearchParam param){

        param.searchParamValidate();

        PageHelper.startPage(param.getPageNum(), param.getPageSize());
        PageInfo<ArtistListDto> page = PageInfo.of(artistMapper.findList(param));

        return PageResponse.<ArtistListDto>builder()
                .param(param)
                .page(page)
                .build();
    }

    public int updateArtist(Long id, ArtistUpdateFormDto artistUpdateFormDto) {

        findById(id);

        int result = artistMapper.update(id, artistUpdateFormDto);

        return result;
    }

    @Transactional
    public int deleteArtist(Long id) {

        findById(id);

        applicationEventPublisher.publishEvent(new ArtistDeleteEvent(id));

        return 1;
    }

}
