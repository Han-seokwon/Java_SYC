package users;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import file.FileManager;

// User 인스턴스를 해시맵에 저장하고 유저 데이터의 조회 및 추가 기능을 수행
public class UserDBManager {
	
	// 각 유저 인스턴스를 저장하는 해시맵으로 이메일을 key, 유저 인스턴스를 value로 한다.
	private static HashMap<String, User> userDBMap = new HashMap<>();	
	
	public static void printUserDBMap() { // 디버깅용
		for (Map.Entry<String, User> entry : userDBMap.entrySet()) {
            String key = entry.getKey();
            User value = entry.getValue();
            System.out.println("Key: " + key + ", Value: " + value.toString() + "\n");
        }
	}
	// 해당하는 이메일이 존재하는지 확인 (등록된 유저인지 확인용)
	public static boolean isEmailExist(String email) {
		return userDBMap.containsKey(email);
	}
	
	
	/*
	 * 이메일에 해당하는 User 인스턴스 반환, isEmailExist() 사용하여 등록된 유저인지 확인후 사용하는 것 권장
	 * return : 인자로 전달된 이메일에 일치하는 유저
	 */
	public static User findUserByEmail(String email) throws NullPointerException{
		User userFound = userDBMap.get(email);
		if(userFound == null) { // 해당 이메일이 없는 경우
			throw new NullPointerException("경고 : 존재하지 않는 이메일입니다.");
		}
		return userFound;
	}
	
	/*
	 * 유저이름에 해당하는 User 인스턴스 반환, isEmailExist() 사용하여 등록된 유저인지 확인후 사용하는 것 권장
	 * return : 인자로 전달된 유저이름에 일치하는 유저
	 */
	public static User findUserByUsername(String username) throws NullPointerException{
		for(User user : userDBMap.values()) {
			if(user.getUsername().equals(username)) {
				return user;
			}
		}
		// 해당 유저가 없는 경우
		throw new NullPointerException("경고 : 존재하지 않는 이메일입니다.");
	}
	
	
	/*
	 *  유저 데이터의 유효성을 검사하고 email을 key로 하여 해시맵에 추가함
	 */
	public static void addUser(User user) throws IllegalArgumentException{
		if(!User.isVaild(user)) { // 전달된 유저객체가 유효한지 확인
			throw new IllegalArgumentException("유저 객체가 유효하지 않아 userDBMap에 저장할 수 없습니다..");
		}
		userDBMap.put(user.getEmail(), user); // 이메일을 key, user를 value로 저장
	}
	
	// UserDB 폴더에 저장된 객체 파일들을 해시맵에 저장하여 초기화, 프로그램 시작시 한 번만 실행
	public static void init() {
		String dirpath = String.format("\\users\\UserDB"); // 경로 지정
		// 해당 폴더에 저장된 모든 파일을 Object로 변환하여 ArrayList<Object>로 변환 
		ArrayList<Object> objList = FileManager.readAllObjectFileInDirectory(dirpath); 
		try {
			// 각 Object 들을 User로 형변환
			for (Object obj : objList) {			 
				if(obj instanceof User) {
					User user = (User)obj;
					addUser(user);				 
				} else {
					throw new ClassCastException("User 인스턴스로 변환할 수 없습니다.");
				}
			}
		} catch (ClassCastException e) {
			e.printStackTrace();			
		}
	}
	
	
	




}
