package net;

import com.fasterxml.jackson.databind.ObjectMapper;
import module.*;
import entity.*;
import entityModel.*;
import sqlutil.*;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.Socket;
import java.net.URL;
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
                //��ʼ����uid
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


                //ѡ��ģ��
                case 3:
                    Course(cmd);
                    break;

                //ͼ���
                case  5:
                    Library(cmd);
                    break;

                //����ģ��
                case 7:
                    bank(cmd);
                    break;

                case 8:
                    System.out.println("800");
                    LoginModelServer l22 = new LoginModelServer(oos ,ois);
                    try {
                        l22.LoginHandler();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    } catch (ClassNotFoundException e) {
                        throw new RuntimeException(e);
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                    break;

                case 9:
                    shop(cmd);
                    break;

                case 12:
                    System.out.println("1200");
                    UsersMainModelServer u222 = new UsersMainModelServer(oos ,ois);
                    try {
                        u222.UsersMainHandler();
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    } catch (ClassNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                    break;

                //��Ϣ�ͻ��˶Ͽ�����
                case -3:
                    currentServer.mess.remove(this);
                    this.close();

                //��Ϣ�ͻ��˶Ͽ�����
                case -4:
                    currentServer.clients.remove(this);
                    this.close();

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
                if(!target.curUser.equals(curUser)) {
                    try {
                        System.out.println(mes);
                        target.oos.writeInt(0011);
                        target.oos.flush();
                        target.oos.writeObject("All user");
                        target.oos.writeObject(mes);
                        target.oos.flush();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
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
                if(target.curUser.equals(correctid)){
                    try {
                        System.out.println("��Ϣ���͸�"+correctid);
                        System.out.println(mes);
                        target.oos.writeInt(0011);
                        target.oos.flush();
                        target.oos.writeObject(curUser.substring(2));
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
                    if(!target.curUser.equals(curUser)) {
                        try {
                            target.oos.writeInt(0031);
                            target.oos.flush();
                            target.oos.writeObject("All user");
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
                        if(!target.curUser.equals(curUser)) {
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
                    if(!target.curUser.equals(curUser)) {
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

        else if(cmd==004)  try {
            String id ="&&"+(String) ois.readObject();
            String mes = (String) ois.readObject();

            // ������Ϣ�������ͻ���
            for (ClientThread target : currentServer.mess) {
                if(target.curUser.equals(id)) {
                    try {
                        target.oos.writeInt(0031);
                        target.oos.flush();
                        target.oos.writeObject(curUser.substring(2));
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
                    if(target.curUser.equals(id)) {
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
                if(target.curUser.equals(id)) {
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
                    if(!target.curUser.equals(curUser)) {
                        try {
                            target.oos.writeInt(0051);
                            target.oos.flush();
                            target.oos.writeObject("All user");
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
                        if(!target.curUser.equals(curUser)) {
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
                    if(!target.curUser.equals(curUser)) {
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

        else if(cmd==006){
            try {

                String id ="&&"+(String) ois.readObject();
                String mes = (String) ois.readObject();
                String filename=(String) ois.readObject();

                // ������Ϣ�������ͻ���
                for (ClientThread target : currentServer.mess) {
                    if (target.curUser.equals(id)) {
                        try {
                            target.oos.writeInt(0051);
                            target.oos.flush();
                            target.oos.writeObject(curUser.substring(2));
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
                        if (target.curUser.equals(id)) {
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
                    if (target.curUser.equals(id)) {
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
                if(!server.curUser.equals(curUser)) {
                    String cur = server.curUser;

                    // ȥ��ǰ�����ַ�
                    String modifiedCur = cur.substring(2);
                    result[index++] = modifiedCur;
                }
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
                            target.oos.flush();
                            target.oos.writeObject(ip);
                            target.oos.flush();
                            target.oos.writeObject(curUser.substring(2));
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

        else if(cmd==10)
        {
            if(currentServer.count==0) {
                try {
                    String id = "&&" + (String) ois.readObject();
                    for (ClientThread target : currentServer.mess) {
                        if (target.curUser.equals(id)) {
                            oos.writeInt(101);
                            oos.flush();
                            break;
                        }
                    }
                    currentServer.count+=1;
                } catch (IOException e) {
                    throw new RuntimeException(e);
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }
            else
                currentServer.count-=1;
        }

        else if(cmd==11)
        {
            try {
                String s=(String) ois.readObject();
                String an= new gpt_yun().gpt(s);
                System.out.println(an);

                oos.writeInt(111);
                oos.writeObject(an);
                oos.flush();
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }


        }
    }

    /**
     * ���ܺ��� ��������ģ�͵�API AccessToken
     * @return ��������ģ�͵�API AccessToken
     * @throws IOException
     */


    /**
     * ѧ������ģ��
     *
     * @paramcmd ���ܵ���Ϣ
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
    public void Course(int cmd) {

        CourseSystem courseSystem=new CourseSystem();

        switch (cmd) {

            //�����û���Ϣ��ʾ���û��Ŀγ�ѡ���¼��������һ�������γ���Ϣ�ı�����
            case 301: {
                User userInfo=new User("-1"," "," "," "," ");
                try {
                    userInfo=(User) ois.readObject();
                }catch (Exception e){
                    e.printStackTrace();
                }
                try {
                    CourseTable coursetable=courseSystem.ChooseDisplay(userInfo);
                    oos.writeInt(3011);
                    oos.flush();
                    oos.writeObject(coursetable);
                    oos.flush();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                break;
            }

            //���ѡ�μ�¼
            case 302:{
                try{
                    User userInfo=(User) ois.readObject();
                    Course course=(Course) ois.readObject();
                    //�жϿγ�ʣ�������Ƿ����0
                    if(course.getCrsCSize()<0){
                        try {
                            oos.writeInt(3022);
                            oos.flush();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    else{
                        courseSystem.CprAdd(userInfo,course);
                        course.setCrsCSize(course.getCrsCSize()-1);
                        courseSystem.CourseModify(course);//�޸����ݿ�γ����ݣ��޸Ŀγ�ʣ��������
                        try {
                            oos.writeInt(3021);
                            oos.flush();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }catch(Exception e){
                    e.printStackTrace();
                }
                break;
            }

            //ɾ��ѡ�μ�¼
            case 303:{

                try {
                    try {
                        User userInfo=(User) ois.readObject();
                        Course course=(Course) ois.readObject();
                        courseSystem.CprDelete(userInfo,course);
                        course.setCrsCSize(course.getCrsCSize()+1);
                        courseSystem.CourseModify(course);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    oos.writeInt(3031);
                    oos.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;

            }
            //�޸Ŀγ���Ϣ
            case 304:{
                try{
                    Course course=(Course) ois.readObject();
                    try {
                        //�޸Ŀγ�
                        courseSystem.CourseModify(course);
                        oos.writeInt(3041);
                        oos.flush();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }catch(Exception e){
                    e.printStackTrace();
                }
                break;
            }

            //ɾ���γ���Ϣ�����ѡ�μ�¼
            case 305:{
                try {
                    Course course=(Course) ois.readObject();
                    courseSystem.CourseDelete(course);
                    CrsPickRecord crsPickRecord=new CrsPickRecord(-1,course.getCrsId()," ","-1"," "," ","",0,0,0,-1);
                    RecordTable  cprv=courseSystem.CprSearch(crsPickRecord,3);//��ѯ�����ÿγ̵�ѡ�μ�¼
                    for(int i=0;i<cprv.getRecordCount();i++){
                        courseSystem.CprDelete_P2(cprv.getRecordAtIndex(i));//ɾ����ѡ�μ�¼
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
                try {
                    oos.writeInt(3051);
                    oos.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;

            }
            //��ʾ���п�ѡ��Ŀγ̣�������һ�������γ���Ϣ�ı�����
            case 306: {
                try {
                    CourseTable courseTable=courseSystem.CourseDis();
                    oos.writeInt(3061);
                    oos.flush();
                    oos.writeObject(courseTable);
                    oos.flush();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                break;
            }

            //���ݸ�����ѧ���Ϳγ���Ϣ�жϸ�ѧ���Ƿ�ѡ���ض��γ�
            case 307:{
                User userInfo=new User("-1"," "," "," "," ");
                Course course=new Course(-1,"", "", "", "", "", "-1", " ", -1,-1);
                try {
                    userInfo=(User) ois.readObject();
                    course=(Course) ois.readObject();
                }catch (Exception e){
                    e.printStackTrace();
                }
                try {
                    boolean re=courseSystem.IfChoose(userInfo,course);
                    if(re){
                        oos.writeInt(3071);
                        oos.flush();
                    }
                    else{
                        oos.writeInt(3072);
                        oos.flush();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            }

            //�����ض����������γ̣������ذ������������Ŀγ���Ϣ�ı�����
            case 308:{
                Course course=new Course(-1,"", "", "", "", "", "-1", " ", -1,-1);
                int c=0;
                try {
                    course = (Course) ois.readObject();
                    c = (int) ois.readObject();
                }
                catch (Exception e){
                    e.printStackTrace();
                }
                try {
                    CourseTable courseTable=courseSystem.CourseSearch(course,c);
                    oos.writeInt(3081);
                    oos.flush();
                    oos.writeObject(courseTable);
                    oos.flush();

                }catch(Exception e){
                    e.printStackTrace();
                }
                break;
            }
            //��ӡ����ѡ����Ϣ
            case 309:{
                try {
                    RecordTable recordTable=courseSystem.CprDis();
                    oos.writeInt(3091);
                    oos.flush();
                    oos.writeObject(recordTable);
                    oos.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;

            }
            //�����γ�
            case 310:{
                try{
                    Course course=(Course) ois.readObject();
                    try {
                        if(courseSystem.CourseAdd(course)) {
                            oos.writeInt(3101);
                            oos.flush();
                        }
                        else
                        {
                            oos.writeInt(3102);
                            oos.flush();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }catch(Exception e){
                    e.printStackTrace();
                }
                break;
            }

            //����ѡ�μ�¼
            case 311:{
                CrsPickRecord crsPickRecord=new CrsPickRecord(-1,-1," ","-1"," "," ","",0,0,0, -1);
                int c=0;
                try {
                    crsPickRecord = (CrsPickRecord) ois.readObject();
                    c = (int) ois.readObject();
                }
                catch (Exception e){
                    e.printStackTrace();
                }
                try {
                    RecordTable recordTable=courseSystem.CprSearch(crsPickRecord,c);
                    oos.writeInt(3111);
                    oos.flush();
                    oos.writeObject(recordTable);
                    oos.flush();

                }catch(Exception e){
                    e.printStackTrace();
                }
                break;
            }

            //ɾ��ѡ�μ�¼
            case 312:{
                try {
                    try {
                        CrsPickRecord crsPickRecord=(CrsPickRecord)ois.readObject();
                        courseSystem.CprDelete_P2(crsPickRecord);
                        Course course=new Course(crsPickRecord.getCrsId(), crsPickRecord.getCrsName(), "","","","","-1","",0,0);
                        CourseTable courseTable=courseSystem.CourseSearch(course,6);
                        for(int i=0;i<courseTable.getCourseVector().size();i++) {
                            courseTable.getCourseVector().get(i).setCrsCSize(courseTable.getCourseVector().get(i).getCrsCSize() + 1);
                            courseSystem.CourseModify(courseTable.getCourseVector().get(i));
                        }

                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    oos.writeInt(3121);
                    oos.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            }

            //�޸ķ���
            case 313:{
                try{
                    CrsPickRecord crsPickRecord=(CrsPickRecord) ois.readObject();
                    try {
                        //�޸ķ���
                        courseSystem.Scoring(crsPickRecord);
                        oos.writeInt(3131);
                        oos.flush();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }catch(Exception e){
                    e.printStackTrace();
                }
                break;
            }
            default:
                break;
        }

    }


    /**
     * ͼ���ģ��
     *
     * @param cmd ���ܵ���Ϣ
     */
    public void Library(int cmd) {
        int flag = 1;
        BookSystem model = new BookSystem();
        Book bk = new Book("", "", "", "", "", "", 0,"","");
        BookRecord record = new BookRecord("", "", "", "", "", false, "", "", "", 0);
        BookIllegal illegal=new BookIllegal(0,"","","","","","","",0,false);
        Paper paper=new Paper(0, "","","","","","",0);
        if (cmd / 10 == 50) {
            try {
                bk = (Book) ois.readObject();
                System.out.println("success");
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
            switch (cmd) {
                case 501:
                    try {
                        System.out.println("�������յ��ź�");
                        int writeback = model.addbook(bk) ? 5011 : 5012;
                        System.out.println(writeback);
                        oos.writeInt(writeback);
                        oos.flush();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;

                case 502:
                    try {
                        int writeback = model.deletebook(bk) ? 5021 : 5022;
                        System.out.println(writeback);
                        oos.writeInt(writeback);
                        oos.flush();
                    } catch (IOException | SQLException e) {
                        e.printStackTrace();
                    }
                    break;

                case 503:
                    try {
                        int writeback = model.modifybook(bk) ? 5031 : 5032;
                        System.out.println(writeback);
                        oos.writeInt(writeback);
                        oos.flush();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;

                case 504:
                    try {
                        flag = ois.readInt();
                        System.out.println(flag);
                        List<Book> list = model.searchbook(bk, flag);
                        System.out.println(list.size());
                        if (list != null) {
                            oos.writeObject(list);
                            oos.flush();
                        } else {
                            oos.writeObject(null);
                            oos.flush();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
            }
        }
        if (cmd / 10 == 51) {
            try {
                record = (BookRecord) ois.readObject();
                System.out.println("success");
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }

            switch (cmd) {
                case 511:
                    try {
                        System.out.println("�յ��ͻ�������");
                        int writeback = model.borrowbook(record) ? 5111 : 5112;
                        System.out.println(writeback);
                        oos.writeInt(writeback);
                        oos.flush();

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;

                case 512:
                    try {
                        int writeback = model.returnbook(record) ? 5121 : 5122;
                        System.out.println(writeback);
                        oos.writeInt(writeback);
                        oos.flush();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;

                case 513:
                    try {
                        flag = ois.readInt();
                        System.out.println(flag);
                        List<BookRecord> list = model.searchstatus(record, flag);
                        if (list != null) {
                            oos.writeObject(list);
                            oos.flush();
                        } else {
                            oos.writeObject(null);
                            oos.flush();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
            }
        }
        if(cmd/10==52)
        {
            try {
                illegal = (BookIllegal) ois.readObject();
                System.out.println("success");
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
            switch (cmd)
            {
                case 521:
                    try {
                        System.out.println("�յ��ͻ�������");
                        int writeback = model.addpenality(illegal) ? 5211:5212;
                        System.out.println(writeback);
                        oos.writeInt(writeback);
                        oos.flush();

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;

                case 522:
                    try {
                        int writeback = model.modifyilleagl(illegal) ? 5211 : 5212;
                        System.out.println(writeback);
                        oos.writeInt(writeback);
                        oos.flush();

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;

                case 523:
                    try {
                        flag = ois.readInt();
                        System.out.println(flag);
                        List<BookIllegal> list = model.searchilleagl(illegal, flag);
                        if (list != null) {
                            oos.writeObject(list);
                            oos.flush();
                        } else {
                            oos.writeObject(null);
                            oos.flush();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;

                case 524:
                    try {
                        int writeback = model.deleteillegal(illegal) ? 5241 : 5242;
                        System.out.println(writeback);
                        oos.writeInt(writeback);
                        oos.flush();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
            }
        }

        if (cmd / 10 == 53) {
            try {
                paper = (Paper) ois.readObject();
                System.out.println("success");
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }

            switch (cmd) {
                case 531:
                    try {
                        System.out.println("�յ��ͻ�������");
                        String Filename= (String) ois.readObject();
                        System.out.println(Filename);
                        String projectPath = System.getProperty("user.dir");
                        String savePath = projectPath + "/src/Source/" + Filename;
                        FileOutputStream fos = new FileOutputStream(savePath);
                        String modifiedPath = savePath.replaceAll("\\\\", "/");
                        System.out.println(modifiedPath);
                        byte[] bufferx = new byte[4096];
                        int bytesReadx;
                        while((bytesReadx=ois.read(bufferx)) != -1) {
                            fos.write(bufferx, 0, bytesReadx);
                            fos.flush();
                            if ((new String(bufferx, 0, bytesReadx)).equals("STOPSTOP")) {
                                break;
                            }
                        }
                        fos.close();
                        paper.setPath(modifiedPath);
                        int writeback = model.addpaper(paper) ? 5311 : 5312;
                        System.out.println(writeback);
                        oos.writeInt(writeback);
                        oos.flush();

                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (ClassNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                    break;

                case 532:
                    try {
                        int writeback = model.deletepaper(paper) ? 5321 : 5322;
                        System.out.println(writeback);
                        oos.writeInt(writeback);
                        oos.flush();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;

                case 533:
                    try {
                        String Filename= (String) ois.readObject();
                        System.out.println(Filename);
                        String projectPath = System.getProperty("user.dir");
                        String savePath = projectPath + "/src/Source/" + Filename;
                        FileOutputStream fos = new FileOutputStream(savePath);
                        String modifiedPath = savePath.replaceAll("\\\\", "/");
                        System.out.println(modifiedPath);
                        byte[] bufferx = new byte[4096];
                        int bytesReadx;
                        while((bytesReadx=ois.read(bufferx)) != -1) {
                            fos.write(bufferx, 0, bytesReadx);
                            fos.flush();
                            if ((new String(bufferx, 0, bytesReadx)).equals("STOPSTOP")) {
                                break;
                            }
                        }
                        fos.close();
                        paper.setPath(modifiedPath);
                        int writeback = model.modifypaper(paper) ? 5331 : 5332;
                        System.out.println(writeback);
                        oos.writeInt(writeback);
                        oos.flush();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (ClassNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                    break;

                case 534:
                    try {
                        flag = ois.readInt();
                        System.out.println(flag);
                        List<Paper> list = model.searchpaper(paper, flag);
                        if (list != null) {
                            oos.writeObject(list);
                            oos.flush();
                        } else {
                            oos.writeObject(null);
                            oos.flush();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;

                case 535:
                    try {
                        oos.writeInt(5351);
                        oos.flush();
                        String path=paper.getPath();
                        System.out.println(path);
                        File file=new File(path);
                        String filename=file.getName();
                        FileInputStream fis;
                        byte[] buffer;
                        byte[] stopData;
                        int bytesRead;
                        String stopMessage;
                        oos.writeObject(filename);
                        oos.flush();
                        fis = new FileInputStream(path);
                        buffer = new byte[4096];

                        while ((bytesRead = fis.read(buffer)) != -1) {
                            oos.write(buffer, 0, bytesRead);
                        }

                        oos.flush();
                        fis.close();
                        paper.setCount(paper.getCount()+1);
                        model.modifypaper(paper);
                        stopMessage = "STOPSTOPSTOP";
                        stopData = stopMessage.getBytes();
                        oos.write(stopData);
                        oos.flush();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;

                case 536:
                    try {
                        oos.writeInt(5361);
                        oos.flush();
                        String path=paper.getPath();
                        System.out.println(path);
                        File file=new File(path);
                        String filename=file.getName();
                        FileInputStream fis;
                        byte[] buffer;
                        byte[] stopData;
                        int bytesRead;
                        String stopMessage;
                        oos.writeObject(filename);
                        oos.flush();
                        fis = new FileInputStream(path);
                        buffer = new byte[4096];

                        while ((bytesRead = fis.read(buffer)) != -1) {
                            oos.write(buffer, 0, bytesRead);
                        }

                        oos.flush();
                        fis.close();
                        stopMessage = "STOPSTOPSTOP";
                        stopData = stopMessage.getBytes();
                        oos.write(stopData);
                        oos.flush();
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

    private void shop(int cmd){

        Item itemp=null;
        Cart ctemp=null;
        ItemRecord rtemp=null;
        Shop sp=new Shop();

        if(cmd==901||cmd==902||cmd==903||cmd==904){
            try {
                itemp=(Item)ois.readObject();
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
            System.out.println("��Ʒ");
        }


        else if(cmd==911||cmd==912||cmd==913) {
            try {
                ctemp=(Cart) ois.readObject();
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
            System.out.println("���ﳵ");}

        else if(cmd==922||cmd==921){
            try {
                rtemp=(ItemRecord)ois.readObject();
                System.out.println("���Ѽ�¼");
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }


        switch (cmd){
            case 901:
                try {
                    System.out.println("���������յ��ź�");

                    int writeBack = sp.addItem(itemp)?9011:9012;
                    System.out.println(writeBack);
                    oos.writeInt(writeBack);
                    oos.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case 902:
                try {
                    System.out.println("���������յ��ź�");
                    int writeBack = sp.deleteItem(itemp)?9021:9022;
                    System.out.println(writeBack);
                    oos.writeInt(writeBack);
                    oos.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            case 903:
                try {
                    System.out.println("���������յ��ź�");
                    int writeBack = sp.modifyItem(itemp)?9031:9032;
                    System.out.println(writeBack);
                    oos.writeInt(writeBack);
                    oos.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case 904:
                try {
                    System.out.println("���������յ��ź�");

                    int writeBack = sp.searchItem(itemp)!=null?9041:9042;
                    System.out.println(writeBack);
                    oos.writeInt(writeBack);
                    oos.flush();
                    oos.writeObject(sp.searchItem(itemp));
                    oos.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case 911:
                try {
                    System.out.println("���������յ��ź�");
                    int writeBack = sp.addtoCart(ctemp)?9111:9112;
                    System.out.println(writeBack);
                    oos.writeInt(writeBack);
                    oos.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case 912:
                try {
                    System.out.println("���������յ��ź�");
                    int writeBack = sp.deletefromCart(ctemp)?9121:9122;
                    System.out.println(writeBack);
                    oos.writeInt(writeBack);
                    oos.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case 913:
                try {
                    System.out.println("���������յ��ź�");
                    int writeBack = sp.searchInCart(ctemp)!=null?9131:9132;
                    System.out.println(writeBack);
                    oos.writeInt(writeBack);
                    oos.flush();

                    if (writeBack==9131){
                        oos.writeObject(sp.searchInCart(ctemp));
                        System.out.println("�ɹ�����");
                    }
                    oos.flush();
			/*		if(sp.searchinCart(ctemp)!=null) {wb=MessageTypes.SHOP_TRADE_LIKE_QUERY_SUCCESS;
						oos.writeInt(wb);
						oos.flush();
						oos.writeObject(sp.searchinCart(ctemp));
						oos.flush();
					}
					else {wb=MessageTypes.SHOP_TRADE_LIKE_QUERY_FAIL;
						oos.writeInt(wb);
						oos.flush();}*/
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case 921:
                try {
                    System.out.println("���������յ��ź�");
                    int writeBack = sp.addItemRecord(rtemp)?9211:9212;

                    System.out.println(writeBack);
                    oos.writeInt(writeBack);
                    oos.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;

            case 922:
                try {
                    System.out.println("���������յ��ź�");
                    int writeBack = sp.searchItemRecord(rtemp)!=null?9221:9222;
                    System.out.println(writeBack);
                    oos.writeInt(writeBack);
                    oos.flush();

                    if (writeBack==9221){
                        oos.writeObject(sp.searchItemRecord(rtemp));
                        System.out.println("�ɹ��������Ѽ�¼");
                    }
                    oos.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;

            case 945:
                try {
                    UserDao_Imp temp1=new UserDao_Imp();
                    User uu=(User) ois.readObject();
                    Double b = temp1.select(uu.getId()).getBalance();

                    oos.writeDouble(b);
                    oos.flush();
                    System.out.println(b);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
                break;

            case 946:
                try {
                    UserDao_Imp temp1=new UserDao_Imp();
                    User uu=(User) ois.readObject();
                    Double b = ois.readDouble();

                    temp1.updateC(uu.getId(),b);

                    System.out.println(b);
                } catch (IOException | ClassNotFoundException | SQLException e) {
                    throw new RuntimeException(e);
                }




        }
    }
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

            case 709:
                try{
                    User uu=(User) ois.readObject();
                    UserDao_Imp temp=new UserDao_Imp();
                    oos.writeDouble(temp.select(uu.getId()).getBalance());
                    oos.flush();
                    System.out.println(temp.select(uu.getId()).getBalance());
                }catch (Exception e)
                {
                    e.printStackTrace();
                }


        }


    }

}




