package entityModel;

import entity.User;
import sqlutil.DBConector_wyj;

import java.sql.*;
import java.util.HashMap;

public class UserDao_Imp implements UserDao {
    //Mysql��ѯ���ģ��
    private static final String SQL_USER_LOGIN = "SELECT * FROM test.user  WHERE uid=? AND upassword=?";
    //Mysql������ģ��
    private static final String SQL_USER_INSERT = "INSERT INTO test.user VALUES(?,?,?,?,?,?,?,?)";
    //Mysql�޸����ģ��
    private static final String SQL_USER_UPDATE="update test.user set uname=?,upassword=?,occupation=?,academy=?,sex=?,age=?  where uid=?";
    private static final String SQL_USER_UPDATEB="update test.user set upassword=? where uid=?";
    private static final String SQL_USER_UPDATEC="update test.user set balance=? where uid=?";
    private static final String SQL_USER_SEARCHALL="select * from test.user";




    //�û���¼
    @Override

    public User login(User user) throws SQLException {
        // TODO Auto-generated method stub
        //�������ݿ⣬�������Ӷ���conn
        Connection conn = DBConector_wyj.getConnection();
        //����Ԥ���뻷��
        PreparedStatement prepare = conn.prepareStatement(SQL_USER_LOGIN);
        //����sql����еĲ���
        prepare.setString(1,user.getId());
        prepare.setString(2,user.getPassword());
        System.out.println("id: "+user.getId()+"p: "+user.getPassword());
        //ִ�����
        ResultSet result= prepare.executeQuery();

        //�洢���
        User u=new User();
        while(result.next())
        {
            //System.out.println("��½�ɹ�");
            u.setId(result.getString("uid"));
            u.setName(result.getString("uname"));
            u.setPassword(result.getString("upassword"));
            u.setOccupation(result.getString("occupation"));
            u.setAcademy((result.getString("academy")));
            u.setBalance(result.getDouble("balance"));
            u.setSex(result.getString("sex"));
            u.setAge(result.getInt("age"));
            //������Դ
            prepare.close();
            conn.close();
            result.close();
            return u;
        }
            //System.out.println("��½ʧ��");
        //������Դ
        prepare.close();
        conn.close();
        result.close();
            return null;


    }


    @Override
    public boolean insert(User user)  {
        // TODO Auto-generated method stub
        //�������ݿ⣬�������Ӷ���conn
        Connection conn = DBConector_wyj.getConnection();


        try {
            //����Ԥ���뻷��
            PreparedStatement prepare = conn.prepareStatement(SQL_USER_INSERT);
            //����sql����еĲ���
            prepare.setString(1, user.getId());
            prepare.setString(2, user.getName());
            prepare.setString(3, user.getPassword());
            prepare.setString(4, user.getOccupation());
            prepare.setString(5, user.getAcademy());
            prepare.setString(6, user.getSex());
            prepare.setString(7, String.valueOf(user.getBalance()));
            prepare.setString(8, String.valueOf(user.getAge()));

            int result = prepare.executeUpdate();
            if (result > 0) {
                System.out.println("��" + result + "�б����");
                //������Դ
                prepare.close();
                conn.close();
                return true;
            } else {
                System.out.println("���ʧ��");
                //������Դ
                prepare.close();
                conn.close();
                return false;
            }
        }
        catch (SQLIntegrityConstraintViolationException e)
        {
            return false;
        } catch (SQLException e1) {
            try {
                throw new RuntimeException(e1);
            }
            catch( RuntimeException e2)
            {
                return false;
            }
        }


    }

    @Override
    public boolean delete(String idtemp) throws SQLException {
        // TODO Auto-generated method stub
        //�������ݿ⣬�������Ӷ���conn
        Connection conn = DBConector_wyj.getConnection();
        System.out.println("�ɹ����ӵ����ݿ⣡");
        System.out.println("ɾ���û�ID: "+idtemp);
        //ɾ�����ݵĴ���
        String sql = "delete from test.user where uid = "+idtemp;
        Statement pst= conn.createStatement();
        int rs = pst.executeUpdate(sql);//�������ݶ���
        if(rs>0)
        {
            System.out.println("��"+rs+"�б�ɾ��");
            //������Դ
            pst.close();
            conn.close();

            return true;
        }
        else
        {
            System.out.println("ɾ��ʧ��");
            //������Դ
            pst.close();
            conn.close();
            return false;
        }

    }


