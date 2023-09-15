package module;

import entity.*;
import entityModel.CourseModel;
import entityModel.CrsPickRecordModel;

import java.sql.ResultSet;
import java.util.Date;
import java.util.Vector;

public class CourseSystem {
    private CourseModel courseModel;

    private CrsPickRecordModel crsPickRecordModel;

    public CourseSystem(){
        this.courseModel=new CourseModel();
        this.crsPickRecordModel=new CrsPickRecordModel();
    }

    //�û�����ѡ������
    public boolean Scoring(CrsPickRecord crsPickRecord) {
        try {
            crsPickRecordModel.modifyPickRecord(crsPickRecord);
            return true;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }
    }

    //�����û���Ϣ��ʾ���û��Ŀγ�ѡ���¼��������һ�������γ���Ϣ�ı�����
    public CourseTable ChooseDisplay(User userInfo) {
        try {
            CrsPickRecord crsPickRecord = new CrsPickRecord(-1, -1, " ", userInfo.getId(), " ", " ","", 0,0,0,-1);
            ResultSet rs = (ResultSet) crsPickRecordModel.queryPickRecord(crsPickRecord, 5);
            Vector<CrsPickRecord> cprVector = new Vector<CrsPickRecord>();
            Vector<Course> courseVector = new Vector<Course>();
            int count = 0;

            // ��� ResultSet �Ƿ�Ϊ��
            if (rs != null) {
                // �ڽ���� rs ��ѭ��������ʹ�� next() �������η���ÿһ�е�����
                do {
                    CrsPickRecord temp = new CrsPickRecord(rs.getInt("crsPickId"), rs.getInt("crsId"), rs.getString("crsName"),
                            rs.getString("stuId"), rs.getString("stuName"), rs.getString("teacherId"),rs.getString("teacherName"),
                            rs.getDouble("v1"),rs.getDouble("v2"),rs.getDouble("v3"), rs.getDouble("crsPickScore"));
                    cprVector.add(temp);
                    count++;
                } while (rs.next());

                // ��ӡÿ��ѡ�μ�¼��ÿ���γ�
                for (int i = 0; i < count; i++) {
                    courseVector.add(CrsDisplay_P2(cprVector, i)); // ���ÿ��ѡ�μ�¼�Ŀγ�����
                }
            CourseTable courseTable = new CourseTable(courseVector);
            System.out.println(courseTable);
            return courseTable;
        }
            System.out.println("ResultSet is empty");
            return null; // ���߸�����Ҫ�����ʵ���ֵ
        } catch (Exception e) {
            System.out.println("Database exception");
            e.printStackTrace();
            return null;
        }
    }

    //���ݸ����Ŀγ�ѡ���¼��������������ȡ��Ӧ�γ̵���ϸ��Ϣ��������һ���γ̶���
    public Course CrsDisplay_P2(Vector<CrsPickRecord> cpr, int count){
        try {
            Course course=new Course(cpr.get(count).getCrsId(),"", "", "", "", "", "-1", " ", -1,-1);
            ResultSet rs1= (ResultSet)courseModel.queryCourse(course,2);
            Course temp = new Course(rs1.getInt("crsId"), rs1.getString("crsName"), rs1.getString("crsTime"), rs1.getString("crsRoom"),
                    rs1.getString("crsDate"),rs1.getString("crsMajor"),rs1.getString("teacherId"),rs1.getString("teacherName"),
                    rs1.getInt("crsSize"),rs1.getInt("crsCSize"));
            return temp;

        }catch (Exception e)
        {
            System.out.println("Database exception");
            e.printStackTrace();
            return null;
        }
    }

