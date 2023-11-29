package gui;

import java.io.InputStream;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.JOptionPane;

// 다이얼로그 생성하는 클래스
public class Dialog {
	static final int DEFALUT_POPUP_SOUND = 0;
	static final int ALERT_SOUND = 1;
	
	static final int USER_FILE_SAVING_ERROR = 0;
	
	static void showInfoDialog(String dialogTitle, String dialogMsg) {
		playEffectSound(DEFALUT_POPUP_SOUND);
		JOptionPane.showMessageDialog(null, dialogMsg, dialogTitle, JOptionPane.INFORMATION_MESSAGE);
	}
	
	static void showAlertDialog(String dialogTitle, String dialogMsg) {
		playEffectSound(ALERT_SOUND);
		JOptionPane.showMessageDialog(null, dialogMsg, dialogTitle, JOptionPane.WARNING_MESSAGE);
	}
	static void showAlertDialog(String dialogTitle, int errorType) {
		if(errorType == USER_FILE_SAVING_ERROR) { // 유저 파일 저장 문제 발생한 경우
			showAlertDialog(dialogTitle, "시스템 문제로 유저 데이터가 정상적으로 저장되지 못했습니다. 관리자에게 문의해주세요.");
		}
	}
	
	
	// 버튼 클릭 사운드 재생
    private static void playEffectSound(int soundType) {
    	InputStream soundFileStream;
    	if(soundType == DEFALUT_POPUP_SOUND) {
    		soundFileStream = Dialog.class.getResourceAsStream("/sources/btn_click_sound.wav");
    		
    	} else if(soundType == ALERT_SOUND){
    		soundFileStream = Dialog.class.getResourceAsStream("/sources/alert_sound.wav");
    	} else {
    		return;
    	}
    	
        try {
        	// 음성파일 InputStream ->  AudioInputStream로 변환
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(soundFileStream);  
            Clip clip = AudioSystem.getClip(); // 사운드 파일 재생을 위한 Clip 객체 생성
            clip.open(audioInputStream); // 사운드 파일 오픈
            clip.start(); // 재생
        } catch (Exception e) {
        	System.out.println(e.getMessage());            
        }
    }
    
}
