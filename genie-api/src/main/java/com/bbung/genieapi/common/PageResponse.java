package com.bbung.genieapi.common;

import com.github.pagehelper.PageInfo;
import lombok.*;

@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PageResponse <T>{

    private SearchParam param;
    private PageInfo<T> page;

}
