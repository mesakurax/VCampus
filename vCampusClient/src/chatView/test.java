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
        client a=new client();
        a.run(a1);
        client b=new client();
        b.run(b1);
       // client c=new client();
       // c.run();
    }
}





class client {

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

