package LoginView;

import entity.Operation;
import entity.User;
import mainUiView.LoginToMain;
import utils.SocketHelper;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class LoginUI extends JFrame {
    private User utemp;
    private MyButton b1;
    private MyButton b2;
    private MyButton b3;
    private MyTextField textField;
    private MyPasswordField textField2;

    //���������
    private static ObjectOutputStream outputStream;
    private static ObjectInputStream inputStream;

    //sockethelper
    private SocketHelper sh;


    int operation;//�ж��û��Ĳ������͵�¼orע��,-1δѡ������0��¼��1ע��.
    Boolean isfinished;//�жϵ�ǰ�û��Ƿ��������,


    //����ͼƬ
    private ImageIcon img;
    //������ǩ
    private JLabel background;
    //�������
    private JPanel jp;


    private int WIDTH;
    private int HEIGHT;


    public User getUser() {
        return utemp;
    }

    public boolean getResult() {
        return isfinished;
    }

    public void setResult(boolean temp) {
        isfinished = temp;
    }

    public void close() {
        setVisible(false);
    }

    public int getOperation() {
        return operation;
    }

    public void setOperation(int temp) {
        operation = temp;
    }

    //����������
    public void setBak() {
        WIDTH = img.getIconWidth();
        HEIGHT = img.getIconHeight();
        ((JPanel) this.getContentPane()).setOpaque(false);
        //ͼƬ��Ӧ��ǩ��С
        img.setImage(img.getImage().getScaledInstance(WIDTH, HEIGHT, Image.SCALE_DEFAULT));
        //����������ǩ
        background = new JLabel(img);
        this.getLayeredPane().add(background, new Integer(Integer.MIN_VALUE));
        background.setBounds(0, 0, WIDTH, HEIGHT);
    }

    public LoginUI(SocketHelper stemp) {

        sh=stemp;

        //�������������
        outputStream = stemp.getOs();
        inputStream = stemp.getIs();

        //�������ڴ�С
        this.setResizable(false);

        //���ñ���

        //���ͼƬ
        img = new ImageIcon("./src/LoginView/pic/image2.jpg"); //���ͼƬ
        setBak(); //���ñ�������
        Container c = getContentPane(); //��ȡJFrame���
        jp = new JPanel(); //������JPanel
        jp.setOpaque(false);
        //��JPanel����Ϊ͸�� �����Ͳ�����ס����ı��� ��������JPanel��������
        jp.setBounds(0,0,WIDTH, HEIGHT);
        c.add(jp);
        setBounds(0, 0, WIDTH, HEIGHT + 37);
        setVisible(true);
        //JPanel����ȱʡ
        jp.setLayout(null);


        //����ȱʡ
        setLayout(null);

        //���ÿɼ���
        setVisible(true);


        //��������
        utemp = new User();
        operation = -1;
        isfinished = false;

        //��ť�Լ���������
        Font f = new Font("΢���ź�", Font.BOLD, 20);
        Font f2 = new Font("΢���ź�", Font.BOLD, 15);
        Font f3 = new Font("�����п�", Font.BOLD, 50);
        Font f4 = new Font("�����п�", Font.BOLD, 70);
        b1 = new MyButton("��¼");
        b1.setFont(f);
        b2 = new MyButton("ע��");
        b2.setFont(f2);
        b3 = new MyButton("�޸�����");
        b3.setFont(f2);


        //��������
        b1.setActionCommand("��¼");
        b2.setActionCommand("ע��");
        b3.setActionCommand("�޸�����");


        //��ǩ
        JLabel lab = new JLabel("�����֤����");
        lab.setFont(f3);
        lab.setForeground(Color.black);
        JLabel lab2 = new JLabel("���ϴ�ѧ");
        lab2.setFont(f4);
        lab2.setForeground(Color.black);

        //�����
        textField = new MyTextField(20);
        textField.setText("�û���");
        textField.setOpaque(false);
        textField.setForeground(Color.black);
        textField.setFont(f);

        //�����
        textField2 = new MyPasswordField(20);
        //��������
        textField2.setEchoChar((char)0);
        textField2.setText("����");
        textField2.setOpaque(false);
        textField2.setForeground(Color.black);
        textField2.setFont(f);

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
                                               textField.setForeground(Color.black); //����ʾ��������Ϊ��ɫ
                                               textField.setText("�û���");     //��ʾ��ʾ����
                                           }
                                       }
                                   });

        //��������
        textField2.addFocusListener(new FocusListener() {

            public void focusGained(FocusEvent e) {
                //�õ�����ʱ����ǰ�ı������ʾ���ֺʹ����ö���ʱ����ʾ����һ����˵���û���Ҫ��������
                if (textField2.getText().equals("����")) {
                    textField2.setText("");     //����ʾ�������
                    //�������
                    textField2.setEchoChar('*');
                    textField2.setForeground(Color.black);  //�����û������������ɫΪ��ɫ
                }


            }

            @Override
            public void focusLost (FocusEvent e){
                //ʧȥ����ʱ���û���δ���ı����������κ����ݣ�����������ʾ��ʾ����
                if (textField2.getText().equals("")) {
                    //����
                    textField2.setEchoChar((char)0);
                    textField2.setForeground(Color.black); //����ʾ��������Ϊ��ɫ
                    textField2.setText("����");     //��ʾ��ʾ����
                }
            }
        });

        //������
        jp.add(textField);
        jp.add(textField2);
        jp.add(lab);
        jp.add(lab2);
        jp.add(b1);
        jp.add(b2);
        jp.add(b3);


        //��Сλ���趨
        lab.setBounds(270, 380, 320, 50);
        lab2.setBounds(280, 270, 300, 80);

        textField.setBounds(320, 480, 220, 30);
        textField2.setBounds(320, 530, 220, 30);
        b1.setBounds(320, 580, 220, 30);
        b2.setBounds(320, 620, 100, 30);
        b3.setBounds(440, 620, 100, 30);


        //��ť���ü���
        MyListener1 lis1 = new MyListener1();
        b1.addActionListener(lis1);
        b2.addActionListener(lis1);
        b3.addActionListener(lis1);

        //�˳�����
        addWindowListener(new WindowAdapter() {
            //�رմ���
            @Override
            public void windowClosing(WindowEvent e) {
                operation = -2;
                isfinished = true;
            }
        });
        //pack();
    }

    //��������
    class MyListener1 implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getActionCommand().equals("��¼")) {
                boolean r1=!textField.getText().equals(null)&&!textField.getText().equals("�û���");
                boolean r2=!textField2.getText().equals(null)&&!textField2.getText().equals("����");
                if(r1&&r2) {
                    utemp.setId(textField.getText());
                    utemp.setPassword(textField2.getText());
                    System.out.println("��¼���� ");
                    utemp.UserPrint();
                    textField.setForeground(Color.black); //����ʾ��������Ϊ��ɫ
                    textField.setText("�û���");     //��ʾ��ʾ����
                    textField2.setForeground(Color.black); //����ʾ��������Ϊ��ɫ
                    textField2.setEchoChar((char)0);//����
                    textField2.setText("����");     //��ʾ��ʾ����
                    System.out.println("��¼");

                    //����Ϊ�ͺ�˵���ϵ

                    try {
                        //��������
                        outputStream.reset();
                        outputStream.writeInt(800);
                        outputStream.flush();

                        //ʵ������������
                        Operation oper=new Operation();
                        oper.addUser(utemp);
                        oper.setOperationcode(001);
                        // �����˷��Ͷ���
                        System.out.println("��¼����");
                        utemp.UserPrint();
                        //���Ͷ���
                        outputStream.reset();
                        outputStream.writeObject(oper);
                        outputStream.flush();
                        System.out.println("�����ͳɹ�");

                        // ��ȡ����˷��͵���Ӧ����
                        oper.copy((Operation)inputStream.readObject());
                        System.out.println("�����ȡ�ɹ�");
                        System.out.println("�������Ӧ��oper����");
                        if(oper.getSuccess())
                        {
                            System.out.println("��¼�ɹ�");
                            utemp=oper.getUser(1);
                            System.out.println("��ǰ��¼�ɹ����û��ǣ� " + utemp.getName());
                            setVisible(false);
                            new LoginToMain(sh,utemp);

                        }
                        else
                        {
                            System.out.println("��½ʧ��");
                            JOptionPane.showMessageDialog(null, "��½ʧ��", "",JOptionPane.WARNING_MESSAGE);
                        }
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    } catch (ClassNotFoundException | InterruptedException ex) {
                        throw new RuntimeException(ex);
                    }


                }
            } else if (e.getActionCommand().equals("ע��")) {
                //����ע�����
                System.out.println("ע��");
                RegisterUI ru=new RegisterUI(sh);
            } else {
                System.out.println("�޸�����");
                //�����޸Ľ���
                PasswordChange pc=new PasswordChange(sh);
            }

        }
    }
    //public static void main(String[] args)
    //{
       // new LoginUI();
    //}

}


