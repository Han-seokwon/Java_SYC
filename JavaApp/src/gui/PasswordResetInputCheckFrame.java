package gui;

import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.SwingConstants;

import users.AccountManager;
import users.PasswordManager;
import users.User;

//	 비밀번호 초기화 과정 3단계로 유저이름, 이메일, 비번 초기화 답변이 모두 인증된 사용자가 최종적으로 새롭게 변경할 비밀번호를 입력하는 프레임
public class PasswordResetInputCheckFrame extends DesignedJFrame{	
	private JPasswordField newPasswordField;
	private JPasswordField passwordConfirmField;
	private JButton resetButton;
	private User user;
	// 현재 비밀번호를 초기화하려는 User 인스턴스를 인자로 받음
	public PasswordResetInputCheckFrame(User user) {
		super(500, 500, "비밀번호 초기화");
		this.user = user;

		// 컨텐트팬 생성
		DesignedContentPane contentPane = new DesignedContentPane(this);
		setContentPane(contentPane);
		// 컨텐트팬 레이아웃
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[] {30, 200, 200};
		gbl_contentPane.rowHeights = new int[] {100, 100, 50, 50};
		gbl_contentPane.columnWeights = new double[]{0.0, 0.0, 0.0};
		gbl_contentPane.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0};
		contentPane.setLayout(gbl_contentPane);


		// 새로운 비밀번호 라벨
		JLabel newPasswordLabel = new JLabel("새로운 비밀번호:");
		// 새로운 비밀번호 레이아웃
		newPasswordLabel.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_newPasswordLabel = new GridBagConstraints();
		gbc_newPasswordLabel.fill = GridBagConstraints.BOTH;
		gbc_newPasswordLabel.insets = new Insets(0, 0, 5, 5);
		gbc_newPasswordLabel.gridx = 1;
		gbc_newPasswordLabel.gridy = 0;
		contentPane.add(newPasswordLabel, gbc_newPasswordLabel);
		
		// 새로운 비밀번호 입력 필드
		newPasswordField = new JPasswordField();
		GridBagConstraints gbc_newPasswordField = new GridBagConstraints();
		gbc_newPasswordField.fill = GridBagConstraints.BOTH;
		gbc_newPasswordField.insets = new Insets(0, 0, 5, 5);
		gbc_newPasswordField.gridx = 2;
		gbc_newPasswordField.gridy = 0;
		contentPane.add(newPasswordField, gbc_newPasswordField);

		// 비밀번호 확인 라벨
		JLabel confirmPasswordLabel = new JLabel("비밀번호 확인:");
		confirmPasswordLabel.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_confirmPasswordLabel = new GridBagConstraints();
		gbc_confirmPasswordLabel.fill = GridBagConstraints.BOTH;
		gbc_confirmPasswordLabel.insets = new Insets(0, 0, 5, 5);
		gbc_confirmPasswordLabel.gridx = 1;
		gbc_confirmPasswordLabel.gridy = 1;
		contentPane.add(confirmPasswordLabel, gbc_confirmPasswordLabel);

		resetButton = new DesignedButton("비밀번호 초기화", COLOR.SUNFLOWER);
		// 초기화 버튼에 이벤트 핸들러 적용
		resetButton.addActionListener(new CheckNewPasswordListener());
		passwordConfirmField = new JPasswordField();
		GridBagConstraints gbc_passwordConfirmField = new GridBagConstraints();
		gbc_passwordConfirmField.fill = GridBagConstraints.BOTH;
		gbc_passwordConfirmField.insets = new Insets(0, 0, 5, 5);
		gbc_passwordConfirmField.gridx = 2;
		gbc_passwordConfirmField.gridy = 1;
		contentPane.add(passwordConfirmField, gbc_passwordConfirmField);
		
		
		// 취소버튼 레이아웃
		GridBagConstraints gbc_cancelButton = new GridBagConstraints();
		gbc_cancelButton.fill = GridBagConstraints.BOTH;
		gbc_cancelButton.insets = new Insets(0, 0, 0, 5);
		gbc_cancelButton.gridx = 1;
		gbc_cancelButton.gridy = 3;

		// 취소 버튼 추가
		contentPane.add(new CancelButton(), gbc_cancelButton);
		
		// 초기화 버튼
		GridBagConstraints gbc_resetButton = new GridBagConstraints();
		gbc_resetButton.insets = new Insets(0, 0, 0, 5);
		gbc_resetButton.fill = GridBagConstraints.BOTH;
		gbc_resetButton.gridx = 2;
		gbc_resetButton.gridy = 3;
		contentPane.add(resetButton, gbc_resetButton);

		contentPane.applyFontAndBackgroundToAllComponents();  // 전체 폰트 적용 및 패널 배경색 투명하게 적용
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

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PasswordResetInputCheckFrame frame = new PasswordResetInputCheckFrame(new User("qwer", "hoh9170", "qwr@na.com", "d1dd", "1. 좋아하는 동물은?", "사자"));
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

}


