package module;

//import common.User;

import entity.Operation;
import entity.User;
import entityModel.UserDao_Imp;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.SQLException;
import java.util.HashMap;

public class UsersMainModelServer extends Thread{

    //private SocketHelper socket;
    private static Operation operresult;
    //���������
    private static ObjectOutputStream outputStream;
    private static ObjectInputStream inputStream;

    public static void register(User utemp) throws SQLException {

        //������ȡ������
        operresult.operClear();
        //ʵ�������ݿ��������
        UserDao_Imp daotemp = new UserDao_Imp();
        //��¼����
        boolean result = daotemp.insert(utemp);
        if (result) {
            operresult.setSuccess(true);
        }
    }
    public static void searchAll() throws SQLException {

        //ʵ�������ݿ��������
        UserDao_Imp daotemp = new UserDao_Imp();
        //��ѯȫ������
        operresult.setUsers(daotemp.searchAll());
        if(!operresult.getUsers().isEmpty())
        {
            operresult.setSuccess(true);
        }
    }
    public static void searchOne(User utemp) throws SQLException {

        //������ȡ������
        operresult.operClear();
        //ʵ�������ݿ��������
        UserDao_Imp daotemp = new UserDao_Imp();
        System.out.println("kkkkkkkkk: "+utemp.getId());
        //��ѯһ������
        User u=daotemp.select(utemp.getId());

        if(u!=null)
        {
            operresult.addUser(u);
            operresult.setSuccess(true);
        }
    }
    public static void deleteOne(User utemp) throws SQLException {

        //ʵ�������ݿ��������
        UserDao_Imp daotemp = new UserDao_Imp();
        //ɾ��һ������
        if(daotemp.delete(utemp.getId()))
        {

            operresult.setSuccess(true);
        }
        else
        {
            operresult.setSuccess(false);
        }
    }
    public static void updateOne(User utemp) throws SQLException {

        //������ȡ������
        operresult.operClear();
        //ʵ�������ݿ��������
        UserDao_Imp daotemp = new UserDao_Imp();
        //ɾ��һ������
        if(daotemp.update(utemp))
        {

            operresult.setSuccess(true);
        }
    }

    public static void searchOcupation(String ocu) throws SQLException {

        //������ȡ������
        operresult.operClear();

        //ʵ�������ݿ��������
        UserDao_Imp daotemp = new UserDao_Imp();
        //��ѯȫ������
        HashMap<Integer,User> users=new HashMap<Integer,User>();
        users=daotemp.searchAll();
        if(!users.isEmpty())
        {
            //ɸѡ
            for(int i=1;i<=users.size();i++)
            {
                if(users.get(i).getOccupation().equals(ocu))
                {
                    operresult.addUser(users.get(i));
                }
            }
            if(!operresult.getUsers().isEmpty())
            {
                operresult.setSuccess(true);
            }
        }
        else
        {
            operresult.setSuccess(false);
            System.out.println("����Ϊ��ǰְҵ");
        }
    }

    public UsersMainModelServer(ObjectOutputStream ostemp,ObjectInputStream oitemp) {

        //�����������
        outputStream = ostemp;
        inputStream = oitemp;

        //ʵ��������
        operresult=new Operation();
    }
    public static void UsersMainHandler() throws SQLException, IOException, ClassNotFoundException {


        boolean isfinished = false;
        // while (!isfinished) {
        // ��ȡ�ͻ��˷��͵Ķ���
        operresult.copy((Operation) inputStream.readObject());
        System.out.println("�����û��˶���");
        System.out.println("��ţ� " + operresult.getOperationcode());

        if (operresult.getOperationcode() == 002) {
            System.out.println("ע��");
            //ע�����
            System.out.println("909090");
            register(operresult.getUser(1));
            // ��ͻ��˷�����Ӧ����
            outputStream.reset();
            outputStream.writeObject(operresult);
            //���ò���
            operresult.operClear();
            // ������Դ
            outputStream.flush();
        }
        //��ѯȫ��
        else if (operresult.getOperationcode() == 003) {
            System.out.println("��ѯȫ��");
            //������ȡ��ϣ����
            operresult.operClear();
            searchAll();
            // ��ͻ��˷�����Ӧ����
            outputStream.reset();
            outputStream.writeObject(operresult);
            //���ò���
            operresult.operClear();
            // ������Դ
            outputStream.flush();


        }
        //��ѯһ��
        else if (operresult.getOperationcode() == 004) {
            System.out.println("��ѯһ��");
            System.out.println("��ѯ: " + operresult.getUser(1).getId());
            searchOne(operresult.getUser(1));
            // ��ͻ��˷�����Ӧ����
            outputStream.reset();
            outputStream.writeObject(operresult);
            //���ò���
            operresult.operClear();
            // ������Դ
            outputStream.flush();


        }
        //ɾ��
        else if (operresult.getOperationcode() == 005) {
            System.out.println("ɾ��");
            //��ȡ�����������
            int number=operresult.getUsers().size();
            boolean finalresult=true;
            //���½��
            for(int i=1;i<=number;i++) {
                //ִ��ɾ������
                deleteOne(operresult.getUser(i));
                //���½��
                if(!operresult.getSuccess())
                {
                    finalresult=false;
                }
            }
            //���ý��
            operresult.operClear();
            operresult.setSuccess(finalresult);
            // ��ͻ��˷�����Ӧ����
            outputStream.reset();
            outputStream.writeObject(operresult);
            //���ò���
            operresult.operClear();
            // ������Դ
            outputStream.flush();


        }
        //����
        else if (operresult.getOperationcode() == 006) {
            System.out.println("����");
            updateOne(operresult.getUser(1));
            // ��ͻ��˷�����Ӧ����
            outputStream.reset();
            outputStream.writeObject(operresult);
            //���ò���
            operresult.operClear();
            // ������Դ
            outputStream.flush();


        }
        else if(operresult.getOperationcode()==007)
        {
            System.out.println("��ְҵ��ѯ");
            searchOcupation(operresult.getUser(1).getOccupation());
            // ��ͻ��˷�����Ӧ����
            outputStream.reset();
            outputStream.writeObject(operresult);
            //���ò���
            operresult.operClear();
            // ������Դ
            outputStream.flush();
        }

        //��ֹ����
        else if (operresult.getOperationcode() == -1) {
            System.out.println("�������ֹ");
            isfinished = true;

        }
    }
}
// }






