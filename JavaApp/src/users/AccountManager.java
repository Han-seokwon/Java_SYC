package users;

import java.io.IOException;
import java.util.regex.Pattern;

import file.FileManager;

public class AccountManager {
	
	// È¸¿ø°¡ÀÔ Á¤º¸ À¯È¿¼º È®ÀÎ ¸Ş¼­µå
	// param : ÀÌ¸§, ÀÌ¸ŞÀÏ, ºñ¹ø, ºñ¹øÈ®ÀÎ, ºñ¹ø ÃÊ±âÈ­¿ë Áú¹®¿¡ ´ëÇÑ ´äº¯
	public static void registerInputCheck(String name, String email, String password, String passwordConfirm, String answer) throws IOException{
		String errMsg = "";
		
		if(name.isEmpty()) {
			errMsg += "°æ°í: »ç¿ëÀÚ ÀÌ¸§ÀÌ ÀÔ·ÂµÇÁö ¾Ê¾Ò½À´Ï´Ù.\n";
		}
		
		 // Æ¯¼ö¹®ÀÚ Á¦¿Ü
        if (!Pattern.matches("[a-zA-Z°¡-ÆR0-9]+", name)) {
        	errMsg += "°æ°í: »ç¿ëÀÚ ÀÌ¸§¿¡ Æ¯¼ö ¹®ÀÚ¸¦ »ç¿ëÇÒ ¼ö ¾ø½À´Ï´Ù.\n";
        }
        
        // ÀÌ¸ŞÀÏ Çü½Ä ÁØ¼ö
        if (!Pattern.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$", email)) {
        	errMsg += "°æ°í: ¿Ã¹Ù¸¥ ÀÌ¸ŞÀÏ Çü½ÄÀÌ ¾Æ´Õ´Ï´Ù.\n";
        }
        
        // Áßº¹µÇ´Â ÀÌ¸ŞÀÏÀÌ ÀÖ´ÂÁö È®ÀÎ
        if (UserDBManager.isEmailExist(email)) {
        	errMsg += "°æ°í: ÀÌ¹Ì µî·ÏµÈ ÀÌ¸ŞÀÏÀÔ´Ï´Ù.\n";
        }
        
        try { // ºñ¹Ğ¹øÈ£ À¯È¿¼º È®ÀÎ
        	checkPasswordVaildity(password, passwordConfirm);
		} catch (IOException e) {
			errMsg += e.getMessage();
		}
        
        if (answer.isEmpty()){
        	errMsg += "°æ°í: ºñ¹Ğ¹øÈ£ ÃÊ±âÈ­¿ë Áú¹®¿¡ ´ëÇÑ ´äº¯ÀÌ ÀÔ·ÂµÇÁö ¾Ê¾Ò½À´Ï´Ù.\n";
        }
        
        if(!errMsg.isEmpty()) {
        	throw new IOException(errMsg);
        }                
	}

	// ºñ¹Ğ¹øÈ£ À¯È¿¼º È®ÀÎ¿ë ¸Ş¼­µå (ºñ¹Ğ¹øÈ£ ÃÊ±âÈ­¿¡¼­µµ »ç¿ëÇÏ¹Ç·Î º°°³ÀÇ ¸Ş¼­µå·Î ºĞ¸®)
	public static void checkPasswordVaildity(String password, String passwordConfirm) throws IOException{

		String errMsg = "";
        // ÃÖ¼Ò 8ÀÚ ÀÌ»ó, ¿µ¹®ÀÚ¿Í ¼ıÀÚ °¢°¢ 1°³ ÀÌ»ó Æ÷ÇÔ        
        if (!Pattern.matches("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$", password)) {
        	errMsg += "°æ°í: ºñ¹Ğ¹øÈ£´Â ÃÖ¼Ò 8ÀÚ ÀÌ»óÀÌ¾î¾ß ÇÏ°í ÃÖ¼Ò ÇÏ³ªÀÇ ¼ıÀÚ¿Í ¿µ¹®ÀÚ¸¦ Æ÷ÇÔÇØ¾ß ÇÕ´Ï´Ù.\n";
        }        
        
        if (!(password.equals(passwordConfirm))){
        	errMsg += "°æ°í: ÀÔ·ÂµÈ ºñ¹Ğ¹øÈ£°¡ ÀÏÄ¡ ÇÏÁö ¾Ê½À´Ï´Ù.\n";
        }
        if(!errMsg.isEmpty()) {
        	throw new IOException(errMsg);
        } 
	}

