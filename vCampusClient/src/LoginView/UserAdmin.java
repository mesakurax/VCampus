package LoginView;

import entity.User;
import entity.Operation;
import utils.SocketHelper;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Vector;
import  utils.UIStyler;

public class UserAdmin extends JPanel {

    public static final int CMD = 1200;

    //���������
    private static ObjectOutputStream outputStream;
    private static ObjectInputStream inputStream;

    //����ͼƬ
    private Image image = null;

    //�ı���
    private MyTextField t;


    //������
    private Vector<Vector<String>> vData;// ����������������Ϊ�б�ֹһ�У������������������������ӷ���add(row)
    private Vector<String> vName;// ���������⣩������ʹ������add()�����������
    private JTable jTable1;//���
    JTableHeader tab_header;//��ͷ

    private JScrollPane scroll;//�������������Χ
    private DefaultTableModel model; //�½�һ��Ĭ������ģ��


    //��ܴ�С
    private int WIDTH;
    private int HEIGHT;

    //���������
    public HashMap<Integer, User> users;


    //��ͼ��д
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        ImageIcon img = new ImageIcon("./src/LoginView/pic/image0.jpg");
        //System.out.println("kkkkk: "+img.getIconWidth()+" ooooo: "+img.getIconHeight());
        //img.paintIcon(this, g, 0, 0);
        g.drawImage(img.getImage(), 0, 0, 1685, 1030, img.getImageObserver());
    }

    //��ť��ɫ
    public void setBtnTran(JButton button1){

        button1.setForeground(Color.BLACK);
        button1.setMargin(new Insets(0, 0, 0, 0));//���߿�����������ҿռ�����Ϊ0
        //ȥ�߿�
        button1.setBorderPainted(false);
        button1.setFocusPainted(false);
        button1.setContentAreaFilled(false);//��ȥĬ�ϵı������
        button1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                button1.setOpaque(true);
                button1.setBackground(Color.white);
                button1.setFocusPainted(false);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                button1.setOpaque(false);
                button1.setFocusPainted(false);
                button1.setBackground(Color.BLACK);
            }
        });
    }


    public UserAdmin(SocketHelper stemp) {

        //�������������
        outputStream = stemp.getOs();
        inputStream = stemp.getIs();

        //����users
        users = new HashMap<Integer, User>();


        //��ܳ���
        WIDTH = 1685;
        HEIGHT = 1030;

        //����ȱʡ
        setLayout(null);
        //���ô�С
        setSize(WIDTH, HEIGHT);
        //����ȱʡ
        setLayout(null);


        JLabel chessboard2;
        add(chessboard2=new JLabel(new ImageIcon("./src/LoginView/pic/image7.png")));
        chessboard2.setBounds(0,27,273,88);


        Font f3 = new Font("����", Font.BOLD, 60);
        JLabel lab = new JLabel("���ϴ�ѧ�û�����");
        lab.setFont(f3);
        lab.setForeground(Color.WHITE);
        lab.setBounds(1035,15,600,135);
        add(lab);

        JLabel lb=new JLabel();
        lb.setBackground(new Color(36,50,30));
        lb.setOpaque(true);
        lb.setBounds(0,0,1685,150);

        JLabel lb1=new JLabel();
        lb1.setBackground(new Color(64,80,50));
        lb1.setOpaque(true);
        lb1.setBounds(0,150,1685,50);





        //�½���ť
        JButton button1 = new JButton("��ѯȫ��");
        JButton button2 = new JButton("�û���ѯ");
        JButton button3 = new JButton("����û�");
        JButton button4=new JButton("��ѯ��ʦ");
        JButton button5 = new JButton("��ѯѧ��");
        JButton button6 = new JButton("ɾ����ѡ");

        //��������
        Font f = new Font("����", Font.BOLD, 20);
        Font f1 = new Font("����", Font.BOLD, 26);

        //�½��ı���
        t = new MyTextField(20);
        t.setOpaque(false);
        t.setFont(f);
        t.setText("�����빤��/һ��ͨ��");
        t.setForeground(new Color(1,2,3));
        t.setVisible(true);


        //�ı������

        t.addFocusListener(new FocusListener() {

            public void focusGained(FocusEvent e) {
                //�õ�����ʱ����ǰ�ı������ʾ���ֺʹ����ö���ʱ����ʾ����һ����˵���û���Ҫ��������
                if (t.getText().equals("�����빤��/һ��ͨ��")) {
                    t.setText("");     //����ʾ�������
                    t.setForeground(Color.black);  //�����û������������ɫΪ��ɫ
                }


            }

            @Override
            public void focusLost(FocusEvent e) {
                //ʧȥ����ʱ���û���δ���ı����������κ����ݣ�����������ʾ��ʾ����
                if (t.getText().equals("")) {
                    t.setForeground(new Color(3, 1, 2));
                    ; //����ʾ��������Ϊ��ɫ
                    t.setText("�����빤��/һ��ͨ��");     //��ʾ��ʾ����
                }
            }
        });


        //��ť��������
        button1.setFont(f1);
        button2.setFont(f1);
        button3.setFont(f1);
        button4.setFont(f1);
        button5.setFont(f1);
        button6.setFont(f1);




        //Ϊ��ť��������
        button1.setActionCommand("��ѯȫ��");
        button2.setActionCommand("�û���ѯ");
        button3.setActionCommand("����û�");
        button4.setActionCommand("��ѯ��ʦ");
        button5.setActionCommand("��ѯѧ��");
        button6.setActionCommand("ɾ����ѡ");


        //��ť��ɫ����
        setBtnTran(button1);
        setBtnTran(button2);
        setBtnTran(button3);
        setBtnTran(button4);
        setBtnTran(button5);
        setBtnTran(button6);

        //��ťλ�ô�С����;
        int x1=0;
        button1.setBounds(x1, 150, 150, 50);
        button2.setBounds(x1+960, 150, 150, 50);
        //button2.setBounds(1500, 150, 150, 50);
        button3.setBounds(x1+150, 150, 150, 50);
        button4.setBounds(x1+300,150,150,50);
        button5.setBounds(x1+450, 150, 150, 50);
        button6.setBounds(x1+600, 150, 150, 50);

        //�ı������ô�Сλ��
        t.setBounds(x1+750, 161, 200, 28);


        //���ü���
        MyListener lis = new MyListener();
        button1.addActionListener(lis);
        button2.addActionListener(lis);
        button3.addActionListener(lis);
        button4.addActionListener(lis);
        button5.addActionListener(lis);
        button6.addActionListener(lis);

        //��Ӱ�ť
        add(button1);
        add(button2);
        add(button3);
        add(button4);
        add(button5);
        add(button6);



        //����ı���
        add(t);

        //��ӱ�ǩ
        add(lb);
        add(lb1);

        //����������
        iniTable();
        //���ñ��
        setTable();


        //������
        jTable1.getModel().addTableModelListener(new TableModelListener() {

            public void tableChanged(TableModelEvent e) {
                if (e.getType() == TableModelEvent.UPDATE) {

                    int row = jTable1.getEditingRow();
                    int col = jTable1.getEditingColumn();

                    if (col != 7) {
                        //System.out.println("���Ķ���"+vData.get(row).get(col) );
                        User utemp = new User();
                        utemp.setId(vData.get(row).get(0));
                        utemp.setName(vData.get(row).get(1));
                        utemp.setPassword(vData.get(row).get(2));
                        utemp.setSex(vData.get(row).get(3));
                        utemp.setAge(Integer.parseInt(vData.get(row).get(4)));
                        utemp.setOccupation(vData.get(row).get(5));
                        utemp.setAcademy(vData.get(row).get(6));
                        //utemp.UserPrint();


                        //����ǰ��ղ�������
                        clearUsers();
                        //�洢����
                        addUser(utemp);

                        //����Ϊ�����ϵ

                        try {
                            //��������
                            outputStream.reset();
                            outputStream.writeInt(CMD);
                            outputStream.flush();

                            //ʵ������������
                            Operation oper = new Operation();
                            //���ò�����
                            oper.setOperationcode(006);
                            //�洢��ѯ�û�ID
                            oper.addUser(users.get(1));
                            // �����˷��Ͷ���
                            outputStream.reset();
                            outputStream.writeObject(oper);
                            outputStream.flush();
                            System.out.println("�����ͳɹ�");

                            // ��ȡ����˷��͵���Ӧ����
                            oper.copy((Operation) inputStream.readObject());
                            System.out.println("�����ȡ�ɹ�");
                            System.out.println("�������Ӧ��oper����");
                            if (oper.getSuccess()) {

                                System.out.println("���³ɹ�");
                                JOptionPane.showMessageDialog(null, "�û��޸ĳɹ�");


                            } else {
                                System.out.println("����ʧ��");
                                JOptionPane.showMessageDialog(null, "�û��޸�ʧ��", "", JOptionPane.WARNING_MESSAGE);

                            }
                        } catch (IOException ex) {
                            throw new RuntimeException(ex);
                        } catch (ClassNotFoundException ex) {
                            throw new RuntimeException(ex);
                        }


                    }


                }

            }

        });


    }

    //����������
    public void iniTable() {
        vData = new Vector<Vector<String>>(); // ����������������Ϊ�б�ֹһ�У������������������������ӷ���add(row)
        vName = new Vector<String>(); // ���������⣩������ʹ������add()�����������
        jTable1 = new JTable();
        jTable1.setRowHeight(50);

        //�޸ĸ�дȨ��
        model = new DefaultTableModel(vData, vName) {
            @Override
            public boolean isCellEditable(int row, int column) {
                if (column == 0) {
                    return false;
                } else {
                    return true;
                }
            }
        };
        ; //�½�һ��Ĭ������ģ��
        scroll = new JScrollPane();//�½��������
        //������ͷ
        vName.add(0, "����/һ��ͨ��");
        vName.add(1, "����");
        vName.add(2, "����");
        vName.add(3, "�Ա�");
        vName.add(4, "����");
        vName.add(5, "ְҵ");
        vName.add(6, "ѧԺ");
        vName.add(7, "ɾ����");

        //�������λ������
        scroll.setBounds(150, 210, 1500, 800);
        //��ͷ����
        tab_header = jTable1.getTableHeader();                    //��ȡ��ͷ
        tab_header.setFont(new Font("���ķ���", Font.BOLD, 20));
        //tab_header.setOpaque(false);
        tab_header.setForeground(new Color(255, 255, 255));
        tab_header.setBackground(new Color(1,2,3, 0));
        tab_header.setReorderingAllowed(false);
        tab_header.setPreferredSize(new Dimension(tab_header.getWidth(), 30));    //�޸ı�ͷ�ĸ߶�
        jTable1.setFont(new Font("�����п�", Font.PLAIN, 20));
        model.setDataVector(vData, vName);//�½�һ������ģ��
        System.out.println("bbbbbbbbb");

        TransparentHeaderRenderer hr = new TransparentHeaderRenderer(); //�����Զ������Ⱦ��
        hr.setOpaque(false);
        hr.setHorizontalAlignment(JLabel.CENTER);
        jTable1.setDefaultRenderer(Object.class, hr);
        jTable1.setModel(model);

        //������ģ��
        TableColumnModel cm = jTable1.getColumnModel();
        //�õ���i���ж���
        TableColumn column = cm.getColumn(7);
        column.setPreferredWidth(31);

        jTable1.setOpaque(false);
        // ���� TableCellRenderer Ϊ����Ĭ����Ⱦ��
        DefaultTableCellRenderer cellRenderer = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component component = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                component.setFont(new Font("���ķ���", Font.BOLD, 24));
                // �Զ�����ɫ����
                if (isSelected) {
                    // ��ǰ�б�ѡ��ʱ����ɫ
                    component.setBackground(Color.WHITE);
                    component.setForeground(Color.BLACK);
                } else {
                    // �����е���ɫ
                    component.setBackground(new Color(0, 0, 0, 0)); // ���ñ���͸��
                    component.setForeground(Color.WHITE);
                }

                return component;
            }
        };
        jTable1.setDefaultRenderer(Object.class, cellRenderer);

        // ���ѡ�������������ѡ���е���ɫ
        //jTable1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); // ����ֻ��ѡ����
        jTable1.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) { // ȷ��ֻ��ѡ�����ʱ����
                    int selectedRow = jTable1.getSelectedRow();
                    jTable1.repaint(); // ˢ�±���Ը���ѡ���е���ɫ
                }
            }
        });

        scroll.getViewport().setOpaque(false);
        scroll.setViewportView(jTable1);
        jTable1.getColumnModel().getColumn(7).setCellRenderer(new TableCellRendererButton());
        jTable1.getColumnModel().getColumn(7).setCellEditor(new TableCellEditorButton());
        System.out.println("nnnnnn");
        add(scroll);

        UIStyler.setTransparentTable(scroll);

        jTable1.getSelectionModel().setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
    }




    //���ñ��
    public void setTable() {
        vData.clear();
        for (int times = 1; times <= users.size(); times++) {


            Vector vRow = new Vector();
            vRow.add(0, users.get(times).getId());
            vRow.add(1, users.get(times).getName());
            vRow.add(2, users.get(times).getPassword());
            vRow.add(3, users.get(times).getSex());
            vRow.add(4, Integer.toString(users.get(times).getAge()));
            vRow.add(5, users.get(times).getOccupation());
            vRow.add(6, users.get(times).getAcademy());
            vRow.add(7, "");
            vData.add(vRow);


        }

        //jTable1.getColumnModel().getColumn(7).setCellRenderer(new TableCellRendererButton());
        //jTable1.getColumnModel().getColumn(7).setCellEditor(new TableCellEditorButton());
        jTable1.setOpaque(false);
        scroll.setOpaque(false);
        scroll.getViewport().setOpaque(false);
        scroll.setViewportView(jTable1);
        System.out.println("..............");


    }
    //��ӱ��������
    public void addUser(User utemp) {
        users.put(users.size() + 1, utemp);
    }

    //���Users
    public void clearUsers() {
        users.clear();
    }

    public static void main(String[] args) throws IOException {

        SocketHelper socketHelper111 = new SocketHelper();
        socketHelper111.getConnection("localhost", 8081);

        socketHelper111.getOs().reset();
        socketHelper111.getOs().writeInt(1);
        socketHelper111.getOs().flush();
        System.out.println("ppppppp");


        UserAdmin ui = new UserAdmin(socketHelper111);

        JFrame j0000 = new JFrame();
        j0000.setVisible(true);
        j0000.add(ui);
        j0000.setLayout(null);
        j0000.setBounds(0, 0, 1685, 1030);
        ui.setLocation(0,0);


    }

    //�Զ��������
    class MyListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

            if (e.getActionCommand().equals("��ѯȫ��")) {

                try {
                    //��������
                    outputStream.reset();
                    outputStream.writeInt(CMD);
                    outputStream.flush();

                    //����������
                    Operation oper = new Operation();
                    System.out.println("808080");
                    oper.setOperationcode(003);
                    // �����˷��Ͷ���
                    outputStream.reset();
                    outputStream.writeObject(oper);
                    outputStream.flush();
                    System.out.println("�����ͳɹ�");

                    // ��ȡ����˷��͵���Ӧ����
                    oper.copy((Operation) inputStream.readObject());
                    System.out.println("�����ȡ�ɹ�");
                    // System.out.println("�������Ӧ��oper����");
                    if (oper.getSuccess()) {

                        System.out.println("ȫ����ѯ�ɹ�");

                        //�������
                        clearUsers();

                        //��ui��������
                        for (int i = 1; i <= oper.getUsers().size(); i++) {
                            addUser(oper.getUser(i));
                        }
                        //���Ʊ��
                        setTable();
                    } else {
                        System.out.println("ȫ����ѯʧ��");
                        JOptionPane.showMessageDialog(null, "ȫ����ѯʧ��", "", JOptionPane.WARNING_MESSAGE);

                    }
                } catch (IOException | ClassNotFoundException ex) {
                    throw new RuntimeException(ex);
                }


            }
            else if (e.getActionCommand().equals("����û�")) {

                AddUI ad=new AddUI();
            }
            else if (e.getActionCommand().equals("�û���ѯ")) {
                if (!t.getText().isEmpty() && !t.getText().equals("�����빤��/һ��ͨ��")) {
                    User u = new User();
                    u.setId(t.getText());
                    //�������
                    clearUsers();
                    //�������
                    addUser(u);
                    //����ı�
                    t.setForeground(new Color(3, 1, 2, 255)); //����ʾ��������Ϊ��ɫ
                    t.setText("�����빤��/һ��ͨ��");     //��ʾ��ʾ����

                    //����Ϊ����ͨ��
                    //��������
                    try {
                        //��������
                        outputStream.reset();
                        outputStream.writeInt(CMD);
                        outputStream.flush();

                        //ʵ������������
                        Operation oper = new Operation();
                        //���ò�����
                        oper.setOperationcode(004);
                        System.out.println("��ǰ��ѯ�û�ID: " + users.get(1).getId());
                        //�洢��ѯ�û�ID
                        oper.addUser(users.get(1));
                        // �����˷��Ͷ���
                        outputStream.reset();
                        outputStream.writeObject(oper);
                        outputStream.flush();
                        System.out.println("�����ͳɹ�");

                        // ��ȡ����˷��͵���Ӧ����
                        oper.operClear();
                        oper.copy((Operation) inputStream.readObject());
                        System.out.println("�����ȡ�ɹ�");

                        if (oper.getSuccess()) {
                            System.out.println("��ѯ�ɹ�");
                            //�������
                            clearUsers();
                            //��ui��������
                            oper.getUser(1).UserPrint();
                            addUser(oper.getUser(1));

                            //��ȡ�ɹ����Ʊ��
                            setTable();


                        } else {
                            System.out.println("��ѯʧ��");
                            JOptionPane.showMessageDialog(null, "δ��ѯ�����û�", "", JOptionPane.WARNING_MESSAGE);

                        }

                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    } catch (ClassNotFoundException ex) {
                        throw new RuntimeException(ex);
                    }


                }
            }
            else if (e.getActionCommand().equals("��ѯ��ʦ")) {


                try {
                    //��������
                    outputStream.reset();
                    outputStream.writeInt(CMD);
                    outputStream.flush();


                    //ʵ������������
                    Operation oper = new Operation();
                    //���ò�����
                    oper.setOperationcode(007);
                    //�½��û�
                    User u=new User();
                    u.setOccupation("��ʦ");
                    //�������洢�û�
                    oper.addUser(u);

                    // �����˷��Ͷ���
                    outputStream.reset();
                    outputStream.writeObject(oper);
                    outputStream.flush();
                    System.out.println("�����ͳɹ�");

                    // ��ȡ����˷��͵���Ӧ����
                    oper.operClear();
                    oper.copy((Operation) inputStream.readObject());
                    System.out.println("�����ȡ�ɹ�");

                    if(oper.getSuccess())
                    {
                        //�������
                        clearUsers();

                        //�������
                        for(int i=1;i<=oper.getUsers().size();i++)
                        {
                            addUser(oper.getUser(i));
                        }

                        //���Ʊ��
                        setTable();
                    }
                    else
                    {
                        //��ѯʧ��
                        JOptionPane.showMessageDialog(null, "δ��ѯ����ְҵ���û�", "", JOptionPane.WARNING_MESSAGE);

                    }

                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                } catch (ClassNotFoundException ex) {
                    throw new RuntimeException(ex);
                }

            }
            else if (e.getActionCommand().equals("��ѯѧ��")) {


                try {
                    //��������
                    outputStream.reset();
                    outputStream.writeInt(CMD);
                    outputStream.flush();


                    //ʵ������������
                    Operation oper = new Operation();
                    //���ò�����
                    oper.setOperationcode(007);
                    //�½��û�
                    User u=new User();
                    u.setOccupation("ѧ��");
                    //�������洢�û�
                    oper.addUser(u);

                    // �����˷��Ͷ���
                    outputStream.reset();
                    outputStream.writeObject(oper);
                    outputStream.flush();
                    System.out.println("�����ͳɹ�");

                    // ��ȡ����˷��͵���Ӧ����
                    oper.operClear();
                    oper.copy((Operation) inputStream.readObject());
                    System.out.println("�����ȡ�ɹ�");

                    if(oper.getSuccess())
                    {
                        //�������
                        clearUsers();

                        //�������
                        for(int i=1;i<=oper.getUsers().size();i++)
                        {
                            addUser(oper.getUser(i));
                        }

                        //���Ʊ��
                        setTable();
                    }
                    else
                    {
                        //��ѯʧ��
                        JOptionPane.showMessageDialog(null, "δ��ѯ����ְҵ���û�", "", JOptionPane.WARNING_MESSAGE);

                    }

                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                } catch (ClassNotFoundException ex) {
                    throw new RuntimeException(ex);
                }

            }
            else if (e.getActionCommand().equals("ɾ����ѡ"))
            {
                //�½�����洢������
                int[] operrows;
                System.out.println("ɾ����ѡ��");
                operrows=jTable1.getSelectedRows();
                //�ж��û��Ƿ������ѡ���ٽ��к�������
                if(operrows.length!=0) {
                    //��������
                    clearUsers();

                    for (int i = 0; i < operrows.length; i++) {
                        User u = new User();
                        //�������
                        u.setId(vData.get(operrows[i]).get(0));
                        addUser(u);
                        System.out.println("��ť�¼�����----: " + vData.get(operrows[i]).get(0));
                    }




                    //����Ϊ�ͺ����ϵ


                try {
                    //��������
                    outputStream.reset();
                    outputStream.writeInt(CMD);
                    outputStream.flush();

                    //ʵ������������
                    Operation oper = new Operation();
                    //���ò�����
                    oper.setOperationcode(005);
                    //�洢��ѯ�û�ID
                    oper.setUsers(users);
                    // �����˷��Ͷ���
                    outputStream.reset();
                    outputStream.writeObject(oper);
                    outputStream.flush();
                    System.out.println("�����ͳɹ�");

                    // ��ȡ����˷��͵���Ӧ����
                    oper.copy((Operation) inputStream.readObject());
                    System.out.println("�����ȡ�ɹ�");

                    if (oper.getSuccess()) {

                        System.out.println("ɾ���ɹ�");
                        JOptionPane.showMessageDialog(null, "�û�ɾ���ɹ�");

                    } else {
                        System.out.println("ɾ��ʧ��");
                        JOptionPane.showMessageDialog(null, "�û�ɾ��ʧ��", "", JOptionPane.WARNING_MESSAGE);
                    }
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                } catch (ClassNotFoundException ex) {
                    throw new RuntimeException(ex);
                }


            }


                }
            }



        }

    //�Զ�����Ⱦ
    class TableCellRendererButton implements TableCellRenderer {


        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            //��������
            Font f = new Font("���ķ���", Font.BOLD, 24);
            MyButton button = new MyButton("ɾ��");
            button.setBUTTON_COLOR2(new Color(1,2,3, 47));
            button.setBUTTON_COLOR1(new Color(2,3,4, 116));
            button.setFont(f);
            button.setForeground(Color.WHITE);
            return button;
        }

    }

    //�Զ���͸�������Ⱦ
    class TransparentHeaderRenderer extends DefaultTableCellRenderer {
        public TransparentHeaderRenderer() {
            setOpaque(false);
        }
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            setBackground(new Color(0, 0, 0, 0)); //���ñ���ɫΪ͸��
            return this;
        }
    }

    //�Զ���༭
    class TableCellEditorButton extends DefaultCellEditor {

        private MyButton btn;
        private int r;

        public TableCellEditorButton() {
            super(new JTextField());
            //���õ��һ�ξͼ������Ĭ�Ϻ����ǵ��2�μ��
            this.setClickCountToStart(1);
            btn = new MyButton("ɾ��");
            //��������
            Font f1 = new Font("���ķ���", Font.BOLD, 24);
            btn= new MyButton("ɾ��");
            btn.setFont(f1);
            btn.setBUTTON_COLOR2(new Color(1,2,3, 47));
            btn.setBUTTON_COLOR1(new Color(2,3,4, 116));
            btn.setForeground(Color.WHITE);
            btn.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    System.out.println("ɾ���û�");
                    System.out.println("��ť�¼�����----: " + vData.get(r).get(0));
                    User u = new User();
                    u.setId(vData.get(r).get(0));
                    //��������
                    clearUsers();
                    //�������
                    addUser(u);

                    //����Ϊ�ͺ����ϵ

                    try {
                        //��������
                        outputStream.reset();
                        outputStream.writeInt(CMD);
                        outputStream.flush();

                        //ʵ������������
                        Operation oper = new Operation();
                        //���ò�����
                        oper.setOperationcode(005);
                        //�洢��ѯ�û�ID
                        oper.addUser(users.get(1));
                        // �����˷��Ͷ���
                        outputStream.reset();
                        outputStream.writeObject(oper);
                        outputStream.flush();
                        System.out.println("�����ͳɹ�");

                        // ��ȡ����˷��͵���Ӧ����
                        oper.copy((Operation) inputStream.readObject());
                        System.out.println("�����ȡ�ɹ�");

                        if (oper.getSuccess()) {

                            System.out.println("ɾ���ɹ�");
                            JOptionPane.showMessageDialog(null, "�û�ɾ���ɹ�");

                        } else {
                            System.out.println("ɾ��ʧ��");
                            JOptionPane.showMessageDialog(null, "�û�ɾ��ʧ��", "", JOptionPane.WARNING_MESSAGE);
                        }
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    } catch (ClassNotFoundException ex) {
                        throw new RuntimeException(ex);
                    }


                }
            });

        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {

            r = row;
            return btn;
        }


    }

    //�ض������
    public class AddUI  extends JFrame {
        //�û���
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

        public AddUI()
        {

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
            t4.addItem("����Ա" +
                    "");
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


                        //�����Ǻͺ�˵�ͨѶ
                        try {
                            //��������
                            outputStream.reset();
                            outputStream.writeInt(CMD);
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
                            if (oper.getSuccess()) {

                                JOptionPane.showMessageDialog(null, "�û���ӳɹ�");

                                close();

                            } else {
                                JOptionPane.showMessageDialog(null, "�û����ʧ��", "",JOptionPane.WARNING_MESSAGE);

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




    }
    //�����Ⱦ
    DefaultTableCellRenderer cellRenderer = new DefaultTableCellRenderer() {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            Component component = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            component.setFont(new Font("���ķ���", Font.BOLD, 24));
            // �Զ�����ɫ����
            if (isSelected) {
                // ��ǰ�б�ѡ��ʱ����ɫ
                component.setBackground(Color.WHITE);
                component.setForeground(Color.BLACK);
            } else {
                // �����е���ɫ
                component.setBackground(new Color(0, 0, 0, 0)); // ���ñ���͸��
                component.setForeground(Color.WHITE);
            }

            return component;
        }
    };


}





