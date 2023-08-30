package net;

import entity.*;
import module.*;


import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;


/**
 * �ͻ����߳�
 */
public class ClientThread extends Thread implements MessageTypes{
	/**
	 * �ͻ��˵�ǰ���ӵķ������߳�
	 */
	private ServerThread currentServer;
	/**
	 * �ͻ���socket
	 */
	private Socket client;
	/**
	 * ����������
	 */
	private ObjectInputStream ois;
	/**
	 * ���������
	 */
	private ObjectOutputStream oos;
	/**
	 * ��ǰ��¼�û�
	 */
	public String curUser;

	public ClientThread(Socket s, ServerThread st) {
		client = s;
		currentServer = st;
		curUser ="&&";
		try {
			//���������������������ͻ����෴��
			ois = new ObjectInputStream(client.getInputStream());
			oos = new ObjectOutputStream(client.getOutputStream());

		} catch (IOException e) {
			System.out.println("Cannot get IO stream");
			e.printStackTrace();
		}
	}
	
	public void run() {
		int cmd = 0;//�ӿͻ��˶�������Ϣ

		while (true) {
			//��ȡ��Ϣ
			try {
				cmd = ois.readInt();
				currentServer.severRun.textArea.append("���������ܵ���ָ�" + cmd + " ���ԣ�" + curUser + "\n");
				System.out.println("���������ܵ���ָ�" + cmd + " ���ԣ�" + curUser);
			} catch (IOException e) {
				//������ָ�˵���ѵǳ�
				return;
			}

			//�ж���Ϣ������һ���ͣ����ö�Ӧģ�麯�������Ӧ����
			switch (cmd / 100) {
				case -1:
					String uId = "";
					try {
						uId = (String) ois.readObject();
						curUser = curUser + uId;
					} catch (Exception e) {
						e.printStackTrace();
					}
					break;


				//����ģ��
				case 7:
					bank(cmd);
					break;

			}
		}
	}

	public void close() {
		if (client != null) {
			try {
				oos.close();
				ois.close();
				client.close();

				currentServer.closeClientConnection(this);//�ڷ������߳��йرոÿͻ���
				String msg="�û�" + curUser + "�ѵǳ�\n"+"�ͻ���IP��" + client.getInetAddress().getHostAddress() +"  �ѶϿ�,��ʣ��"+currentServer.getSize()+"���ͻ���";
				currentServer.severRun.textArea.append(msg);

			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}



	/**
	 * ��ģ�鹦�ܺ���
	 *
	 * ����������ͻ��˵����ݽ����������ģʽ��
	 * 1. �������ӿͻ��˶�ȡ��Ϣ
	 * 2. �������ӿͻ��˶�ȡ�����������ѡ��
	 * 3. ��������ͻ���д������״̬
	 * 4. ��������ͻ���д��������������ѡ��
	 */



	/**
	 * ��¼ģ��
	 *
	 * @param cmd ���ܵ���Ϣ
	 */




	/**
	 * �γ̹���ģ��
	 *
	 * @param cmd ���ܵ���Ϣ
	 */




	/**
	 * �̵�ģ��
	 *
	 * @param cmd ���ܵ���Ϣ
	 */


	/**
	 * ����ģ��
	 *
	 * @param cmd ���ܵ���Ϣ
	 */
	private void bank(int cmd){
		bankSystem model=new bankSystem();
		rechargeRecord info=new rechargeRecord();
		try{
			info=(rechargeRecord) ois.readObject();
		}
		catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		}

		switch (cmd){
			case 701:
				try {
					System.out.println("���������յ��ź�");
					int writeBack = model.addRecord(info)?7011:7012;
					System.out.println(writeBack);
					oos.writeInt(writeBack);
					oos.flush();
				} catch (IOException e) {
					e.printStackTrace();
				}

				break;
		}


	}


}




