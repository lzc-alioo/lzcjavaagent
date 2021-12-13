package com.example.lzcjavaagent2.socket;

import com.example.lzcjavaagent2.aspect.ProfilingAspect;
import com.example.lzcjavaagent2.model.MethodNode;

import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

/**
 * @author: 悟心
 * @time: 2021/11/22 23:50
 * @description:
 */
public class SocketServer {
    public static void main(String[] args) throws IOException {
        SocketServer.start();
    }

    public static void start() {
        try {
            //参数传入端口号，下面是以本机55555端口作为数据传输的端口
            //端口范围0到65535，1~1023之间的端口一般都会被系统占用（没有权限是操作不了的），建议选序号大一点的
            ServerSocket dataServer = new ServerSocket(3658);
            System.out.println("socketserver启动成功");
            while (true) {
                //这是的accept方法用来形成与服务器相连的socket对象，必须得另一边客户端连接才会生成socket对象，否则会一直卡在这等客户端连接
                //两端的socket对象可以理解为连接两端的线缆和插孔
                Socket socket = dataServer.accept();

                sendData(socket);

                socket.close();
                dataServer.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void sendData(Socket socket) {
        try {
            OutputStream os = socket.getOutputStream();

            while (true) {
                if (TraceBuffer.list.isEmpty()) {
                    Thread.sleep(1000L);
                    continue;
                }
                MethodNode node = TraceBuffer.list.remove(0);
                StringBuffer buf = new StringBuffer("\n");
                ProfilingAspect.log(node, 1, buf);
                byte[] b = buf.toString().getBytes(StandardCharsets.UTF_8);
                os.write(b, 0, b.length);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
