package com.xyt.metademo.controller;

import com.xyt.metademo.service.MockSearchService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import skynet.boot.pandora.api.ApiRequest;
import skynet.boot.pandora.api.ApiResponse;

/**
 * @Author : ytxu5
 * @Date : Created in 16:22 2024/11/21
 * @Description:
 */
@Slf4j
@RestController
@RequestMapping("/mock")
public class MockSearchController {


    private final MockSearchService mockSearchService;

    public MockSearchController(MockSearchService mockSearchService) {
        log.info(" mock controller init ...");
        this.mockSearchService = mockSearchService;
    }


    @PostMapping("/search")
    public ApiResponse response(@RequestBody ApiRequest apiRequest){
        log.info("apiRequest: {}", apiRequest);
        return mockSearchService.mockService();
    }
}
