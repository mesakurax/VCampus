package beautyComponent;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import java.awt.*;

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
public class TransparentTable {
    public TransparentTable(){}

    //�����뻬�����ڵ�Table��Ϊ͸��
    public void set(JScrollPane scrollPane1){
        JViewport viewport = scrollPane1.getViewport();
        JTable table1 = (JTable) viewport.getView();
        TransparentHeaderRenderer hr = new TransparentHeaderRenderer(); //�����Զ������Ⱦ��
        hr.setOpaque(false);
        hr.setHorizontalAlignment(JLabel.CENTER);

        table1.getTableHeader().setDefaultRenderer(hr);
        table1.setOpaque(false);//��table����Ϊ͸��
        table1.setDefaultRenderer(Object.class, hr);

        JTableHeader head = table1.getTableHeader(); // �������������
        head.setOpaque(false);

        scrollPane1.setOpaque(false);
        scrollPane1.getViewport().setOpaque(false);
        scrollPane1.getVerticalScrollBar().setOpaque(false);//����������͸��
        scrollPane1.setColumnHeaderView(table1.getTableHeader());
        scrollPane1.getColumnHeader().setOpaque(false);
    }
}
