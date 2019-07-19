package com.kedian.tcp.client.bean;

/**
 * @author wuzh
 * @version V1.0
 * @Package com.kedian.tcp.client.bean
 * @Description:
 * @date 2019/7/19
 */
public class ServerInfo {

    private String sn;
    private int port;
    private String address;

    public ServerInfo(String sn, int port, String address) {
        this.sn = sn;
        this.port = port;
        this.address = address;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "ServerInfo{" +
                "sn='" + sn + '\'' +
                ", port=" + port +
                ", address='" + address + '\'' +
                '}';
    }
}
