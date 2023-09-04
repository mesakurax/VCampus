/*
 * Created by JFormDesigner on Sat Sep 02 22:34:52 AWST 2023
 */

package chatView;


import module.*;
import entity.*;
import utils.*;

import javax.imageio.ImageIO;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.*;
import javax.swing.*;
import javax.swing.text.*;

import org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper;
import org.jb2011.lnf.beautyeye.ch3_button.BEButtonUI;
import sun.swing.table.DefaultTableCellHeaderRenderer;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.*;



/**
 * @author 22431
 */
public class chatView extends JPanel{
    private SocketHelper message=new SocketHelper();;

    private User uu;

    public void beautify(){
        try {
            BeautyEyeLNFHelper.frameBorderStyle = BeautyEyeLNFHelper.FrameBorderStyle.generalNoTranslucencyShadow;
            org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper.launchBeautyEyeLNF();
            UIManager.put("RootPane.setupButtonVisible",false);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    public chatView(User uu) {

        beautify();
        this.uu=uu;
        message.getConnection(message.ip,message.port);
        try
        {
            //���߷���������Ϣͨ��
            message.getOs().writeInt(0);
            message.getOs().flush();

            //��ʼ���û�����
            message.getOs().writeInt(-100);
            message.getOs().flush();
            message.getOs().writeObject(uu.getId());
            message.getOs().flush();

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        initComponents();
        new Thread(new Message_L()).start();

        StyledDocument doc = textPane1.getStyledDocument();
        SimpleAttributeSet centerAlignment = new SimpleAttributeSet();
        StyleConstants.setAlignment(centerAlignment, StyleConstants.ALIGN_CENTER);
        doc.setParagraphAttributes(0, doc.getLength(), centerAlignment, false);

        try {
            doc.insertString(doc.getLength(), "��ӭ���������ң�\n", null);

            // ���ú����еĶ��뷽ʽΪ����
            SimpleAttributeSet topAlignment = new SimpleAttributeSet();
            StyleConstants.setAlignment(topAlignment, StyleConstants.ALIGN_LEFT);
            doc.setParagraphAttributes(doc.getLength(), 1, topAlignment, false);
        } catch (BadLocationException e) {
            throw new RuntimeException(e);
        }
    }

    private void messageMouseClicked() {
        // TODO add your code here
        if(textField1.getText().isEmpty())
        {
            JOptionPane.showMessageDialog(this,"�벻Ҫ���Ϳ���Ϣ����");
            return;
        }

        if(comboBox1.getSelectedItem().toString().equals("Ⱥ��")){
            String mes="(Ⱥ���ı�) "+uu.getId()+" "+uu.getName();
            mes=mes+"  ["+Timehelp.getCurrentTime()+"] :\n";
            mes=mes+textField1.getText();

            try {
                message.getOs().writeInt(001);
                message.getOs().flush();
                message.getOs().writeObject(mes);
                message.getOs().flush();
            }catch (Exception exception){
                exception.printStackTrace();
            }
        }

        else if(comboBox1.getSelectedItem().toString().equals("˽��")){
            if(textField2.getText().isEmpty())
            {
                JOptionPane.showMessageDialog(this,"��������������ID����");
                return;
            }

            String mes="(˽���ı�) "+uu.getId()+" "+uu.getName();
            mes=mes+"  ["+Timehelp.getCurrentTime()+"] :\n";
            mes=mes+textField1.getText();


            try {
                StyledDocument doc = textPane1.getStyledDocument();
                // ��ȡ�ĵ�
                textPane1.setCaretPosition(doc.getLength()); // ������λ���ƶ������
                doc.insertString(doc.getLength(), "\n", null);
                doc.insertString(doc.getLength(), "\n", null);
                doc.insertString(doc.getLength(), mes, null);


                message.getOs().writeInt(002);
                message.getOs().flush();
                message.getOs().writeObject(textField2.getText());
                message.getOs().flush();
                message.getOs().writeObject(mes);
                message.getOs().flush();
            }catch (Exception exception){
                exception.printStackTrace();
            }
        }

        textField1.setText("");
    }


    private void comboBox1ItemStateChanged() {
        // TODO add your code here
        if(comboBox1.getSelectedItem().toString().equals("Ⱥ��"))
            textField2.setText("");
    }


    private void imageMouseClicked() {
        // TODO add your code here
        if(comboBox1.getSelectedItem().toString().equals("Ⱥ��"))
        {
            JFileChooser fileChooser = new JFileChooser();

            // �����ļ���������ֻ��ʾͼƬ�ļ�
            FileNameExtensionFilter filter = new FileNameExtensionFilter("Image Files", "jpg", "jpeg", "png", "gif");
            fileChooser.setFileFilter(filter);

            // ��ʾ�ļ�ѡ��Ի���
            int result = fileChooser.showOpenDialog(this);

            if (result == JFileChooser.APPROVE_OPTION) {
                // �û�ѡ�����ļ�
                File selectedFile = fileChooser.getSelectedFile();
                String imagePath = selectedFile.getAbsolutePath();
                System.out.println("ѡ���ͼ���ļ�: " + imagePath);


                try {
                    //������ͼƬ
                    message.getOs().writeInt(003);
                    message.getOs().flush();

                    String mes="(Ⱥ��ͼƬ) "+uu.getId()+" "+uu.getName();
                    mes=mes+"  ["+Timehelp.getCurrentTime()+"] :\n";

                    message.getOs().writeObject(mes);
                    message.getOs().flush();

                    BufferedInputStream bis = new BufferedInputStream(new FileInputStream(imagePath));
                    int readLen = 0;
                    byte[] buf = new byte[1024];
                    while( (readLen = bis.read(buf)) != -1 ) {
                        message.getOs().write(buf,0,readLen);
                    }
                    message.getOs().flush();
                    bis.close();

                    String stopMessage = "STOP";
                    byte[] stopData = stopMessage.getBytes();
                    message.getOs().write(stopData);
                    message.getOs().flush();

                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }


            } else if (result == JFileChooser.CANCEL_OPTION) {
                // �û�ȡ��ѡ���ļ�
                System.out.println("ȡ��ѡ��ͼ��");
            } else if (result == JFileChooser.ERROR_OPTION) {
                // ��������
                System.out.println("��������");
            }

        }

        else if(comboBox1.getSelectedItem().toString().equals("˽��"))
        {
            if(textField2.getText().isEmpty())
            {
                JOptionPane.showMessageDialog(this,"��������������ID����");
                return;
            }

            JFileChooser fileChooser = new JFileChooser();

            // �����ļ���������ֻ��ʾͼƬ�ļ�
            FileNameExtensionFilter filter = new FileNameExtensionFilter("Image Files", "jpg", "jpeg", "png", "gif");
            fileChooser.setFileFilter(filter);

            // ��ʾ�ļ�ѡ��Ի���
            int result = fileChooser.showOpenDialog(this);

            if (result == JFileChooser.APPROVE_OPTION) {
                // �û�ѡ�����ļ�
                File selectedFile = fileChooser.getSelectedFile();
                String imagePath = selectedFile.getAbsolutePath();
                System.out.println("ѡ���ͼ���ļ�: " + imagePath);


                try {
                    //������ͼƬ
                    message.getOs().writeInt(004);
                    message.getOs().flush();

                    message.getOs().writeObject(textField2.getText());
                    message.getOs().flush();

                    String mes="(˽��ͼƬ) "+uu.getId()+" "+uu.getName();
                    mes=mes+"  ["+Timehelp.getCurrentTime()+"] :\n";

                    message.getOs().writeObject(mes);
                    message.getOs().flush();

                    BufferedImage image = ImageIO.read(new File(imagePath));
                    ImageIcon icon = ImageHelper.resizeImage(image, 700, 600);

                    StyledDocument doc = textPane1.getStyledDocument();

                    textPane1.setCaretPosition(doc.getLength()); // ������λ���ƶ������
                    doc.insertString(doc.getLength(), "\n", null);
                    doc.insertString(doc.getLength(), "\n", null);
                    doc.insertString(doc.getLength(), mes, null);
                    doc.insertString(doc.getLength(), " ", null);
                    textPane1.setCaretPosition(doc.getLength()); // ������λ���ƶ������
                    textPane1.insertIcon(icon);

                    FileInputStream fis=new FileInputStream(imagePath);
                    BufferedInputStream bis = new BufferedInputStream(fis);
                    int readLen = 0;
                    byte[] buf = new byte[1024];
                    while( (readLen = bis.read(buf)) != -1 ) {
                        message.getOs().write(buf,0,readLen);
                    }
                    message.getOs().flush();
                    bis.close();
                    fis.close();

                    String stopMessage = "STOP";
                    byte[] stopData = stopMessage.getBytes();
                    message.getOs().write(stopData);
                    message.getOs().flush();

                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }


            } else if (result == JFileChooser.CANCEL_OPTION) {
                // �û�ȡ��ѡ���ļ�
                System.out.println("ȡ��ѡ��ͼ��");
            } else if (result == JFileChooser.ERROR_OPTION) {
                // ��������
                System.out.println("��������");
            }

        }
    }


    private void fileMouseClicked() {
        // TODO add your code here
        if (comboBox1.getSelectedItem().toString().equals("Ⱥ��")) {
            JFileChooser fileChooser = new JFileChooser();

            // �����ļ�ѡ�����ı���
            fileChooser.setDialogTitle("ѡ���ļ�");

            // ��ʾ�ļ�ѡ�����Ի���
            int result = fileChooser.showOpenDialog(null);

            // �����û�ѡ��Ľ��
            if (result == JFileChooser.APPROVE_OPTION) {
                // �û�ѡ�����ļ�
                String selectedFilePath = fileChooser.getSelectedFile().getAbsolutePath();
                System.out.println("��ѡ���ļ�·����" + selectedFilePath);

                try {
                    //�������ļ�
                    message.getOs().writeInt(005);
                    message.getOs().flush();

                    String selectedFileName = new File(selectedFilePath).getName();
                    String mes="(Ⱥ���ļ�) "+uu.getId()+" "+uu.getName();
                    mes=mes+"  ["+Timehelp.getCurrentTime()+"] :\n"+selectedFileName;
                    message.getOs().writeObject(mes);
                    message.getOs().flush();


                    FileInputStream fis = new FileInputStream(selectedFilePath);

                    // ����һ�������������ڶ�ȡ�ļ�����
                    byte[] buffer = new byte[4096];
                    int bytesRead;
                    while ((bytesRead = fis.read(buffer)) != -1) {
                        // ���ļ�����д�������
                        message.getOs().write(buffer, 0, bytesRead);
                    }
                    // ˢ������������ر�����
                    message.getOs().flush();
                    fis.close();


                    String stopMessage = "STOP";
                    byte[] stopData = stopMessage.getBytes();
                    message.getOs().write(stopData);
                    message.getOs().flush();

                } catch (Exception e) {
                    throw new RuntimeException(e);
                }

            } else if (result == JFileChooser.CANCEL_OPTION) {
                // �û�ȡ����ѡ��
                System.out.println("ѡ��ȡ����");
            }
        }
    }

    private void textPane1MouseClicked(MouseEvent e) {
        // TODO add your code here
        int offset = textPane1.viewToModel(e.getPoint());

        StyledDocument doc = textPane1.getStyledDocument();
        // ��ȡ���λ�õ�Ԫ��
        Element element = doc.getCharacterElement(offset);


        if (element.getAttributes().getAttribute(StyleConstants.IconAttribute) != null) {
            Icon icon = (Icon) element.getAttributes().getAttribute(StyleConstants.IconAttribute);

            // ����һ����������
            JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(textPane1);

            try {
                // ������ʱ�ļ�
                // ��ȡϵͳĬ�ϵ�ͼƬ�鿴��
                Desktop desktop = Desktop.getDesktop();

                // ������ʱ�ļ�
                File tempFile = File.createTempFile("image", ".png");

                // ��ͼ�񱣴浽��ʱ�ļ�
                BufferedImage image = new BufferedImage(icon.getIconWidth(), icon.getIconHeight(), BufferedImage.TYPE_INT_ARGB);
                icon.paintIcon(null, image.getGraphics(), 0, 0);
                ImageIO.write(image, "png", tempFile);

                // ����ʱ�ļ�������ϵͳĬ�ϵ�ͼƬ�鿴��
                desktop.open(tempFile);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }




    class Message_L implements Runnable {
        @Override
        public void run() {
            try {
                while (true) {
                    int cmd=message.getIs().readInt();
                    if (cmd == 0011 ) {
                        String mes = (String) message.getIs().readObject();
                        System.out.println(mes);
                        StyledDocument doc = textPane1.getStyledDocument();
                        textPane1.setCaretPosition(doc.getLength()); // ������λ���ƶ������
                        doc.insertString(doc.getLength(), "\n", null);
                        doc.insertString(doc.getLength(), "\n", null);
                        doc.insertString(doc.getLength(), mes, null);

                    }
                    else if(cmd==0031) {
                        String mes = (String) message.getIs().readObject();
                        System.out.println(mes);

                        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

                        byte[] buffer = new byte[1024];
                        int bytesRead;

                        while ((bytesRead = message.getIs().read(buffer)) != -1) {
                            outputStream.write(buffer, 0, bytesRead);

                            if (new String(buffer, 0, bytesRead).equals("STOP")) {
                                break;
                            }
                        }
                        byte[] imageData = outputStream.toByteArray();


                        ByteArrayInputStream inputStream = new ByteArrayInputStream(imageData);
                        BufferedImage image = ImageIO.read(inputStream);
                        inputStream.close();


                        ImageIcon icon = ImageHelper.resizeImage(image, 700, 600);

                        StyledDocument doc = textPane1.getStyledDocument();

                        textPane1.setCaretPosition(doc.getLength()); // ������λ���ƶ������
                        doc.insertString(doc.getLength(), "\n", null);
                        doc.insertString(doc.getLength(), "\n", null);
                        doc.insertString(doc.getLength(), mes, null);
                        doc.insertString(doc.getLength(), " ", null);
                        textPane1.setCaretPosition(doc.getLength()); // ������λ���ƶ������
                        textPane1.insertIcon(icon);

                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        scrollPane3 = new JScrollPane();
        textPane1 = new JTextPane();
        textField1 = new JTextField();
        button1 = new JButton();
        button2 = new JButton();
        comboBox1 = new JComboBox<>();
        textField2 = new JTextField();
        button3 = new JButton();

        //======== this ========
        setPreferredSize(new Dimension(1680, 1030));
        setLayout(null);

        //======== scrollPane3 ========
        {

            //---- textPane1 ----
            textPane1.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    textPane1MouseClicked(e);
                }
            });
            scrollPane3.setViewportView(textPane1);
        }
        add(scrollPane3);
        scrollPane3.setBounds(285, 30, 1060, 810);
        add(textField1);
        textField1.setBounds(525, 935, 460, 60);

        //---- button1 ----
        button1.setText("\u6d88\u606f");
        button1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                messageMouseClicked();
            }
        });
        add(button1);
        button1.setBounds(1040, 935, 93, 55);

        //---- button2 ----
        button2.setText("\u56fe\u7247");
        button2.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                imageMouseClicked();
            }
        });
        add(button2);
        button2.setBounds(1160, 935, 85, 50);

        //---- comboBox1 ----
        comboBox1.setModel(new DefaultComboBoxModel<>(new String[] {
            "\u7fa4\u53d1",
            "\u79c1\u804a"
        }));
        comboBox1.setFont(new Font("\u534e\u6587\u4eff\u5b8b", Font.BOLD, 14));
        comboBox1.addItemListener(e -> comboBox1ItemStateChanged());
        add(comboBox1);
        comboBox1.setBounds(530, 875, 118, 50);
        add(textField2);
        textField2.setBounds(685, 875, 145, 45);

        //---- button3 ----
        button3.setText("\u6587\u4ef6");
        button3.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                fileMouseClicked();
            }
        });
        add(button3);
        button3.setBounds(1280, 940, 95, 45);

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
    private JScrollPane scrollPane3;
    private JTextPane textPane1;
    private JTextField textField1;
    private JButton button1;
    private JButton button2;
    private JComboBox<String> comboBox1;
    private JTextField textField2;
    private JButton button3;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on

}
