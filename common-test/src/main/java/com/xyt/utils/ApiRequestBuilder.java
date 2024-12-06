package com.xyt.utils;

import com.iflytek.turing.spark.client.domain.SparkApiRequest;
import com.iflytek.turing.spark.data.SparkChatParam;
import com.iflytek.turing.spark.data.SparkRequestData;
import com.iflytek.turing.spark.data.SparkRequestParam;
import com.iflytek.turing.spark.data.SparkTextData;
import com.iflytek.turing.spark.enums.SparkChatRole;

import java.util.Collections;

/**
 * @Author : ytxu5
 * @Date : Created in 15:45 2024/11/22
 * @Description:
 */
public class ApiRequestBuilder {

    public static SparkApiRequest buildTextSparkApiRequest(String traceId, String content) {
        return buildTextSparkApiRequest(traceId, content, new SparkTextData.SparkContentMeta());
    }

    public static SparkApiRequest buildTextSparkApiRequest(String traceId, String content, String category) {
        SparkTextData.SparkContentMeta sparkContentMeta = new SparkTextData.SparkContentMeta();
        sparkContentMeta.setCategory(category);
        return buildTextSparkApiRequest(traceId, content, sparkContentMeta);
    }

    public static SparkApiRequest buildTextSparkApiRequest(String traceId, String content, SparkTextData.SparkContentMeta contentMeta) {
        SparkChatParam param = new SparkChatParam(traceId);
        param.setTopK(1);
        SparkRequestParam sparkRequestParam = new SparkRequestParam(param);
        SparkTextData sparkRequestText = new SparkTextData(content, SparkChatRole.user);
        sparkRequestText.setContentMeta(contentMeta);
        SparkRequestData sparkRequestData = SparkRequestData.build(Collections.singletonList(sparkRequestText));
        SparkApiRequest sparkApiRequest = new SparkApiRequest(sparkRequestParam, sparkRequestData);
        sparkApiRequest.setTraceId(traceId);
        return new SparkApiRequest(sparkRequestParam, sparkRequestData);
    }
}
