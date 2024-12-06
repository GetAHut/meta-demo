package com.xyt.utils;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.alibaba.fastjson2.TypeReference;
import com.iflytek.turing.spark.data.SparkResponseChoices;
import com.iflytek.turing.spark.data.SparkResponseData;
import com.iflytek.turing.spark.data.SparkTextData;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.apache.commons.lang3.StringUtils;
import skynet.boot.pandora.api.ApiResponse;
import skynet.boot.pandora.api.ApiResponseGenerics;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * @Author : ytxu5
 * @Date : Created in 15:40 2024/11/22
 * @Description:
 */
@Slf4j
public class OkHttpWebSocketClient {

    private final OkHttpClient client;
    private WebSocket webSocket;
    public final StringBuffer stringBuffer;

    public OkHttpWebSocketClient() {
        this.stringBuffer = new StringBuffer();
        client = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(0, TimeUnit.SECONDS) // 禁用超时，保持长连接
                .build();
    }

    public void connect(String url, CountDownLatch latch) {
        Request request = new Request.Builder()
                .url(url)
                .build();

        webSocket = client.newWebSocket(request, new WebSocketListener() {
            final StringBuffer stringBuffer = new StringBuffer();
            final TypeReference<ApiResponseGenerics<SparkResponseData>> TYPE_REFERENCE = new TypeReference<ApiResponseGenerics<SparkResponseData>>() {
            };

            @Override
            public void onOpen(@NonNull WebSocket webSocket, Response response) {
                System.out.println("WebSocket 连接成功");
            }

            @Override
            public void onMessage(WebSocket webSocket, String text) {
                System.out.println("接收到消息: " + text);
                if (!StringUtils.isEmpty(text)) {
                    ApiResponseGenerics<SparkResponseData> response = JSON.parseObject(text, TYPE_REFERENCE);
                    SparkResponseData payload = response.getPayload();
                    if (!Objects.isNull(payload.getChoices())) {
                        SparkResponseChoices choices = payload.getChoices();
                        List<SparkTextData> data = choices.getText();
                        for (SparkTextData datum : data) {
                            stringBuffer.append(datum.getContent());
                        }
                        if (choices.getStatus() == 2) {
                            log.info("gpt result: {}", stringBuffer.toString());
                            close(latch);
                        }
                    }
                }
            }

            @Override
            public void onClosing(WebSocket webSocket, int code, String reason) {
                System.out.println("WebSocket 正在关闭: " + reason);
            }

            @Override
            public void onClosed(WebSocket webSocket, int code, String reason) {
                System.out.println("WebSocket 已关闭: " + reason);

            }

            @Override
            public void onFailure(WebSocket webSocket, Throwable t, Response response) {
                System.err.println("WebSocket 发生错误: " + t.getMessage());
            }
        });
    }

    public void sendMessage(String message, CountDownLatch latch) throws InterruptedException {
        if (webSocket != null) {
            webSocket.send(message);
            latch.await();
        } else {
            System.err.println("WebSocket 未连接，无法发送消息");
        }
    }

    public void close(CountDownLatch latch) {
        if (webSocket != null) {
            log.info("客户端关闭连接");
            webSocket.close(1000, "客户端关闭连接");
            latch.countDown();
            log.info("latch release");
        }
        client.dispatcher().executorService().shutdown();
    }

}