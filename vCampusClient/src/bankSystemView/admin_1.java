/*
 * Created by JFormDesigner on Sat Sep 02 12:55:57 AWST 2023
 */

package bankSystemView;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.*;


import beautyComponent.TransparentTable;
import module.*;
import entity.*;
import utils.*;

import org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper;
import org.jb2011.lnf.beautyeye.ch3_button.BEButtonUI;
import sun.swing.table.DefaultTableCellHeaderRenderer;



/**
 * @author 22431
 */
public class admin_1 extends JPanel {
    private SocketHelper socketHelper;
    private bankSystem model;

    public void beautify(){
        try {
            BeautyEyeLNFHelper.frameBorderStyle = BeautyEyeLNFHelper.FrameBorderStyle.generalNoTranslucencyShadow;
            BeautyEyeLNFHelper.launchBeautyEyeLNF();
            UIManager.put("RootPane.setupButtonVisible",false);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public admin_1(SocketHelper socketHelper) {
        beautify();
        initComponents();

        new TransparentTable().set(scrollPane1);
        this.socketHelper=socketHelper;
        model=new bankSystem(this.socketHelper);

        UIStyler.setBelowButton(button2);
        UIStyler.setBelowButton(button3);
        UIStyler.setBelowButton(button4);
        UIStyler.setTransparentTable(scrollPane1);

        table1.setFont(new Font("���ķ���", Font.BOLD, 28));
        table1.setRowHeight(60);
        JTableHeader head = table1.getTableHeader(); // �������������
        head.setPreferredSize(new Dimension(head.getWidth(), 40));// ���ñ�ͷ��С

        refreshTable();
    }
    public void refreshTable() {
        rechargeRecord[] sss = model.admin_requestRecord(new rechargeRecord());
        DefaultTableModel tableModel = (DefaultTableModel) table1.getModel();
        // ����������
        tableModel.setRowCount(0);
        textField1.setText("");//�����

        for (rechargeRecord record : sss) {
            String isDisposeText = (record.getIsDispose() == 0) ? "��" : "��";
            Object[] rowData = {
                    record.getrId(),
                    record.getuId(),
                    record.getuName(),
                    record.getAmount(),
                    isDisposeText,
                    record.getStatus(),
                    record.getTime(),
            };
            tableModel.addRow(rowData);
        }
    }


    private void acceptMouseClicked(MouseEvent e) {
        // TODO add your code here
        int selectedRow = table1.getSelectedRow();
        if (selectedRow != -1) {
            TableModel model = table1.getModel();
            Object rowData[] = new Object[model.getColumnCount()];
            for (int i = 0; i < model.getColumnCount(); i++) {
                rowData[i] = model.getValueAt(selectedRow, i);
            }

            rechargeRecord temp=new rechargeRecord();
            temp.setrId((Integer) rowData[0]);
            temp.setuId((String) rowData[1]);
            temp.setAmount((Double) rowData[3]);
             if(this.model.admin_accept(temp))
                 JOptionPane.showMessageDialog(this,"��ͨ���ó�ֵ���󣡣�");
             else
                 JOptionPane.showMessageDialog(this,"����ʧ�ܣ�������һ�Σ���");
        }
        else
            JOptionPane.showMessageDialog(this,"��ѡ���ֵ��¼����");
        refreshTable();

    }

    private void refuseMouseClicked(MouseEvent e) {
        // TODO add your code here��
        int selectedRow = table1.getSelectedRow();
        selectedRow=table1.convertRowIndexToModel(selectedRow);
        if (selectedRow != -1) {
            TableModel model = table1.getModel();
            Object rowData[] = new Object[model.getColumnCount()];
            for (int i = 0; i < model.getColumnCount(); i++) {
                rowData[i] = model.getValueAt(selectedRow, i);
            }

            rechargeRecord temp=new rechargeRecord();
            temp.setrId((Integer) rowData[0]);
            if(this.model.admin_refuse(temp))
            {
                JOptionPane.showMessageDialog(this,"�Ѿܾ��ó�ֵ���󣡣�");
                refreshTable();
            }
            else
                JOptionPane.showMessageDialog(this,"����ʧ�ܣ�������һ�Σ���");
        }
        else
            JOptionPane.showMessageDialog(this,"��ѡ���ֵ��¼����");


    }

    private void refreshMouseClicked(MouseEvent e) {
        // TODO add your code here
        refreshTable();
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        scrollPane1 = new JScrollPane();
        table1 = new JTable();
        panel1 = new JPanel();
        textField1 = new JTextField();
        button3 = new JButton();
        button2 = new JButton();
        button4 = new JButton();
        label3 = new JLabel();

        //======== this ========
        setPreferredSize(new Dimension(1685, 815));
        setBackground(new Color(0x00ffcc));
        setOpaque(false);
        setMinimumSize(new Dimension(1685, 815));
        setLayout(null);

        //======== scrollPane1 ========
        {

            //---- table1 ----
            table1.setModel(new DefaultTableModel(
                new Object[][] {
                    {"", "", "", "", "", "", ""},
                    {null, null, null, null, null, null, null},
                    {null, null, null, null, null, null, null},
                    {null, null, null, null, null, null, null},
                    {null, null, null, null, null, null, null},
                    {null, null, null, null, null, null, null},
                    {null, null, null, null, null, null, null},
                    {null, null, null, null, null, null, null},
                    {null, null, null, null, null, null, null},
                    {null, null, null, null, null, null, null},
                    {null, null, null, null, null, null, null},
                    {null, null, null, null, null, null, null},
                    {null, null, null, null, null, null, null},
                    {null, null, null, null, null, null, null},
                    {null, null, null, null, null, null, null},
                    {null, null, null, null, null, null, null},
                    {null, null, null, null, null, null, null},
                    {null, null, null, null, null, null, null},
                    {null, null, null, null, null, null, null},
                    {null, null, null, null, null, null, null},
                    {null, null, null, null, null, null, null},
                    {null, null, null, null, null, null, null},
                    {null, null, null, null, null, null, null},
                    {null, null, null, null, null, null, null},
                    {null, null, null, null, null, null, null},
                    {null, null, null, null, null, null, null},
                    {null, null, null, null, null, null, null},
                    {null, null, null, null, null, null, null},
                    {null, null, null, null, null, null, null},
                    {null, null, null, null, null, null, null},
                    {null, null, null, null, null, null, null},
                    {null, null, null, null, null, null, null},
                    {null, null, null, null, null, null, null},
                    {null, null, null, null, null, null, null},
                    {null, null, null, null, null, null, null},
                    {null, null, null, null, null, null, null},
                    {null, null, null, null, null, null, null},
                    {null, null, null, null, null, null, null},
                    {null, null, null, null, null, null, null},
                    {null, null, null, null, null, null, null},
                    {null, null, null, null, null, null, null},
                    {null, null, null, null, null, null, null},
                    {null, null, null, null, null, null, null},
                    {null, null, null, null, null, null, null},
                    {null, null, null, null, null, null, null},
                    {null, null, null, null, null, null, null},
                    {null, null, null, null, null, null, null},
                    {null, null, null, null, null, null, null},
                    {null, null, null, null, null, null, null},
                    {null, null, null, null, null, null, null},
                },
                new String[] {
                    "\u7f16\u53f7", "ID", "\u59d3\u540d", "\u91d1\u989d", "\u662f\u5426\u5904\u7406", "\u7ed3\u679c", "\u65f6\u95f4"
                }
            ) {
                boolean[] columnEditable = new boolean[] {
                    false, false, false, false, false, false, false
                };
                @Override
                public boolean isCellEditable(int rowIndex, int columnIndex) {
                    return columnEditable[columnIndex];
                }
            });
            {
                TableColumnModel cm = table1.getColumnModel();
                cm.getColumn(0).setMinWidth(15);
                cm.getColumn(6).setPreferredWidth(160);
            }
            scrollPane1.setViewportView(table1);
        }
        add(scrollPane1);
        scrollPane1.setBounds(45, 95, 1600, 695);

        //======== panel1 ========
        {
            panel1.setForeground(Color.white);
            panel1.setBackground(new Color(0x2b4e50));
            panel1.setPreferredSize(new Dimension(1685, 815));
            panel1.setOpaque(false);
            panel1.setLayout(null);

            //---- textField1 ----
            textField1.setFont(new Font("\u6977\u4f53", Font.BOLD, 24));
            textField1.setForeground(Color.black);
            textField1.setBackground(Color.lightGray);
            panel1.add(textField1);
            textField1.setBounds(45, 15, 345, 65);

            //---- button3 ----
            button3.setText("\u62d2\u7edd");
            button3.setFont(new Font("\u5e7c\u5706", Font.BOLD, 20));
            button3.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    refuseMouseClicked(e);
                }
            });
            panel1.add(button3);
            button3.setBounds(610, 25, 150, 50);

            //---- button2 ----
            button2.setText("\u901a\u8fc7");
            button2.setFont(new Font("\u5e7c\u5706", Font.BOLD, 20));
            button2.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    acceptMouseClicked(e);
                }
            });
            panel1.add(button2);
            button2.setBounds(435, 25, 150, 50);

            //---- button4 ----
            button4.setText("\u5237\u65b0");
            button4.setFont(new Font("\u5e7c\u5706", Font.BOLD, 20));
            button4.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    refreshMouseClicked(e);
                }
            });
            panel1.add(button4);
            button4.setBounds(795, 25, 150, 50);

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

        //---- label3 ----
        label3.setText("text");
        label3.setIcon(new ImageIcon(getClass().getResource("/bankSystemView/pic/imageonline-co-brightnessadjusted (4).png")));
        add(label3);
        label3.setBounds(0, 0, 1685, 830);

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
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>((DefaultTableModel) table1.getModel());
        table1.setRowSorter(sorter);

        String tipText = "�������ֵ����ID";
        textField1.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                if (textField1.getText().equals(tipText)) {
                } else {
                    updateFilter();
                }
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                if (textField1.getText().equals(tipText)) {
                } else {
                    updateFilter();
                }
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                if (textField1.getText().equals(tipText)) {
                } else {
                    updateFilter();
                }

            }

            private void updateFilter() {
                String searchText = textField1.getText();
                if (searchText.trim().length() == 0) {
                    sorter.setRowFilter(null);
                } else {
                    sorter.setRowFilter(RowFilter.regexFilter("(?i)" + searchText));
                }
            }
        });
        textField1.setForeground(Color.GRAY);
        // ��ӽ��������������ý���ʱ����ı����ݺ��޸�������ɫ
        textField1.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (textField1.getText().equals(tipText)) {
                    textField1.setText("");
                    textField1.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (textField1.getText().isEmpty()) {
                    textField1.setText(tipText);
                    textField1.setForeground(Color.GRAY);
                }
            }
        });
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
    private JScrollPane scrollPane1;
    private JTable table1;
    private JPanel panel1;
    private JTextField textField1;
    private JButton button3;
    private JButton button2;
    private JButton button4;
    private JLabel label3;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on

   /* public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    JFrame frame = new JFrame();
                    SocketHelper socketHelper=new SocketHelper();
                    socketHelper.getConnection(socketHelper.ip,socketHelper.port);
                    socketHelper.getOs().writeInt(1);
                    admin_1 stuAdmin = new admin_1(socketHelper);

                    frame.setLayout(new BorderLayout()); // ���ò��ֹ�����ΪBorderLayout

                    frame.add(stuAdmin, BorderLayout.CENTER); // ��StuAdmin��ӵ�CENTERλ��
                    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    frame.pack();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }*/
}
