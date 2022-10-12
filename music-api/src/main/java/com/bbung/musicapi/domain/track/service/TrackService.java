package com.bbung.musicapi.domain.track.service;

import com.bbung.musicapi.domain.track.dto.TrackFormDto;
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

    @Transactional
    public void updateTrack(Long albumId, List<TrackFormDto> trackFormList) {

        List<Track> tracks = trackToArray(albumId, trackFormList);

        trackMapper.delete(deleteTrackToArray(albumId, tracks));
        trackMapper.insert(tracks.stream().filter(item -> item.getId() == null).collect(Collectors.toList()));
        trackMapper.update(tracks.stream().filter(item -> item.getId() != null).collect(Collectors.toList()));
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

    private List<Track> deleteTrackToArray(Long albumId, List<Track> tracks){
        return trackMapper.findList(albumId).stream()
                .filter(item -> !tracks.stream()
                        .map(i -> i.getId()).collect(Collectors.toList())
                        .contains(item.getId())).collect(Collectors.toList());
    };
}
