package com.bbung.musicapi.domain.album.listener;

import com.bbung.musicapi.domain.album.mapper.AlbumMapper;
import com.bbung.musicapi.domain.track.mapper.TrackMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class AlbumEventHandler {

    private final AlbumMapper albumMapper;
    private final TrackMapper trackMapper;

    @EventListener
    @Order(1)
    public void trackDelete(AlbumDeleteEvent event){

        log.info("TrackDelete");

        trackMapper.deleteAll(event.getId());
    }

    @EventListener
    @Order(2)
    public void albumDelete(AlbumDeleteEvent event){

        log.info("albumDelete");

        albumMapper.delete(event.getId());
    }
}
