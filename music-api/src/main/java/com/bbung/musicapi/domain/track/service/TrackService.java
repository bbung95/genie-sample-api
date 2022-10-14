package com.bbung.musicapi.domain.track.service;

import com.bbung.musicapi.domain.track.dto.TrackFormDto;
import com.bbung.musicapi.domain.track.mapper.TrackMapper;
import com.bbung.musicapi.entity.Track;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
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

        deleteTracks(albumId, tracks);
        insertTracks(tracks);
        updateTracks(tracks);
    }

    private List<Track> trackToArray(Long albumId, List<TrackFormDto> trackFormList){
        return trackFormList.stream()
                .map(item -> {
                    Track track = modelMapper.map(item, Track.class);
                    track.setAlbumId(albumId);
                    return track;
                })
                .collect(Collectors.toList());
    }

    private void insertTracks(List<Track> tracks) {
        List<Track> insertList = tracks.stream()
                .filter(item -> item.getId() == null).collect(Collectors.toList());
        if(insertList.size() > 0){
            trackMapper.insert(insertList);
        }
    }

    private void updateTracks(List<Track> tracks) {
        List<Track> updateList = tracks.stream()
                .filter(item -> item.getId() != null).collect(Collectors.toList());
        if(updateList.size() > 0){
            trackMapper.update(updateList);
        }
    }

    private void deleteTracks(Long albumId, List<Track> tracks){
        List<Track> deleteList = trackMapper.findList(albumId).stream()
                .filter(item -> !tracks.stream()
                        .map(i -> i.getId()).collect(Collectors.toList())
                        .contains(item.getId())).collect(Collectors.toList());

        trackMapper.delete(deleteList);
    };

}
