package module;

import entity.StudentRoll;
import entityModel.StudentRollModel;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

/**
 * ѧ��ѧ����ϢController
 * 
 *
 */
public class StudentRollController {
	/**
	 * ѧ��ѧ����ϢModel
	 */
	private StudentRollModel model;
	
	public StudentRollController() {
		this.model = new StudentRollModel();
	}
	
	/**
	 * ����ѧ����Ϣ��Ӧ����
	 * �����ṩ�Ĳ�ͬkey��ѧ�ţ����������ؽ��
	 * 
	 * @param info ���ҵ�key
	 * @return ����ѯѧ����ϸ��Ϣ
	 */
	public StudentRoll query_ID(StudentRoll info) {
		try {
			ResultSet rs = (ResultSet)model.Search(info,1);
			if (rs.next()) {
				System.out.println("JIANCHA SEX"+rs.getString("stuSex"));
				return new StudentRoll(rs.getString("stuId"), rs.getString("stuName"), rs.getString("stuSex"), rs.getString("stuGrade"),
					rs.getString("stuPlace"), rs.getString("stuDepart"), rs.getString("stuProf"));
			}
			
			
			return null;
			
		} catch (SQLException e) {
			System.out.println("Database exception");
			e.printStackTrace();

			return null;
		} 
	}

	public StudentRoll query_Name(StudentRoll info) {
		try {
			ResultSet rs = (ResultSet)model.Search(info,2);

			if (rs.next()) {System.out.println("JIANCHA SEX"+rs.getString("stuSex"));
				return new StudentRoll(rs.getString("stuId"), rs.getString("stuName"), rs.getString("stuSex"), rs.getString("stuGrade"),
						rs.getString("stuPlace"), rs.getString("stuDepart"), rs.getString("stuProf"));
			}


			return null;

		} catch (SQLException e) {
			System.out.println("Database exception");
			e.printStackTrace();

			return null;
		}
	}

	/**
	 * ��ѯ����ѧ����Ϣ��Ӧ����
	 * 
	 * @return ����ѧ����ϸ��Ϣ
	 */
	public Vector<StudentRoll> queryAll() {
		try {
			ResultSet rs = (ResultSet)model.Search(null,3);
			Vector<StudentRoll> v = new Vector<StudentRoll>();
			
			while (rs.next()) {
				StudentRoll temp =new StudentRoll(rs.getString("stuId"), rs.getString("stuName"), rs.getString("stuSex"), rs.getString("stuGrade"),
						rs.getString("stuPlace"), rs.getString("stuDepart"), rs.getString("stuProf"));
				v.add(temp);
			}
			
			return ((Vector<StudentRoll>) v);
			
		} catch (SQLException e) {
			System.out.println("Database exception");
			e.printStackTrace();

			return null;
		} 
	}

	/**
	 * ���ѧ����Ϣ��Ӧ����
	 * 
	 * @param info Ҫ��ӵ�ѧ��
	 * @return �Ƿ���ӳɹ�
	 */
	public boolean addInfo(StudentRoll info) {
		return model.Insert(info);
	}
	
	/**
	 * �޸�ѧ����Ϣ��Ӧ����
	 * 
	 * @param info Ҫ�޸ĵ�ѧ��
	 * @return �Ƿ��޸ĳɹ�
	 */
	public boolean modifyInfo(StudentRoll info) {
		return model.Modify(info);
	}
	
	/**
	 * ɾ��ѧ����Ϣ��Ӧ����
	 * 
	 * @param info Ҫɾ����ѧ��
	 * @return �Ƿ�ɾ���ɹ�
	 */
	public boolean deleteInfo(StudentRoll info) {
		return model.Delete(info);
	}
	
}
