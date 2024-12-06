package com.xyt.metademo.service;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.alibaba.fastjson2.TypeReference;
import com.xyt.metademo.domain.SearchResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import skynet.boot.pandora.api.ApiResponse;
import skynet.boot.pandora.api.ApiResponseHeader;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author : ytxu5
 * @Date : Created in 16:24 2024/11/21
 * @Description:
 */
@Slf4j
@Service
public class MockSearchService {

    private static final List<String> matches = new ArrayList<>();
    public static final TypeReference<List<SearchResponse>> TYPE_REFERENCE = new TypeReference<List<SearchResponse>>() {
    };

        static {
        matches.add("day1 24024年11月1日: navi vs g2");
        matches.add("day2 24024年11月2日: faze vs g2");
        matches.add("day3 24024年11月3日: faze vs navi");
        matches.add("day4 24024年11月4日: lvg  vs g2");
        matches.add("day5 24024年11月5日: lvg  vs navi");
    }

    public ApiResponse mockService() {
        ApiResponse apiResponse = new ApiResponse();
        String filePath = "answer/answer1.json";
        try {

            List<SearchResponse> searchResponses = mockDataFromFile(filePath);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("result", searchResponses);
            apiResponse.setPayload(jsonObject);
        } catch (Exception e) {
            ApiResponseHeader apiResponseHeader = new ApiResponseHeader();
            apiResponseHeader.setCode(-1);
            apiResponseHeader.setMessage(e.getMessage());
            apiResponse.setHeader(apiResponseHeader);
        }
        return apiResponse;
    }

    private List<SearchResponse> mockDataFromFile(String filePath) {
        try {
            ClassPathResource classPathResource = new ClassPathResource(filePath);
            String data = IOUtils.toString(classPathResource.getInputStream(), Charset.defaultCharset());
            return JSON.parseObject(data, TYPE_REFERENCE);
        } catch (IOException e) {
            log.error("data parsed error: {}", e.getMessage());
            throw new RuntimeException(e);
        }

    }

    private static List<SearchResponse> mockDataBuilder(int lines) {
        String source = "www.baidu.com";
        List<SearchResponse> result = new ArrayList<>(lines);
        for (int i = 0; i < lines; i++) {
            SearchResponse searchResponse = new SearchResponse(i + 1, source, String.valueOf(i + 1), matches.get(i));
            result.add(searchResponse);
        }
        return result;
    }
}
