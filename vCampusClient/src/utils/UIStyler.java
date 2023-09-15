package utils;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.plaf.basic.BasicComboBoxUI;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class UIStyler {
    public UIStyler() {}

    public static void createHeader(Container container) {
        // ����������
        JPanel topPanel = new JPanel();
        topPanel.setBackground(new Color(36, 50, 30));
        topPanel.setBounds(0, 0, 1685, 150);
        container.add(topPanel);

        // ����ϸ����
        JPanel bottomPanel = new JPanel();
        bottomPanel.setBackground(new Color(64, 80, 50));
        bottomPanel.setBounds(0, 150, 1685, 50);
        container.add(bottomPanel);
    }

    public static void setCombobox(JComboBox comboBox) {
        comboBox.setOpaque(false);
        comboBox.setBackground(new Color(64, 80, 50, 128));
        comboBox.setUI(new BasicComboBoxUI() {
            @Override
            protected JButton createArrowButton() {
                return new JButton() {
                    @Override
                    public int getWidth() {
                        return 0;
                    }
                };
            }
        });
        comboBox.setBorder(BorderFactory.createLineBorder(new Color(64, 80, 50), 2));
        comboBox.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index,
                                                          boolean isSelected, boolean cellHasFocus) {
                Component renderer = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                list.setSelectionBackground(new Color(64, 80, 50, 128));
                if (isSelected) {
                    renderer.setBackground(new Color(64, 80, 50)); // ѡ����ı�����ɫΪ��͸��
                    renderer.setForeground(Color.white); // ѡ�����������ɫΪ��ɫ
                } else {
                    renderer.setBackground(new Color(255, 255, 255)); // δѡ����ı�����ɫΪ��͸��
                    renderer.setForeground(Color.black); // δѡ�����������ɫΪ��ɫ
                }
                return renderer;
            }
        });
    }

    public static  void setTextField(JTextField textField)
    {
        textField.setBorder(BorderFactory.createLineBorder(new Color(64, 80, 50),2));
        textField.setOpaque(false);
        textField.setBackground(Color.white);  // ���ñ�����ɫΪ��ɫ
        textField.setForeground(Color.black);

    }

    public static void setTopButton(JButton button1) {
        button1.setSize(150, 50);
        button1.setForeground(Color.black);  //������ɫ
        button1.setFont(new Font("����", Font.BOLD, 26));
        button1.setMargin(new Insets(0, 0, 0, 0));//���߿�����������ҿռ�����Ϊ0
        button1.setContentAreaFilled(false);//��ȥĬ�ϵı������
        button1.setBorderPainted(false);//����ӡ�߿�
        button1.setBorder(null);
        button1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                button1.setOpaque(true);
                button1.setBackground(Color.white);  //�������ȥ����
            }

            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                button1.setOpaque(false);
            }
        });
    }

    public static void setBelowButton(JButton button1) {
        button1.setSize(150, 50);
        button1.setFont(new Font("���ķ���", Font.BOLD, 24));
    }

    public static void setBelowButton1(JButton button1) {
        button1.setBorder(BorderFactory.createLineBorder(new Color(64, 80, 50),2));
        button1.setForeground(Color.black);  //������ɫ
        button1.setBackground(Color.white);
        button1.setContentAreaFilled(false);//��ȥĬ�ϵı������
        button1.setSize(150, 50);
        button1.setFont(new Font("���ķ���", Font.BOLD, 24));

        button1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                button1.setContentAreaFilled(true);//��ȥĬ�ϵı������
                button1.setForeground(Color.white);
                button1.setBackground(new Color(64, 80, 50));

            }
            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                button1.setContentAreaFilled(false);
                button1.setForeground(Color.black);  //������ɫ
                button1.setBackground(Color.white);
            }
        });
    }

    //�����뻬�����ڵ�Table��Ϊ͸��
    public static void setTransparentTable(JScrollPane scrollPane1) {
        JViewport viewport = scrollPane1.getViewport();
        JTable table1 = (JTable) viewport.getView();
        TransparentHeaderRenderer hr = new TransparentHeaderRenderer(); // �����Զ������Ⱦ��
        hr.setOpaque(false);
        hr.setHorizontalAlignment(JLabel.CENTER);

        table1.getTableHeader().setDefaultRenderer(hr);
        table1.setOpaque(false); // ��table����Ϊ͸��
        table1.setDefaultRenderer(Object.class, hr);
        table1.setForeground(Color.WHITE);

        JTableHeader head = table1.getTableHeader(); // �������������
        head.setFont(new Font("���ķ���", Font.BOLD, 40));
        head.setPreferredSize(new Dimension(head.getWidth(), 40));
        table1.setFont(new Font("���ķ���", Font.BOLD, 28));
        head.setOpaque(false);

        scrollPane1.setOpaque(false);
        scrollPane1.getViewport().setOpaque(false);
        scrollPane1.getVerticalScrollBar().setOpaque(false); // ����������͸��
        scrollPane1.setColumnHeaderView(table1.getTableHeader());
        scrollPane1.getColumnHeader().setOpaque(false);
        DefaultTableCellRenderer cellRenderer = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component component = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                component.setFont(new Font("���ķ���", Font.BOLD, 28));
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
        cellRenderer.setHorizontalAlignment(SwingConstants.CENTER);

        // ���� TableCellRenderer Ϊ����Ĭ����Ⱦ��
        table1.setDefaultRenderer(Object.class, cellRenderer);

        // ���ѡ�������������ѡ���е���ɫ
        table1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); // ����ֻ��ѡ����
        table1.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) { // ȷ��ֻ��ѡ�����ʱ����
                    int selectedRow = table1.getSelectedRow();
                    table1.repaint(); // ˢ�±���Ը���ѡ���е���ɫ
                }
            }
        });
    }
}

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
