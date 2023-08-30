package net;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;


/**
 * ���������߳�
 */
public class ServerThread extends Thread {

    private ServerSocket server;   //������Socket

    private Vector<ClientThread> clients=new Vector<ClientThread>(); //�����ӵĿͻ����߳�����

    public SeverRun severRun;


    public ServerThread(SeverRun severRun) {

        this.severRun=severRun;

        try {
            server = new ServerSocket(8081);

            this.severRun.textArea.append("Server main thread start.\nListen on port 8081"+"\n");
            System.out.println ("Server main thread start.\nListen on port 8081"+"\n");

            this.start();
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void run() {
        //��������������
        while(!server.isClosed()) {

            this.severRun.setVisible(true);
            try {
                Socket client = server.accept();  //�����µĿͻ���
                ClientThread current = new ClientThread(client, this);
                current.start();
                clients.add(current);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void close() {
        //���������Socket�ѱ���
        if (server != null) {
            try {
                server.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * ���ص�ǰ�����ӿͻ�������
     */
    public int getSize() {
        return clients.size();
    }

    /**
     * ������������µĿͻ���
     */
    public int addClientConnection(ClientThread ct) {
        clients.add(ct);

        return clients.size();
    }

    /**
     * ���������Ƴ��رյĿͻ���
     *
     * @param ct Ҫ�رյĿͻ����߳�
     * @return �ر�״̬
     */
    public boolean closeClientConnection(ClientThread ct) {
        if (clients.contains(ct)) {
            clients.remove(ct);

            return true;
        }

        return false;
    }

    /**
     * �������а���¼�û�IDѰ�ҿͻ���
     */
    public boolean searchClientConnection(String curUser) {

        this.severRun.textArea.append("��Ѱ֮ǰ���У�"+clients.size()+"���ͻ���");
        System.out.println("��Ѱ֮ǰ���У�"+clients.size()+"���ͻ���");
        for (ClientThread ct: clients) {
            //this.severRun.textArea.append("������������searchClientConnection(String curUser)\n�����curUser="+curUser+"\t�������ͻ��������е�curUser="+ct.curUser);
            if (ct.curUser != null && ct.curUser.equals(curUser)) {
                return true;
            }
        }
        return false;
    }
}

