package net;

import entity.*;
import module.*;


import javax.swing.*;
import java.awt.*;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.SQLException;
import java.util.List;
import java.util.Vector;


/**
 * �ͻ����߳�
 */
public class ClientThread extends Thread implements MessageTypes {
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

    public ObjectInputStream getOis() {
        return ois;
    }

    /**
     * ��ǰ��¼�û�
     */
    public String curUser;

    public ClientThread(Socket s, ServerThread st) {
        client = s;
        currentServer = st;
        curUser = "&&";
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

                //�����Ҵ���
                case 0:
                    chatRoom(cmd);
                    break;

                //ѧ��ģ��
                case 2:
                    StudentRoll(cmd);
                    break;

                //ͼ���
                case  5:
                    Library(cmd);
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
                String msg = "�û�" + curUser + "�ѵǳ�\n" + "�ͻ���IP��" + client.getInetAddress().getHostAddress() + "  �ѶϿ�,��ʣ��" + currentServer.getSize() + "���ͻ���";
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
     * ����ģ��
     *
     * @param cmd ���ܵ���Ϣ
     */
    private void chatRoom(int cmd) {
        if(cmd==001) {
            String mes = "";

            try {
                mes = (String) ois.readObject();
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println("��Ϣ�ᷢ��������Ϊ" + currentServer.mess.size());
            for (ClientThread target:currentServer.mess) {
                try {
                    System.out.println(mes);
                    target.oos.writeInt(0011);
                    target.oos.flush();
                    target.oos.writeObject(mes);
                    target.oos.flush();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        else if(cmd==002) {
            String mes = "";
            String id = "";
            try {
                id = (String) ois.readObject();
                mes=(String) ois.readObject();
            } catch (Exception e) {
                e.printStackTrace();
            }
            String correctid="&&"+id;

            for (ClientThread target:currentServer.mess){
                if(target.curUser.equals(correctid)||target.curUser.equals(curUser)){
                    try {
                        System.out.println("��Ϣ���͸�"+correctid);
                        System.out.println(mes);
                        target.oos.writeInt(0011);
                        target.oos.flush();
                        target.oos.writeObject(mes);
                        target.oos.flush();
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }
        }

        else if(cmd==003) {
            try {
                String mes = (String) ois.readObject();

                // ������Ϣ�������ͻ���
                for (ClientThread target : currentServer.mess) {
                    try {
                        target.oos.writeInt(0031);
                        target.oos.flush();
                        target.oos.writeObject(mes);
                        target.oos.flush();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                // �ֿ鴫���ļ���
                byte[] buffer = new byte[4096];
                int bytesRead;

                while ((bytesRead = ois.read(buffer)) != -1) {
                    // �����ļ����������ͻ���
                    for (ClientThread target : currentServer.mess) {
                        try {
                            target.oos.write(buffer, 0, bytesRead);
                            target.oos.flush();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    if (new String(buffer, 0, bytesRead).equals("STOP")) {
                        break;
                    }
                }

                // ����ֹͣ��ʶ�������ͻ���
                byte[] stopData = "STOP".getBytes();
                for (ClientThread target : currentServer.mess) {
                    try {
                        target.oos.write(stopData);
                        target.oos.flush();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        else if(cmd==004)  try {
            String id ="&&"+(String) ois.readObject();
            String mes = (String) ois.readObject();

            // ������Ϣ�������ͻ���
            for (ClientThread target : currentServer.mess) {
                if(target.curUser.equals(id)||target.curUser.equals(curUser)) {
                    try {
                        target.oos.writeInt(0031);
                        target.oos.flush();
                        target.oos.writeObject(mes);
                        target.oos.flush();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            // �ֿ鴫���ļ���
            byte[] buffer = new byte[4096];
            int bytesRead;

            while ((bytesRead = ois.read(buffer)) != -1) {
                // �����ļ����������ͻ���
                for (ClientThread target : currentServer.mess) {
                    if(target.curUser.equals(id)||target.curUser.equals(curUser)) {
                        try {
                            target.oos.write(buffer, 0, bytesRead);
                            target.oos.flush();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
                if (new String(buffer, 0, bytesRead).equals("STOP")) {
                    break;
                }
            }

            // ����ֹͣ��ʶ�������ͻ���
            byte[] stopData = "STOP".getBytes();
            for (ClientThread target : currentServer.mess) {
                if(target.curUser.equals(id)||target.curUser.equals(curUser)) {
                    try {
                        target.oos.write(stopData);
                        target.oos.flush();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        else if(cmd==005){
            try {
                String mes = (String) ois.readObject();
                String filename=(String) ois.readObject();

                // ������Ϣ�������ͻ���
                for (ClientThread target : currentServer.mess) {
                    try {
                        target.oos.writeInt(0051);
                        target.oos.flush();
                        target.oos.writeObject(mes);
                        target.oos.flush();
                        target.oos.writeObject(filename);
                        target.oos.flush();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                // �ֿ鴫���ļ���
                byte[] buffer = new byte[4096];
                int bytesRead;

                while ((bytesRead = ois.read(buffer)) != -1) {
                    // �����ļ����������ͻ���
                    for (ClientThread target : currentServer.mess) {
                        try {
                            target.oos.write(buffer, 0, bytesRead);
                            target.oos.flush();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    if (new String(buffer, 0, bytesRead).equals("STOP")) {
                        break;
                    }
                }

                // ����ֹͣ��ʶ�������ͻ���
                byte[] stopData = "STOP".getBytes();
                for (ClientThread target : currentServer.mess) {
                    try {
                        target.oos.write(stopData);
                        target.oos.flush();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        else if(cmd==006){
            try {

                String id ="&&"+(String) ois.readObject();
                String mes = (String) ois.readObject();
                String filename=(String) ois.readObject();

                // ������Ϣ�������ͻ���
                for (ClientThread target : currentServer.mess) {
                    if (target.curUser.equals(id) || target.curUser.equals(curUser)) {
                        try {
                            target.oos.writeInt(0051);
                            target.oos.flush();
                            target.oos.writeObject(mes);
                            target.oos.flush();
                            target.oos.writeObject(filename);
                            target.oos.flush();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }

                // �ֿ鴫���ļ���
                byte[] buffer = new byte[4096];
                int bytesRead;

                while ((bytesRead = ois.read(buffer)) != -1) {
                    // �����ļ����������ͻ���
                    for (ClientThread target : currentServer.mess) {
                        if (target.curUser.equals(id) || target.curUser.equals(curUser)) {
                            try {
                                target.oos.write(buffer, 0, bytesRead);
                                target.oos.flush();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    if (new String(buffer, 0, bytesRead).equals("STOP")) {
                        break;
                    }
                }

                // ����ֹͣ��ʶ�������ͻ���
                byte[] stopData = "STOP".getBytes();
                for (ClientThread target : currentServer.mess) {
                    if (target.curUser.equals(id) || target.curUser.equals(curUser)) {
                        try {
                            target.oos.write(stopData);
                            target.oos.flush();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        else if(cmd==007) {
            String[] result = new String[currentServer.mess.size()];

            int index = 0;
            for (ClientThread server : currentServer.mess) {
                String cur = server.curUser;

                // ȥ��ǰ�����ַ�
                String modifiedCur = cur.substring(2);
                result[index++] = modifiedCur;
            }

            try {
                oos.writeInt(0071);
                oos.flush();
                oos.writeObject(result);
                oos.flush();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }

        //����ͨ��
        else if(cmd==8)
        {
            try {
                String id="&&"+(String) ois.readObject();
                for(ClientThread target:currentServer.mess)
                {
                    if(target.curUser.equals(id))
                    {
                        target.oos.writeInt(81);
                        target.oos.flush();
                        target.oos.writeObject(curUser.substring(2));
                        target.oos.flush();
                        break;
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        //���ؽ��
        else if(cmd==9)
        {
            try {
                String id="&&"+(String) ois.readObject();
                int num=ois.readInt();
                if(num==91) {
                    for (ClientThread target : currentServer.mess) {
                        if (target.curUser.equals(id)) {
                            target.oos.writeInt(91);
                            target.oos.flush();
                            break;
                        }
                    }
                }
                else
                {
                    String ip=(String) ois.readObject();
                    System.out.println(ip);
                    for (ClientThread target : currentServer.mess) {
                        if (target.curUser.equals(id)) {
                            target.oos.writeInt(92);
                            target.oos.writeObject(ip);
                            target.oos.flush();
                            break;
                        }
                    }
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }




    /**
     * ѧ������ģ��
     *
     * @param cmd ���ܵ���Ϣ
     */
    private void StudentRoll(int cmd) {

        StudentRollController stuCrl = new StudentRollController();
        StudentRoll stu = new StudentRoll();
        try {
            stu = (StudentRoll) ois.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        }
        //���ݲ�ͬ��Ϣ�����в�ͬ����
        switch (cmd) {
            //��ѧ�Ų�ѯѧ����Ϣ
            case STUDENTROLL_INFO_QUERY_ID: {
                try {

                    StudentRoll result = stuCrl.query_ID(stu);
                    if (result != null) {
                        oos.writeInt(STUDENTROLL_INFO_QUERY_ID_SUCCESS);
                        oos.flush();
                        oos.writeObject(result);
                        oos.flush();
                    } else {
                        oos.writeInt(STUDENTROLL_INFO_QUERY_ID_FAIL);
                        oos.flush();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

                break;
            }
            //���ѧ����Ϣ
            case STUDENTROLL_ADD: {
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
            case STUDENTROLL_DELETE:
                try {
                    int wb = (stuCrl.deleteInfo(stu) ? STUDENTROLL_DELETE_SUCCESS : STUDENTROLL_DELETE_FAIL);
                    oos.writeInt(wb);

                    oos.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                break;

            //�޸�ѧ����Ϣ
            case STUDENTROLL_MODIFY: {
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
            case STUDENTROLL_INFO_QUERY_NAME: {
                try {

                    StudentRoll result = stuCrl.query_Name(stu);
                    if (result != null) {
                        oos.writeInt(STUDENTROLL_INFO_QUERY_NAME_SUCCESS);
                        oos.flush();
                        oos.writeObject(result);
                        oos.flush();
                    } else {
                        oos.writeInt(STUDENTROLL_INFO_QUERY_NAME_FAIL);
                        oos.flush();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

                break;
            }

            //����ȫ��ѧ����Ϣ������Աʹ�ã�
            case STUDENTROLL_INFO_QUERY_ALL:
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
     * ͼ���ģ��
     *
     * @param cmd ���ܵ���Ϣ
     */

    public void Library(int cmd)
    {
        int flag=1;
        BookSystem model=new BookSystem();
        Book bk=new Book("","","","","","",0);
        BookRecord record=new BookRecord("","","","","",false,"","","",0);

        if(cmd/10==50)
        {
            try {
                bk=(Book) ois.readObject();
            } catch (IOException |ClassNotFoundException e) {
                e.printStackTrace();
            }
            switch (cmd)
            {
                case 501:
                    try {
                        System.out.println("�������յ��ź�");
                        int writeback=model.addbook(bk)?5011:5012;
                        System.out.println(writeback);
                        oos.writeInt(writeback);
                        oos.flush();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;

                case 502:
                    try {
                        int writeback=model.deletebook(bk)?5021:5022;
                        System.out.println(writeback);
                        oos.writeInt(writeback);
                        oos.flush();
                    } catch (IOException | SQLException e) {
                        e.printStackTrace();
                    }
                    break;

                case 503:
                    try {
                        int writeback=model.modifybook(bk)?5031:5032;
                        System.out.println(writeback);
                        oos.writeInt(writeback);
                        oos.flush();
                    }catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;

                case  504:
                    try {
                        flag=ois.readInt();
                        System.out.println(flag);
                        java.util.List<Book> list=model.searchbook(bk,flag);
                        System.out.println(list.size());
                        if(list!=null)
                        {
                            oos.writeObject(list);
                            oos.flush();
                        }
                        else
                        {
                            oos.writeObject(null);
                            oos.flush();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
            }
        }
        if(cmd/10==51)
        {
            try {
                record=(BookRecord) ois.readObject();
            } catch (IOException |ClassNotFoundException e) {
                e.printStackTrace();
            }

            switch (cmd)
            {
                case 511:
                    try {
                        System.out.println("�յ��ͻ�������");
                        int writeback=model.borrowbook(record)?5111:5112;
                        System.out.println(writeback);
                        oos.writeInt(writeback);
                        oos.flush();

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;

                case 512:
                    try {
                        int writeback=model.returnbook(record)?5121:5122;
                        System.out.println(writeback);
                        oos.writeInt(writeback);
                        oos.flush();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;

                case 513:
                    try {
                        flag=ois.readInt();
                        System.out.println(flag);
                        List<BookRecord> list=model.searchstatus(record,flag);
                        if(list!=null)
                        {
                            oos.writeObject(list);
                            oos.flush();
                        }
                        else
                        {
                            oos.writeObject(null);
                            oos.flush();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
            }
        }
    }

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
    private void bank(int cmd) {
        bankSystem model = new bankSystem();
        rechargeRecord info = new rechargeRecord();
        try {
            info = (rechargeRecord) ois.readObject();
        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }

        switch (cmd) {
            case 701:
                try {
                    System.out.println("���������յ��ź�");
                    int writeBack = model.addRecord(info) ? 7011 : 7012;
                    System.out.println(writeBack);
                    oos.writeInt(writeBack);
                    oos.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                break;

            case 702:
                try {
                    rechargeRecord[] result = model.queryRecord(info,1);

                    if (result != null) {
                        System.out.println(7021);
                        oos.writeInt(7021);
                        oos.flush();
                        oos.writeObject(result);
                        oos.flush();
                    } else {
                        System.out.println(7022);
                        oos.writeInt(7022);
                        oos.flush();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;

            case 703:
                try {
                    rechargeRecord[] result = model.queryRecord(info,2);

                    if (result != null) {
                        System.out.println(7031);
                        oos.writeInt(7031);
                        oos.flush();
                        oos.writeObject(result);
                        oos.flush();
                    } else {
                        System.out.println(7032);
                        oos.writeInt(7032);
                        oos.flush();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;

            case 704:
                try {
                    rechargeRecord[] result = model.queryRecord(info,3);

                    if (result != null) {
                        System.out.println(7041);
                        oos.writeInt(7041);
                        oos.flush();
                        oos.writeObject(result);
                        oos.flush();
                    } else {
                        System.out.println(7042);
                        oos.writeInt(7042);
                        oos.flush();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;

            case 705:
                try {
                    int writeBack = model.accept(info)?7051:7052;
                    System.out.println(writeBack);
                    oos.writeInt(writeBack);
                    oos.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;

            case 706:
                try {
                    int writeBack = model.refuse(info)?7061:7062;
                    System.out.println(writeBack);
                    oos.writeInt(writeBack);
                    oos.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;

            case 707:
                try {
                    rechargeRecord[] result = model.queryRecord(info,4);

                    if (result != null) {
                        System.out.println(7071);
                        oos.writeInt(7071);
                        oos.flush();
                        oos.writeObject(result);
                        oos.flush();
                    } else {
                        System.out.println(7072);
                        oos.writeInt(7072);
                        oos.flush();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;

            case 708:
                try {
                    rechargeRecord[] result = model.queryRecord(info,5);

                    if (result != null) {
                        System.out.println(7081);
                        oos.writeInt(7081);
                        oos.flush();
                        oos.writeObject(result);
                        oos.flush();
                    } else {
                        System.out.println(7082);
                        oos.writeInt(7082);
                        oos.flush();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;



        }


    }



}




