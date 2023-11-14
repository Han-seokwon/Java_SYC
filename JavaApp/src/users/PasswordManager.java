package users;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;


public class PasswordManager {
	/* 비밀번호를 해싱하는 메서드, 이메일을 솔트로 이용함
	 * return : 해싱된 문자열   
	 */
    public static String hashPassword(String password, String email) {
        try {
        	// @를 기준으로 앞의 문자열을 추출
        	String salt = email.substring(email.indexOf("@")); 
        	// SHA-256 해시 함수를 사용하여 MessageDigest 객체를 초기화
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            // password와 salt를 결합한 후 byte 배열로 변환
            md.update((password + salt).getBytes()); 
            // 다이제스트(해싱)된 바이트 배열 반환
            byte[] hashedBytes = md.digest();

            StringBuilder sb = new StringBuilder();
            //  바이트 배열을 16진수 문자열로 변환하여 해시된 비밀번호를 생성
            for (byte b : hashedBytes) {
                sb.append(String.format("%02x", b));
            }
            
            return sb.toString();
            
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }
}
