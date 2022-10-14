package com.bbung.musicapi.domain.album.controller;

import com.bbung.musicapi.domain.album.dto.AlbumFormDto;
import com.bbung.musicapi.domain.album.service.AlbumService;
import com.bbung.musicapi.domain.artist.mapper.ArtistMapper;
import com.bbung.musicapi.domain.track.dto.TrackFormDto;
import com.bbung.musicapi.domain.track.enums.TrackExposure;
import com.bbung.musicapi.entity.Artist;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Sql("/truncate.sql")
class ApiAlbumControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AlbumService albumService;

    @Autowired
    private ArtistMapper artistMapper;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void sampleArtist(){
        Artist artist = Artist.builder()
                .name("뉴진스")
                .agency("ADOR")
                .birthday(LocalDate.of(2022, 7, 22))
                .contents("하이브 소속")
                .nationality("다국적")
                .registrant("bbung")
                .build();

        artistMapper.insert(artist);
    }

    @BeforeEach
    public void sampleAlbum(){
        AlbumFormDto albumFormDto = AlbumFormDto.builder()
                .title("NewJeans")
                .contents("데뷔앨범")
                .releaseDate(LocalDate.of(2022, 07, 20))
                .artistId(1L)
                .genreId(1L)
                .tracks(trackList())
                .build();

        albumService.saveAlbum(albumFormDto);
    }

    @Nested
    @DisplayName("앨범 등록 Mock Test")
    class insertAlbumMock{

        private final Long SUCCESS_ID = 1L;
        private final Long FAIL_ID = 100L;

        @Test
        @DisplayName("정상적으로 동작한 경우 200")
        public void insertAlbumTest() throws Exception {

            AlbumFormDto albumFormDto = AlbumFormDto.builder()
                    .title("NewJeans")
                    .contents("데뷔앨범")
                    .releaseDate(LocalDate.of(2022, 07, 20))
                    .artistId(SUCCESS_ID)
                    .genreId(1L)
                    .tracks(trackList())
                    .build();

            mockMvc.perform(post("/api/album")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(albumFormDto)))
                    .andDo(print())
                    .andExpect(status().isCreated())
                    .andExpect(jsonPath("id").exists());
        }

        @Test
        @DisplayName("아티스트 ID가 존재하지 않을 경우 400")
        public void insertAlbumArtistNotFoundBadRequest() throws Exception {

            AlbumFormDto albumFormDto = AlbumFormDto.builder()
                    .title("NewJeans")
                    .contents("데뷔앨범")
                    .releaseDate(LocalDate.of(2022, 07, 20))
                    .artistId(FAIL_ID)
                    .genreId(1L)
                    .tracks(trackList())
                    .build();

            mockMvc.perform(post("/api/album")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(albumFormDto)))
                    .andDo(print())
                    .andExpect(status().isBadRequest());
        }

        @Test
        @DisplayName("필수값을 입력하지 않은 경우 400")
        public void insertAlbumValidationBadRequest() throws Exception {

            AlbumFormDto albumFormDto = AlbumFormDto.builder()
                    .title("")
                    .contents("")
                    .releaseDate(LocalDate.of(2022, 07, 20))
                    .artistId(SUCCESS_ID)
                    .genreId(1L)
                    .tracks(trackList())
                    .build();

            mockMvc.perform(post("/api/album")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(albumFormDto)))
                    .andDo(print())
                    .andExpect(status().isBadRequest());
        }
    }

    @Nested
    @DisplayName("앨범 상세 Mock Test")
    class findAlbumMock{

        private final Long SUCCESS_ID = 1L;
        private final Long FAIL_ID = 100L;

        @Test
        @DisplayName("정상적으로 동작한 경우 200")
        public void findAlbumTest() throws Exception {

            mockMvc.perform(get("/api/album/{id}", SUCCESS_ID))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("title").exists())
                    .andExpect(jsonPath("contents").exists())
                    .andExpect(jsonPath("artistName").exists());
        }

        @Test
        @DisplayName("앨범 ID가 존재하지 않을 경우 400")
        public void findAlbumNotfoundTest() throws Exception {
            mockMvc.perform(get("/api/album/{id}", FAIL_ID))
                    .andDo(print())
                    .andExpect(status().isBadRequest());
        }
    }

    @Nested
    @DisplayName("앨범 수정 Mock Test")
    class updateAlbumMock{

        private final Long SUCCESS_ID = 1L;
        private final Long FAIL_ID = 100L;

        @Test
        @DisplayName("정상적으로 동작한 경우 200")
        public void findAlbumTest() throws Exception {

            AlbumFormDto album = AlbumFormDto.builder()
                    .title("르세라핌 1st")
                    .contents("데뷔앨범")
                    .releaseDate(LocalDate.of(2022, 07, 20))
                    .artistId(1L)
                    .genreId(1L)
                    .tracks(new ArrayList<>())
                    .build();

            mockMvc.perform(put("/api/album/{id}", SUCCESS_ID)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(album)))
                    .andDo(print())
                    .andExpect(status().isOk());
        }

        @Test
        @DisplayName("앨범 ID가 존재하지 않을 경우 400")
        public void findAlbumNotfoundTest() throws Exception {

            AlbumFormDto album = AlbumFormDto.builder()
                    .title("르세라핌 1st")
                    .contents("앨범")
                    .releaseDate(LocalDate.of(2022, 07, 20))
                    .artistId(1L)
                    .genreId(1L)
                    .tracks(new ArrayList<>())
                    .build();

            mockMvc.perform(put("/api/album/{id}", FAIL_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(album)))
                    .andDo(print())
                    .andExpect(status().isBadRequest());
        }

        @Test
        @DisplayName("아티스트 ID가 존재하지 않을 경우 400")
        public void findAlbumArtistNotfoundTest() throws Exception {

            AlbumFormDto album = AlbumFormDto.builder()
                    .title("르세라핌 1st")
                    .contents("앨범")
                    .releaseDate(LocalDate.of(2022, 07, 20))
                    .artistId(100L)
                    .genreId(1L)
                    .tracks(new ArrayList<>())
                    .build();

            mockMvc.perform(put("/api/album/{id}", FAIL_ID)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(album)))
                    .andDo(print())
                    .andExpect(status().isBadRequest());
        }

        @Test
        @DisplayName("필수값을 입력하지 않은 경우 400")
        public void findAlbumValidationTest() throws Exception {

            AlbumFormDto album = AlbumFormDto.builder()
                    .title("")
                    .contents("앨범")
                    .releaseDate(LocalDate.of(2022, 07, 20))
                    .artistId(1L)
                    .genreId(1L)
                    .tracks(trackList())
                    .build();

            mockMvc.perform(put("/api/album/{id}", FAIL_ID)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(album)))
                    .andDo(print())
                    .andExpect(status().isBadRequest());
        }
    }

    @Nested
    @DisplayName("앨범 삭제 Mock Test")
    class deleteAlbum{

        private final Long SUCCESS_ID = 1L;
        private final Long FAIL_ID = 100L;

        @Test
        @DisplayName("")
        public void deleteAlbumTest() throws Exception {

            mockMvc.perform(delete("/api/album/{id}", SUCCESS_ID))
                    .andDo(print())
                    .andExpect(status().isOk());
        }

        @Test
        @DisplayName("")
        public void deleteAlbumNotFoundTest() throws Exception {

            mockMvc.perform(delete("/api/album/{id}", FAIL_ID))
                    .andDo(print())
                    .andExpect(status().isBadRequest());
        }
    }

    @Nested
    @DisplayName("앨범 목록 조회 Mock Test")
    class findAlbumList{

        private int loopCount = 100;

        @BeforeEach
        void init(){

            for(int i = 0; i < loopCount; i++){
                AlbumFormDto album = AlbumFormDto.builder()
                        .title("NewJeans " + i)
                        .contents("데뷔앨범")
                        .releaseDate(LocalDate.of(2022, 07, 20))
                        .artistId(1L)
                        .genreId(1L)
                        .build();

                albumService.saveAlbum(album);
            }
        }

        @Test
        @DisplayName("정상적으로 동작한 경우 200")
        public void findAlbumTest() throws Exception {

            mockMvc.perform(get("/api/album")
                    .param("keyword", "")
                    .param("pageNum", "1")
                    .param("pageSize", "10"))
                .andDo(print())
                .andExpect(jsonPath("page").exists())
                .andExpect(jsonPath("page.total").value(loopCount + 1));
        }

        @Test
        @DisplayName("검색이 정상적으로 동작한 경우 200")
        public void findAlbumSearchTest() throws Exception {

            mockMvc.perform(get("/api/album")
                            .param("keyword", "0")
                            .param("pageNum", "1")
                            .param("pageSize", "10"))
                    .andDo(print())
                    .andExpect(jsonPath("page").exists())
                    .andExpect(jsonPath("page.total").value( 10));
        }
    }


    private List<TrackFormDto> trackList(){
        List<TrackFormDto> trackFormList = new ArrayList<>();

        for(int i = 1; i <= 4; i++){
            TrackFormDto trackFormDto = new TrackFormDto();
            trackFormDto.setId(Integer.toUnsignedLong(i));
            trackFormDto.setTitle("음원" + i);
            trackFormDto.setOrders(i);
            trackFormDto.setExposure(TrackExposure.EXPOSURE.name());
            trackFormDto.setPlayTime("03:10");
            trackFormList.add(trackFormDto);
        }

        return trackFormList;
    }

}