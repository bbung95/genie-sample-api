package com.bbung.musicapi.domain.album.controller;

import com.bbung.musicapi.common.PageResponse;
import com.bbung.musicapi.domain.album.dto.AlbumDto;
import com.bbung.musicapi.domain.album.dto.AlbumFormDto;
import com.bbung.musicapi.domain.album.dto.AlbumSearchParam;
import com.bbung.musicapi.domain.album.exception.AlbumValidationException;
import com.bbung.musicapi.domain.album.service.AlbumService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/album")
public class ApiAlbumController {

    private final AlbumService albumService;

    private final ObjectMapper objectMapper;

    @PostMapping
    public ResponseEntity saveAlbum(@RequestBody @Valid AlbumFormDto albumFormDto, BindingResult bindingResult){

        if(bindingResult.hasErrors()){
            throw new AlbumValidationException(bindingResult);
        }

        Long id = albumService.saveAlbum(albumFormDto);
        ObjectNode result = objectMapper.createObjectNode().put("id", id);

        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @GetMapping("{id}")
    public ResponseEntity findAlbum(@PathVariable Long id){

        AlbumDto findAlbum = albumService.findById(id);

        return ResponseEntity.status(HttpStatus.OK).body(findAlbum);
    }

    @GetMapping
    public ResponseEntity findAlbumList(AlbumSearchParam param){

        PageResponse<AlbumDto> result = albumService.findList(param);

        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @PutMapping("{id}")
    public ResponseEntity updateAlbum(@PathVariable Long id,
                                      @RequestBody @Valid AlbumFormDto albumFormDto,
                                      BindingResult bindingResult) {

        if(bindingResult.hasErrors()){
            throw new AlbumValidationException(bindingResult);
        }

        albumService.updateAlbum(id, albumFormDto);

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping("{id}")
    public ResponseEntity deleteAlbum(@PathVariable Long id){

        albumService.deleteAlbum(id);

        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
