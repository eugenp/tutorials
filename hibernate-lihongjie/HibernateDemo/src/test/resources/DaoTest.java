package test;

import org.vo.User;

import dao.UserDao;
import dao.impl.UserDaoImpl;

/**
 * ��ɾ�Ĳ� ������
 * @author Administrator
 *
 */
public class DaoTest {
	public static void main(String[] args) {
		UserDao dao = new UserDaoImpl();
		User user = new User();
		//����
		user.setUsername("zhangsan");
		user.setPassword("111111");
		dao.saveUser(user);
		System.out.println(user.getUsername()+" "+user.getPassword());
		System.out.println("�����ɹ���");
		//�޸�
		user.setUsername("newname");
		user.setPassword("22222");
		dao.updateUser(user);
		System.out.println(user.getUsername()+" "+user.getPassword());
		System.out.println("�޸ĳɹ���");
		//ͨ���û�����ѯ
//		User u = dao.findUserByName(user.getUsername());
//		System.out.println("��ѯ������user��passwordΪ�� "+u.getPassword());
		//ͨ���û�����ѯ2
		User uu = dao.findByName2(user.getUsername());
		System.out.println("��ѯ�������µ�user��passwordΪ�� "+uu.getPassword());
		
	}


}
