package users;

// 회원 가입에 필요한 정보를 저장하는 클래스
public class ResistrationFormat {
    private String name;
    private String solvedName; // solved.ac에 등록된 프로필 이름
    private String email;
    private String password;
    private String passwordConfirm;
    private String resetPwQuestion;
    private String answer;

    public ResistrationFormat() {}
    public ResistrationFormat(String name, String solvedName, String email, String password, String passwordConfirm, String resetPwQuestion, String answer) {
        this.name = name;
        this.solvedName = solvedName;
        this.email = email;
        this.password = password;
        this.passwordConfirm = passwordConfirm;
        this.resetPwQuestion = resetPwQuestion;
        this.answer = answer;
    }

    @Override
	public String toString() {
		return "ResisterationFormat [name=" + name + ",\n solvedName=" + solvedName + ",\n email=" + email + ",\n password="
				+ password + ",\n passwordConfirm=" + passwordConfirm + ",\n resetPwQuestion : "+ resetPwQuestion +",\n answer=" + answer + "]";
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
	public String getAnswer() {
        return answer;
    }

}



