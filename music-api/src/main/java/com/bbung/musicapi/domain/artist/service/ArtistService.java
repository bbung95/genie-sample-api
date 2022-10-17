package com.bbung.musicapi.domain.artist.service;

import com.bbung.musicapi.common.MemberInfo;
import com.bbung.musicapi.common.PageResponse;
import com.bbung.musicapi.domain.artist.dto.*;
import com.bbung.musicapi.domain.artist.exception.ArtistNotFoundException;
import com.bbung.musicapi.domain.artist.listener.ArtistDeleteEvent;
import com.bbung.musicapi.domain.artist.mapper.ArtistMapper;
import com.bbung.musicapi.util.AuthUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ArtistService {

    private final ArtistMapper artistMapper;
    private final ModelMapper modelMapper;
    private final AuthUtil authUtil;

    private final ApplicationEventPublisher applicationEventPublisher;

    public Long saveArtist(ArtistFormDto artistFormDto){

        MemberInfo memberInfo = authUtil.getAuth();
        artistFormDto.setRegistrant(memberInfo.getName());

        artistMapper.insert(artistFormDto);

        return artistFormDto.getId();
    }

    public ArtistDto findById(Long id){

        ArtistDto artistDto = artistMapper.findById(id)
                .orElseThrow(() -> new ArtistNotFoundException(Long.toString(id)));

        return artistDto;
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

    public void updateArtist(Long id, ArtistUpdateFormDto artistUpdateFormDto) {

        findById(id);
        artistMapper.update(id, artistUpdateFormDto);
    }

    @Transactional
    public void deleteArtist(Long id) {

        findById(id);
        applicationEventPublisher.publishEvent(new ArtistDeleteEvent(id));
    }

}
