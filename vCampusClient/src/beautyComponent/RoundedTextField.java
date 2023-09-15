package beautyComponent;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.geom.RoundRectangle2D;

public class RoundedTextField extends JTextField {
    private int arc;
    private Color borderColor = Color.BLACK; // Ĭ�ϱ߿���ɫ
    private Color hoverBorderColor = new Color(0, 128, 0); // ����ɫ
    private String placeholder = ""; // ռλ���ı�
    private boolean isPlaceholderVisible = true;
    private Font customFont = new Font("����", Font.PLAIN, 15); // Ĭ������

    private void init(){
        setOpaque(false);
        setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        setFont(customFont);

        // �������¼�������
        addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                // �����ͣʱ�����ñ߿���ɫΪǳ��ɫ
                borderColor = hoverBorderColor;
                repaint();
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                // ����뿪ʱ���ָ�Ĭ�ϵı߿���ɫ
                borderColor = Color.BLACK;
                repaint();
            }
        });

        // ��ӽ��������
        addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                // �ı����ý���ʱ�����ռλ���ı��������Ϊ��ɫ
                if (isPlaceholderVisible) {
                    setText("");
                    setForeground(Color.BLACK);
                    isPlaceholderVisible = false;
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                // �ı���ʧȥ����ʱ������ı�Ϊ�գ���ʾռλ���ı���������ɫΪ��ɫ
                if (getText().isEmpty()) {
                    setText(placeholder);
                    setFont(new Font("���ķ���", Font.BOLD | Font.ITALIC, getFont().getSize()));
                    setForeground(Color.GRAY);
                    isPlaceholderVisible = true;
                }
            }
        });
    }
    public RoundedTextField() {
        this.arc = 30;
        init();
    }
    public RoundedTextField(int arc) {
        this.arc = arc;
        init();
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int width = getWidth();
        int height = getHeight();

        RoundRectangle2D roundedRectangle = new RoundRectangle2D.Float(0, 0, width - 1, height - 1, arc, arc);

        g2.setColor(getBackground());
        g2.fill(roundedRectangle);

        g2.setColor(borderColor); // ʹ�õ�ǰ�߿���ɫ
        g2.draw(roundedRectangle);

        super.paintComponent(g2);
        g2.dispose();
    }

    // ����ռλ���ı�
    public void setPlaceholder(String placeholder) {
        this.placeholder = placeholder;
        repaint();
    }

    // ��������
    public void setCustomFont(Font font) {
        customFont = font;
        setFont(font);
        repaint();
    }

    // �����ı����Բ�ǰ뾶
    public void setArc(int arc) {
        this.arc = arc;
        repaint();
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 200);

        RoundedTextField textField = new RoundedTextField(30);
        textField.setPreferredSize(new Dimension(150, 30));

        frame.getContentPane().setLayout(new FlowLayout());
        frame.getContentPane().add(textField);
        frame.add(new JButton());

        // ����ռλ���ı�
        textField.setPlaceholder("�������ı�");

        // ���������С
        textField.setCustomFont(new Font("΢���ź�", Font.PLAIN, 16));
        frame.setUndecorated(true);
        frame.setVisible(true);
    }
}
