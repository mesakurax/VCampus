package mainUiView;

import entity.User;
import utils.SocketHelper;

public class LoginToMain {
    public LoginToMain(SocketHelper socketHelper, User userInfo) throws InterruptedException {
        String type = userInfo.getOccupation();
        if(type.equals("ѧ��")){
            new mainUIStu(socketHelper,userInfo).setVisible(true);
        }
        else if(type.equals("��ʦ")){
            new mainUITea(socketHelper,userInfo).setVisible(true);
        }
        else{
            new mainUIAdmin(socketHelper,userInfo).setVisible(true);
        }
    }
}
