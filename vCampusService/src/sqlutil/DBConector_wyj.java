package sqlutil;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

public class DBConector_wyj {
    public static String driver;
    private static String url;
    private static String username;
    private static String password;

    //��̬����
    static
    {
        //��ȡ���ݿ������Ϣ�ļ�

        InputStream is = DBConector_wyj.class.getClassLoader().getResourceAsStream("dbbasicinformation.properties");


///����Properties���͵Ķ���
        Properties p=new Properties();
//�������ļ�
        try {
            p.load(is);
            url=p.getProperty("url");
            username = p.getProperty("username");
            password=p.getProperty("password");
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        String driver = p.getProperty("driver");

//����mysql����
        try {
            Class.forName(driver);
            System.out.println("�������سɹ�");
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    //������Ӷ���ķ���
    public static Connection getConnection()
    {
        try {
            Connection con=DriverManager.getConnection(url, username, password);
            System.out.println("���ݿ����ӳɹ�");
            return con;
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        System.out.println("���ݿ�����ʧ��");
        return null;
    }

    //�ͷ���Դ
    public static void close(Connection conn, PreparedStatement sta, ResultSet res)
    {
        try {
            if(res!=null)
            {
                res.close();
                res=null;
            }

            if(sta!=null)
            {
                sta.close();
                sta=null;
            }
            if(conn!=null)
            {
                conn.close();
                conn=null;
            }
        }catch(SQLException e)
        {
            e.printStackTrace();
        }
    }

    public static void close(Connection conn,PreparedStatement sta)
    {
        try {


            if(sta!=null)
            {
                sta.close();
                sta=null;
            }
            if(conn!=null)
            {
                conn.close();
                conn=null;
            }
        }catch(SQLException e)
        {
            e.printStackTrace();
        }
    }
}

