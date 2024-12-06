package com.xyt.test.utils;

/**
 * @Author : ytxu5
 * @Date : Created in 09:16 2024/11/26
 * @Description:
 */
public class ThreadUtils {


    public static void main(String[] args) {
        new Thread(() -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println(1);
        }).start();
        System.out.println(2);
    }
}
