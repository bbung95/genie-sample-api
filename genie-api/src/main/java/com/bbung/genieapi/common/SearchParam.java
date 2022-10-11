package com.bbung.genieapi.common;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Min;

@Setter
@Getter
@ToString
public class SearchParam {

    private String searchType;
    private String keyword;

    private int pageSize = 10;
    private int pageNum;

}
