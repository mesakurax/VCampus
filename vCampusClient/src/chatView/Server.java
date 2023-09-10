package chatView;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.TargetDataLine;

/**
 * ��Ҫʵ�־�����ͨѶ�з���˵Ĺ���
 *
 * @author Administrator
 */
public class Server extends Thread{
    private OutputStream out;
    private InputStream in;
    private ServerSocket serverSocket;
    private Socket socket;
    //private int counter = 1;
    private byte[] bos=new byte[2048];
    //private static ByteArrayOutputStream baos;
    private  byte[] bis=new byte[2048];

    private volatile boolean running = true; // Flag to control the while loop

    public Server() {
    }

    public void stopServer() {
        running = false; // Set the flag to false to stop the while loop
    }
    public void run() {
        try {

            serverSocket = new ServerSocket(9000, 20);
            // �ȴ�����
            System.out.println("�����:�ȴ�����");
            socket = serverSocket.accept();
            out = socket.getOutputStream();
            // out.flush();
            System.out.println("����ˣ����ӳɹ�");
            // ����ͨѶ
            in = socket.getInputStream();
            TargetDataLine targetDataLine = AudioUtils.getTargetDataLine();

            SourceDataLine sourceDataLine = AudioUtils.getSourceDataLine();

            while (running) {

                //��ȡ��Ƶ��
                int writeLen = targetDataLine.read(bos,0,bos.length);
                //��
                if (bos != null) {
                    out.write(bos,0,writeLen);
                }

                //��
                int readLen = in.read(bis);
                if (bis != null) {
                    sourceDataLine.write(bis, 0, readLen);
                }
            }

            // Cleanup code
            out.close();
            in.close();
            socket.close();
            targetDataLine.close();
            sourceDataLine.close();
            serverSocket.close();


        } catch (Exception ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void main(String args[]) throws UnknownHostException {
        InetAddress localHost = InetAddress.getLocalHost();
        String ipAddress = localHost.getHostAddress();
        System.out.println(ipAddress);
        Server e=new Server();
        e.start();
    }

}