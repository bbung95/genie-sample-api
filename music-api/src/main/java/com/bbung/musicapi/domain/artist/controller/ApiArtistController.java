package com.bbung.musicapi.domain.artist.controller;

import com.bbung.musicapi.common.PageResponse;
import com.bbung.musicapi.domain.artist.dto.*;
import com.bbung.musicapi.domain.artist.exception.ArtistValidationException;
import com.bbung.musicapi.domain.artist.service.ArtistService;
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
@RequestMapping("api/artist")
public class ApiArtistController {

    private final ArtistService artistService;

    private final ObjectMapper objectMapper;

    @PostMapping
    public ResponseEntity saveArtist(@RequestBody @Valid ArtistFormDto artistFormDto,
                                     BindingResult result){

        if(result.hasErrors()){
            throw new ArtistValidationException(result);
        }

        Long id = artistService.saveArtist(artistFormDto);
        ObjectNode bodyData = objectMapper.createObjectNode().put("id", id);

        return ResponseEntity.status(HttpStatus.CREATED).body(bodyData);
    }

    @GetMapping("{id}")
    public ResponseEntity findArtist(@PathVariable Long id){

        ArtistDto artistDto = artistService.findById(id);

        return ResponseEntity.status(HttpStatus.OK).body(artistDto);
    }

    @GetMapping
    public ResponseEntity findArtistList(ArtistSearchParam param){

        PageResponse<ArtistListDto> result = artistService.findList(param);

        System.out.println(result.getPage());

        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @PutMapping("{id}")
    public ResponseEntity updateArtist(@PathVariable Long id,
                                       @RequestBody @Valid ArtistUpdateFormDto artistUpdateFormDto,
                                       BindingResult result) {

        if (result.hasErrors()) {
            throw new ArtistValidationException(result);
        }

        artistService.updateArtist(id, artistUpdateFormDto);

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping("{id}")
    public ResponseEntity deleteArtist(@PathVariable Long id) {

        artistService.deleteArtist(id);

        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
