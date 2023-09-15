/*
 * Created by JFormDesigner on Mon Sep 11 17:03:57 CST 2023
 */

package CourseView_M;

import beautyComponent.TransparentTable;
import entity.Course;
import entity.CourseTable;
import module.CourseSystem;
import utils.SocketHelper;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Vector;
import javax.swing.*;
import javax.swing.table.*;

/**
 * @author 13352
 */
public class Modify_adm extends JFrame {
    public SocketHelper socketHelper;

    CourseSystem model;

    Course_M adm;

    Course course;//��¼Ҫ�޸ĵĿγ�

    public Vector<int[][]> CrsArr;

    public CourseTable courseTable;
    public Modify_adm(SocketHelper socketHelper, Course_M adm) {
        initComponents();
        this.model= new CourseSystem(socketHelper);
        this.adm=adm;
        this.courseTable=adm.courseTable;
        this.socketHelper = socketHelper;

        course =adm.course;
        DefaultTableModel tableModel = (DefaultTableModel) table1.getModel();
        tableModel.setRowCount(0);
        Object[] rowData = {course.getCrsId(), course.getCrsName(),course.getCrsTime(), course.getCrsRoom(), course.getCrsDate(), course.getMajor(), course.getTeacherId(), course.getTeacherName(), course.getCrsSize(),course.getCrsCSize()};
        tableModel.addRow(rowData);

        Vector<int[][]> CourseArrangeV=new Vector<int[][]>();
        for(int i=0;i<18;i++){
            int[][] temp=new int[5][5];
            for(int j=0;j<5;j++)
            {
                for(int n=0;n<5;n++)
                {
                    temp[j][n]=0;
                }
            }
            CourseArrangeV.add(temp);
        }

        for(int i=0;i<courseTable.getCourseVector().size();i++) {
            int week = -1;
            int time = -1;
            int address = -1;
            if (courseTable.getCourseVector().get(i).getCrsDate() == "���ſ�" || courseTable.getCourseVector().get(i).getCrsTime() == "���ſ�") {
                continue;
            } else {
                switch (courseTable.getCourseVector().get(i).getCrsDate()) {
                    case "��һ":
                        week = 0;
                        break;
                    case "�ܶ�":
                        week = 1;
                        break;
                    case "����":
                        week = 2;
                        break;
                    case "����":
                        week = 3;
                        break;
                    case "����":
                        week = 4;
                        break;
                    default:
                        continue;
                }
                switch (courseTable.getCourseVector().get(i).getCrsTime()) {
                    case "��1-2��":
                        time = 0;
                        break;
                    case "��3-4��":
                        time = 1;
                        break;
                    case "��5-6��":
                        time = 2;
                        break;
                    case "��7-8��":
                        time = 3;
                        break;
                    case "��9-10��":
                        time = 4;
                        break;
                    default:
                        continue;
                }
                switch (courseTable.getCourseVector().get(i).getCrsRoom()) {
                    case "��һ101":
                        address = 0;
                        break;
                    case "��һ102":
                        address = 1;
                        break;
                    case "��һ103":
                        address = 2;
                        break;
                    case "��һ104":
                        address = 3;
                        break;
                    case "��һ105":
                        address = 4;
                        break;
                    case "��һ106":
                        address = 5;
                        break;
                    case "�̶�201":
                        address = 6;
                        break;
                    case "�̶�202":
                        address = 7;
                        break;
                    case "�̶�203":
                        address = 8;
                        break;
                    case "�̶�204":
                        address = 9;
                        break;
                    case "�̶�205":
                        address = 10;
                        break;
                    case "�̶�206":
                        address = 11;
                        break;
                    case "����301":
                        address = 12;
                        break;
                    case "����302":
                        address = 13;
                        break;
                    case "����303":
                        address = 14;
                        break;
                    case "����304":
                        address = 15;
                        break;
                    case "����305":
                        address = 16;
                        break;
                    case "����306":
                        address = 17;
                        break;
                    default:
                        continue;
                }
                CourseArrangeV.get(address)[week][time] = 1;
            }
        }

        CrsArr=CourseArrangeV;

        // ���������б���ѡ��
        Vector<String> item = new Vector<String>();
        item.add("���ſ�");
        item.add("��1-2��");
        item.add("��3-4��");
        item.add("��5-6��");
        item.add("��7-8��");
        item.add("��9-10��");
        JComboBox JComboBoxItem = new JComboBox(item);

        Vector<String> item1 = new Vector<String>();
        item1.add("���ſ�");
        item1.add("��һ");
        item1.add("�ܶ�");
        item1.add("����");
        item1.add("����");
        item1.add("����");
        JComboBox JComboBoxItem1 = new JComboBox(item1);

        Vector<String> Room = new Vector<String>();
        Room.add("���ſ�");
        Room.add("��һ101");
        Room.add("��һ102");
        Room.add("��һ103");
        Room.add("��һ104");
        Room.add("��һ105");
        Room.add("��һ106");
        Room.add("�̶�201");
        Room.add("�̶�202");
        Room.add("�̶�203");
        Room.add("�̶�204");
        Room.add("�̶�205");
        Room.add("�̶�206");
        Room.add("����301");
        Room.add("����302");
        Room.add("����303");
        Room.add("����304");
        Room.add("����305");
        Room.add("����306");
        JComboBox JComboBoxItem2 = new JComboBox(Room);

        TableColumn brandColumn1 = table1.getColumnModel().getColumn(2);
        brandColumn1.setCellEditor(new DefaultCellEditor(JComboBoxItem));
        TableColumn brandColumn2 = table1.getColumnModel().getColumn(4);
        brandColumn2.setCellEditor(new DefaultCellEditor(JComboBoxItem1));
        TableColumn brandColumn3 = table1.getColumnModel().getColumn(3);
        brandColumn3.setCellEditor(new DefaultCellEditor(JComboBoxItem2));

    }

