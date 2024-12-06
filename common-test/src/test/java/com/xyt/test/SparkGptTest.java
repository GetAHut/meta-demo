package com.xyt.test;

import com.alibaba.fastjson2.TypeReference;
import com.iflytek.turing.spark.client.domain.SparkApiRequest;
import com.xyt.config.TestProperties;
import com.xyt.utils.OkHttpWebSocketClient;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;
import java.util.concurrent.CountDownLatch;

import static com.xyt.utils.ApiRequestBuilder.buildTextSparkApiRequest;

/**
 * @Author : ytxu5
 * @Date : Created in 15:39 2024/11/22
 * @Description:
 */
@Slf4j
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SparkGptTest {


    public static final TypeReference<SparkApiRequest> TYPE_REFERENCE = new TypeReference<SparkApiRequest>() {
    };

    private static OkHttpWebSocketClient okHttpWebSocketClient;

    @Autowired
    private TestProperties testProperties;

    @BeforeAll
    public static void before(){
        okHttpWebSocketClient = new OkHttpWebSocketClient();
    }

    @Test
    public void chatPluginTest() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        String traceId = String.format("%s-%s", "Meta-Test", UUID.randomUUID().toString().replace("-", "").substring(30));
        SparkApiRequest iflySearch = buildTextSparkApiRequest(traceId, "文蛤和花蛤有什么不同?", "ifly_search");
        okHttpWebSocketClient.connect(testProperties.getChatEndpoint(), latch);
        okHttpWebSocketClient.sendMessage(iflySearch.toJson(), latch);
    }

}