    //��ʾ���п�ѡ��Ŀγ̣�������һ�������γ���Ϣ�ı�����
    public  CourseTable CourseDis(){
        try {
            Course course=new Course(-1,"", "", "", "", "", "-1", " ", -1,-1);
            ResultSet rs= (ResultSet)courseModel.queryCourse(course,1);
            Vector<Course> courseVector=new Vector<Course>();
            do{
                Course temp = new Course(rs.getInt("crsId"), rs.getString("crsName"), rs.getString("crsTime"), rs.getString("crsRoom"),
                        rs.getString("crsDate"),rs.getString("crsMajor"),rs.getString("teacherId"),rs.getString("teacherName"),
                        rs.getInt("crsSize"),rs.getInt("crsCSize"));
                courseVector.add(temp);
            }
            while (rs.next());
            CourseTable courseTable=new CourseTable(courseVector);
            System.out.println(courseTable);
            return courseTable;
        }catch (Exception e)
        {
            System.out.println("Database exception");
            e.printStackTrace();
            return null;
        }

    }

    //���ѧ���Ƿ�ѡ�����ض��γ̣����ݸ�����ѧ���Ϳγ���Ϣ�ж��Ƿ�ѡ�񣬲����ز���ֵ��
    public boolean IfChoose(User userinfo,Course course){

        try {
            CrsPickRecord crsPickRecord=new CrsPickRecord(-1,course.getCrsId(),course.getCrsName(),userinfo.getId()," ",course.getTeacherId(), course.getTeacherName(), 0,0,0,-1);
            ResultSet rs= (ResultSet)crsPickRecordModel.queryPickRecord(crsPickRecord,7);
            if(rs!=null) {
                System.out.println("The student has selected the course");
                return true;
            }
            else
            {
                System.out.println("The student does not select the course");
                return false;
            }


        }catch (Exception e)
        {
            System.out.println("Database exception");
            e.printStackTrace();
            return false;
        }

    }


//����ѧ���Ϳγ���Ϣ�����ݿ������ѡ�μ�¼�������ز����Ƿ�ɹ��Ĳ���ֵ
    public boolean CprAdd(User userinfo,Course course){
        try {
            CrsPickRecord crsPickRecord=new CrsPickRecord(-1,course.getCrsId(),course.getCrsName(),userinfo.getId(),userinfo.getName(),course.getTeacherId(), course.getTeacherName(), 0,0,0,-1);
            crsPickRecordModel.addcrsPickRecord(crsPickRecord);
            return true;


        }catch (Exception e)
        {
            System.out.println("Database exception");
            e.printStackTrace();
            return false;
        }
    }

//����ѧ���Ϳγ���Ϣ�����ݿ���ɾ��ѡ�μ�¼�������ز����Ƿ�ɹ��Ĳ���ֵ��
    public boolean CprDelete(User userinfo,Course course){
        try {
            String date=new Date().toString();
            System.out.println(date);
            CrsPickRecord crsPickRecord=new CrsPickRecord(-1,course.getCrsId(),course.getCrsName(),userinfo.getId(),userinfo.getName(),course.getTeacherName(), course.getTeacherName(),0,0,0, -1);
            ResultSet rs= (ResultSet)crsPickRecordModel.queryPickRecord(crsPickRecord,7);
            if (rs!=null) {
                CrsPickRecord temp = new CrsPickRecord(rs.getInt("crsPickId"), rs.getInt("crsId"), rs.getString("crsName"), rs.getString("stuId"),
                        rs.getString("stuName"), rs.getString("teacherId"), rs.getString("teacherName"),
                        rs.getDouble("v1"),rs.getDouble("v2"),rs.getDouble("v3"), rs.getDouble("crsPickScore"));

                crsPickRecordModel.deletePickRecord(temp);
                return true;
            } else {
                // ��������Ϊ�յ����
                System.out.println("free");
                return false;
            }


        }catch (Exception e)
        {
            System.out.println("Database exception");
            e.printStackTrace();
            return false;
        }

    }

