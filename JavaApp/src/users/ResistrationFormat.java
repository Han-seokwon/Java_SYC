package users;

import java.io.IOException;
import java.util.regex.Pattern;

import fetcher.JsonFetcher;

// È¸¿ø °¡ÀÔ¿¡ ÇÊ¿äÇÑ Á¤º¸¸¦ ÀúÀåÇÏ´Â Å¬·¡½º
public class ResistrationFormat {
    private String name;
    private String solvedName; // solved.ac¿¡ µî·ÏµÈ ÇÁ·ÎÇÊ ÀÌ¸§
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
	
	// È¸¿ø°¡ÀÔ Á¤º¸ À¯È¿¼º È®ÀÎ ¸Ş¼­µå
	public void isVaildFormat() throws IOException{
		String errMsg = "";
		
		if(name.isBlank()) {
			errMsg += "°æ°í: »ç¿ëÀÚ ÀÌ¸§ÀÌ ÀÔ·ÂµÇÁö ¾Ê¾Ò½À´Ï´Ù.\n";
		}
		// Æ¯¼ö¹®ÀÚ Á¦¿Ü
		if (!Pattern.matches("[a-zA-Z°¡-ÆR0-9]+", name)) {
			errMsg += "°æ°í: »ç¿ëÀÚ ÀÌ¸§¿¡ Æ¯¼ö ¹®ÀÚ¸¦ »ç¿ëÇÒ ¼ö ¾ø½À´Ï´Ù.\n";
		}
		
		// solved.ac¿¡ µî·ÏµÈ È¸¿øÀÌ¸§ÀÎÁö È®ÀÎ				
		if(!JsonFetcher.checkUserRegistered_InSolvedAC(solvedName)) { // solved api·Î À¯Àú¸¦ È®ÀÎÇßÀ» ¶§, À¯Àú¸¦ Ã£Áö ¸øÇÑ °æ¿ì
			errMsg += String.format("°æ°í: %s ÀÌ¸§À¸·Î solved.ac¿¡ µî·ÏµÈ °èÁ¤ÀÌ ¾ø½À´Ï´Ù.\n", solvedName);
		}		
        
        // ÀÌ¸ŞÀÏ Çü½Ä ÁØ¼ö
        if (!Pattern.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$", email)) {
        	errMsg += "°æ°í: ¿Ã¹Ù¸¥ ÀÌ¸ŞÀÏ Çü½ÄÀÌ ¾Æ´Õ´Ï´Ù.\n";
        }
        
        // Áßº¹µÇ´Â ÀÌ¸ŞÀÏÀÌ ÀÖ´ÂÁö È®ÀÎ (ÀÌ¸ŞÀÏÀº °íÀ¯ÇØ¾ß ÇÔ)
        if (UserDBManager.isEmailExist(email)) {
        	errMsg += "°æ°í: ÀÌ¹Ì µî·ÏµÈ ÀÌ¸ŞÀÏÀÔ´Ï´Ù.\n";
        }
        
        try { // ºñ¹Ğ¹øÈ£ À¯È¿¼º È®ÀÎ
        	PasswordManager.checkPasswordVaildity(password, passwordConfirm);
		} catch (IOException e) {
			errMsg += e.getMessage();
		}
        
        if (resetPwAnswer.isBlank()){
        	errMsg += "°æ°í: ºñ¹Ğ¹øÈ£ ÃÊ±âÈ­¿ë Áú¹®¿¡ ´ëÇÑ ´äº¯ÀÌ ÀÔ·ÂµÇÁö ¾Ê¾Ò½À´Ï´Ù.\n";
        }
        
        if(!errMsg.isEmpty()) {
        	throw new IOException(errMsg);
        }  
	}

}



