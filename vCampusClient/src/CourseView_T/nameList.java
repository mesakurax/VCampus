/*
 * Created by JFormDesigner on Wed Sep 06 16:09:41 CST 2023
 */

package CourseView_T;

import utils.UIStyler;
import entity.Course;
import entity.CrsPickRecord;
import entity.RecordTable;
import entity.User;
import module.CourseSystem;
import org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper;
import utils.SocketHelper;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * @author 13352
 */
public class nameList extends JPanel {
    CourseSystem model;
    User userInfo;

    CrsPickRecord crsPickRecord;

    Course course;

    public SocketHelper socketHelper;

    RecordTable rs;//��¼��ѯ���

    RecordTable recordTable;//��¼�������
    int num=0;//��¼��ѯbutton״��

    Double[] scoreArray = {0.0, 0.0, 0.0};

    Double[] valueArray= {0.0, 0.0,0.0};

    public void beautify(){
        try {
            BeautyEyeLNFHelper.frameBorderStyle = BeautyEyeLNFHelper.FrameBorderStyle.generalNoTranslucencyShadow;
            BeautyEyeLNFHelper.launchBeautyEyeLNF();
            UIManager.put("RootPane.setupButtonVisible",false);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public nameList(User userInfo, SocketHelper socketHelper) {
        //beautify();
        initComponents();

        new UIStyler().setTextField(textField2);
        new UIStyler().setCombobox(comboBox2);
        new UIStyler().setBelowButton(button7);//�����·���ť��ʽ
        button7.setSize(200,50);
        new UIStyler().setBelowButton(button6);//�����·���ť��ʽ
        UIStyler.setTransparentTable(scrollPane2);


        this.socketHelper = socketHelper;
        this.userInfo = userInfo;
        this.course=null;
        model=new CourseSystem(this.socketHelper);
        DefaultTableModel tableModel = (DefaultTableModel) table2.getModel();
        tableModel.addTableModelListener(tableModelListener);//��ӱ��ı����
        refreshTable();
    }

    //ĳ��ʦ��ĳ�γ̵�ĳѧ��
    private void PickQueryMouseClicked(MouseEvent e) {
        // TODO add your code here
        JButton button = (JButton) e.getSource();
        if (button.getText().equals("��ѯ")) {
            // ִ�в�ѯ���߼�
            String flag = comboBox2.getSelectedItem().toString();
            System.out.println(flag);
            String text = textField2.getText();
            try {
                if(textField2.getText().isEmpty())
                {
                    JOptionPane.showMessageDialog(null, "�������������", "����ʧ��", JOptionPane.ERROR_MESSAGE);
                }
                else {
                    if (flag.equals("��ѧ����Ų�ѯ")) {
                        CrsPickRecord record=new CrsPickRecord(-1,course.getCrsId(),"",text, "", userInfo.getId(), "",0,0,0,0);
                        System.out.println("��ʼ����");
                        rs = model.CprSearch(record, 9);
                        if (rs==null) {
                            textField2.setText("");//�����
                            table2.clearSelection();
                            JOptionPane.showMessageDialog(null, "δ�ҵ�ƥ���ѧ��", "����ʧ��", JOptionPane.ERROR_MESSAGE);
                        } else {
                            DefaultTableModel tableModel = (DefaultTableModel) table2.getModel();//��ȡ�������������ģ�Ͷ���ʹ�� DefaultTableModel ��ķ����������͹���������ݡ�
                            tableModel.setRowCount(0); // ��ձ������
                            for(int i=0;i<rs.getCount();i++){
                                CrsPickRecord temp=rs.getIndex(i);
                                Object[] rowData = {temp.getCrsId(),temp.getCrsName(),temp.getStuId(),temp.getStuName(),temp.getCrsPickScore()};
                                tableModel.addRow(rowData); // ���������
                            }
                            button.setText("����");
                            num=1;
                        }
                    }
                    if (flag.equals("��ѧ��������ѯ")) {
                        CrsPickRecord record=new CrsPickRecord(-1,course.getCrsId(),text, userInfo.getId(), "", userInfo.getId(), "",0,0,0,0);
                        System.out.println("��ʼ����");
                        rs = model.CprSearch(record, 10);
                        if (rs==null) {
                            textField2.setText("");//�����
                            table2.clearSelection();
                            JOptionPane.showMessageDialog(null, "δ�ҵ�ƥ���ѧ��", "����ʧ��", JOptionPane.ERROR_MESSAGE);
                        }else {
                            DefaultTableModel tableModel = (DefaultTableModel) table2.getModel();//��ȡ�������������ģ�Ͷ���ʹ�� DefaultTableModel ��ķ����������͹���������ݡ�
                            tableModel.setRowCount(0); // ��ձ������
                            for(int i=0;i<rs.getCount();i++){
                                CrsPickRecord temp=rs.getIndex(i);
                                Object[] rowData = {temp.getCrsId(),temp.getCrsName(),temp.getStuId(),temp.getStuName(),temp.getCrsPickScore()};
                                tableModel.addRow(rowData); // ���������
                            }
                            button.setText("����");
                            num=1;
                        }
                    }
                }

            }catch(HeadlessException ex){
                throw new RuntimeException(ex);
            }

        } else if (button.getText().equals("����")) {
            // ִ�з��ص��߼�
            refreshTable();
            button.setText("��ѯ");
            num=0;
        }

    }

    public void setCourseTData(Course course) {
        this.course = course;
        if(course!=null) {
            refreshTable(); // ���ݽ��պ����ˢ�·������ɽ���
        }
        else {
            DefaultTableModel tableModel = (DefaultTableModel) table2.getModel();//��ȡ�������������ģ�Ͷ���ʹ�� DefaultTableModel ��ķ����������͹���������ݡ�
            tableModel.setRowCount(0); // ��ձ������
        }
    }
    public void refreshTable() {//��ӡ��ĳ��ʦ��ĳ�ε�ѧ������
        button6.setText("��ѯ");
        textField2.setText("");//�����
        // ��ȡ���ģ��
        DefaultTableModel tableModel = (DefaultTableModel) table2.getModel();//��ȡ�������������ģ�Ͷ���ʹ�� DefaultTableModel ��ķ����������͹���������ݡ�
        tableModel.setRowCount(0); // ��ձ������
        if(course!=null) {
            // �� model �������¼
            CrsPickRecord temp1 = new CrsPickRecord(-1, course.getCrsId(), "", "", "", userInfo.getId(),
                    "",0,0,0,0);
            recordTable = model.CprSearch(temp1, 11);

            if (recordTable != null) {
                for (int i = 0; i < recordTable.getCount(); i++) {
                    CrsPickRecord temp = recordTable.getIndex(i);
                    Object[] rowData = {temp.getCrsId(), temp.getCrsName(), temp.getStuId(), temp.getStuName(),temp.getScore1(),temp.getScore2(),temp.getScore3(), temp.getCrsPickScore()};
                    tableModel.addRow(rowData); // ���������
                }
            }
            else {
                JOptionPane.showMessageDialog(this, "��Ǹ��û��ѧ��ѡ��ÿΣ�");
            }
        }

    }

    

    //������������ݸ����
    private void processTableValue(Double value) {
        // ��������жԱ��ֵ�Ĵ���
        ObjectInputStream is;
        ObjectOutputStream os;

        is = socketHelper.getIs();
        os = socketHelper.getOs();

            CrsPickRecord temp = new CrsPickRecord(crsPickRecord.getCrsPickId(), crsPickRecord.getCrsId(),
                    crsPickRecord.getCrsName(), crsPickRecord.getStuId(), crsPickRecord.getStuName(),
                    crsPickRecord.getTeacherId(), crsPickRecord.getTeacherName(), valueArray[0],valueArray[1],valueArray[2],value);
            System.out.println(temp);


            try {
                os.writeInt(313); // ����ѡ��γ̵�������
                os.flush();
                os.writeObject(temp); // ���Ϳγ���Ϣ
                os.flush();

                try {
                    int r = is.readInt(); // ���շ��������صĽ����
                    if (r != 3131) {
                        refreshTable();
                        ((DefaultTableModel) table2.getModel()).fireTableDataChanged();//֪ͨģ�͸���
                        table2.updateUI();//ˢ�±��
                        JOptionPane.showMessageDialog(this, "���ʧ�ܣ�");
                    }else{
                        refreshTable();
                        ((DefaultTableModel) table2.getModel()).fireTableDataChanged();//֪ͨģ�͸���
                        table2.updateUI();//ˢ�±��
                        if(valueArray[0]!=0&&valueArray[1]!=0&&valueArray[2]!=0) {
                            JOptionPane.showMessageDialog(this, "��ֳɹ�");
                        }
                    }
                } catch (Exception ess) {
                    ess.printStackTrace();
                }
            } catch (IOException ess) {
                ess.printStackTrace();
            }

    }

    //�������ı�
    TableModelListener tableModelListener = new TableModelListener() {

        @Override
        public void tableChanged(TableModelEvent e) {
            if(scoreArray[0]==0&&scoreArray[1]==0&&scoreArray[2]==0)
                return;
            if (e.getType() == TableModelEvent.UPDATE) {
                int row = e.getFirstRow();
                int column = e.getColumn();
                if(row<0||column<0)
                    return;
                System.out.println("Cell at row " + row + ", column " + column + " changed.");

                //��ȡ��ѯ�򷵻�״̬�µ�ѡ�μ�¼����
                if(num==0) {
                    crsPickRecord=recordTable.getCPR().get(row);
                }
                else {
                    crsPickRecord=rs.getCPR().get(row);
                }

                // ��ȡ����е�ֵ
                DefaultTableModel tableModel = (DefaultTableModel) table2.getModel();
                Object value1 = tableModel.getValueAt(row, 4);
                Object value2 = tableModel.getValueAt(row, 5);
                Object value3 = tableModel.getValueAt(row, 6);

                valueArray[0] = (Double) value1;
                valueArray[1] = (Double) value2;
                valueArray[2] = (Double) value3;

                System.out.println("ƽʱ�ɼ�"+valueArray[0]);
                Double score=valueArray[0]*scoreArray[0]+valueArray[1]*scoreArray[1]+valueArray[2]*scoreArray[2];
                System.out.println(score);

                processTableValue(score);
            }
        }
    };



    private void ScoreMouseClicked(MouseEvent e) {
        // TODO add your code here
        Score adm=new Score(this);
    }

    public JTable getTable()
    {
        return table2;
    }



    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        panel1 = new JPanel();
        button6 = new JButton();
        scrollPane2 = new JScrollPane();
        table2 = new JTable();
        label4 = new JLabel();
        comboBox2 = new JComboBox<>();
        textField2 = new JTextField();
        button7 = new JButton();
        label1 = new JLabel();

        //======== this ========
        setLayout(null);

        //======== panel1 ========
        {
            panel1.setPreferredSize(new Dimension(1685, 830));
            panel1.setBackground(new Color(0xdfe1e5));
            panel1.setLayout(null);

            //---- button6 ----
            button6.setText("\u67e5\u8be2");
            button6.setPreferredSize(new Dimension(100, 50));
            button6.setFont(new Font("\u534e\u6587\u4eff\u5b8b", Font.BOLD, 24));
            button6.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    PickQueryMouseClicked(e);
                }
            });
            panel1.add(button6);
            button6.setBounds(850, 50, 150, 50);

