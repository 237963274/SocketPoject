package com.kedian.tcp.client;

import com.kedian.tcp.client.bean.ServerInfo;

import java.io.IOException;

/**
 * @author wuzh
 * @version V1.0
 * @Package com.kedian.tcp.client
 * @Description:
 * @date 2019/7/19
 */
public class Client {

    public static void main(String[] args) {
        ServerInfo info = UDPSearcher.searchServer(10000);
        System.out.println("Server:" + info);

        if (info != null) {
            try {
                TCPClient.linkWith(info);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
