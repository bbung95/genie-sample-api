package com.bbung.genieapi.domain.artist.service;

import com.bbung.genieapi.common.MemberInfo;
import com.bbung.genieapi.common.PageResponse;
import com.bbung.genieapi.domain.artist.dto.*;
import com.bbung.genieapi.domain.artist.exception.ArtistNotFoundException;
import com.bbung.genieapi.domain.artist.mapper.ArtistMapper;
import com.bbung.genieapi.entity.Artist;
import com.bbung.genieapi.util.AuthUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ArtistService {

    private final ArtistMapper artistMapper;

    private final ModelMapper modelMapper;

    private final AuthUtil authUtil;

    public Long saveArtist(ArtistFormDto artistFormDto){

        MemberInfo memberInfo = authUtil.getAuth();

        Artist artist = modelMapper.map(artistFormDto, Artist.class);
        artist.setRegistrant(memberInfo.getName());

        Long id = artistMapper.insert(artist);

        return id;
    }

    public ArtistDto findById(Long id){

        Optional<ArtistDto> findArtist = artistMapper.findById(id);

        if(!findArtist.isPresent()){
            throw new ArtistNotFoundException(Long.toString(id));
        }

        return findArtist.get();
    }

    public PageResponse<ArtistListDto> findList(ArtistSearchParam param){

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

    public int deleteArtist(Long id) {

        findById(id);

        int result = artistMapper.delete(id);

        return result;
    }
}
