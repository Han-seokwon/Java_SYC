package gui;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.SwingUtilities;

import users.AccountManager;
import users.PasswordManager;
import users.User;
import users.UserDBManager;

//	 비밀번호 초기화 과정 3단계로 유저이름, 이메일, 비번 초기화 답변이 모두 인증된 사용자가 최종적으로 새롭게 변경할 비밀번호를 입력하는 프레임
public class PasswordResetInputCheckFrame extends JFrame{	
	private JPasswordField newPasswordField;
	private JPasswordField passwordConfirmField;
	private JButton resetButton;
	private User user;
	// 현재 비밀번호를 초기화하려는 User 인스턴스를 인자로 받음
	public PasswordResetInputCheckFrame(User user) {
		this.user = user;
		
		// 컴포넌트 생성 및 초기화
		JPanel panel = new JPanel(new GridLayout(3, 2));        

		JLabel newPasswordLabel = new JLabel("새로운 비밀번호:");
		newPasswordField = new JPasswordField();

		JLabel confirmPasswordLabel = new JLabel("비밀번호 확인:");
		passwordConfirmField = new JPasswordField();

		resetButton = new JButton("비밀번호 초기화");
		// 초기화 버튼에 이벤트 핸들러 적용
		resetButton.addActionListener(new CheckNewPasswordListener());

		// 레이아웃
		setLayout(new GridLayout(3, 2, 70, 70));
		
		panel.add(newPasswordLabel);
		panel.add(newPasswordField);
		panel.add(confirmPasswordLabel);
		panel.add(passwordConfirmField);
		panel.add(new CancelButton());
		panel.add(resetButton);
		add(panel);
		
		// 프레임 속성 설정
		setTitle("비밀번호 초기화");
		setSize(500, 500);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); 
		setVisible(true);

	}

	class CheckNewPasswordListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			boolean isValidPw = true;
			String newPassword = new String(newPasswordField.getPassword());
			String newPasswordConfirm = new String(passwordConfirmField.getPassword());

			try {// 초기화 비밀번호 유효성 확인
				AccountManager.checkPasswordVaildity(newPassword, newPasswordConfirm);
			} catch (IOException err) {
				JOptionPane.showMessageDialog(null, err.getMessage(), "비밀번호 초기화 실패", JOptionPane.WARNING_MESSAGE);
				isValidPw = false;
			} 
			if(isValidPw) {
//				 비밀번호 초기화과 완료되었다는 팝업창을 띄우고 비밀번호를 업데이트함
				JOptionPane.showMessageDialog(null, "비밀번호가 초기화되었습니다.", "비밀번호 초기화 성공", JOptionPane.INFORMATION_MESSAGE);
				PasswordManager.updatePassword(user, newPassword);// 비밀번호 업데이트
				dispose(); 
			}
		}
	}

}
