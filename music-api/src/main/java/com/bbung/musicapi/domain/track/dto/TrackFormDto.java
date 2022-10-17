package com.bbung.musicapi.domain.track.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Setter
@Getter
@ToString
public class TrackFormDto {

    private Long id;
    @NotBlank(message = "음원명을 입력해주세요.")
    private String title;
    @NotBlank(message = "재생시간을 입력해주세요.")
    private String playTime;
    @NotBlank(message = "노출여부를 선택해주세요.")
    private String exposure;
    @Min(value = 1, message = "순서를 입력해주세요.")
    private int orders;

    private Long albumId;
}
