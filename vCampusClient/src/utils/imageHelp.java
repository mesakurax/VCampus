package utils;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class imageHelp{
    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);

        // ������ť
        JButton button = new JButton("ѡ��ͼ��");

        // ��Ӱ�ť����¼��������
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // �����ļ�ѡ����
                JFileChooser fileChooser = new JFileChooser();

                // �����ļ���������ֻ��ʾͼƬ�ļ�
                FileNameExtensionFilter filter = new FileNameExtensionFilter("Image Files", "jpg", "jpeg", "png", "gif");
                fileChooser.setFileFilter(filter);

                // ��ʾ�ļ�ѡ��Ի���
                int result = fileChooser.showOpenDialog(frame);

                if (result == JFileChooser.APPROVE_OPTION) {
                    // �û�ѡ�����ļ�
                    File selectedFile = fileChooser.getSelectedFile();
                    String imagePath = selectedFile.getAbsolutePath();
                    System.out.println("ѡ���ͼ���ļ�: " + imagePath);
                    // ���������ͼ�������������ʾͼ���
                } else if (result == JFileChooser.CANCEL_OPTION) {
                    // �û�ȡ��ѡ���ļ�
                    System.out.println("ȡ��ѡ��ͼ��");
                } else if (result == JFileChooser.ERROR_OPTION) {
                    // ��������
                    System.out.println("��������");
                }
            }
        });

        // ����ť��ӵ�����
        frame.getContentPane().add(button);
        frame.setVisible(true);
    }
}