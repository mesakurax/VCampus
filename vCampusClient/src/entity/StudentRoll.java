package entity;

import java.io.Serializable;

public class StudentRoll implements Serializable {
    private static final long serialVersionUID = 154598L;
    String stuId; //ѧ��ID
    String stuName;   //ѧ������
    String sSex;    //ѧ���Ա�
    String sGrades;  //ѧ���꼶
    String sPlace;  //ѧ����ַ
    String sDepart; //ѧ������
    String sProfession; //ѧ��רҵ

    public StudentRoll(String id, String name, String gender, String sGr,
                       String sP, String department, String major) {
        stuId = id;
        stuName = name;
        sSex = gender;
        sGrades = sGr;
        sPlace = sP;
        sDepart = department;
        sProfession = major;
    }

    public StudentRoll() {
        stuId = "";
        stuName = "";
        sSex = "";
        sGrades = "";
        sPlace = "";
        sDepart = "";
        sProfession = "";
    }
    public StudentRoll(String id) {
        stuId = id;
        stuName = "";
        sSex = "";
        sGrades = "";
        sPlace = "";
        sDepart = "";
        sProfession = "";
    }

    public String getStuName() {
        return this.stuName;
    }

    public void setStuName(String sName) {
        this.stuName = sName;
    }

    public String getStuId() {
        return this.stuId;
    }

    public void setStuId(String stuId) {
        this.stuId = stuId;
    }

    public String getsGrades() {
        return this.sGrades;
    }

    public void setsGrades(String sAge) {
        this.sGrades = sAge;
    }

    public String getsSex() {
        return this.sSex;
    }

    public void setsSex(String sSex) {
        this.sSex = sSex;
    }


    public String getsPlace() {
        return this.sPlace;
    }

    public void setsPlace(String sPlace) {
        this.sPlace = sPlace;
    }

    public String getsDepart() {
        return this.sDepart;
    }

    public void setsDepart(String sDepart) {
        this.sDepart = sDepart;
    }

    public String getsProfession() {return this.sProfession;}

    public void setsProfession(String sProfession) {this.sProfession = sProfession;}

}