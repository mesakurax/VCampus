package utils;

import javax.swing.*;
import java.awt.*;


public class dimensionUtil {
    public static Rectangle getBounds(){
        Dimension screenSize =Toolkit.getDefaultToolkit().getScreenSize();
        //��֤�����治�Ḳ�ǵ�����Ļ��������
        Insets screenInsets=Toolkit.getDefaultToolkit().getScreenInsets(new JFrame().getGraphicsConfiguration());
        Rectangle rectangle=new Rectangle(screenInsets.left,screenInsets.top,
                screenSize.width-screenInsets.left-screenInsets.right,
                screenSize.height-screenInsets.top-screenInsets.bottom);
        return rectangle;
    }
}
