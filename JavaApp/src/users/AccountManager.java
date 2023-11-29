package users;

import java.io.IOException;
import java.util.regex.Pattern;

import fetcher.JsonFetcher;
import file.FileManager;

// ȸ�� ������ �ʿ��� static �޼������ ���� Ŭ����
public class AccountManager {
	/* ȸ�� ������ ��ȿ���� Ȯ�ε� ȸ�� ���� ������� ���� �ν��Ͻ��� �����ϰ� 
	 * �̸� UserDB ������ ����ȭ ���Ϸ� �����ϰ� UserDBManager �ؽøʿ��� �����ϴ� �޼���	 * 
	 * param :  ��ȿ���� Ȯ�ε� ȸ�� ���� ���
	 * return : ���������� ȸ�� ��ϵ� ��� ȸ�� �ν��Ͻ� ��ȯ
	 */
	public static User createAccount(ResistrationFormat format) throws IOException{
		User newUser;
		try {
			newUser = new User(format); // ���ο� User ��ü ����
			newUser.createUpdateUserFile(); // ���� ��ü UserDB ������ ���Ϸ� ����
			UserDBManager.addUser(newUser); // UserDBManager �ؽøʿ� ��ü �߰�
		} catch (Exception e) {
			System.out.println(e.getMessage()); // ���� �޽��� ���
			throw new IOException("���� ������ ���������� �������� ���߽��ϴ�.");
		}
		return newUser;		
	}
	
	// �̸���, ����� �Է¹޾� �ش� ȸ���� �����ϴ��� Ȯ���ϰ�, ����� ��ġ�ϴ��� Ȯ���ϴ� �޼���
	public static User checklogin(String email, String password) throws NullPointerException{
		try {
			// �̸��� Ȯ��
			User user = UserDBManager.findUserByEmail(email); // �̸��� ������ throws NullPointerException
			// ��й�ȣ Ȯ��
			String hashedPw = PasswordManager.hashPassword(password, email);
			// �Է¹��� ��й�ȣ�� �ؽ��Ͽ� user �ν��Ͻ��� ����� �ؽ� ��й�ȣ�� ��
			if(!hashedPw.equals(user.getPassword_hashed())) {
				throw new NullPointerException("��� : ��й�ȣ�� �ٸ��ϴ�.");
			} else { // ��й�ȣ�� ��ġ�ϴ� ���
				return user;
			}
		} catch (NullPointerException e) { 
			throw e;// ���� �޽����� �����ϱ� ���� ���� �Ǵ�����
		}
	}	
}
