package module;

//import common.User;

import entity.Operation;
import entity.User;
import entityModel.UserDao_Imp;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.SQLException;

public class LoginModelServer extends Thread{

    private static Operation operresult;

    public static void login(User utemp) throws SQLException {


        //������ȡ������
        operresult.operClear();
        //ʵ�������ݿ��������
        UserDao_Imp daotemp=new UserDao_Imp();
        //��¼����
        User u=daotemp.login(utemp);
        if(u!=null)
        {
            operresult.addUser(u);
            operresult.setSuccess(true);
        }



    }
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
    public static void passwordChange(User utemp, User unew) throws SQLException {

        //������ȡ������
        operresult.operClear();
        //ʵ�������ݿ��������
        UserDao_Imp daotemp = new UserDao_Imp();
        //ɾ��һ������
        if(daotemp.login(utemp)!=null)
        {

            System.out.println("��¼�ɹ�090909");
            if(daotemp.updateB(unew))
            {
                System.out.println("�޸ĳɹ�090909");
                operresult.setSuccess(true);
            }
        }
    }

    //���������
    private static ObjectOutputStream outputStream;
    private static ObjectInputStream inputStream;



    public LoginModelServer(ObjectOutputStream ostemp, ObjectInputStream oitemp) {

        //�����������
        outputStream = ostemp;
        inputStream = oitemp;

        //ʵ��������
         operresult=new Operation();

    }

    public static  void LoginHandler() throws IOException, ClassNotFoundException, SQLException {

        //�û��Ƿ��˳�
        boolean isfinished=false;
       // while (!isfinished) {
            System.out.println("4545454545");

            // ��ȡ�ͻ��˷��͵Ķ���
             operresult.copy((Operation)inputStream.readObject());
            // operresult=((Operation)inputStream.readObject());

            //��ȡ����

            if (operresult.getOperationcode() == 001) {
                System.out.println("��¼");
                //��¼����
                login(operresult.getUser(1));
                if(operresult.getSuccess())
                {
                    isfinished=true;
                }
                // ��ͻ��˷�����Ӧ����
                outputStream.reset();
                outputStream.writeObject(operresult);
                //���ò���
                operresult.operClear();
                // ������Դ
                outputStream.flush();
            } else if (operresult.getOperationcode() == 002) {
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

            //�޸�����
            else if (operresult.getOperationcode() == 007) {
                System.out.println("�޸�");
                passwordChange(operresult.getUser(1),operresult.getUser(2));
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
                isfinished=true;

            }

        }
   // }



}


