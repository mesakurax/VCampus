package chatView;
import entity.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class mytextpane {
    private String uid;
    private JTextPane textPane;

    public mytextpane(String uid, JTextPane textPane) {
        this.uid = uid;
        this.textPane = textPane;

        textPane.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int offset = textPane.viewToModel(e.getPoint());

                StyledDocument doc = textPane.getStyledDocument();
                // ��ȡ���λ�õ�Ԫ��
                Element element = doc.getCharacterElement(offset);


                if (element.getAttributes().getAttribute(StyleConstants.IconAttribute) != null) {
                    Icon icon = (Icon) element.getAttributes().getAttribute(StyleConstants.IconAttribute);

                    // ����һ����������
                    JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(textPane);

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

                        if(!text.isEmpty()) {
                            String projectPath = System.getProperty("user.dir");
                            String savePath = projectPath + "/" + "src/chatView/Sourse/" + text;
                            File file = new File(savePath);

                            if (file.exists()) {
                                Desktop.getDesktop().open(file);
                            } else {
                                System.out.println("�ļ�������");
                            }
                        }
                    } catch (BadLocationException | IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });

    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public JTextPane getTextPane() {
        return textPane;
    }

    public void setTextPane(JTextPane textPane) {
        this.textPane = textPane;
    }

    public static JTextPane copyTextPane(JTextPane sourceTextPane,String uid) {
            JTextPane targetTextPane = new JTextPane();

            // ��ȡԴ�ı���ʽ
            StyledDocument sourceDoc = sourceTextPane.getStyledDocument();
            StyledDocument targetDoc = targetTextPane.getStyledDocument();

            // ������ʽ
            int length = sourceDoc.getLength();
            AttributeSet attrs = sourceDoc.getCharacterElement(0).getAttributes();
            targetDoc.setCharacterAttributes(0, length, attrs, true);

            // �����������ɫ
            Style defaultStyle = targetDoc.getStyle(StyleContext.DEFAULT_STYLE);
            Style sourceStyle = sourceDoc.getStyle(StyleContext.DEFAULT_STYLE);
            targetDoc.setLogicalStyle(0, defaultStyle);
            targetDoc.getLogicalStyle(0).addAttributes(sourceStyle);

             StyledDocument doc = targetTextPane.getStyledDocument();
             SimpleAttributeSet centerAlignment = new SimpleAttributeSet();
              StyleConstants.setAlignment(centerAlignment, StyleConstants.ALIGN_CENTER);
             doc.setParagraphAttributes(0, doc.getLength(), centerAlignment, false);

        // ������ʽ
                 Style style = sourceTextPane.addStyle("CustomStyle", null);
              StyleConstants.setBackground(style, Color.GRAY); // ���ñ�����ɫΪ��ɫ

        // ������ʽ
         doc.setParagraphAttributes(0, doc.getLength(), style, false);

        try {
            // ���û�ӭ�����ʽ
            SimpleAttributeSet welcomeStyle = new SimpleAttributeSet();
            StyleConstants.setFontFamily(welcomeStyle, "��Բ");  // ��������Ϊ��Բ
            StyleConstants.setFontSize(welcomeStyle, 24);  // ���������СΪ20
            doc.insertString(doc.getLength(), "��ʼ��"+uid+"����ɣ���\n", welcomeStyle);

            // ���ú����е���ʽ
            SimpleAttributeSet textStyle = new SimpleAttributeSet();
            StyleConstants.setFontFamily(textStyle, "��Բ");  // ��������Ϊ��Բ
            StyleConstants.setFontSize(textStyle, 18);  // ���������СΪ14
            doc.setParagraphAttributes(doc.getLength(), 1, textStyle, false);
            SimpleAttributeSet topAlignment = new SimpleAttributeSet();
            StyleConstants.setAlignment(topAlignment, StyleConstants.ALIGN_LEFT);
            doc.setParagraphAttributes(doc.getLength(), 1, topAlignment, false);
        } catch (BadLocationException e) {
            throw new RuntimeException(e);
        }

            return targetTextPane;
        }

}
