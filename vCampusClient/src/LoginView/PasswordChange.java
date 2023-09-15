package LoginView;
import entity.Operation;
import entity.User;
import utils.SocketHelper;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class PasswordChange extends JFrame {


    //���������
    private static ObjectOutputStream outputStream;
    private static ObjectInputStream inputStream;

    private User utemp;
    private User unew;

    private MyButton button1;
    private MyTextField textField;
    private MyPasswordField textField2;
    private MyPasswordField textField3;


    //����ͼƬ
    private ImageIcon img;
    //������ǩ
    private JLabel background;
    //�������
    private JPanel jp;


    private int WIDTH;
    private int HEIGHT;


    public User getUser()
    {
        return utemp;
    }

    public User getNewuser()
    {
        return unew;
    }

    public void close()
    {
        setVisible(false);
    }

    //����������
    public void setBak() {
        WIDTH = 400;
        HEIGHT = 600;
        ((JPanel) this.getContentPane()).setOpaque(false);
        //ͼƬ��Ӧ��ǩ��С
        img.setImage(img.getImage().getScaledInstance(WIDTH, HEIGHT, Image.SCALE_DEFAULT));
        //����������ǩ
        background = new JLabel(img);
        this.getLayeredPane().add(background, new Integer(Integer.MIN_VALUE));
        background.setBounds(0, 0, WIDTH, HEIGHT);
    }
    public PasswordChange(SocketHelper stemp)
    {

        //�������������
        outputStream = stemp.getOs();
        inputStream = stemp.getIs();


        //���ÿɼ���
        setVisible(true);

        //������س�ʼ��
        utemp=new User();
        unew=new User();

        //�������ڴ�С
        this.setResizable(false);

        //������س�ʼ��

        //���ͼƬ
        img = new ImageIcon("./src/LoginView/pic/image5.jpg"); //���ͼƬ
        setBak(); //���ñ�������
        Container c = getContentPane(); //��ȡJFrame���
        jp = new JPanel(); //������JPanel
        jp.setOpaque(false);
        //��JPanel����Ϊ͸�� �����Ͳ�����ס����ı��� ��������JPanel��������
        jp.setBounds(0,0,WIDTH, HEIGHT);
        c.add(jp);
        setBounds(700, 200, WIDTH, HEIGHT);
        //JPanel����ȱʡ
        jp.setLayout(null);


        //����ȱʡ
        setLayout(null);


        //��������
        Font f = new Font("�����п�", Font.BOLD, 20);

        //��ť
        button1= new MyButton("�޸�����");
        button1.setActionCommand("�޸�����");
        button1.setFont(f);


        //�����
        textField=new MyTextField(20);
        textField2=new MyPasswordField(20);
        textField3=new MyPasswordField(20);

        //������
        jp.add(textField);
        jp.add(textField2);
        jp.add(textField3);
        jp.add(button1);

        //���������
        textField.setText("�û���");
        textField.setOpaque(false);
        textField.setForeground(Color.white);
        textField.setFont(f);

        //��������
        textField2.setEchoChar((char)0);
        textField2.setText("ԭ����");
        textField2.setOpaque(false);
        textField2.setForeground(Color.white);
        textField2.setFont(f);

        textField3.setEchoChar((char)0);
        textField3.setText("������");
        textField3.setOpaque(false);
        textField3.setForeground(Color.white);
        textField3.setFont(f);


        //�ı������

        textField.addFocusListener(new FocusListener() {

            public void focusGained(FocusEvent e) {
                //�õ�����ʱ����ǰ�ı������ʾ���ֺʹ����ö���ʱ����ʾ����һ����˵���û���Ҫ��������
                if (textField.getText().equals("�û���")) {
                    textField.setText("");     //����ʾ�������
                    textField.setForeground(Color.black);  //�����û������������ɫΪ��ɫ
                }


            }

            @Override
            public void focusLost (FocusEvent e){
                //ʧȥ����ʱ���û���δ���ı����������κ����ݣ�����������ʾ��ʾ����
                if (textField.getText().equals("")) {
                    textField.setForeground(Color.white); //����ʾ��������Ϊ��ɫ
                    textField.setText("�û���");     //��ʾ��ʾ����
                }
            }
        });



        //��������
        textField2.addFocusListener(new FocusListener() {

            public void focusGained(FocusEvent e) {
                //�õ�����ʱ����ǰ�ı������ʾ���ֺʹ����ö���ʱ����ʾ����һ����˵���û���Ҫ��������
                if (textField2.getText().equals("ԭ����")) {
                    textField2.setText("");     //����ʾ�������
                    //�������
                    textField2.setEchoChar('?');
                    textField2.setForeground(Color.black);  //�����û������������ɫΪ��ɫ
                }


            }

            @Override
            public void focusLost (FocusEvent e){
                //ʧȥ����ʱ���û���δ���ı����������κ����ݣ�����������ʾ��ʾ����
                if (textField2.getText().equals("")) {
                    //����
                    textField2.setEchoChar((char)0);
                    textField2.setForeground(Color.white); //����ʾ��������Ϊ��ɫ
                    textField2.setText("ԭ����");     //��ʾ��ʾ����
                }
            }
        });

        textField3.addFocusListener(new FocusListener() {

            public void focusGained(FocusEvent e) {
                //�õ�����ʱ����ǰ�ı������ʾ���ֺʹ����ö���ʱ����ʾ����һ����˵���û���Ҫ��������
                if (textField3.getText().equals("������")) {
                    textField3.setText("");     //����ʾ�������
                    //�������
                    textField3.setEchoChar('?');
                    textField3.setForeground(Color.black);  //�����û������������ɫΪ��ɫ
                }


            }

            @Override
            public void focusLost (FocusEvent e){
                //ʧȥ����ʱ���û���δ���ı����������κ����ݣ�����������ʾ��ʾ����
                if (textField3.getText().equals("")) {
                    //����
                    textField3.setEchoChar((char)0);
                    textField3.setForeground(Color.white); //����ʾ��������Ϊ��ɫ
                    textField3.setText("������");     //��ʾ��ʾ����
                }
            }
        });

        //��Сλ���趨
        textField.setBounds(90,120,220,40);
        textField2.setBounds(90,180,220,40);
        textField3.setBounds(90,240,220,40);
        button1.setBounds(90,300,220,40);



        //��ť���ü���
        MyListener1 lis1=new MyListener1();
        button1.addActionListener(lis1);

        //�˳�����
        addWindowListener(new WindowAdapter() {
            //�رմ���
            @Override
            public void windowClosing(WindowEvent e) {
                  setVisible(false);
            }
        });
        //pack();
    }

    //��������
    class MyListener1 implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String id=textField.getText();
            String op=textField2.getText();
            String np=textField3.getText();
            //�ж��Ƿ�����
            Boolean r1=!id.isEmpty()&&!id.equals("�û���");
            Boolean r2=!op.isEmpty()&&!op.equals("ԭ����");
            Boolean r3=!np.isEmpty()&&!np.equals("������");
            //����
            if(r1&&r2&&r3) {
                utemp.setId(id);
                utemp.setPassword(op);
                unew.setId(id);
                unew.setPassword(np);
                textField.setText("�û���");
                textField.setOpaque(false);
                textField.setForeground(Color.white);
                //����
                textField2.setEchoChar((char) 0);
                textField2.setForeground(Color.white); //����ʾ��������Ϊ��ɫ
                textField2.setText("ԭ����");     //��ʾ��ʾ����
                //����
                textField3.setEchoChar((char) 0);
                textField3.setForeground(Color.white); //����ʾ��������Ϊ��ɫ
                textField3.setText("������");     //��ʾ��ʾ����


                //�����Ǻͺ��ͨѶ
                //��������
                try {
                    outputStream.reset();
                    outputStream.writeInt(800);
                    outputStream.flush();


                    //ʵ������������
                    Operation oper=new Operation();
                    oper.addUser(utemp);
                    oper.addUser(unew);
                    oper.setOperationcode(007);
                    // �����˷��Ͷ���
                    outputStream.reset();
                    outputStream.writeObject(oper);
                    outputStream.flush();
                    System.out.println("�����ͳɹ�");

                    // ��ȡ����˷��͵���Ӧ����
                    oper.copy((Operation) inputStream.readObject());
                    System.out.println("�����ȡ�ɹ�");
                    System.out.println("�������Ӧ��oper����");

                    if(oper.getSuccess())
                    {
                        JOptionPane.showMessageDialog(null, "�����޸ĳɹ�");
                        setVisible(false);
                    }
                    else {
                        JOptionPane.showMessageDialog(null, "�����޸�ʧ��", "", JOptionPane.WARNING_MESSAGE);
                    }
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                } catch (ClassNotFoundException ex) {
                    throw new RuntimeException(ex);
                }


            }
            else {
                JOptionPane.showMessageDialog(null, "�����Ƿ�����ȫ����Ϣ", "",JOptionPane.WARNING_MESSAGE);

            }


        }
    }



}
