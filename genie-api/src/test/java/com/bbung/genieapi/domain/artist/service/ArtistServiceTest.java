package com.bbung.genieapi.domain.artist.service;

import com.bbung.genieapi.common.PageResponse;
import com.bbung.genieapi.domain.artist.dto.*;
import com.bbung.genieapi.domain.artist.exception.ArtistNotFoundException;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Sql("/truncate.sql")
class ArtistServiceTest {

    private final int SUCCESS = 1;
    @Autowired
    private ArtistService artistService;

    @Test
    @DisplayName("아티스트 등록 테스트")
    public void insertArtistServiceTest() throws Exception {

        ArtistFormDto artistFormDto = ArtistFormDto.builder()
                .name("뉴진스")
                .agency("ADOR")
                .birthday(LocalDate.of(2022, 7, 22))
                .contents("하이브 소속")
                .nationality("다국적")
                .build();

        Long id = artistService.saveArtist(artistFormDto);

        assertThat(id).isEqualTo(1L);
    }

    @Nested
    @DisplayName("아티스트 상세보기 테스트")
    class findArtistTest{

        @Test
        @DisplayName("성공")
        public void findArtistServiceTest() throws Exception {

            ArtistFormDto artistFormDto = ArtistFormDto.builder()
                    .name("뉴진스")
                    .agency("ADOR")
                    .birthday(LocalDate.of(2022, 7, 22))
                    .contents("하이브 소속")
                    .nationality("다국적")
                    .build();

            Long id = artistService.saveArtist(artistFormDto);

            ArtistDto artistDto = artistService.findById(id);

            assertThat(artistDto.getName()).isEqualTo(artistFormDto.getName());
            assertThat(artistDto.getAgency()).isEqualTo(artistFormDto.getAgency());
            assertThat(artistDto.getBirthday()).isEqualTo(artistFormDto.getBirthday());
            assertThat(artistDto.getContents()).isEqualTo(artistFormDto.getContents());
            assertThat(artistDto.getNationality()).isEqualTo(artistFormDto.getNationality());
        }

        @Test
        @DisplayName("ID가 존재하지 않을 경우 BadRequest")
        public void findArtistServiceBadRequestTest() throws Exception {

            Long id = 100L;

            ArtistNotFoundException result = assertThrows(ArtistNotFoundException.class, () -> {
                artistService.findById(id);
            });

            assertThat(result.getMessage()).isEqualTo("해당 ID " + id + "가 존재하지 않습니다.");
        }
    }

    @Test
    @DisplayName("아티스트 목록 조회 테스트")
    public void findArtistListServiceTest() throws Exception {

        int loopCount = 100;

        for(int i = 0; i < loopCount; i++){
            ArtistFormDto artistFormDto = ArtistFormDto.builder()
                    .name("뉴진스" + i)
                    .agency("ADOR")
                    .birthday(LocalDate.of(2022, 7, 22))
                    .contents("하이브 소속")
                    .nationality("다국적")
                    .build();

            artistService.saveArtist(artistFormDto);
        }

        ArtistSearchParam param = new ArtistSearchParam();
        param.setKeyword("0");
        PageResponse<ArtistListDto> result = artistService.findList(param);

        assertThat(result.getPage().getTotal()).isEqualTo(10);
        assertThat(result.getParam().getKeyword()).isEqualTo(param.getKeyword());
    }

    @Nested
    @DisplayName("아티스트 업데이트 테스트")
    class updateArtistTest {

        @Test
        @DisplayName("성공")
        public void updateArtistServiceTest() throws Exception {

            ArtistFormDto artistFormDto = ArtistFormDto.builder()
                        .name("뉴진스")
                        .agency("ADOR")
                        .birthday(LocalDate.of(2022, 7, 22))
                        .contents("하이브 소속")
                        .nationality("다국적")
                        .build();

            Long id = artistService.saveArtist(artistFormDto);

            ArtistUpdateFormDto artistUpdateFormDto = ArtistUpdateFormDto.builder()
                        .name("르세라핌")
                        .agency("쏘쓰뮤직")
                        .birthday(LocalDate.of(2022, 7, 22))
                        .contents("하이브 소속")
                        .nationality("다국적")
                        .build();

            int result = artistService.updateArtist(id, artistUpdateFormDto);

            assertThat(result).isEqualTo(SUCCESS);
        }

        @Test
        @DisplayName("ID가 존재하지 않을 경우 BadRequest")
        public void updateArtistServiceBadRequestTest() throws Exception {

            Long id = 100L;

            ArtistUpdateFormDto artistUpdateFormDto = ArtistUpdateFormDto.builder()
                    .name("르세라핌")
                    .agency("쏘쓰뮤직")
                    .birthday(LocalDate.of(2022, 7, 22))
                    .contents("하이브 소속")
                    .nationality("다국적")
                    .build();

            ArtistNotFoundException result = assertThrows(ArtistNotFoundException.class, () -> {
                artistService.updateArtist(id, artistUpdateFormDto);
            });

            assertThat(result.getMessage()).isEqualTo("해당 ID " + id + "가 존재하지 않습니다.");
        }
    }

    @Nested
    @DisplayName("아티스트 삭제 테스트")
    class deleteArtistTest{

        @Test
        @DisplayName("성공")
        public void deleteArtistServiceTest() throws Exception {

            ArtistFormDto artistFormDto = ArtistFormDto.builder()
                    .name("뉴진스")
                    .agency("ADOR")
                    .birthday(LocalDate.of(2022, 7, 22))
                    .contents("하이브 소속")
                    .nationality("다국적")
                    .build();

            Long id = artistService.saveArtist(artistFormDto);

            int result = artistService.deleteArtist(id);

            assertThat(result).isEqualTo(SUCCESS);
        }

        @Test
        @DisplayName("ID가 존재하지 않을 경우 BadRequest")
        public void deleteArtistServiceBadRequestTest() throws Exception {

            Long id = 100L;

            ArtistNotFoundException result = assertThrows(ArtistNotFoundException.class, () -> {
                artistService.deleteArtist(id);
            });

            assertThat(result.getMessage()).isEqualTo("해당 ID " + id + "가 존재하지 않습니다.");
        }
    }

}