	/* È¸¿ø Á¤º¸ÀÇ À¯È¿¼ºÀÌ È®ÀÎµÈ À¯Àú¸¦ UserDB Æú´õ¿¡ ÆÄÀÏ·Î ÀúÀåÇÏ°í UserDBManager ÇØ½Ã¸Ê¿¡µµ ÀúÀåÇÏ´Â ¸Ş¼­µå
	 * param :  È¸¿ø Á¤º¸ÀÇ À¯È¿¼ºÀÌ È®ÀÎµÈ À¯Àú °´Ã¼
	 */
	public static void createAccount(User user) {
		String filename = FileManager.emailToFilename(user.getEmail());
		String filepath = String.format("\\users\\UserDB\\%s.txt", filename); // °æ·Î ÁöÁ¤
		FileManager.createUpdateObjectFile(user, filepath); // UserDB Æú´õ¿¡ °´Ã¼ ÅØ½ºÆ® ÆÄÀÏ »ı¼º
		UserDBManager.addUser(user.getEmail(), user); // UserDBManager ÇØ½Ã¸Ê¿¡ °´Ã¼ Ãß°¡
	}
	
	// ÀÌ¸ŞÀÏ, ºñ¹øÀ» ÀÔ·Â¹Ş¾Æ ÇØ´ç È¸¿øÀÌ Á¸ÀçÇÏ´ÂÁö È®ÀÎÇÏ°í, ºñ¹øÀÌ ÀÏÄ¡ÇÏ´ÂÁö È®ÀÎÇÏ´Â ¸Ş¼­µå
	public static User checklogin(String email, String password) throws NullPointerException{
		System.out.println(email);
		System.out.println(password);
		try {
			// ÀÌ¸ŞÀÏ È®ÀÎ
			User user = UserDBManager.findUserByEmail(email); // ÀÌ¸ŞÀÏ ¾øÀ¸¸é throws NullPointerException
			// ºñ¹Ğ¹øÈ£ È®ÀÎ
			String hashedPw = PasswordManager.hashPassword(password, email);
			// ÀÔ·Â¹ŞÀº ºñ¹Ğ¹øÈ£¸¦ ÇØ½ÌÇÏ¿© user ÀÎ½ºÅÏ½º¿¡ ÀúÀåµÈ ÇØ½Ì ºñ¹Ğ¹øÈ£°ú ºñ±³
			if(!hashedPw.equals(user.getPassword_hashed())) {
				throw new NullPointerException("°æ°í : ºñ¹Ğ¹øÈ£°¡ ´Ù¸¨´Ï´Ù.");
			} else { // ºñ¹Ğ¹øÈ£°¡ ÀÏÄ¡ÇÏ´Â °æ¿ì
				return user;
			}
		} catch (NullPointerException e) { 
			throw e;// ¿¡·¯ ¸Ş½ÃÁö¸¦ Àü´ŞÇÏ±â À§ÇØ ¿¹¿Ü µÇ´øÁö±â
		}
	}
	
	/* »ç¿ëÀÚÀÇ ºñ¹Ğ¹øÈ£¸¦ º¯°æÇÏ´Â ¸Ş¼­µå, UserDB Æú´õ¿¡ ÀúÀåµÈ °´Ã¼ÆÄÀÏµµ ¼öÁ¤
	 * param : ºñ¹øÀ» ¼öÁ¤ÇÒ À¯Àú °´Ã¼, ¼öÁ¤ÇÒ ºñ¹ø ¹®ÀÚ¿­
	 */
	public static void updatePassword(User user, String newPassWord) {
		String newPassword_hashed = PasswordManager.hashPassword(newPassWord, user.getEmail());
		user.setPassword_hashed(newPassword_hashed);		
		// ÇØ´ç °´Ã¼ ÆÄÀÏ ¾÷µ¥ÀÌÆ®
		String filename = FileManager.emailToFilename(user.getEmail());
		String filepath = String.format("/users/UserDB/%s.txt", filename); // °æ·Î ÁöÁ¤
		FileManager.createUpdateObjectFile(user, filepath);
		
	}
	
}
