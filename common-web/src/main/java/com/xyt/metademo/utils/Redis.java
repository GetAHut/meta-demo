package com.xyt.metademo.utils;

import redis.clients.jedis.Jedis;

/**
 * 在线redis
 * @link <a href="https://console.upstash.com/redis/bc2ddc84-9edc-4680-84d7-796278fc9409?tab=details" />
 *
 * @Author : ytxu5
 * @Date : Created in 17:17 2024/12/5
 * @Description:
 */
public class Redis {

    public static void main(String[] args) {
        Jedis jedis = new Jedis("trusted-doberman-22707.upstash.io", 6379, true);
        jedis.auth("AVizAAIjcDE4NjkyN2ZkOTFlMWU0OGMyYTEyYzUzYjAxN2ZiMTM0NHAxMA");
        jedis.set("foo", "bar");
        String value = jedis.get("foo");
        System.out.println(value);
    }

}