    private void modifyMouseClicked(MouseEvent e) {
        // TODO add your code here
        // TODO add your code here
        ObjectInputStream is;
        ObjectOutputStream os;

        is = socketHelper.getIs();
        os = socketHelper.getOs();


        int flag = 0;
        for (int i = 0; i < 10; i++) {
            if (table1.getValueAt(0, i) == null) {
                JOptionPane.showMessageDialog(this, "�������޸���Ϣ��");
                return;
            }
        }
        String crsId_s = table1.getValueAt(0, 0).toString();
        Integer crsId = Integer.parseInt(crsId_s);
        String crsName = table1.getValueAt(0, 1).toString();
        String crsTime = table1.getValueAt(0, 2).toString();
        String crsRoom = table1.getValueAt(0, 3).toString();
        String crsDate = table1.getValueAt(0, 4).toString();
        String crsMajor = table1.getValueAt(0, 5).toString();
        String teacherId = table1.getValueAt(0, 6).toString();
        String teacherName = table1.getValueAt(0, 7).toString();
        String crsSize_s = table1.getValueAt(0, 8).toString();
        Integer crsSize = Integer.parseInt(crsSize_s);
        String crsCSize_s = table1.getValueAt(0, 9).toString();
        Integer crsCSize = Integer.parseInt(crsCSize_s);


        Course course = new Course(crsId, crsName, crsTime, crsRoom, crsDate, crsMajor, teacherId, teacherName, crsSize, crsCSize);
        if (course == this.course) {
            JOptionPane.showMessageDialog(this, "�������޸���Ϣ��");
            return;
        }
        this.dispose();

        int flag1=0;
        if(crsDate==this.course.getCrsDate()&&crsDate==this.course.getCrsDate()&&crsRoom==this.course.getCrsRoom())
            flag1=1;

        int week = -1;
        int time = -1;
        int address = -1;
        if (crsDate.equals("���ſ�") || crsTime.equals("���ſ�") || crsRoom.equals("���ſ�")||flag1==1) {
            try {
                os.writeInt(304); // ����ѡ��γ̵�������
                os.flush();
                os.writeObject(course); // ���Ϳγ���Ϣ
                os.flush();

                try {
                    int r = is.readInt(); // ���շ��������صĽ����
                    if (r == 3041) {
                        adm.refreshTable();
                        JOptionPane.showMessageDialog(this, "�޸ĳɹ���");
                    } else {
                        JOptionPane.showMessageDialog(this, "�޸�ʧ��");
                    }
                } catch (Exception ess) {
                    ess.printStackTrace();
                }
            } catch (IOException ess) {
                ess.printStackTrace();
            }
        } else {
            switch (crsDate) {
                case "��һ":
                    week = 0;
                    break;
                case "�ܶ�":
                    week = 1;
                    break;
                case "����":
                    week = 2;
                    break;
                case "����":
                    week = 3;
                    break;
                case "����":
                    week = 4;
                    break;
                default:
                    break;
            }
            switch (crsTime) {
                case "��1-2��":
                    time = 0;
                    break;
                case "��3-4��":
                    time = 1;
                    break;
                case "��5-6��":
                    time = 2;
                    break;
                case "��7-8��":
                    time = 3;
                    break;
                case "��9-10��":
                    time = 4;
                    break;
                default:
                    break;
            }
            switch (crsRoom) {
                case "��һ101":
                    address = 0;
                    break;
                case "��һ102":
                    address = 1;
                    break;
                case "��һ103":
                    address = 2;
                    break;
                case "��һ104":
                    address = 3;
                    break;
                case "��һ105":
                    address = 4;
                    break;
                case "��һ106":
                    address = 5;
                    break;
                case "�̶�201":
                    address = 6;
                    break;
                case "�̶�202":
                    address = 7;
                    break;
                case "�̶�203":
                    address = 8;
                    break;
                case "�̶�204":
                    address = 9;
                    break;
                case "�̶�205":
                    address = 10;
                    break;
                case "�̶�206":
                    address = 11;
                    break;
                case "����301":
                    address = 12;
                    break;
                case "����302":
                    address = 13;
                    break;
                case "����303":
                    address = 14;
                    break;
                case "����304":
                    address = 15;
                    break;
                case "����305":
                    address = 16;
                    break;
                case "����306":
                    address = 17;
                    break;
                default:
                    break;
            }

            if (address == -1) {
                JOptionPane.showMessageDialog(this, "�ڿν�����Ч");
                return;
            }
            if (CrsArr.get(address)[week][time] == 0) {
                try {
                    os.writeInt(304); // ����ѡ��γ̵�������
                    os.flush();
                    os.writeObject(course); // ���Ϳγ���Ϣ
                    os.flush();

                    try {
                        int r = is.readInt(); // ���շ��������صĽ����
                        if (r == 3041) {
                            adm.refreshTable();
                            JOptionPane.showMessageDialog(this, "�޸ĳɹ���");
                        } else {
                            JOptionPane.showMessageDialog(this, "�޸�ʧ��");
                        }
                    } catch (Exception ess) {
                        ess.printStackTrace();
                    }
                } catch (IOException ess) {
                    ess.printStackTrace();
                }
            }
            else {
                JOptionPane.showMessageDialog(this, "�γ̰��ų�ͻ��");
            }
        }



    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        this2 = new JFrame();
        scrollPane1 = new JScrollPane();
        table1 = new JTable();
        button1 = new JButton();
        label1 = new JLabel();

        //======== this2 ========
        {
            this2.setVisible(true);
            Container this2ContentPane = this2.getContentPane();
            this2ContentPane.setLayout(null);

            //======== scrollPane1 ========
            {

                //---- table1 ----
                table1.setModel(new DefaultTableModel(
                    new Object[][] {
                        {null, null, null, null, null, null, null, null, null, null},
                    },
                    new String[] {
                        "\u8bfe\u7a0b\u7f16\u53f7", "\u8bfe\u7a0b\u540d\u79f0", "\u8bfe\u7a0b\u65f6\u95f4", "\u6559\u5ba4", "\u8bfe\u7a0b\u65e5\u671f", "\u5f00\u8bfe\u5b66\u9662", "\u8001\u5e08\u7f16\u53f7", "\u8001\u5e08\u540d\u79f0", "\u8bfe\u7a0b\u5bb9\u91cf", "\u8bfe\u7a0b\u5269\u4f59\u5bb9\u91cf"
                    }
                ));
                table1.setForeground(new Color(0x2b2d30));
                table1.setFont(new Font("\u534e\u6587\u4eff\u5b8b", Font.BOLD, 14));
                scrollPane1.setViewportView(table1);
            }
            this2ContentPane.add(scrollPane1);
            scrollPane1.setBounds(20, 5, 860, 75);

            //---- button1 ----
            button1.setText("\u4fee\u6539");
            button1.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    modifyMouseClicked(e);
                }
            });
            this2ContentPane.add(button1);
            button1.setBounds(905, 25, 105, 40);

            //---- label1 ----
            label1.setIcon(new ImageIcon(getClass().getResource("/pic/\u6811.jpg")));
            label1.setForeground(new Color(0x2b2d30));
            label1.setFont(new Font("\u534e\u6587\u4eff\u5b8b", Font.BOLD, 13));
            this2ContentPane.add(label1);
            label1.setBounds(new Rectangle(new Point(0, 0), label1.getPreferredSize()));

            {
                // compute preferred size
                Dimension preferredSize = new Dimension();
                for(int i = 0; i < this2ContentPane.getComponentCount(); i++) {
                    Rectangle bounds = this2ContentPane.getComponent(i).getBounds();
                    preferredSize.width = Math.max(bounds.x + bounds.width, preferredSize.width);
                    preferredSize.height = Math.max(bounds.y + bounds.height, preferredSize.height);
                }
                Insets insets = this2ContentPane.getInsets();
                preferredSize.width += insets.right;
                preferredSize.height += insets.bottom;
                this2ContentPane.setMinimumSize(preferredSize);
                this2ContentPane.setPreferredSize(preferredSize);
            }
            this2.setSize(1060, 120);
            this2.setLocationRelativeTo(this2.getOwner());
        }
        // JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
    private JFrame this2;
    private JScrollPane scrollPane1;
    private JTable table1;
    private JButton button1;
    private JLabel label1;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on
}
