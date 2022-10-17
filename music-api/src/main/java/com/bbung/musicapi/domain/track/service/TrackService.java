package com.bbung.musicapi.domain.track.service;

import com.bbung.musicapi.domain.track.dto.TrackDto;
import com.bbung.musicapi.domain.track.dto.TrackFormDto;
import com.bbung.musicapi.domain.track.mapper.TrackMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class TrackService {

    private final TrackMapper trackMapper;

    public void saveTracks(Long albumId, List<TrackFormDto> trackFormList){

        if(trackFormList.size() > 0) {
            trackToArray(albumId, trackFormList);
            trackMapper.insert(trackFormList);
        }
    }

    public void updateTrack(Long albumId, List<TrackFormDto> trackFormList) {

        trackToArray(albumId, trackFormList);
        deleteTracks(albumId, trackFormList);
        insertTracks(trackFormList);
        updateTracks(trackFormList);
    }

    private void trackToArray(Long albumId, List<TrackFormDto> trackFormList){
        trackFormList.stream().forEach(item -> item.setAlbumId(albumId));
    }

    private void insertTracks(List<TrackFormDto> tracks) {
        List<TrackFormDto> insertList = tracks.stream()
                .filter(item -> item.getId() == null).collect(Collectors.toList());
        if(insertList.size() > 0){
            trackMapper.insert(insertList);
        }
    }

    private void updateTracks(List<TrackFormDto> tracks) {
        List<TrackFormDto> updateList = tracks.stream()
                .filter(item -> item.getId() != null).collect(Collectors.toList());
        if(updateList.size() > 0){
            trackMapper.update(updateList);
        }
    }

    private void deleteTracks(Long albumId, List<TrackFormDto> tracks){
        List<TrackDto> deleteList = trackMapper.findList(albumId).stream()
                .filter(item -> !tracks.stream()
                        .map(i -> i.getId()).collect(Collectors.toList())
                        .contains(item.getId())).collect(Collectors.toList());

        trackMapper.delete(deleteList);
    };

}
