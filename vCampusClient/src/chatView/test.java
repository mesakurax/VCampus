package chatView;

import entity.User;
import utils.SocketHelper;
import utils.Timehelp;

import javax.swing.*;
import java.awt.*;

public class test {

    public test() {
    }

    public static void main(String[] args) {

        User a1=new User();
        a1.setName("С��");
        a1.setId("090212");
        User b1=new User();
        b1.setName("С��");
        b1.setId("090211");
        User c1=new User();
        c1.setName("С��edkoi");
        c1.setId("090216");
        client_pp a=new client_pp();
        a.run(a1);
        client_pp b=new client_pp();
        b.run(b1);
       // client_pp c=new client_pp();
     //   c.run(c1);
    }
}




class client_pp {

    public void run(User info) {
        try {
            JFrame frame = new JFrame();

            chatView stuAdmin = new chatView(info);

            frame.setLayout(new BorderLayout()); // ���ò��ֹ�����ΪBorderLayout

            frame.add(stuAdmin, BorderLayout.CENTER); // ��StuAdmin��ӵ�CENTERλ��
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.pack();
            frame.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

