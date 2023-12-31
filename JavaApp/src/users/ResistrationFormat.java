package users;

import java.io.IOException;
import java.util.regex.Pattern;

import fetcher.JsonFetcher;

// 회원 가입에 필요한 정보를 저장하는 클래스
public class ResistrationFormat {
    private String name;
    private String solvedName; // solved.ac에 등록된 프로필 이름
    private String email;
    private String password;
    private String passwordConfirm;
    private String resetPwQuestion;
    private String resetPwAnswer;

    public ResistrationFormat() {}
    public ResistrationFormat(String name, String solvedName, String email, String password, String passwordConfirm, String resetPwQuestion, String resetPwAnswer) {
        this.name = name;
        this.solvedName = solvedName;
        this.email = email;
        this.password = password;
        this.passwordConfirm = passwordConfirm;
        this.resetPwQuestion = resetPwQuestion;
        this.resetPwAnswer = resetPwAnswer;
    }

    @Override
	public String toString() {
		return "ResisterationFormat [name=" + name +
				",\n solvedName=" + solvedName + 
				",\n email=" + email + 
				",\n password=" + password 
				+ ",\n passwordConfirm=" + passwordConfirm + 
				",\n resetPwQuestion : "+ resetPwQuestion +
				",\n resetPwAnswer=" + resetPwAnswer + "]";
	}
    
	public String getName() {
        return name;
    }

    public String getSolvedName() {
        return solvedName;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getPasswordConfirm() {
        return passwordConfirm;
    }

    public String getResetPwQuestion() {
		return resetPwQuestion;
	}
	public String getResetPwAnswer() {
        return resetPwAnswer;
    }
	
	// 회원가입 정보 유효성 확인 메서드
	public void isVaildFormat() throws IOException{
		String errMsg = "";
		
		if(name.isBlank()) {
			errMsg += "경고: 사용자 이름이 입력되지 않았습니다.\n";
		}
		// 특수문자 제외
		if (!Pattern.matches("[a-zA-Z가-힣0-9]+", name)) {
			errMsg += "경고: 사용자 이름에 특수 문자를 사용할 수 없습니다.\n";
		}
		
		// solved.ac에 등록된 회원이름인지 확인				
		if(!JsonFetcher.checkUserRegistered_InSolvedAC(solvedName)) { // solved api로 유저를 확인했을 때, 유저를 찾지 못한 경우
			errMsg += String.format("경고: %s 이름으로 solved.ac에 등록된 계정이 없습니다.\n", solvedName);
		}		
        
        // 이메일 형식 준수
        if (!Pattern.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$", email)) {
        	errMsg += "경고: 올바른 이메일 형식이 아닙니다.\n";
        }
        
        // 중복되는 이메일이 있는지 확인 (이메일은 고유해야 함)
        if (UserDBManager.isEmailExist(email)) {
        	errMsg += "경고: 이미 등록된 이메일입니다.\n";
        }
        
        try { // 비밀번호 유효성 확인
        	PasswordManager.checkPasswordVaildity(password, passwordConfirm);
		} catch (IOException e) {
			errMsg += e.getMessage();
		}
        
        if (resetPwAnswer.isBlank()){
        	errMsg += "경고: 비밀번호 초기화용 질문에 대한 답변이 입력되지 않았습니다.\n";
        }
        
        if(!errMsg.isEmpty()) {
        	throw new IOException(errMsg);
        }  
	}

}



