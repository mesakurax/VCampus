/*
 * Created by JFormDesigner on Tue Sep 05 12:42:43 CST 2023
 */

package CourseView_S;

import utils.UIStyler;
import entity.CrsPickRecord;
import entity.RecordTable;
import entity.User;
import module.CourseSystem;
import org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper;
import utils.SocketHelper;

import javax.swing.*;
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
public class Record_S extends JPanel {
    CourseSystem model;
    User userInfo;

    CrsPickRecord crsPickRecord;

    public SocketHelper socketHelper;

    RecordTable rs;//��¼��ѯ���
    int num=0;//��¼button״��

    RecordTable recordTable;//��¼�������

    public void beautify(){
        try {
            BeautyEyeLNFHelper.frameBorderStyle = BeautyEyeLNFHelper.FrameBorderStyle.generalNoTranslucencyShadow;
            BeautyEyeLNFHelper.launchBeautyEyeLNF();
            UIManager.put("RootPane.setupButtonVisible",false);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public Record_S(User userInfo, SocketHelper socketHelper) {
        //beautify();
        initComponents();
        new UIStyler().setTextField(textField2);
        new UIStyler().setCombobox(comboBox2);
        new UIStyler().setBelowButton(button5);//�����·���ť��ʽ
        new UIStyler().setBelowButton(button6);//�����·���ť��ʽ
        new UIStyler().setTransparentTable(scrollPane2);//���ñ���ʽ

        this.socketHelper = socketHelper;
        this.userInfo = userInfo;
        model=new CourseSystem(this.socketHelper);
        refreshTable();
    }

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
                    if (flag.equals("���γ̱�Ų�ѯ")) {
                        int number = Integer.parseInt(text);
                        CrsPickRecord record=new CrsPickRecord(-1,number,"",userInfo.getId(), "", "", "",0,0,0,0);
                        System.out.println("��ʼ����");
                        rs = model.CprSearch(record, 7);
                        if (rs==null) {
                            textField2.setText("");//�����
                            table2.clearSelection();
                            JOptionPane.showMessageDialog(null, "δ�ҵ�ƥ���ѡ�μ�¼", "����ʧ��", JOptionPane.ERROR_MESSAGE);
                        } else {
                            DefaultTableModel tableModel = (DefaultTableModel) table2.getModel();//��ȡ�������������ģ�Ͷ���ʹ�� DefaultTableModel ��ķ����������͹���������ݡ�
                            tableModel.setRowCount(0); // ��ձ������
                            for(int i=0;i<rs.getCount();i++){
                                CrsPickRecord temp=rs.getIndex(i);
                                Object[] rowData = {temp.getCrsPickId(),temp.getCrsId(),temp.getCrsName(),temp.getStuId(),temp.getStuName(),temp.getTeacherId(),temp.getTeacherName(),temp.getCrsPickScore()};
                                tableModel.addRow(rowData); // ���������
                            }
                            button.setText("����");
                            num=1;
                        }
                    }
                    if (flag.equals("���γ����Ʋ�ѯ")) {
                        CrsPickRecord record=new CrsPickRecord(-1,1,text, userInfo.getId(), "", "", "",0,0,0,0);
                        System.out.println("��ʼ����");
                        rs = model.CprSearch(record, 2);
                        if (rs==null) {
                            textField2.setText("");//�����
                            table2.clearSelection();
                            JOptionPane.showMessageDialog(null, "δ�ҵ�ƥ���ѡ�μ�¼", "����ʧ��", JOptionPane.ERROR_MESSAGE);
                        }else {
                            DefaultTableModel tableModel = (DefaultTableModel) table2.getModel();//��ȡ�������������ģ�Ͷ���ʹ�� DefaultTableModel ��ķ����������͹���������ݡ�
                            tableModel.setRowCount(0); // ��ձ������
                            for(int i=0;i<rs.getCount();i++){
                                CrsPickRecord temp=rs.getIndex(i);
                                Object[] rowData = {temp.getCrsPickId(),temp.getCrsId(),temp.getCrsName(),temp.getStuId(),temp.getStuName(),temp.getTeacherId(),temp.getTeacherName(),temp.getCrsPickScore()};
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


    private void PickDeleteMouseClicked(MouseEvent e) {
        // TODO add your code here
        int selectedRow = table2.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "����ѡ��һ�пγ���Ϣ");
            return;
        }
        ObjectInputStream is;
        ObjectOutputStream os;

        is = socketHelper.getIs(); // ��socketHelper��ȡObjectInputStream����
        os = socketHelper.getOs(); // ��socketHelper��ȡObjectOutputStream����

            // ��ѡ�γ�
            try {
                os.writeInt(312); // ������ѡ�γ̵�������
                os.flush();
                os.writeObject(crsPickRecord); // �����û���Ϣ
                os.flush();

                try {
                    int re = is.readInt(); // ���շ��������صĽ����
                    if (re == 3121) {
                        // ��ѡ�ɹ�
                        if(num==0)
                        refreshTable();
                        else {
                            DefaultTableModel tableModel = (DefaultTableModel) table2.getModel();//��ȡ�������������ģ�Ͷ���ʹ�� DefaultTableModel ��ķ����������͹���������ݡ�
                            tableModel.setRowCount(0); // ��ձ������

                            for(int i=0;i<rs.getCount();i++){

                                if(rs.getIndex(i)!=crsPickRecord) {
                                    CrsPickRecord temp = rs.getIndex(i);
                                    Object[] rowData = {temp.getCrsPickId(), temp.getCrsId(), temp.getCrsName(), temp.getStuId(), temp.getStuName(), temp.getTeacherId(),temp.getTeacherName(), temp.getCrsPickScore()};
                                    tableModel.addRow(rowData); // ���������
                                }
                            }
                        }
                        JOptionPane.showMessageDialog(this, "��ѡ�ɹ���");
                    } else if (re == 3032) {
                        // ��ѡʧ��
                        JOptionPane.showMessageDialog(this, "��ѡʧ�ܣ�");
                    }
                } catch (Exception ess) {
                    ess.printStackTrace();
                }
            } catch (IOException ess) {
                ess.printStackTrace();
            }
        }

    public void refreshTable() {
        button6.setText("��ѯ");
        textField2.setText("");//�����
        // �� model �������¼
        CrsPickRecord temp1 = new CrsPickRecord(-1, -1, "", userInfo.getId(),userInfo.getName(), "", "",0,0,0,0);
        recordTable = model.CprSearch(temp1,8);
        // ��ȡ���ģ��
        DefaultTableModel tableModel = (DefaultTableModel) table2.getModel();//��ȡ�������������ģ�Ͷ���ʹ�� DefaultTableModel ��ķ����������͹���������ݡ�
        tableModel.setRowCount(0); // ��ձ������
        if(recordTable != null) {
            for (int i = 0; i < recordTable.getCount(); i++) {
                CrsPickRecord temp = recordTable.getIndex(i);
                Object[] rowData = {temp.getCrsPickId(), temp.getCrsId(), temp.getCrsName(), temp.getStuId(), temp.getStuName(), temp.getTeacherId(),temp.getTeacherName(), temp.getCrsPickScore()};
                tableModel.addRow(rowData); // ���������
            }
        }
        else {
            System.out.println("RecordTable is null");
        }
    }


    private void table2MouseClicked(MouseEvent e) {
        // TODO add your code here
        int rowNum=table2.getSelectedRow();
        if(num==0) {
            crsPickRecord=recordTable.getCPR().get(rowNum);
        }
        else {
            crsPickRecord=rs.getCPR().get(rowNum);
        }

    }




    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        panel1 = new JPanel();
        scrollPane2 = new JScrollPane();
        table2 = new JTable();
        comboBox2 = new JComboBox<>();
        textField2 = new JTextField();
        label5 = new JLabel();
        button6 = new JButton();
        button5 = new JButton();
        label1 = new JLabel();

        //======== this ========
        setPreferredSize(new Dimension(1685, 860));
        setLayout(null);

        //======== panel1 ========
        {
            panel1.setPreferredSize(new Dimension(1685, 830));
            panel1.setBackground(new Color(0xdfe1e5));
            panel1.setLayout(null);

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
                        "\u9009\u8bfe\u7f16\u53f7", "\u8bfe\u7a0b\u7f16\u53f7", "\u8bfe\u7a0b\u540d\u79f0", "\u5b66\u751f\u7f16\u53f7", "\u5b66\u751f\u59d3\u540d", "\u8001\u5e08\u7f16\u53f7", "\u8001\u5e08\u59d3\u540d", "\u8bfe\u7a0b\u6210\u7ee9"
                    }
                ));
                table2.setPreferredSize(new Dimension(1300, 750));
                table2.setRowHeight(40);
                table2.setFont(new Font("\u6977\u4f53", Font.BOLD, 22));
                table2.setForeground(Color.black);
                table2.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        table2MouseClicked(e);
                    }
                });
                scrollPane2.setViewportView(table2);
            }
            panel1.add(scrollPane2);
            scrollPane2.setBounds(125, 150, 1435, 530);

            //---- comboBox2 ----
            comboBox2.setModel(new DefaultComboBoxModel<>(new String[] {
                "\u6309\u8bfe\u7a0b\u7f16\u53f7\u67e5\u8be2",
                "\u6309\u8bfe\u7a0b\u540d\u79f0\u67e5\u8be2"
            }));
            comboBox2.setPreferredSize(new Dimension(200, 50));
            comboBox2.setFont(new Font("\u534e\u6587\u4eff\u5b8b", Font.BOLD, 24));
            comboBox2.setForeground(new Color(0x2b2d30));
            panel1.add(comboBox2);
            comboBox2.setBounds(350, 50, 250, 50);

            //---- textField2 ----
            textField2.setPreferredSize(new Dimension(150, 50));
            textField2.setFont(new Font("\u534e\u6587\u4eff\u5b8b", Font.BOLD, 24));
            textField2.setForeground(new Color(0x2b2d30));
            panel1.add(textField2);
            textField2.setBounds(650, 50, 150, 50);

            //---- label5 ----
            label5.setText("\u67e5\u8be2\u6761\u4ef6");
            label5.setFont(new Font("\u534e\u6587\u4eff\u5b8b", Font.BOLD, 24));
            label5.setForeground(Color.black);
            panel1.add(label5);
            label5.setBounds(200, 50, 100, 50);

            //---- button6 ----
            button6.setText("\u67e5\u8be2");
            button6.setPreferredSize(new Dimension(100, 50));
            button6.setFont(new Font("\u534e\u6587\u4eff\u5b8b", Font.BOLD, 24));
            button6.setForeground(new Color(0x2b2d30));
            button6.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    PickQueryMouseClicked(e);
                }
            });
            panel1.add(button6);
            button6.setBounds(850, 50, 150, 50);

            //---- button5 ----
            button5.setText("\u9000\u8bfe");
            button5.setPreferredSize(new Dimension(100, 50));
            button5.setFont(new Font("\u534e\u6587\u4eff\u5b8b", Font.BOLD, 24));
            button5.setForeground(new Color(0x2b2d30));
            button5.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    PickDeleteMouseClicked(e);
                }
            });
            panel1.add(button5);
            button5.setBounds(1050, 50, 150, 50);

            //---- label1 ----
            label1.setIcon(new ImageIcon(getClass().getResource("/pic/\u80cc\u666f2.png")));
            panel1.add(label1);
            label1.setBounds(0, 0, label1.getPreferredSize().width, 830);

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
    private JScrollPane scrollPane2;
    private JTable table2;
    private JComboBox<String> comboBox2;
    private JTextField textField2;
    private JLabel label5;
    private JButton button6;
    private JButton button5;
    private JLabel label1;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on
}
