package users;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.regex.Pattern;

import file.FileManager;

// 비밀번호관련 작업을 하는 static 메서드를 모은 클래스
public class PasswordManager {
	

	/*  비밀번호 유효성 확인
	 * @param  password : 비번
	 * @param  password : 비번 확인
	 */
	public static void checkPasswordVaildity(String password, String passwordConfirm) throws IOException{

		String errMsg = "";
        // 최소 8자 이상, 영문자와 숫자 각각 1개 이상 포함        
        if (!Pattern.matches("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$", password)) {
        	errMsg += "경고: 비밀번호는 최소 8자 이상이어야 하고 최소 하나의 숫자와 영문자를 포함해야 합니다.\n";
        }        
        
        if (!(password.equals(passwordConfirm))){
        	errMsg += "경고: 입력된 비밀번호가 일치 하지 않습니다.\n";
        }
        if(!errMsg.isEmpty()) {
        	throw new IOException(errMsg);
        } 
	}
	
	/* 사용자의 비밀번호를 변경하는 메서드, UserDB 폴더에 저장된 객체파일도 수정
	 * @param user : 비번을 수정할 유저 객체
	 * @param newPassWord : 수정할 비번 
	 */
	public static void updatePassword(User user, String newPassWord) throws IOException{
		String newPassword_hashed = hashPassword(newPassWord, user.getEmail());
		user.setPassword_hashed(newPassword_hashed);	
		user.createUpdateUserFile(); 	// 해당 객체 파일 업데이트		
	}
	
	/* 비밀번호를 해싱하는 메서드, 이메일을 솔트로 이용함
	 * @ 
	 * @return : 해싱된 문자열   
	 */
    public static String hashPassword(String password, String email) {
        try {
        	// @를 기준으로 앞의 문자열을 추출
        	String salt = email.substring(email.indexOf("@")); 
        	// SHA-256 해시 함수를 사용하여 MessageDigest 객체를 초기화
            MessageDigest md = MessageDigest.getInstance("SHA-256"); // throws NoSuchAlgorithmException
            // password와 salt를 결합한 후 byte 배열로 변환
            md.update((password + salt).getBytes()); 
            // 다이제스트(해싱)된 바이트 배열 반환
            byte[] hashedBytes = md.digest();

            StringBuilder sb = new StringBuilder();
            //  바이트 배열을 16진수 문자열로 변환하여 해시된 비밀번호를 생성
            for (byte b : hashedBytes) {
                sb.append(String.format("%02x", b));
            }
            
            return sb.toString(); // StringBuilder -> String
            
        } catch (NoSuchAlgorithmException e) { // SHA-256 해시 함수를 사용할 수 없는 경우
            e.printStackTrace();
            return String.valueOf(password.hashCode()); // 기본 해시코드로 변환하여 반환
        }
    }
}
