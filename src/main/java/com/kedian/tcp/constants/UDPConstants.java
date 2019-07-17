package com.kedian.tcp.constants;

/**
 * @author wuzh
 * @version V1.0
 * @Package com.kedian.tcp.constants
 * @Description:
 * @date 2019/7/17
 */
public class UDPConstants {
    //公用头部
    public static byte[] HEADER=new byte[]{7, 7, 7, 7, 7, 7, 7, 7};

    //服务器固化UDP接收端口
    public static int PORT_SERVER=30201;
    //客户端回送端口
    public static int PORT_CLIENT_RESPONSE = 30202;
}
