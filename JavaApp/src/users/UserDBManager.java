package users;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import file.FileManager;

// User �ν��Ͻ��� �ؽøʿ� �����ϰ� ���� �������� ��ȸ �� �߰� ����� ����
public class UserDBManager {
	
	// �� ���� �ν��Ͻ��� �����ϴ� �ؽø����� �̸����� key, ���� �ν��Ͻ��� value�� �Ѵ�.
	private static HashMap<String, User> userDBMap = new HashMap<>();	
	
	public static void printUserDBMap() { // ������
		for (Map.Entry<String, User> entry : userDBMap.entrySet()) {
            String key = entry.getKey();
            User value = entry.getValue();
            System.out.println("Key: " + key + ", Value: " + value.toString() + "\n");
        }
	}
	// �ش��ϴ� �̸����� �����ϴ��� Ȯ�� (��ϵ� �������� Ȯ�ο�)
	public static boolean isEmailExist(String email) {
		return userDBMap.containsKey(email);
	}
	
	
	/*
	 * �̸��Ͽ� �ش��ϴ� User �ν��Ͻ� ��ȯ, isEmailExist() ����Ͽ� ��ϵ� �������� Ȯ���� ����ϴ� �� ����
	 * return : ���ڷ� ���޵� �̸��Ͽ� ��ġ�ϴ� ����
	 */
	public static User findUserByEmail(String email) throws NullPointerException{
		User userFound = userDBMap.get(email);
		if(userFound == null) { // �ش� �̸����� ���� ���
			throw new NullPointerException("��� : �������� �ʴ� �̸����Դϴ�.");
		}
		return userFound;
	}
	
	/*
	 * �����̸��� �ش��ϴ� User �ν��Ͻ� ��ȯ, isEmailExist() ����Ͽ� ��ϵ� �������� Ȯ���� ����ϴ� �� ����
	 * return : ���ڷ� ���޵� �����̸��� ��ġ�ϴ� ����
	 */
	public static User findUserByUsername(String username) throws NullPointerException{
		for(User user : userDBMap.values()) {
			if(user.getUsername().equals(username)) {
				return user;
			}
		}
		// �ش� ������ ���� ���
		throw new NullPointerException("��� : �������� �ʴ� �̸����Դϴ�.");
	}
	
	
	/*
	 *  ���� �������� ��ȿ���� �˻��ϰ� email�� key�� �Ͽ� �ؽøʿ� �߰���
	 */
	public static void addUser(User user) throws IllegalArgumentException{
		if(!User.isVaild(user)) { // ���޵� ������ü�� ��ȿ���� Ȯ��
			throw new IllegalArgumentException("���� ��ü�� ��ȿ���� �ʾ� userDBMap�� ������ �� �����ϴ�..");
		}
		userDBMap.put(user.getEmail(), user); // �̸����� key, user�� value�� ����
	}
	
	// UserDB ������ ����� ��ü ���ϵ��� �ؽøʿ� �����Ͽ� �ʱ�ȭ, ���α׷� ���۽� �� ���� ����
	public static void init() {
		String dirpath = String.format("\\users\\UserDB"); // ��� ����
		// �ش� ������ ����� ��� ������ Object�� ��ȯ�Ͽ� ArrayList<Object>�� ��ȯ 
		ArrayList<Object> objList = FileManager.readAllObjectFileInDirectory(dirpath); 
		try {
			// �� Object ���� User�� ����ȯ
			for (Object obj : objList) {			 
				if(obj instanceof User) {
					User user = (User)obj;
					addUser(user);				 
				} else {
					throw new ClassCastException("User �ν��Ͻ��� ��ȯ�� �� �����ϴ�.");
				}
			}
		} catch (ClassCastException e) {
			e.printStackTrace();			
		}
	}
	
	
	




}
