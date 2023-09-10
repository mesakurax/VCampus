/*
 * Created by JFormDesigner on Sat Sep 02 22:34:52 AWST 2023
 */

package chatView;


import javax.swing.event.*;

import entity.*;
import utils.*;

import javax.imageio.ImageIO;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.*;
import javax.swing.text.*;

import org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.*;


/**
 * @author 22431
 */
public class chatView extends JPanel{
    private SocketHelper message=new SocketHelper();;

    private User uu;

    private tonghua_wait deng;

    private tonghua_accept accept;

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
            // ���û�ӭ�����ʽ
            SimpleAttributeSet welcomeStyle = new SimpleAttributeSet();
            StyleConstants.setFontFamily(welcomeStyle, "��Բ");  // ��������Ϊ��Բ
            StyleConstants.setFontSize(welcomeStyle, 22);  // ���������СΪ20
            doc.insertString(doc.getLength(), "��ӭ���������ң�\n", welcomeStyle);

            // ���ú����е���ʽ
            SimpleAttributeSet textStyle = new SimpleAttributeSet();
            StyleConstants.setFontFamily(textStyle, "��Բ");  // ��������Ϊ��Բ
            StyleConstants.setFontSize(textStyle, 16);  // ���������СΪ14
            doc.setParagraphAttributes(doc.getLength(), 1, textStyle, false);
            SimpleAttributeSet topAlignment = new SimpleAttributeSet();
            StyleConstants.setAlignment(topAlignment, StyleConstants.ALIGN_LEFT);
            doc.setParagraphAttributes(doc.getLength(), 1, topAlignment, false);
        } catch (BadLocationException e) {
            throw new RuntimeException(e);
        }
        refreshlist();
    }

    private void messageMouseClicked() {
        // TODO add your code here
        if(textField2.getText().isEmpty())
        {
            JOptionPane.showMessageDialog(this,"��ѡ��������󣡣�");
            refreshlist();
            return;
        }
        if(textField1.getText().isEmpty())
        {
            JOptionPane.showMessageDialog(this,"�벻Ҫ���Ϳ���Ϣ����");
            refreshlist();
            return;
        }

        if(textField2.getText().equals("All user")){
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

        else {
            String mes="(˽���ı�) "+uu.getId()+" "+uu.getName();
            mes=mes+"  ["+Timehelp.getCurrentTime()+"] :\n";
            mes=mes+textField1.getText();

            try {
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
        refreshlist();

    }


    private void imageMouseClicked() {
        // TODO add your code here
        if(textField2.getText().isEmpty())
        {
            JOptionPane.showMessageDialog(this,"��ѡ��������󣡣�");
            refreshlist();
            return;
        }
        if(textField2.getText().equals("All user"))
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

        else
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

        refreshlist();
    }


    private void fileMouseClicked() {
        // TODO add your code here
        if(textField2.getText().isEmpty())
        {
            JOptionPane.showMessageDialog(this,"��ѡ��������󣡣�");
            refreshlist();
            return;
        }
        if (textField2.getText().equals("All user")) {
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
                    message.getOs().writeObject(selectedFileName);
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

        else {
            if (textField2.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "��������������ID����");
                return;
            }
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
                    message.getOs().writeInt(006);
                    message.getOs().flush();
                    message.getOs().writeObject(textField2.getText());
                    message.getOs().flush();

                    String selectedFileName = new File(selectedFilePath).getName();
                    String mes="(˽���ļ�) "+uu.getId()+" "+uu.getName();
                    mes=mes+"  ["+Timehelp.getCurrentTime()+"] :\n"+selectedFileName;
                    message.getOs().writeObject(mes);
                    message.getOs().flush();
                    message.getOs().writeObject(selectedFileName);
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

        refreshlist();
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
        else {
            // �ı������߼�
            Element paragraphElement = doc.getParagraphElement(offset);

            try {
                int start = paragraphElement.getStartOffset();
                int end = paragraphElement.getEndOffset();

                String text = doc.getText(start, end - start).trim();
                String projectPath = System.getProperty("user.dir");
                String savePath = projectPath + "/" + "src/chatView/Sourse/"+text;
                File file = new File(savePath);

                if (file.exists()) {
                    Desktop.getDesktop().open(file);
                } else {
                    System.out.println("�ļ�������");
                }
            } catch (BadLocationException | IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    private void refreshlist()
    {
        try {
            message.getOs().writeInt(007);
            message.getOs().flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void list1ValueChanged(ListSelectionEvent e) {
        // TODO add your code here
        if (!e.getValueIsAdjusting()) {
            JList<String> source = (JList<String>) e.getSource();
            String selectedItem = source.getSelectedValue();
            if(selectedItem!=null)
                textField2.setText(selectedItem);
        }
    }

    private void tonghuaMouseClicked(MouseEvent e) {
        // TODO add your code here
        if(textField2.getText().isEmpty())
        {
            JOptionPane.showMessageDialog(this,"ͨ��������Ϊ��");
            refreshlist();
            return;
        }
        if(textField2.getText().equals("All user"))
        {
            JOptionPane.showMessageDialog(this,"��ѡ��ָ���û�����ͨ��!");
            refreshlist();
            return;
        }
        try {
            message.getOs().writeInt(8);
            message.getOs().writeObject(textField2.getText());
            message.getOs().flush();
            deng=new tonghua_wait();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }


    public class Message_L implements Runnable {
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
                        refreshlist();

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
                        refreshlist();

                    }
                    else if(cmd==0051){
                        String mes = (String) message.getIs().readObject();
                        System.out.println(mes);
                        String filename=(String) message.getIs().readObject();


                        // ��ȡ��ǰ��Ŀ�ĸ�·��
                        String projectPath = System.getProperty("user.dir");
                        String savePath = projectPath + "/" + "src/chatView/Sourse/"+filename;
                        FileOutputStream fos = new FileOutputStream(savePath); // �� "path/to/save/" �滻Ϊ����Ҫ�����ļ���ʵ��·��
                        System.out.println(savePath);

                        // �����ļ����ݲ�д���ļ�
                        byte[] buffer = new byte[4096];
                        int bytesRead;
                        while ((bytesRead = message.getIs().read(buffer)) != -1) {
                            fos.write(buffer, 0, bytesRead);
                            fos.flush();
                            if (new String(buffer, 0, bytesRead).equals("STOP")) {
                                break;
                            }
                        }
                        fos.close();

                        StyledDocument doc = textPane1.getStyledDocument();

                        textPane1.setCaretPosition(doc.getLength()); // ������λ���ƶ������
                        doc.insertString(doc.getLength(), "\n", null);
                        doc.insertString(doc.getLength(), "\n", null);
                        doc.insertString(doc.getLength(), mes, null);
                        refreshlist();
                    }
                    else if(cmd==0071){
                        String[] result=(String[]) message.getIs().readObject();
                        DefaultListModel<String> listModel = new DefaultListModel<>();
                        listModel.addElement("All user");
                        for (String element : result) {
                            listModel.addElement(element);
                        }
                        list1.setModel(listModel);
                    }
                    else if(cmd==81)
                    {
                        accept=new tonghua_accept(message);
                        accept.setVisible(true);
                    }
                    else if(cmd==91) {
                        JOptionPane.showMessageDialog(null,"�Է��ܾ������ͨ����");
                        deng.dispose();
                    }
                    else if(cmd==92) {
                        String ip=(String) message.getIs().readObject();
                        String text=(String) message.getIs().readObject();
                        deng.dispose();
                        new tonghua_clinet(ip,text);
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
        textField2 = new JTextField();
        button3 = new JButton();
        button4 = new JButton();
        scrollPane1 = new JScrollPane();
        list1 = new JList();
        label1 = new JLabel();

        //======== this ========
        setPreferredSize(new Dimension(1680, 1030));
        setLayout(null);

        //======== scrollPane3 ========
        {

            //---- textPane1 ----
            textPane1.setFont(new Font("\u5e7c\u5706", Font.PLAIN, 13));
            textPane1.setEditable(false);
            textPane1.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    textPane1MouseClicked(e);
                }
            });
            scrollPane3.setViewportView(textPane1);
        }
        add(scrollPane3);
        scrollPane3.setBounds(285, 30, 1090, 775);
        add(textField1);
        textField1.setBounds(520, 895, 460, 60);

        //---- button1 ----
        button1.setText("\u6d88\u606f");
        button1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                messageMouseClicked();
            }
        });
        add(button1);
        button1.setBounds(1010, 900, 118, 55);

        //---- button2 ----
        button2.setText("\u56fe\u7247");
        button2.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                imageMouseClicked();
            }
        });
        add(button2);
        button2.setBounds(1150, 900, 115, 55);

        //---- textField2 ----
        textField2.setEditable(false);
        add(textField2);
        textField2.setBounds(520, 835, 145, 45);

        //---- button3 ----
        button3.setText("\u6587\u4ef6");
        button3.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                fileMouseClicked();
            }
        });
        add(button3);
        button3.setBounds(1285, 905, 110, 50);

        //---- button4 ----
        button4.setText("\u901a\u8bdd");
        button4.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                tonghuaMouseClicked(e);
            }
        });
        add(button4);
        button4.setBounds(680, 835, 95, 45);

        //======== scrollPane1 ========
        {

            //---- list1 ----
            list1.setFont(new Font("\u5e7c\u5706", Font.PLAIN, 20));
            list1.addListSelectionListener(e -> list1ValueChanged(e));
            scrollPane1.setViewportView(list1);
        }
        add(scrollPane1);
        scrollPane1.setBounds(80, 75, 160, 720);

        //---- label1 ----
        label1.setText("\u5728\u7ebf\u4eba\u5458");
        label1.setFont(new Font("\u5e7c\u5706", Font.BOLD, 23));
        add(label1);
        label1.setBounds(95, 30, 115, 32);

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
    private JTextField textField2;
    private JButton button3;
    private JButton button4;
    private JScrollPane scrollPane1;
    private JList list1;
    private JLabel label1;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on

}
