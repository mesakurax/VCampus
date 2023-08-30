package net;

import entity.*;
import module.*;


import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Vector;


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
				case 2:
					StudentRoll(cmd);
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

	private void StudentRoll(int cmd) {

		StudentRollController stuCrl = new StudentRollController();
		StudentRoll stu=new StudentRoll();
		try {
			stu=(StudentRoll) ois.readObject();
		}catch (Exception e){
			e.printStackTrace();
		}
		//���ݲ�ͬ��Ϣ�����в�ͬ����
		switch (cmd) {
			//��ѧ�Ų�ѯѧ��ѧ����Ϣ
			case 201: {
				try {

					StudentRoll result = stuCrl.query_ID(stu);
					//System.out.println(result.getsId());
					if (result != null) {
						oos.writeInt(2011);
						oos.flush();
						oos.writeObject(result);
						oos.flush();
					} else {
						oos.writeInt(2012);
						oos.flush();
					}

				} catch (Exception e) {
					e.printStackTrace();
				}

				break;
			}
			//���ѧ����Ϣ
			case 202: {
				try {
					int wb = (stuCrl.addInfo(stu)) ? STUDENTROLL_ADD_SUCCESS : STUDENTROLL_ADD_FAIL;
					oos.writeInt(wb);

					oos.flush();
				} catch (IOException e) {
					e.printStackTrace();
				}

				break;
			}
			//ɾ��ѧ����Ϣ
			case 203:
				try {
					int wb = (stuCrl.deleteInfo(stu) ? STUDENTROLL_DELETE_SUCCESS : STUDENTROLL_DELETE_FAIL);
					oos.writeInt(wb);

					oos.flush();
				} catch (IOException e) {
					e.printStackTrace();
				}

				break;

			//�޸�ѧ����Ϣ
			case 204:{
				try {
					int wb = (stuCrl.modifyInfo(stu)) ? STUDENTROLL_MODIFY_SUCCESS : STUDENTROLL_MODIFY_FAIL;
					oos.writeInt(wb);

					oos.flush();
				} catch (IOException e) {
					e.printStackTrace();
				}

				break;
			}

			//��������ѧ����Ϣ
			case 205: {
				try {

					StudentRoll result = stuCrl.query_Name(stu);
					//System.out.println(result.getsName());
					if (result != null) {
						oos.writeInt(2051);
						oos.flush();
						oos.writeObject(result);
						oos.flush();
					} else {
						oos.writeInt(2052);
						oos.flush();
					}

				} catch (Exception e){
					e.printStackTrace();
				}

				break;
			}

			//ѧ����Ϣ��ѯ������Ա��ѯ��
			case 206:
				try {
					Vector<StudentRoll> result = stuCrl.queryAll();

					if (result != null) {
						System.out.println(result.size());//���ѧ����Ϣ��Ŀ��
						oos.writeInt(STUDENTROLL_INFO_QUERY_ALL_SUCCESS);
						oos.writeObject(result);
					} else {
						oos.writeInt(STUDENTROLL_INFO_QUERY_ALL_FAIL);
					}

					oos.flush();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
	}


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




