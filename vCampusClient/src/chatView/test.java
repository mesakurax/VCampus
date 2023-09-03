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

        client a=new client();
        a.run();
        client b=new client();
        b.run();
        client c=new client();
        c.run();
    }
}





class client {

    public void run() {
        try {
            JFrame frame = new JFrame();

            User t = new User();
            t.setName(Timehelp.getCurrentTime());
            chatView stuAdmin = new chatView(t);

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

