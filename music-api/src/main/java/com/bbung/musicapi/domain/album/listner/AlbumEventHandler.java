package com.bbung.musicapi.domain.album.listner;

import com.bbung.musicapi.domain.album.dto.AlbumDto;
import com.bbung.musicapi.domain.album.mapper.AlbumMapper;
import com.bbung.musicapi.domain.album.service.AlbumService;
import com.bbung.musicapi.domain.track.mapper.TrackMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AlbumEventHandler {

    private final AlbumMapper albumMapper;
    private final TrackMapper trackMapper;

    @EventListener
    @Order(1)
    public void trackDelete(AlbumDeleteEvent event){

        System.out.println("trackDelete");

        trackMapper.deleteAll(event.getId());
    }

    @EventListener
    @Order(2)
    public void albumDelete(AlbumDeleteEvent event){
        System.out.println("albumDelete");

        albumMapper.delete(event.getId());
    }
}
