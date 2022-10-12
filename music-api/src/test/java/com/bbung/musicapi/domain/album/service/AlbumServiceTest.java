package com.bbung.musicapi.domain.album.service;

import com.bbung.musicapi.common.PageResponse;
import com.bbung.musicapi.domain.album.dto.AlbumDto;
import com.bbung.musicapi.domain.album.dto.AlbumFormDto;
import com.bbung.musicapi.domain.album.dto.AlbumSearchParam;
import com.bbung.musicapi.domain.album.exception.AlbumNotFoundException;
import com.bbung.musicapi.domain.artist.exception.ArtistNotFoundException;
import com.bbung.musicapi.domain.artist.exception.ParamValidationException;
import com.bbung.musicapi.domain.artist.mapper.ArtistMapper;
import com.bbung.musicapi.entity.Artist;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Sql("/truncate.sql")
class AlbumServiceTest {

    @Autowired
    private AlbumService albumService;

    @Autowired
    private ArtistMapper artistMapper;

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

    @Nested
    @DisplayName("앨범 등록 TEST")
    class insertAlbum{

        private final Long SUCCESS_ID = 1L;
        private final Long FAIL_ID = 100L;

        @Test
        @DisplayName("성공")
        public void insertAlbumTest() throws Exception {

            AlbumFormDto album = AlbumFormDto.builder()
                    .title("NewJeans")
                    .contents("데뷔앨범")
                    .releaseDate(LocalDate.of(2022, 07, 20))
                    .artistId(SUCCESS_ID)
                    .genreId(1L)
                    .build();

            Long id = albumService.saveAlbum(album);

            assertThat(id).isEqualTo(1L);
        }

        @Test
        @DisplayName("아티스트가 존재하지않을 경우 BadRequest")
        public void insertAlbumBadRequestTest() throws Exception {
            AlbumFormDto album = AlbumFormDto.builder()
                    .title("NewJeans")
                    .contents("데뷔앨범")
                    .releaseDate(LocalDate.of(2022, 07, 20))
                    .artistId(FAIL_ID)
                    .genreId(1L)
                    .build();

            ArtistNotFoundException artistNotFoundException = assertThrows(ArtistNotFoundException.class, () -> {
                albumService.saveAlbum(album);
            });

            assertThat(artistNotFoundException.getMessage()).isEqualTo("해당 ID " + FAIL_ID + "가 존재하지 않습니다.");
        }
    }


    @Nested
    @DisplayName("앨범 상세보기 TEST")
    class findAlbum{

        @Test
        @DisplayName("성공")
        public void findAlbumTest() throws Exception {

            AlbumFormDto album = AlbumFormDto.builder()
                    .title("NewJeans")
                    .contents("데뷔앨범")
                    .releaseDate(LocalDate.of(2022, 07, 20))
                    .artistId(1L)
                    .genreId(1L)
                    .build();

            Long id = albumService.saveAlbum(album);

            AlbumDto albumDto = albumService.findById(id);

            assertThat(albumDto.getArtistName()).isEqualTo("뉴진스");
            assertThat(albumDto.getTitle()).isEqualTo("NewJeans");
            assertThat(albumDto.getGenreTitle()).isEqualTo("댄스");
        }

        @Test
        @DisplayName("ID가 존재하지 않을 경우 BadRequest")
        public void findAlbumNotFoundTest() throws Exception {

            Long id = 100L;

            AlbumNotFoundException albumNotFoundException = assertThrows(AlbumNotFoundException.class, () -> {
                albumService.findById(id);
            });

            assertThat(albumNotFoundException.getMessage()).isEqualTo("해당 ID " + id + "가 존재하지 않습니다.");
        }
    }

    @Nested
    @DisplayName("앨범 목록 조회 TEST")
    class findList{

