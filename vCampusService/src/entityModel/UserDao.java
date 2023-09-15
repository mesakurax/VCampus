package entityModel;

//import common.User;

import entity.User;

import java.sql.SQLException;
import java.util.HashMap;

public interface UserDao {
    User login(User user) throws SQLException;
    boolean insert(User user) throws SQLException;
    boolean delete(String uid) throws SQLException;
    //�޸�ѧ����Ϣ
    boolean update(User usertemp) throws SQLException;
    boolean updateB(User usertemp) throws SQLException;
    boolean updateC(String idtemp,double balancetemp) throws SQLException;

    //��ѯѧ����Ϣ
    User select(String uid) throws SQLException;


    HashMap<Integer, User> searchAll() throws SQLException;
}
