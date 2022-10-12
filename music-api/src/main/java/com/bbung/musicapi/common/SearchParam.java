package com.bbung.musicapi.common;

import com.bbung.musicapi.domain.artist.exception.ParamValidationException;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class SearchParam {

    private String searchType;
    private String keyword;

    private int pageSize = 10;
    private int pageNum;

    public void searchParamValidate(){

        if(this.pageNum < 0){
            throw new ParamValidationException();
        }
        if(this.pageSize < 0){
            throw new ParamValidationException();
        }
    }

}
