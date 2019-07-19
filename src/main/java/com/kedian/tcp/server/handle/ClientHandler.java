package com.kedian.tcp.server.handle;

import com.kedian.tcp.utils.CloseUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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

    public ClientHandler(Socket socket, CloseNotify closeNotify) throws IOException {
        this.socket = socket;
        this.closeNotify = closeNotify;
        this.readHandler = new ClientReadHandler(socket.getInputStream());
        this.writeHandler = new ClientWriteHandler(socket.getOutputStream());
    }


    private void exitBySelf() {
        exit();
        closeNotify.onSelfClose(this);
    }

    public void exit() {
        readHandler.exit();
        writeHandler.exit();
        CloseUtils.close(socket);
        System.out.println("客户端已退出：" + socket.getInetAddress() + " P:" + socket.getPort());
    }

    public void send(String str) {
        writeHandler.send(str);
    }

    public void readToPrint() {
        readHandler.start();
    }

    public interface CloseNotify {
        void onSelfClose(ClientHandler handler);
    }

    class ClientWriteHandler {
        //是否退出
        private boolean done;
        private final PrintStream printStream;
        private final ExecutorService executorService;

        public ClientWriteHandler(OutputStream outputStream) {
            this.printStream = new PrintStream(outputStream);
            this.executorService = Executors.newSingleThreadExecutor();
        }

        //退出
        void exit() {
            done = true;
            CloseUtils.close(printStream);
            executorService.shutdownNow();
        }

        //发送
        void send(String str) {
            executorService.execute(new WriteRunnable(str));
        }

        class WriteRunnable implements Runnable {
            private final String msg;

            public WriteRunnable(String msg) {
                this.msg = msg;
            }


            @Override
            public void run() {
                if (ClientWriteHandler.this.done) {
                    return;
                }
                try {
                    ClientWriteHandler.this.printStream.println(msg);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }


    /**
     * 客户端读取处理类(多线程)
     */
    class ClientReadHandler extends Thread {
        private boolean done = false;
        private final InputStream inputStream;

        ClientReadHandler(InputStream inputStream) {
            this.inputStream = inputStream;
        }

        @Override
        public void run() {
            super.run();
            try {
                //得到输入流,用于接收数据
                BufferedReader socketInput = new BufferedReader(new InputStreamReader(inputStream));

                do {
                    //  客户端拿到一条数据
                    String str = socketInput.readLine();
                    if (str == null) {
                        System.out.println("客户端已无法获取数据");
                        //退出当前客户端
                        ClientHandler.this.exitBySelf();
                        break;
                    }
                    //打印到屏幕
                    System.out.println(str);
                } while (!done);
            } catch (Exception e) {
                if (!done) {
                    System.out.println("连接异常断开");
                    ClientHandler.this.exitBySelf();
                }
            } finally {
                //连接关闭
                CloseUtils.close(inputStream);
            }
        }

        void exit() {
            done = true;
            //连接关闭
            CloseUtils.close(inputStream);
        }
    }

}
