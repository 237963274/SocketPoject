package com.kedian.udp;

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

    public static void main(String[] args) {
        //生成一份唯一标识
        String sn= UUID.randomUUID().toString();

    }

    private static class Provider extends Thread{
        //唯一标识
        private final String sn;
        //是否停止程序标识
        private boolean done=false;
        private DatagramSocket ds=null;

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
                ds=new DatagramSocket(20000);
                while (!done){
                    //构建接收实体
                    final byte[] buf=new byte[512];
                    DatagramPacket receivePack=new DatagramPacket(buf,buf.length);
                    //接收

                }
            } catch (SocketException e) {
                e.printStackTrace();
            } finally {
            }
        }

        private void close(){
            if (ds!=null){
                ds.close();
                ds=null;
            }
        }

        /**
         * 提供结束
         */
        void exit(){
            done=true;
            close();
        }
    }
}
