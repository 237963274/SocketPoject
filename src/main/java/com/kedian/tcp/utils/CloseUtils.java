package com.kedian.tcp.utils;

import java.io.Closeable;
import java.io.IOException;

/**
 * @author wuzh
 * @version V1.0
 * @Package com.kedian.tcp.utils
 * @Description:
 * @date 2019/7/17
 */
public class CloseUtils {

    public static void close(Closeable... closeables){
        if (closeables==null){
            return;
        }
        for (Closeable closeable : closeables) {
            try {
                closeable.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
