package socket;

import com.example.lzcjavaagent2.model.MethodNode;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author: 悟心
 * @time: 2021/11/22 23:50
 * @description:
 */
public class SocketServer {
    public static void main(String[] args) throws IOException {
        //参数传入端口号，下面是以本机55555端口作为数据传输的端口
        //端口范围0到65535，1~1023之间的端口一般都会被系统占用（没有权限是操作不了的），建议选序号大一点的
        ServerSocket dataServer = new ServerSocket(55555);
        while(true){
            //这是的accept方法用来形成与服务器相连的socket对象，必须得另一边客户端连接才会生成socket对象，否则会一直卡在这等客户端连接
            //两端的socket对象可以理解为连接两端的线缆和插孔
            Socket socket = dataServer.accept();

            //这里的输出流获取需通过socket对象获取，数据流向连接的客户端
            OutputStream os = socket.getOutputStream();
            FileInputStream fis = new FileInputStream("a.txt");
            byte[] b = new byte[10];

            //下面是将a.txt的数据传输到连接的客户端
            while (true){
                int len = fis.read(b);
                if (len==-1) break;
                os.write(b,0,len);
            }
            fis.close();
            socket.close();
            dataServer.close();
        }
    }

    public void sendData(Socket socket ) throws InterruptedException {
        while(true){
            if(TraceBuffer.list.isEmpty()){
                Thread.sleep(1000L);
                continue;
            }
            MethodNode node=TraceBuffer.list.remove(0);

        }
    }
}
