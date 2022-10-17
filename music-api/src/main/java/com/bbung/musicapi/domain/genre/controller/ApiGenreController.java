package com.bbung.musicapi.domain.genre.controller;

import com.bbung.musicapi.domain.genre.dto.GenreListDto;
import com.bbung.musicapi.domain.genre.mapper.GenreMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/genre")
public class ApiGenreController {

    private final GenreMapper genreMapper;

    @GetMapping
    public ResponseEntity findList(){

        List<GenreListDto> list = genreMapper.findList();

        return ResponseEntity.status(HttpStatus.OK).body(list);
    }
}
