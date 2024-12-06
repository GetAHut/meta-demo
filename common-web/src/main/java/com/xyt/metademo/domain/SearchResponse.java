package com.xyt.metademo.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * @Author : ytxu5
 * @Date : Created in 16:35 2024/11/21
 * @Description:
 */
@Getter
@Setter
@AllArgsConstructor
@Accessors(chain = true)
public class SearchResponse {

    private int index;
    private String source;
    private String docid;
    private String document;

}
