package entity;
import java.io.Serializable;

public class Course implements Serializable{

    public Integer getCrsId() {
        return crsId;
    }

    public void setCrsId(Integer crsId) {
        this.crsId = crsId;
    }

    private Integer crsId;//�γ̱��
    private String crsName;//�γ�����

    private String crsTime;//�γ�ʱ��

    private String crsRoom;//�γ̵ص�

    private String crsDate;//�γ�����

    private String major;//����ѧԺ

    private String teacherId;//������ʦID

    private String teacherName;//������ʦ����

    private Integer crsSize;//�γ�����

    private Integer crsCSize;//ʣ�������


    public String getCrsName() {
        return crsName;
    }

    public void setCrsName(String crsName) {
        this.crsName = crsName;
    }

    public String getCrsTime() {
        return crsTime;
    }

    public void setCrsTime(String crsTime) {
        this.crsTime = crsTime;
    }

    public String getCrsRoom() {
        return crsRoom;
    }

    public void setCrsRoom(String courseRoom) {
        this.crsRoom = courseRoom;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(String teacherID) {
        this.teacherId = teacherID;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }


    public Integer getCrsSize() {
        return crsSize;
    }

    public void setCrsSize(Integer size) {
        this.crsSize = size;
    }

    public Integer getCrsCSize() {
        return crsCSize;
    }


    public void setCrsCSize(Integer crsCSize) {
        this.crsCSize = crsCSize;
    }

    public String getCrsDate() {
        return crsDate;
    }

    public void setCrsDate(String courseDate) {
        this.crsDate = courseDate;
    }

    public Course(Integer crsId, String crsName, String crsTime, String crsRoom, String crsDate, String major, String teacherId, String teacherName, Integer crsSize, Integer crsCSize) {
        setCrsName(crsName);
        setCrsTime(crsTime);
        setCrsRoom(crsRoom);
        setMajor(major);
        setTeacherId(teacherId);
        setCrsSize(crsSize);
        setCrsCSize(crsCSize);
        setCrsDate(crsDate);
        setCrsId(crsId);
        setTeacherName(teacherName);
    }
    @Override
    public String toString() {
        return "Course ID: " + crsId +
                " Course Name: " + crsName +
                " Course Time: " + crsTime +
                " Course Room: " + crsRoom +
                " Course Date: " + crsDate +
                " Major: " + major +
                " Teacher ID: " + teacherId +
                " Teacher Name: " + teacherName +
                " Course Size: " + crsSize +
                " Remaining Course Size: " + crsCSize+"\n";
    }
}

