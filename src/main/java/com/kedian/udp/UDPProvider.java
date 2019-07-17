package com.kedian.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.UUID;

/**
 * @author wzh
 * @description UDP 提供者，用于提供服务
 * @create 2019-07-14 21:53
 */
public class UDPProvider {

    public static void main(String[] args) throws IOException {
        //生成一份唯一标识
        String sn = UUID.randomUUID().toString();
        Provider provider = new Provider(sn);
        provider.start();
        // 读取任意键盘信息后可以退出
        System.in.read();
        provider.exit();
    }

    private static class Provider extends Thread {
        //唯一标识
        private final String sn;
        //是否停止程序标识
        private boolean done = false;
        private DatagramSocket ds = null;

        public Provider(String sn) {
            super();
            this.sn = sn;
        }

        @Override
        public void run() {
            super.run();
            System.out.println("UDPProvider Started.");
            try {
                // 监听20000端口
                ds = new DatagramSocket(20001);
                while (!done) {
                    //构建接收实体
                    final byte[] buf = new byte[512];
                    //报文：发送或者接受实体
                    DatagramPacket receivePack = new DatagramPacket(buf, buf.length);
                    //接收
                    ds.receive(receivePack);
                    //打印接收到的信息与发送者的信息
                    //发送者ip
                    String ip = receivePack.getAddress().getHostAddress();
                    int port = receivePack.getPort();
                    int dataLen = receivePack.getLength();
                    //接受数据(目前暂时考虑只接受字符串)
                    String data = new String(receivePack.getData(), 0, dataLen);
                    System.out.println("UDPProvider receive from ip:" + ip + " port:" + port + " data:" + data);
                    //解析端口号
                    int responsePort = MessageCreator.parsePort(data);
                    //解析收到的ip
                    if (responsePort != -1) {
                        //构建一份回送数据
                        String responseData = MessageCreator.buildWithSn(sn);
                        byte[] responseDataByte = responseData.getBytes();
                        //根据发送者构建一份回送信息
                        DatagramPacket responsePacket = new DatagramPacket(responseDataByte,
                                responseDataByte.length,
                                receivePack.getAddress(),
                                responsePort);
                        //发送
                        ds.send(responsePacket);
                    }
                }
            } catch (SocketException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                close();
            }
            //完成
            System.out.println("UDPProvider Finished");
        }

        private void close() {
            if (ds != null) {
                ds.close();
                ds = null;
            }
        }

        /**
         * 提供结束
         */
        void exit() {
            done = true;
            close();
        }
    }
}
