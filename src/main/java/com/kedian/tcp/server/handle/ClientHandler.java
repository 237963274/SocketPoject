package com.kedian.tcp.server.handle;

import java.net.Socket;

/**
 * @author wuzh
 * @version V1.0
 * @Package com.kedian.tcp.server.handle
 * @Description:
 * @date 2019/7/17
 */
public class ClientHandler {

    private final Socket socket;
    private final ClientReadHandler readHandler;
    private final ClientWriteHandler writeHandler;
    private final CloseNotify closeNotify;


    class ClientReadHandler extends Thread {
        private boolean done=false;
        private
    }
}
