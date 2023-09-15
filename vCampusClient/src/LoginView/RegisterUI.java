package LoginView;

import entity.Operation;
import entity.User;
import utils.SocketHelper;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class RegisterUI  extends JFrame {



    //���������
    private static ObjectOutputStream outputStream;
    private static ObjectInputStream inputStream;
    private User utemp;



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
    public void close()
    {
        setVisible(false);
    }

    public RegisterUI(SocketHelper stemp)
    {

        //�������������
        outputStream = stemp.getOs();
        inputStream = stemp.getIs();

        //���ÿɼ���
        setVisible(true);


        //���ó�ʼ�û�
        utemp=new User();



        //�������ڴ�С
        this.setResizable(false);


        //��������

        //���ͼƬ
        img = new ImageIcon("./src/LoginView/pic/image4.jpg"); //���ͼƬ
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




        //���ð�ť
        MyButton button1=new MyButton ("ȷ��");
        button1.setBounds(150,360,100,40);
        button1.setBUTTON_COLOR1(new Color(243, 149, 4, 131));
        button1.setBUTTON_COLOR2(new Color(234, 154, 5, 229));
        Font f1 = new Font("�����п�", Font.BOLD, 16);
        button1.setFont(f1);

        //���ñ�ǩ
        JLabel l1=new JLabel("����/һ��ͨ��");
        JLabel l2=new JLabel("����");
        JLabel l3=new JLabel("����");
        JLabel l4=new JLabel("ְҵ");
        JLabel l5=new JLabel("ѧԺ");
        JLabel l6=new JLabel("�Ա�");
        JLabel l7=new JLabel("����");

        //���ñ�ǩ����
        Font f = new Font("�����п�", Font.BOLD, 16);
        l1.setFont(f);
        l2.setFont(f);
        l3.setFont(f);
        l4.setFont(f);
        l5.setFont(f);
        l6.setFont(f);
        l7.setFont(f);

        //���ñ�ǩ��ɫ
        l1.setForeground(new Color(3, 1, 2));
        l2.setForeground(new Color(3, 1, 2));
        l3.setForeground(new Color(3, 1, 2));
        l4.setForeground(new Color(3, 1, 2));
        l5.setForeground(new Color(3, 1, 2));
        l6.setForeground(new Color(3, 1, 2));
        l7.setForeground(new Color(3, 1, 2));

        //���ñ�ǩ��Сλ��
        l1.setBounds(70,50,130,30);
        l2.setBounds(70,90,130,30);
        l3.setBounds(70,130,130,30);
        l4.setBounds(70,170,130,30);
        l5.setBounds(70,210,130,30);
        l6.setBounds(70,250,130,30);
        l7.setBounds(70,290,130,30);


        //�����ı���
        JTextField t1=new  JTextField();
        JTextField t2=new  JTextField();
        JTextField t3=new  JTextField();
        //����ѡ��
        JComboBox<String> t4=new JComboBox<>();
        t4.addItem("��ʦ");// �������б�������ݡ�Item������
        t4.addItem("ѧ��");
        JTextField t5=new  JTextField();
        JTextField t6=new  JTextField();
        JTextField t7=new  JTextField();

        //�����ı���͸��
        t1.setOpaque (false);
        t2.setOpaque (false);
        t3.setOpaque (false);
        t4.setOpaque (false);
        t5.setOpaque (false);
        t6.setOpaque (false);
        t7.setOpaque (false);

        //�����ı�������
        t1.setFont(f1);
        t2.setFont(f1);
        t3.setFont(f1);
        t4.setFont(f1);
        t5.setFont(f1);
        t6.setFont(f1);
        t7.setFont(f1);

        //�����ı�����ɫ
        t1.setForeground(new Color(3, 1, 2));
        t2.setForeground(new Color(3, 1, 2));
        t3.setForeground(new Color(3, 1, 2));
        t4.setForeground(new Color(3, 1, 2));
        t5.setForeground(new Color(3, 1, 2));
        t6.setForeground(new Color(3, 1, 2));
        t7.setForeground(new Color(3, 1, 2));



        t1.setBounds(200,50,120,30);
        t2.setBounds(200,90,120,30);
        t3.setBounds(200,130,120,30);
        t4.setBounds(200,170,120,30);
        t5.setBounds(200,210,120,30);
        t6.setBounds(200,250,120,30);
        t7.setBounds(200,290,120,30);
        //������
        jp.add(t1);
        jp.add(l1);
        jp.add(t2);
        jp.add(l2);
        jp.add(t3);
        jp.add(l3);
        jp.add(t4);
        jp.add(l4);
        jp.add(t5);
        jp.add(l5);
        jp.add(t6);
        jp.add(l6);
        jp.add(t7);
        jp.add(l7);
        jp.add(button1);
        //������ť
        button1.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent evt) {
            //�жϷǿ��߼�
            Boolean r1=!t1.getText().isEmpty()&&!t2.getText().isEmpty()&&!t3.getText().isEmpty();
            Boolean r2=!t5.getText().isEmpty()&&!t6.getText().isEmpty()&&!t7.getText().isEmpty();
            Boolean r3=r1&&r2;
            //����
            if(r3) {

                utemp.setId(t1.getText());
                utemp.setName(t2.getText());
                utemp.setPassword(t3.getText());
                utemp.setOccupation((String) t4.getSelectedItem());
                utemp.setAcademy(t5.getText());
                utemp.setSex(t6.getText());
                utemp.setAge((Integer.valueOf(t7.getText()).intValue()));
                utemp.UserPrint();
                t1.setText("");
                t2.setText("");
                t3.setText("");
                t5.setText("");
                t6.setText("");
                t7.setText("");

                //����Ϊ�ͺ��ͨѶ
                try {
                    //��������
                    outputStream.reset();
                    outputStream.writeInt(800);
                    outputStream.flush();


                    //ʵ������������
                    Operation oper=new Operation();
                    oper.addUser(utemp);
                    System.out.println("808080");
                    oper.getUser(1).UserPrint();
                    oper.setOperationcode(002);
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
                        JOptionPane.showMessageDialog(null, "ע��ɹ�");
                        setVisible(false);
                    }
                    else
                    {
                        JOptionPane.showMessageDialog(null, "ע��ʧ��", "",JOptionPane.WARNING_MESSAGE);
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }


            }
            else
            {
                JOptionPane.showMessageDialog(null, "�����Ƿ����ȫ����Ϣ��д", "",JOptionPane.WARNING_MESSAGE);

            }

        }
    });


        //�رռ���
        addWindowListener(new WindowAdapter() {
            //�رմ���
            @Override
            public void windowClosing(WindowEvent e) {

                setVisible(false);
            }
        });

    }

    //����������
    public void setBak() {
        WIDTH = 400;
        HEIGHT =600;
        ((JPanel) this.getContentPane()).setOpaque(false);
        //ͼƬ��Ӧ��ǩ��С
        img.setImage(img.getImage().getScaledInstance(WIDTH, HEIGHT, Image.SCALE_DEFAULT));
        //����������ǩ
        background = new JLabel(img);
        this.getLayeredPane().add(background, new Integer(Integer.MIN_VALUE));
        background.setBounds(0, 0, WIDTH, HEIGHT);
    }
   // public static void main(String[] args)
   // {
       // new RegisterUI();
   // }



}
