package com.bbung.musicapi.domain.artist.listener;

import com.bbung.musicapi.domain.album.mapper.AlbumMapper;
import com.bbung.musicapi.domain.album.service.AlbumService;
import com.bbung.musicapi.domain.artist.mapper.ArtistMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class ArtistEventHandler {

    private final ArtistMapper artistMapper;
    private final AlbumService albumService;
    private final AlbumMapper albumMapper;

    @EventListener
    @Order(1)
    public void albumDelete(ArtistDeleteEvent event){

        List<Long> list = albumMapper.artistAlbumList(event.getId());

        list.stream().forEach(item -> albumService.deleteAlbum(item));
    }

    @EventListener
    @Order(2)
    public void artistDelete(ArtistDeleteEvent event){

        log.info("ArtistDelete");

        artistMapper.delete(event.getId());
    }
}
