package users;

import java.io.IOException;
import java.util.regex.Pattern;

import fetcher.JsonFetcher;
import file.FileManager;

// 회원 관리에 필요한 static 메서드들을 모은 클래스
public class AccountManager {
	/* 회원 정보의 유효성이 확인된 회원 가입 양식으로 유저 인스턴스를 생성하고 
	 * 이를 UserDB 폴더에 직렬화 파일로 저장하고 UserDBManager 해시맵에도 저장하는 메서드	 * 
	 * param :  유효성이 확인된 회원 가입 양식
	 * return : 정상적으로 회원 등록된 경우 회원 인스턴스 반환
	 */
	public static User createAccount(ResistrationFormat format) throws IOException{
		User newUser;
		try {
			newUser = new User(format); // 새로운 User 객체 생성
			newUser.createUpdateUserFile(); // 유저 객체 UserDB 폴더에 파일로 저장
			UserDBManager.addUser(newUser); // UserDBManager 해시맵에 객체 추가
		} catch (Exception e) {
			System.out.println(e.getMessage()); // 에러 메시지 출력
			throw new IOException("유저 정보를 정상적으로 저장하지 못했습니다.");
		}
		return newUser;		
	}
	
	// 이메일, 비번을 입력받아 해당 회원이 존재하는지 확인하고, 비번이 일치하는지 확인하는 메서드
	public static User checklogin(String email, String password) throws NullPointerException{
		try {
			// 이메일 확인
			User user = UserDBManager.findUserByEmail(email); // 이메일 없으면 throws NullPointerException
			// 비밀번호 확인
			String hashedPw = PasswordManager.hashPassword(password, email);
			// 입력받은 비밀번호를 해싱하여 user 인스턴스에 저장된 해싱 비밀번호과 비교
			if(!hashedPw.equals(user.getPassword_hashed())) {
				throw new NullPointerException("경고 : 비밀번호가 다릅니다.");
			} else { // 비밀번호가 일치하는 경우
				return user;
			}
		} catch (NullPointerException e) { 
			throw e;// 에러 메시지를 전달하기 위해 예외 되던지기
		}
	}	
}
