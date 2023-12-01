package gui;

import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import users.User;
import users.UserDBManager;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.SwingConstants;

// 비밀번호 초기화 과정 1단계 : 유저이름, 이메일 확인 -> 2단계 : 초기화 질문에 대한 답변 확인 -> 3 단계 : 새롭게 변경할 비밀번호 입력

// << 비밀번호 초기화 과정 1단계 >>
// 유저이름, 이메일를 입력하여 비밀번호를 초기화할 사용자를 찾는 프레임
public class PasswordResetUsernameEmailCheckFrame extends DesignedJFrame {


	private JTextField usernameField;
	private JTextField emailField;
	private User user;
	public PasswordResetUsernameEmailCheckFrame() {
		super(500, 500, "비밀번호 초기화");


		// 컨텐트팬 생성
		DesignedContentPane contentPane = new DesignedContentPane(this);
		setContentPane(contentPane);
		// 컨텐트팬  레이아웃
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[] {50, 200, 200};
		gbl_contentPane.rowHeights = new int[] {100, 100, 50, 50};
		gbl_contentPane.columnWeights = new double[]{0.0, 0.0, 0.0};
		gbl_contentPane.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0};
		contentPane.setLayout(gbl_contentPane);

		// 유저이름 라벨
		JLabel usernameLabel = new JLabel("유저 이름:");
		usernameLabel.setHorizontalAlignment(SwingConstants.CENTER);

		// 유저이름 라벨 레이아웃
		GridBagConstraints gbc_usernameLabel = new GridBagConstraints();
		gbc_usernameLabel.fill = GridBagConstraints.BOTH;
		gbc_usernameLabel.insets = new Insets(0, 0, 5, 5);
		gbc_usernameLabel.gridx = 1;
		gbc_usernameLabel.gridy = 0;
		getContentPane().add(usernameLabel, gbc_usernameLabel);
		
		// 유저이름 입력 필드
		usernameField = new JTextField();
		// 유저이름 입력 필드 레이아웃
		GridBagConstraints gbc_usernameField = new GridBagConstraints();
		gbc_usernameField.fill = GridBagConstraints.BOTH;
		gbc_usernameField.insets = new Insets(0, 0, 5, 0);
		gbc_usernameField.gridx = 2;
		gbc_usernameField.gridy = 0;
		getContentPane().add(usernameField, gbc_usernameField);
		
		// 이메일 라벨
		JLabel emailLabel = new JLabel("이메일:");
		// 이메일 라벨 레이아웃
		emailLabel.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_emailLabel = new GridBagConstraints();
		gbc_emailLabel.fill = GridBagConstraints.BOTH;
		gbc_emailLabel.insets = new Insets(0, 0, 5, 5);
		gbc_emailLabel.gridx = 1;
		gbc_emailLabel.gridy = 1;
		getContentPane().add(emailLabel, gbc_emailLabel);
		
		// 이메일 입력 필드
		emailField = new JTextField();
		// 이메일 입력 필드 레이아웃
		GridBagConstraints gbc_emailField = new GridBagConstraints();
		gbc_emailField.fill = GridBagConstraints.BOTH;
		gbc_emailField.insets = new Insets(0, 0, 5, 0);
		gbc_emailField.gridx = 2;
		gbc_emailField.gridy = 1;
		getContentPane().add(emailField, gbc_emailField);
		
		
		// 취소 버튼 레이아웃
		GridBagConstraints gbc_cancleButton = new GridBagConstraints();
		gbc_cancleButton.fill = GridBagConstraints.BOTH;
		gbc_cancleButton.insets = new Insets(0, 0, 5, 5);
		gbc_cancleButton.gridx = 1;
		gbc_cancleButton.gridy = 3;
		// 취소 버튼 생성 및 추가
		getContentPane().add(new CancelButton(), gbc_cancleButton); 		

		// 다음 버튼 생성
		JButton nextButton = new DesignedButton("다음", COLOR.SUNFLOWER);
		nextButton.addActionListener(new CheckNameEmailListener());	// 다음 버튼에 이벤트 핸들러 적용
		// 다음 버튼 레이아웃
		GridBagConstraints gbc_nextButton = new GridBagConstraints();
		gbc_nextButton.insets = new Insets(0, 0, 5, 0);
		gbc_nextButton.fill = GridBagConstraints.BOTH;
		gbc_nextButton.gridx = 2;
		gbc_nextButton.gridy = 3;
		getContentPane().add(nextButton, gbc_nextButton);	

		contentPane.applyFontAndBackgroundToAllComponents();  // 전체 폰트 적용 및 패널 배경색 투명하게 적용
		setVisible(true);

	}
	class CheckNameEmailListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			String username = usernameField.getText();
			String email = emailField.getText();
			if(UserDBManager.isEmailExist(email)) {
				// 유저가 존재하는 것을 확인했으므로 예외처리 필요없음
				user = UserDBManager.findUserByEmail(email); // 유저 설정 -> 참조로 전달하므로 다른 프레임에도 공유됨
				if( username.equals(user.getUsername())) {
					dispose();
					// 비밀번호 초기화용 질문, 답변 확인 프레임 생성
					new PasswordResetAnswerCheckFrame(user); 				
				} else {
					Dialog.showAlertDialog( "인증 실패", "유저 이름이 일치하지 않습니다.");
				}
			} else {
				Dialog.showAlertDialog( "인증 실패", "해당 유저가 존재하지 않습니다.");			}

		}

	}
}
