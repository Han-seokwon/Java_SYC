package users;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.regex.Pattern;

import file.FileManager;

// ��й�ȣ���� �۾��� �ϴ� static �޼��带 ���� Ŭ����
public class PasswordManager {
	

	/*  ��й�ȣ ��ȿ�� Ȯ��
	 * @param  password : ���
	 * @param  password : ��� Ȯ��
	 */
	public static void checkPasswordVaildity(String password, String passwordConfirm) throws IOException{

		String errMsg = "";
        // �ּ� 8�� �̻�, �����ڿ� ���� ���� 1�� �̻� ����        
        if (!Pattern.matches("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$", password)) {
        	errMsg += "���: ��й�ȣ�� �ּ� 8�� �̻��̾�� �ϰ� �ּ� �ϳ��� ���ڿ� �����ڸ� �����ؾ� �մϴ�.\n";
        }        
        
        if (!(password.equals(passwordConfirm))){
        	errMsg += "���: �Էµ� ��й�ȣ�� ��ġ ���� �ʽ��ϴ�.\n";
        }
        if(!errMsg.isEmpty()) {
        	throw new IOException(errMsg);
        } 
	}
	
	/* ������� ��й�ȣ�� �����ϴ� �޼���, UserDB ������ ����� ��ü���ϵ� ����
	 * @param user : ����� ������ ���� ��ü
	 * @param newPassWord : ������ ��� 
	 */
	public static void updatePassword(User user, String newPassWord) throws IOException{
		String newPassword_hashed = hashPassword(newPassWord, user.getEmail());
		user.setPassword_hashed(newPassword_hashed);	
		user.createUpdateUserFile(); 	// �ش� ��ü ���� ������Ʈ		
	}
	
	/* ��й�ȣ�� �ؽ��ϴ� �޼���, �̸����� ��Ʈ�� �̿���
	 * @ 
	 * @return : �ؽ̵� ���ڿ�   
	 */
    public static String hashPassword(String password, String email) {
        try {
        	// @�� �������� ���� ���ڿ��� ����
        	String salt = email.substring(email.indexOf("@")); 
        	// SHA-256 �ؽ� �Լ��� ����Ͽ� MessageDigest ��ü�� �ʱ�ȭ
            MessageDigest md = MessageDigest.getInstance("SHA-256"); // throws NoSuchAlgorithmException
            // password�� salt�� ������ �� byte �迭�� ��ȯ
            md.update((password + salt).getBytes()); 
            // ��������Ʈ(�ؽ�)�� ����Ʈ �迭 ��ȯ
            byte[] hashedBytes = md.digest();

            StringBuilder sb = new StringBuilder();
            //  ����Ʈ �迭�� 16���� ���ڿ��� ��ȯ�Ͽ� �ؽõ� ��й�ȣ�� ����
            for (byte b : hashedBytes) {
                sb.append(String.format("%02x", b));
            }
            
            return sb.toString(); // StringBuilder -> String
            
        } catch (NoSuchAlgorithmException e) { // SHA-256 �ؽ� �Լ��� ����� �� ���� ���
            e.printStackTrace();
            return String.valueOf(password.hashCode()); // �⺻ �ؽ��ڵ�� ��ȯ�Ͽ� ��ȯ
        }
    }
}
