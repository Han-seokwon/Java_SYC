package gui;

import java.io.InputStream;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.JOptionPane;

// ���̾�α� �����ϴ� Ŭ����
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
		if(errorType == USER_FILE_SAVING_ERROR) { // ���� ���� ���� ���� �߻��� ���
			showAlertDialog(dialogTitle, "�ý��� ������ ���� �����Ͱ� ���������� ������� ���߽��ϴ�. �����ڿ��� �������ּ���.");
		}
	}
	
	
	// ��ư Ŭ�� ���� ���
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
        	// �������� InputStream ->  AudioInputStream�� ��ȯ
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(soundFileStream);  
            Clip clip = AudioSystem.getClip(); // ���� ���� ����� ���� Clip ��ü ����
            clip.open(audioInputStream); // ���� ���� ����
            clip.start(); // ���
        } catch (Exception e) {
        	System.out.println(e.getMessage());            
        }
    }
    
}
