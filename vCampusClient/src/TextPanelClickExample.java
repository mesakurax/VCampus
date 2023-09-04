import javax.swing.*;
import javax.swing.event.MouseInputAdapter;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.MouseEvent;

public class TextPanelClickExample extends JFrame {
    private JTextPane textPane;

    public TextPanelClickExample() {
        setTitle("Text Panel Click Example");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        textPane = new JTextPane();
        textPane.setEditable(false);
        add(new JScrollPane(textPane), BorderLayout.CENTER);

        // ���������¼�������
        textPane.addMouseListener(new MouseInputAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int offset = textPane.viewToModel(e.getPoint()); // ��ȡ���λ�õ�ƫ����
                Document document = textPane.getDocument();
                try {
                    int startOffset = Utilities.getRowStart(textPane, offset); // ��ȡ�����е���ʼƫ����
                    int endOffset = Utilities.getRowEnd(textPane, offset); // ��ȡ�����еĽ���ƫ����
                    String lineText = document.getText(startOffset, endOffset - startOffset); // ��ȡ���λ�������е��ı�
                    int mp4Index = lineText.indexOf(".mp4"); // ���� .mp4 ��λ��
                    if (mp4Index != -1) {
                        lineText = lineText.substring(0, mp4Index + 4); // ��ȡ .mp4 ֮ǰ�Ĳ��֣����� .mp4��
                        System.out.println("Clicked line text: " + lineText);
                    }
                } catch (BadLocationException ex) {
                    ex.printStackTrace();
                }
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            TextPanelClickExample frame = new TextPanelClickExample();
            frame.setVisible(true);

            // ���ʾ���ı�
            frame.textPane.setText("Video1.mp4\nVideo2.mov\nImage1.jpg\nVideo3.mp4yuihbhj");
        });
    }
}