            //======== scrollPane2 ========
            {
                scrollPane2.setPreferredSize(new Dimension(460, 427));

                //---- table2 ----
                table2.setModel(new DefaultTableModel(
                    new Object[][] {
                        {null, null, null, null, null, null, null, null},
                        {null, null, null, null, null, null, null, null},
                    },
                    new String[] {
                        "\u8bfe\u7a0b\u7f16\u53f7", "\u8bfe\u7a0b\u540d\u79f0", "\u5b66\u751f\u7f16\u53f7", "\u5b66\u751f\u59d3\u540d", "\u5e73\u65f6\u6210\u7ee9", "\u671f\u4e2d\u6210\u7ee9", "\u671f\u672b\u6210\u7ee9", "\u8bfe\u7a0b\u603b\u8bc4"
                    }
                ) {
                    Class<?>[] columnTypes = new Class<?>[] {
                        Object.class, Object.class, Object.class, Object.class, Double.class, Double.class, Double.class, Double.class
                    };
                    @Override
                    public Class<?> getColumnClass(int columnIndex) {
                        return columnTypes[columnIndex];
                    }
                });
                table2.setPreferredSize(new Dimension(1300, 750));
                table2.setRowHeight(40);
                table2.setFont(new Font("\u6977\u4f53", Font.BOLD, 22));
                scrollPane2.setViewportView(table2);
            }
            panel1.add(scrollPane2);
            scrollPane2.setBounds(125, 150, 1435, 530);