    //���ݿγ�ѡ���¼�����ݿ���ɾ��ѡ�μ�¼�������ز����Ƿ�ɹ��Ĳ���ֵ��
    public boolean CprDelete_P2(CrsPickRecord crsPickRecord){
        try {
            crsPickRecordModel.deletePickRecord(crsPickRecord);
            return true;
        }catch (Exception e)
        {
            System.out.println("Database exception");
            e.printStackTrace();
            return false;
        }

    }

//�����ض����������γ̣������ذ������������Ŀγ���Ϣ�ı�����
public CourseTable CourseSearch(Course course, int c) {
    try {
        Vector<Course> courseVector = new Vector<>();
        ResultSet rs = (ResultSet) courseModel.queryCourse(course, c);
        if (rs != null) {
            do{
                Course temp = new Course(rs.getInt("crsId"), rs.getString("crsName"), rs.getString("crsTime"), rs.getString("crsRoom"),
                        rs.getString("crsDate"), rs.getString("crsMajor"), rs.getString("teacherId"), rs.getString("teacherName"),
                        rs.getInt("crsSize"), rs.getInt("crsCSize"));
                courseVector.add(temp);
            }
            while (rs.next());
            if (!courseVector.isEmpty()) {
                CourseTable courseTable = new CourseTable(courseVector);
                System.out.println(courseTable);
                return courseTable;
            }
        }
        return null;
    } catch (Exception e) {
        System.out.println("Database exception");
        e.printStackTrace();
        return null;
    }
}

//���ݿ���ɾ���γ̣������ز����Ƿ�ɹ��Ĳ���ֵ
    public boolean CourseDelete(Course course){
        try {
            courseModel.deleteCourse(course);
            return true;

        }catch (Exception e)
        {
            System.out.println("Database exception");
            e.printStackTrace();
            return false;
        }

    }

//�������пγ�ѡ���¼��������һ�������γ�ѡ���¼������
    public  RecordTable CprDis(){
        try {
            CrsPickRecord crsPickRecord=new CrsPickRecord(-1,-1," ","-1"," "," ", "",0,0,0,-1);
            ResultSet rs= (ResultSet)crsPickRecordModel.queryPickRecord(crsPickRecord,1);
            Vector<CrsPickRecord> cprVector=new Vector<CrsPickRecord>();
            do
             {
                CrsPickRecord temp = new CrsPickRecord(rs.getInt("crsPickId"),rs.getInt("crsId"),rs.getString("crsName"),rs.getString("stuId"),
                        rs.getString("stuName"), rs.getString("teacherId"), rs.getString("teacherName"),
                        rs.getDouble("v1"),rs.getDouble("v2"),rs.getDouble("v3"), rs.getDouble("crsPickScore"));
                cprVector.add(temp);
            }
            while (rs.next());
            RecordTable recordTable=new RecordTable(cprVector);
            System.out.println(recordTable);
            return recordTable;
        }catch (Exception e)
        {
            System.out.println("Database exception");
            e.printStackTrace();
            return null;
        }

    }

//�����ݿ�����ӿγ̣������ز����Ƿ�ɹ��Ĳ���ֵ
    public boolean CourseAdd(Course course){
        return courseModel.addCourse(course);
    }

    //�޸����ݿ��еĿγ���Ϣ�������ز����Ƿ�ɹ��Ĳ���ֵ
    public boolean CourseModify(Course course){
            return courseModel.modifyCourse(course);
    }

    //�����ض����������γ�ѡ���¼�������ط��������Ŀγ�ѡ���¼����
    public RecordTable CprSearch(CrsPickRecord crsPickRecord, int c) {
        try {
            ResultSet rs = (ResultSet) crsPickRecordModel.queryPickRecord(crsPickRecord, c);
            Vector<CrsPickRecord> cprVector = new Vector<CrsPickRecord>();
            if (rs != null) {
                do
                {
                    CrsPickRecord temp = new CrsPickRecord(rs.getInt("crsPickId"), rs.getInt("crsId"), rs.getString("crsName"), rs.getString("stuId"),
                            rs.getString("stuName"), rs.getString("teacherId"),rs.getString("teacherName"),
                            rs.getDouble("v1"),rs.getDouble("v2"),rs.getDouble("v3"), rs.getDouble("crsPickScore"));
                    cprVector.add(temp);
                }
                while (rs.next());
                if (!cprVector.isEmpty()) {
                    RecordTable recordTable = new RecordTable(cprVector);
                    System.out.println(recordTable);
                    return recordTable;
                }
            }

            return null;
        } catch (Exception e) {
            System.out.println("Database exception");
            e.printStackTrace();
            return null;
        }
    }
}