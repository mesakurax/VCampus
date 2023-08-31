package entity;

import java.io.Serializable;

public class User implements Serializable {
    private String name;
    private String password;
    private String id;
    private String sex;
    private double balance;
    private String occupation;
    private String academy;
    private int age;
    public void setName(String nametemp)
    {
      name=nametemp;
    }

    public String getName()
    {
        return name;
    }

    public void setPassword(String passwordtemp)
    {
        password=passwordtemp;
    }
    public String getPassword()
    {
        return password;
    }
    public void setId(String idtemp)
    {
        id=idtemp;
    }
    public String getId()
    {
        return id;
    }
    public void setOccupation(String occutemp)
    {
        occupation=occutemp;
    }
    public String getOccupation()
    {
        return occupation;
    }
    public void setSex(String Sextemp)
    {
        sex=Sextemp;
    }
    public String getSex()
    {
        return sex;
    }

    public void setAcademy(String Acatemp)
    {
        academy=Acatemp;
    }
    public String getAcademy()
    {
        return academy;
    }

    public void setBalance(double Balancetemp)
    {
        balance=Balancetemp;
    }
    public double  getBalance()
    {
        return balance;
    }

public void setAge(int agetemp)
{
    age=agetemp;
}

public int getAge()
{
    return age;
}




    public User(String idtemp,String nametemp,String passwordtemp,String occutemp,String acatemp)
    {
        id=idtemp;
        name=nametemp;
        password=passwordtemp;
        occupation=occutemp;
        academy=acatemp;
    }

    public User()
    {
        id="id";
        name="name";
        password="12345";
        occupation="none";
        academy="none";
        sex="unknown";
        balance=0.0;
    }

    public void UserPrint()
    {
        System.out.println("id: "+id+" Password: "+password);
    }

}