            //---- label4 ----
            label4.setText("\u67e5\u8be2\u6761\u4ef6");
            label4.setFont(new Font("\u534e\u6587\u4eff\u5b8b", Font.BOLD, 24));
            label4.setForeground(Color.black);
            panel1.add(label4);
            label4.setBounds(200, 50, 100, 50);

            //---- comboBox2 ----
            comboBox2.setModel(new DefaultComboBoxModel<>(new String[] {
                "\u6309\u8bfe\u7a0b\u7f16\u53f7\u67e5\u8be2",
                "\u6309\u8bfe\u7a0b\u540d\u79f0\u67e5\u8be2"
            }));
            comboBox2.setPreferredSize(new Dimension(200, 50));
            comboBox2.setFont(new Font("\u534e\u6587\u4eff\u5b8b", Font.BOLD, 24));
            panel1.add(comboBox2);
            comboBox2.setBounds(350, 50, 250, 50);

            //---- textField2 ----
            textField2.setPreferredSize(new Dimension(150, 50));
            textField2.setFont(new Font("\u534e\u6587\u4eff\u5b8b", Font.BOLD, 24));
            panel1.add(textField2);
            textField2.setBounds(650, 50, 150, 50);

            //---- button7 ----
            button7.setText("\u8bbe\u7f6e\u6253\u5206\u5360\u6bd4");
            button7.setPreferredSize(new Dimension(100, 50));
            button7.setFont(new Font("\u534e\u6587\u4eff\u5b8b", Font.BOLD, 24));
            button7.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    ScoreMouseClicked(e);
                }
            });
            panel1.add(button7);
            button7.setBounds(1050, 50, 200, 50);

            //---- label1 ----
            label1.setIcon(new ImageIcon(getClass().getResource("/pic/\u80cc\u666f2.png")));
            panel1.add(label1);
            label1.setBounds(new Rectangle(new Point(0, 0), label1.getPreferredSize()));

            {
                // compute preferred size
                Dimension preferredSize = new Dimension();
                for(int i = 0; i < panel1.getComponentCount(); i++) {
                    Rectangle bounds = panel1.getComponent(i).getBounds();
                    preferredSize.width = Math.max(bounds.x + bounds.width, preferredSize.width);
                    preferredSize.height = Math.max(bounds.y + bounds.height, preferredSize.height);
                }
                Insets insets = panel1.getInsets();
                preferredSize.width += insets.right;
                preferredSize.height += insets.bottom;
                panel1.setMinimumSize(preferredSize);
                panel1.setPreferredSize(preferredSize);
            }
        }
        add(panel1);
        panel1.setBounds(0, 0, 1685, 830);

        {
            // compute preferred size
            Dimension preferredSize = new Dimension();
            for(int i = 0; i < getComponentCount(); i++) {
                Rectangle bounds = getComponent(i).getBounds();
                preferredSize.width = Math.max(bounds.x + bounds.width, preferredSize.width);
                preferredSize.height = Math.max(bounds.y + bounds.height, preferredSize.height);
            }
            Insets insets = getInsets();
            preferredSize.width += insets.right;
            preferredSize.height += insets.bottom;
            setMinimumSize(preferredSize);
            setPreferredSize(preferredSize);
        }
        // JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
    private JPanel panel1;
    private JButton button6;
    private JScrollPane scrollPane2;
    private JTable table2;
    private JLabel label4;
    private JComboBox<String> comboBox2;
    private JTextField textField2;
    private JButton button7;
    private JLabel label1;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on
}