    @Override
    public boolean update(User usertemp) throws SQLException {
        // TODO Auto-generated method stub
        //�������ݿ⣬�������Ӷ���conn
        Connection conn = DBConector_wyj.getConnection();
        //����Ԥ���뻷��
        PreparedStatement prepare = conn.prepareStatement(SQL_USER_UPDATE);
        prepare.setString(1, usertemp.getName());
        prepare.setString(2, usertemp.getPassword());
        prepare.setString(3, usertemp.getOccupation());
        prepare.setString(4, usertemp.getAcademy());
        prepare.setString(5, usertemp.getSex());
        prepare.setString(6, String.valueOf(usertemp.getAge()));
        prepare.setString(7, usertemp.getId());
        int result=prepare.executeUpdate();
        if(result>0)
        {
            System.out.println("��"+result+"�б��޸�");
            //������Դ
            prepare.close();
            conn.close();
            return true;
        }
        else
        {
            System.out.println("�޸�ʧ��");
            //������Դ
            prepare.close();
            conn.close();
            return false;
        }
    }
    @Override
    public boolean updateB(User usertemp) throws SQLException {
        // TODO Auto-generated method stub
        //�������ݿ⣬�������Ӷ���conn
        Connection conn = DBConector_wyj.getConnection();
        //����Ԥ���뻷��
        PreparedStatement prepare = conn.prepareStatement(SQL_USER_UPDATEB);
        prepare.setString(1, usertemp.getPassword());
        prepare.setString(2, usertemp.getId());

        int result=prepare.executeUpdate();
        if(result>0)
        {
            System.out.println("��"+result+"�б��޸�");
            //������Դ
            prepare.close();
            conn.close();
            return true;
        }
        else
        {
            System.out.println("�޸�ʧ��");
            //������Դ
            prepare.close();
            conn.close();
            return false;
        }
    }
    @Override
    public boolean updateC(String idtemp,double balancetemp) throws SQLException {
        // TODO Auto-generated method stub
        //�������ݿ⣬�������Ӷ���conn
        Connection conn = DBConector_wyj.getConnection();
        //����Ԥ���뻷��
        try {
            PreparedStatement prepare = conn.prepareStatement(SQL_USER_UPDATEC);
            prepare.setString(1, String.valueOf(balancetemp));
            prepare.setString(2,idtemp);

            int result = prepare.executeUpdate();
            if (result > 0) {
                System.out.println("��" + result + "�б��޸�");
                //������Դ
                prepare.close();
                conn.close();
                return true;
            } else {
                System.out.println("�޸�ʧ��");
                //������Դ
                prepare.close();
                conn.close();
                return false;
            }
        }
        catch (SQLIntegrityConstraintViolationException e)
        {
            return false;
        } catch (SQLException e1) {
            try {
                throw new RuntimeException(e1);
            }
            catch( RuntimeException e2)
            {
                return false;
            }
        }

    }

    @Override
    public User select(String idtemp) throws SQLException {
        // TODO Auto-generated method stub
        Connection conn = DBConector_wyj.getConnection();
        System.out.println("�ɹ����ӵ����ݿ⣡");


        String  sql = "select * from test.user where uid="+idtemp+" ";    //Ҫִ�е�SQL
        Statement pst= conn.createStatement();
        ResultSet rs = pst.executeQuery(sql);//�������ݶ���
        System.out.println("���ڲ�ѯ�� "+ idtemp);
        User u=new User();
        while(rs .next())
        {

            System.out.println("�鵽�ˣ�"+ idtemp);
            u.setId(rs .getString("uid"));
            u.setName(rs .getString("uname"));
            u.setPassword(rs .getString("upassword"));
            u.setOccupation(rs .getString("occupation"));
            u.setAcademy((rs .getString("academy")));
            u.setBalance(rs .getDouble("balance"));
            u.setSex(rs .getString("sex"));
            u.setAge(rs .getInt("age"));
            //������Դ
            pst.close();
            conn.close();
            rs.close();
            return u;
        }

        System.out.println("û�鵽�ˣ�"+ idtemp);
        //������Դ
        pst.close();
        conn.close();
        rs.close();
        return null;

    }
    @Override
    public HashMap<Integer, User> searchAll() throws SQLException {
        // TODO Auto-generated method stub
        Connection conn = DBConector_wyj.getConnection();
        //����Ԥ���뻷��
        PreparedStatement prepare = conn.prepareStatement(SQL_USER_SEARCHALL);
        //ִ�����
        ResultSet result= prepare.executeQuery();
        //�½�hashmap
        HashMap<Integer, User> users=new HashMap<Integer,User>();
        while(result.next())
        {
            User u=new User();
            u.setId(result.getString("uid"));
            u.setName(result.getString("uname"));
            u.setPassword(result.getString("upassword"));
            u.setOccupation(result.getString("occupation"));
            u.setAcademy((result.getString("academy")));
            u.setBalance(result.getDouble("balance"));
            u.setSex(result.getString("sex"));
            u.setAge(result.getInt("age"));

            users.put(users.size()+1,u);
        }
        //������Դ
        prepare.close();
        conn.close();
        result.close();
        return users;
    }

}
