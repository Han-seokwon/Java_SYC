package users;

import java.io.IOException;
import java.util.regex.Pattern;

import fetcher.JsonFetcher;

// ȸ�� ���Կ� �ʿ��� ������ �����ϴ� Ŭ����
public class ResistrationFormat {
    private String name;
    private String solvedName; // solved.ac�� ��ϵ� ������ �̸�
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
	
	// ȸ������ ���� ��ȿ�� Ȯ�� �޼���
	public void isVaildFormat() throws IOException{
		String errMsg = "";
		
		if(name.isBlank()) {
			errMsg += "���: ����� �̸��� �Էµ��� �ʾҽ��ϴ�.\n";
		}
		// Ư������ ����
		if (!Pattern.matches("[a-zA-Z��-�R0-9]+", name)) {
			errMsg += "���: ����� �̸��� Ư�� ���ڸ� ����� �� �����ϴ�.\n";
		}
		
		// solved.ac�� ��ϵ� ȸ���̸����� Ȯ��				
		if(!JsonFetcher.checkUserRegistered_InSolvedAC(solvedName)) { // solved api�� ������ Ȯ������ ��, ������ ã�� ���� ���
			errMsg += String.format("���: %s �̸����� solved.ac�� ��ϵ� ������ �����ϴ�.\n", solvedName);
		}		
        
        // �̸��� ���� �ؼ�
        if (!Pattern.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$", email)) {
        	errMsg += "���: �ùٸ� �̸��� ������ �ƴմϴ�.\n";
        }
        
        // �ߺ��Ǵ� �̸����� �ִ��� Ȯ�� (�̸����� �����ؾ� ��)
        if (UserDBManager.isEmailExist(email)) {
        	errMsg += "���: �̹� ��ϵ� �̸����Դϴ�.\n";
        }
        
        try { // ��й�ȣ ��ȿ�� Ȯ��
        	PasswordManager.checkPasswordVaildity(password, passwordConfirm);
		} catch (IOException e) {
			errMsg += e.getMessage();
		}
        
        if (resetPwAnswer.isBlank()){
        	errMsg += "���: ��й�ȣ �ʱ�ȭ�� ������ ���� �亯�� �Էµ��� �ʾҽ��ϴ�.\n";
        }
        
        if(!errMsg.isEmpty()) {
        	throw new IOException(errMsg);
        }  
	}

}



