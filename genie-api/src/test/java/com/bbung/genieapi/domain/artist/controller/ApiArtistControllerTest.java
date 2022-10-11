package com.bbung.genieapi.domain.artist.controller;

import com.bbung.genieapi.domain.artist.dto.ArtistFormDto;
import com.bbung.genieapi.domain.artist.dto.ArtistUpdateFormDto;
import com.bbung.genieapi.domain.artist.service.ArtistService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Sql("/truncate.sql")
class ApiArtistControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ArtistService artistService;

    @Nested
    @DisplayName("아티스트 등록 테스트")
    class insertArtistTest{

        @Test
        @DisplayName("성공 201")
        public void insertArtistMockTest() throws Exception {

            ArtistFormDto artistFormDto = ArtistFormDto.builder()
                    .name("뉴진스")
                    .agency("ADOR")
                    .birthday(LocalDate.of(2022, 7, 22))
                    .contents("하이브 소속")
                    .nationality("다국적")
                    .build();

            mockMvc.perform(post("/api/artist")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(artistFormDto)))
                    .andDo(print())
                    .andExpect(status().isCreated())
                    .andExpect(jsonPath("id").value(1));
        }

        @Test
        @DisplayName("Validation 400")
        public void insertArtistBadRequestMockTest() throws Exception {

            ArtistFormDto artistFormDto = ArtistFormDto.builder()
                    .name("")
                    .agency("ADOR")
                    .contents("하이브 소속")
                    .nationality("다국적")
                    .build();

            mockMvc.perform(post("/api/artist")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(artistFormDto)))
                    .andDo(print())
                    .andExpect(status().isBadRequest());
        }
    }

    @Nested
    @DisplayName("아티스트 상세조회 테스트")
    class findArtistTest{

        @Test
        @DisplayName("성공 200")
        public void findArtistMockTest() throws Exception {

            Long id = saveArtist(1);

            mockMvc.perform(get("/api/artist/{id}", id))
                    .andDo(print())
                    .andExpect(status().isOk());
        }

        @Test
        @DisplayName("아티스트가 존재하지 않을 떄 400")
        public void insertArtistBadRequestMockTest() throws Exception {

            Long id = 100L;

            mockMvc.perform(get("/api/artist/{id}", id))
                    .andDo(print())
                    .andExpect(status().isBadRequest());
        }
    }

    @Nested
    @DisplayName("아티스트 목록 조회 테스트")
    class findArtistListTest{

        @BeforeEach
        void setList(){

            int loopCount = 100;

            for(int i = 0; i < loopCount; i++){
                saveArtist(i);
            }
        }

        @Test
        @DisplayName("성공 200")
        public void findArtistListMockTest() throws Exception {

            mockMvc.perform(get("/api/artist")
                            .param("pageNum", "1")
                            .param("pageSize", "2"))
                    .andDo(print())
                    .andExpect(jsonPath("page.total").value(100))
                    .andExpect(status().isOk());
        }

        @Test
        @DisplayName("검색 성공 200")
        public void findArtistListSearchMockTest() throws Exception {

            mockMvc.perform(get("/api/artist")
                            .param("pageNum", "1")
                            .param("pageSize", "10")
                            .param("keyword", "0"))
                    .andDo(print())
                    .andExpect(jsonPath("page.total").value(10))
                    .andExpect(status().isOk());
        }

        @Test
        @DisplayName("page가 0보다 작을 경우 400")
        public void findArtistListSearchBadRequestMockTest() throws Exception {

            mockMvc.perform(get("/api/artist")
                            .param("pageNum", "-1")
                            .param("pageSize", "0"))
                    .andDo(print())
                    .andExpect(status().isBadRequest());
        }
    }

    @Nested
    @DisplayName("아티스트 수정 테스트")
    class updateArtistTest{

        @Test
        @DisplayName("성공 200")
        public void updateArtistMockTest() throws Exception {

            Long id = saveArtist(1);

            ArtistUpdateFormDto artistUpdateFormDto = ArtistUpdateFormDto.builder()
                    .name("르세라핌")
                    .agency("쏘스뮤작")
                    .birthday(LocalDate.of(2022, 7, 22))
                    .contents("하이브 소속")
                    .nationality("다국적")
                    .build();

            mockMvc.perform(put("/api/artist/{id}", id)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(artistUpdateFormDto)))
                    .andDo(print())
                    .andExpect(status().isOk());

        }

        @Test
        @DisplayName("Validation 400")
        public void updateArtistValidationBadRequestMockTest() throws Exception {

            Long id = saveArtist(1);

            ArtistUpdateFormDto artistUpdateFormDto = ArtistUpdateFormDto.builder()
                    .name("")
                    .agency("쏘스뮤작")
                    .contents("하이브 소속")
                    .nationality("다국적")
                    .build();

            mockMvc.perform(put("/api/artist/{id}", id)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(artistUpdateFormDto)))
                    .andDo(print())
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("statusCode").value("400"));
        }

        @Test
        @DisplayName("아티스트가 존재하지 않을 떄 400")
        public void updateArtistBadRequestMockTest() throws Exception {

            Long id = 100L;

            ArtistUpdateFormDto artistUpdateFormDto = ArtistUpdateFormDto.builder()
                    .name("르세라핌")
                    .agency("쏘스뮤작")
                    .birthday(LocalDate.of(2022, 7, 22))
                    .contents("하이브 소속")
                    .nationality("다국적")
                    .build();

            mockMvc.perform(put("/api/artist/{id}", id)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(artistUpdateFormDto)))
                    .andDo(print())
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("statusCode").value("400"));
        }

    }

    @Nested
    @DisplayName("아티스트 삭제 테스트")
    class deleteArtistTest{

        @Test
        @DisplayName("성공 200")
        public void deleteArtistMockTest() throws Exception {

            Long id = saveArtist(1);

            mockMvc.perform(delete("/api/artist/{id}", id))
                    .andDo(print())
                    .andExpect(status().isOk());
        }

        @Test
        @DisplayName("아티스트가 존재하지 않을 떄 400")
        public void deleteArtistBadRequestMockTest() throws Exception {
            Long id = 100L;

            mockMvc.perform(delete("/api/artist/{id}", id))
                    .andDo(print())
                    .andExpect(status().isBadRequest());

        }

    }


    private Long saveArtist(int num){

        ArtistFormDto artistFormDto = ArtistFormDto.builder()
                .name("뉴진스" + num)
                .agency("ADOR")
                .birthday(LocalDate.of(2022, 7, 22))
                .contents("하이브 소속")
                .nationality("다국적")
                .build();

        return artistService.saveArtist(artistFormDto);
    }
}