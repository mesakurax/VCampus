/*
 * Created by JFormDesigner on Mon Sep 04 16:39:53 CST 2023
 */

package StudentRollview;

import entity.StudentRoll;
import entity.User;
import module.StudentData;
import utils.SocketHelper;

import javax.swing.*;
import javax.swing.GroupLayout;
import java.awt.*;
import java.util.Random;

/**
 * @author OverSky
 */
public class PersonalInfo extends JPanel {
    private SocketHelper socketHelper;
    private StudentRoll studentRoll;
    private String[] text;
    public PersonalInfo(User info, SocketHelper socketHelper) {
        text = new String[]{"ɽ��Զ���²�����ĺ����Ӱպ���档",
                "����һ��ˮ���������ɽ��",
                "��ʶǬ����������ľ�ࡣ",
                "���������������¾�¶�̡�",
                "����һƬ�£��򻧵�������",
                "������Ϧ�£���δ���ȥ���ƣ�",
                "�Ӵ����İ���ҹ��������������¥��",
                "ݺ���Ļ������β�㲨���塣",
                "��Ҷ���˼��޷֣��ջ��Ӵ˲��뿪��",
                "���޿����ֻ࣬Ϊ��˼�ϡ�",
                "ɽ�²�֪�����£�ˮ�������ǰ����ҡҷ����б��",
                "��ʱ�����ڣ����ղ��ƹ顣",
                "ҹ��һ�����Σ�����ʮ�����顣",
                "μ����������������ĺ�ơ�",
                "��ʱ������,��������˵��˼��",
                "��������ѩ������һ���ޡ�",
                "����ʱ����׷ף�·���������ϻꡣ",
                "������ֻ�������������籯���ȣ�",
                "�һ�̶ˮ��ǧ�ߣ��������������顣",
                "��ϼ�������ɣ���ˮ������һɫ��"};

        initComponents();
        this.socketHelper = socketHelper;
        this.studentRoll = new StudentData(socketHelper).Display1(info);
        name.setText(info.getName());

        if (info.getOccupation().equals("ѧ��")) Id.setText("ѧ��: "+ info.getId());
        else Id.setText("����: "+ info.getId());

        Department.setText("ѧԺ: " + info.getAcademy());
        Random random = new Random();
        int randomIndex = random.nextInt(text.length); // ����0�����鳤��֮����������
        String randomString = "<html>" + text[randomIndex] + "</html>";
        saying.setText(randomString);
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        label1 = new JLabel();
        label2 = new JLabel();
        name = new JLabel();
        saying = new JLabel();
        Id = new JLabel();
        Department = new JLabel();

        //======== this ========
        setBackground(Color.white);

        //---- label1 ----
        label1.setIcon(new ImageIcon(getClass().getResource("/StudentRollview/studentRollPic/purpleline.png")));

        //---- label2 ----
        label2.setIcon(new ImageIcon(getClass().getResource("/StudentRollview/studentRollPic/redline.png")));

        //---- name ----
        name.setForeground(Color.black);
        name.setFont(new Font("\u534e\u6587\u6977\u4f53", Font.PLAIN, 28));
        name.setText("text");

        //---- saying ----
        saying.setFont(new Font("\u534e\u6587\u884c\u6977", Font.PLAIN, 25));
        saying.setForeground(Color.black);

        //---- Id ----
        Id.setText("text");
        Id.setFont(new Font("\u534e\u6587\u6977\u4f53", Font.PLAIN, 26));

        //---- Department ----
        Department.setText("text");
        Department.setFont(new Font("\u534e\u6587\u6977\u4f53", Font.PLAIN, 26));

        GroupLayout layout = new GroupLayout(this);
        setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup()
                .addGroup(layout.createSequentialGroup()
                    .addGroup(layout.createParallelGroup()
                        .addComponent(label2)
                        .addComponent(label1))
                    .addGap(0, 0, Short.MAX_VALUE))
                .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                        .addGroup(layout.createSequentialGroup()
                            .addContainerGap(215, Short.MAX_VALUE)
                            .addComponent(saying, GroupLayout.PREFERRED_SIZE, 338, GroupLayout.PREFERRED_SIZE))
                        .addGroup(layout.createSequentialGroup()
                            .addGap(18, 18, 18)
                            .addComponent(name, GroupLayout.PREFERRED_SIZE, 92, GroupLayout.PREFERRED_SIZE)
                            .addGap(66, 66, 66)
                            .addGroup(layout.createParallelGroup()
                                .addComponent(Department, GroupLayout.DEFAULT_SIZE, 377, Short.MAX_VALUE)
                                .addComponent(Id, GroupLayout.DEFAULT_SIZE, 377, Short.MAX_VALUE))))
                    .addGap(27, 27, 27))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup()
                .addGroup(layout.createSequentialGroup()
                    .addComponent(label1)
                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                    .addGroup(layout.createParallelGroup()
                        .addComponent(name, GroupLayout.PREFERRED_SIZE, 66, GroupLayout.PREFERRED_SIZE)
                        .addComponent(Id, GroupLayout.PREFERRED_SIZE, 77, GroupLayout.PREFERRED_SIZE))
                    .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(Department, GroupLayout.PREFERRED_SIZE, 71, GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                    .addComponent(saying, GroupLayout.PREFERRED_SIZE, 90, GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(label2))
        );
        // JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
    private JLabel label1;
    private JLabel label2;
    private JLabel name;
    private JLabel saying;
    private JLabel Id;
    private JLabel Department;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on
}
