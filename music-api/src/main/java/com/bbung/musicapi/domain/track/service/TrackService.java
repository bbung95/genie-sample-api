package com.bbung.musicapi.domain.track.service;

import com.bbung.musicapi.domain.album.service.AlbumService;
import com.bbung.musicapi.domain.track.dto.TrackFormDto;
import com.bbung.musicapi.domain.track.exception.TrackValidationException;
import com.bbung.musicapi.domain.track.mapper.TrackMapper;
import com.bbung.musicapi.entity.Track;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class TrackService {

    private final TrackMapper trackMapper;

    private final ModelMapper modelMapper;

    public void saveTracks(Long albumId, List<TrackFormDto> trackFormList){

        if(trackFormList.size() > 0) {
            List<Track> tracks = trackToArray(albumId, trackFormList);
            trackMapper.insert(tracks);
        }
    }

    public void updateTrack(Long albumId, List<TrackFormDto> trackFormList) {

        List<Track> tracks = trackToArray(albumId, trackFormList);
        deleteTrack(albumId, tracks);

        List<Track> insertList = tracks.stream().filter(item -> item.getId() == null).collect(Collectors.toList());
        if(insertList.size() > 0){
            trackMapper.insert(insertList);
        }
        List<Track> updateList = tracks.stream().filter(item -> item.getId() != null).collect(Collectors.toList());
        if(updateList.size() > 0){
            trackMapper.update(updateList);
        }
    }

    private List<Track> trackToArray(Long albumId, List<TrackFormDto> trackFormList){
        return trackFormList.stream()
                .map(item -> {
                    Track map = modelMapper.map(item, Track.class);
                    map.setAlbumId(albumId);
                    return map;
                })
                .collect(Collectors.toList());
    }

    private void deleteTrack(Long albumId, List<Track> tracks){
        List<Track> deleteList = trackMapper.findList(albumId).stream()
                .filter(item -> !tracks.stream()
                        .map(i -> i.getId()).collect(Collectors.toList())
                        .contains(item.getId())).collect(Collectors.toList());

        trackMapper.delete(deleteList);
    };

//    private void tracksValidationCheck(List<TrackFormDto> trackFormList){
//
//        trackFormList.forEach(item -> {
//            if(item.getTitle() == null){
//                throw new TrackValidationException("음원명");
//            }else if(item.getPlayTime() == null){
//                throw new TrackValidationException("재생시간");
//            }else if(item.getExposure() == null){
//                throw new TrackValidationException("노출여부");
//            }else if(item.getOrders() == 0){
//                throw new TrackValidationException("순서");
//            }
//        });
//
//    }

}