        int loopCount = 100;

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
        @DisplayName("성공")
        public void findListTest() throws Exception {

            AlbumSearchParam param = new AlbumSearchParam();
            param.setPageNum(1);
            param.setPageSize(10);
            PageResponse<AlbumDto> result = albumService.findList(param);

            assertThat(result.getPage().getTotal()).isEqualTo(loopCount);
        }

        @Test
        @DisplayName("검색성공")
        public void findListSearchTest() throws Exception {

            AlbumSearchParam param = new AlbumSearchParam();
            param.setPageNum(1);
            param.setPageSize(10);
            param.setKeyword("0");
            PageResponse<AlbumDto> result = albumService.findList(param);

            assertThat(result.getPage().getTotal()).isEqualTo(10);
        }

        @Test
        @DisplayName("Page가 올바르지 않을 경우 BadRequest")
        public void findListPageBadRequestTest(){
            AlbumSearchParam param = new AlbumSearchParam();
            param.setPageNum(-1);
            param.setPageSize(10);

            ParamValidationException paramValidationException = assertThrows(ParamValidationException.class, () -> {
                albumService.findList(param);
            });

            assertThat(paramValidationException.getMessage()).isEqualTo("요청을 확인해주세요.");
        }
    }

    @Nested
    @DisplayName("앨범 수정 테스트")
    class updateAlbum{

        private final int SUCCESS = 1;
        private final Long SUCCESS_ID = 1L;
        private final Long FAIL_ID = 100L;

        @Test
        @DisplayName("성공")
        public void updateAlbumTest() throws Exception {

            AlbumFormDto album = AlbumFormDto.builder()
                    .title("NewJeans")
                    .contents("데뷔앨범")
                    .releaseDate(LocalDate.of(2022, 07, 20))
                    .artistId(SUCCESS_ID)
                    .genreId(1L)
                    .build();

            Long id = albumService.saveAlbum(album);

            album.setGenreId(2L);

            int result = albumService.updateAlbum(id, album);

            assertThat(SUCCESS).isEqualTo(result);
        }

        @Test
        @DisplayName("ID가 존재하지 않을 경우 BadRequest")
        public void updateAlbumBadRequestTest() throws Exception {

            AlbumFormDto album = AlbumFormDto.builder()
                    .title("NewJeans")
                    .contents("데뷔앨범")
                    .releaseDate(LocalDate.of(2022, 07, 20))
                    .artistId(FAIL_ID)
                    .genreId(1L)
                    .build();

            AlbumNotFoundException albumNotFoundException = assertThrows(AlbumNotFoundException.class, () -> {
                albumService.updateAlbum(FAIL_ID, album);
            });

            assertThat(albumNotFoundException.getMessage()).isEqualTo("해당 ID " + FAIL_ID + "가 존재하지 않습니다.");
        }
    }

    @Nested
    @DisplayName("앨범 삭제 테스트")
    class deleteAlbum{

        private final int SUCCESS = 1;
        private final Long SUCCESS_ID = 1L;
        private final Long FAIL_ID = 100L;

        @Test
        @DisplayName("성공")
        public void deleteAlbumTest() throws Exception {

            AlbumFormDto album = AlbumFormDto.builder()
                    .title("NewJeans")
                    .contents("데뷔앨범")
                    .releaseDate(LocalDate.of(2022, 07, 20))
                    .artistId(SUCCESS_ID)
                    .genreId(1L)
                    .build();

            Long id = albumService.saveAlbum(album);
            int result = albumService.deleteAlbum(id);

            assertThat(SUCCESS).isEqualTo(result);
        }

        @Test
        @DisplayName("ID가 존재하지 않을 경우 BadRequest")
        public void deleteAlbumBadRequestTest() throws Exception {

            AlbumNotFoundException albumNotFoundException = assertThrows(AlbumNotFoundException.class, () -> {
                albumService.deleteAlbum(FAIL_ID);
            });

            assertThat(albumNotFoundException.getMessage()).isEqualTo("해당 ID " + FAIL_ID + "가 존재하지 않습니다.");
        }
    }

}