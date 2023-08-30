package utils;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class SocketHelper {
	private String StuId;
	private Socket socket;
	private ObjectOutputStream os;
	private ObjectInputStream is;
	//	//��¼ip��ַ�Ͷ˿ں�
	public static String ip = "localhost";
	//public static String ip="10.203.169.195";
	public static int port=8081;
	//	//
	public void socketClose(){
		try {
			this.socket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void getConnection(String ip, int port) {
		try {
			this.socket = new Socket(ip, port);

			this.os = new ObjectOutputStream(this.socket.getOutputStream());
			this.is = new ObjectInputStream(this.socket.getInputStream());
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public ObjectOutputStream getOs() {
		return this.os;
	}

	public void setOs(ObjectOutputStream os) {
		this.os = os;
	}

	public ObjectInputStream getIs() {
		return this.is;
	}

	public void setIs(ObjectInputStream is) {
		this.is = is;
	}

	public Socket getSocket() {
		return this.socket;
	}

	public void setSocket(Socket socket) {
		this.socket = socket;
	}

	public String getStuId() {
		return this.StuId;
	}

	public void setStuId(String stuId) {
		this.StuId = stuId;
	}
}