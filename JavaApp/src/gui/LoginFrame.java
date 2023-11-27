package gui;

import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import users.AccountManager;
import users.User;

// 로그인을 진행하는 프레임, 비밀번호 초기화 버튼 클릭시 초기화용 프레임을 오픈함
public class LoginFrame extends DesignedJFrame{
	private JTextField emailField;
	private JPasswordField passwordField;
	// 로그인 성공한 경우 메인프레임의 로그인 관련 컴포넌트를 업데이트 하기위해 멤버로 메인프레임 객체를 가짐
	private MainFrame mainFrame;  
	public LoginFrame(MainFrame mainFrame) {
		super(500, 500, "로그인");
		
		DesignedContentPane contentPane = new DesignedContentPane(this);
		setContentPane(contentPane);
		
		// 메인 프레임 객체로 업데이트
		this.mainFrame = mainFrame; 
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] {200, 200};
		gridBagLayout.rowHeights = new int[] {100, 100, 50, 50};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0};
		contentPane.setLayout(gridBagLayout);

		// 컴포넌트 생성 및 초기화
		JLabel emailLabel = new JLabel("이메일:");
		emailLabel.setHorizontalAlignment(SwingConstants.CENTER);

		// 각 컴포넌트 프레임에 추가
		// 이메일 라벨
		GridBagConstraints gbc_emailLabel = new GridBagConstraints();
		// 이메일 라벨 레이아웃
		gbc_emailLabel.fill = GridBagConstraints.BOTH;
		gbc_emailLabel.insets = new Insets(0, 0, 5, 5);
		gbc_emailLabel.gridx = 0;
		gbc_emailLabel.gridy = 0;
		contentPane.add(emailLabel, gbc_emailLabel);
		
		// 이메일 입력 필드 생성
		emailField = new JTextField(20);
		// 이메일 입력 필드 레이아웃
		GridBagConstraints gbc_emailField = new GridBagConstraints();
		gbc_emailField.fill = GridBagConstraints.BOTH;
		gbc_emailField.insets = new Insets(0, 0, 5, 0);
		gbc_emailField.gridx = 1;
		gbc_emailField.gridy = 0;
		contentPane.add(emailField, gbc_emailField);

		// 비밀번호 라벨
		JLabel passwordLabel = new JLabel("비밀번호:");
		// 비밀번호 라벨 레이아웃
		passwordLabel.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_passwordLabel = new GridBagConstraints();
		gbc_passwordLabel.fill = GridBagConstraints.BOTH;
		gbc_passwordLabel.insets = new Insets(0, 0, 5, 5);
		gbc_passwordLabel.gridx = 0;
		gbc_passwordLabel.gridy = 1;
		contentPane.add(passwordLabel, gbc_passwordLabel);
		
		// 비밀번호 입력 필드 레이아웃
		passwordField = new JPasswordField(20);
		// 비밀번호 입력 필드 레이아웃
		GridBagConstraints gbc_passwordField = new GridBagConstraints();
		gbc_passwordField.fill = GridBagConstraints.BOTH;
		gbc_passwordField.insets = new Insets(0, 0, 5, 0);
		gbc_passwordField.gridx = 1;
		gbc_passwordField.gridy = 1;
		contentPane.add(passwordField, gbc_passwordField);
		
		// 로그인 버튼 생성
		JButton loginButton = new DesignedButton("로그인", COLOR.AQUA_ISLAND);
		loginButton.addActionListener(new LoginButtonListener()); // 로그인 리스너 등록
		// 로그인 버튼 레이아웃
		GridBagConstraints gbc_loginButton = new GridBagConstraints();
		gbc_loginButton.fill = GridBagConstraints.BOTH;
		gbc_loginButton.insets = new Insets(0, 0, 0, 5);
		gbc_loginButton.gridx = 0;
		gbc_loginButton.gridy = 3;
		contentPane.add(loginButton, gbc_loginButton);
		
		// 비번 초기화 버튼 생성
		JButton resetPasswordButton = new DesignedButton("비밀번호 초기화", COLOR.SUNFLOWER);
		resetPasswordButton.addActionListener(new ResetButtonListener()); // 비번 초기화 리스너 등록
		// 비번 초기화 버튼 레이아웃
		GridBagConstraints gbc_resetPasswordButton = new GridBagConstraints();
		gbc_resetPasswordButton.fill = GridBagConstraints.BOTH;
		gbc_resetPasswordButton.gridx = 1;
		gbc_resetPasswordButton.gridy = 3;
		
		// 컨텐트 팬에 추가
		contentPane.add(resetPasswordButton, gbc_resetPasswordButton);
		
		
		setVisible(true);
	}

	class LoginButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			String email = emailField.getText();
			String password = new String(passwordField.getPassword());
			boolean loginSuccess = true;
			String dialogMsg = "로그인 성공";	
			User userLoggedIn = new User();
			try {
				userLoggedIn = AccountManager.checklogin(email, password);	// 로그인 정보 유효성 확인					
			} catch (NullPointerException err) {					
				dialogMsg = err.getMessage();
				loginSuccess = false;					
			}	                
			JOptionPane.showMessageDialog(null, dialogMsg); // 로그인 실패, 성공 여부를 알려주는 팝업창 오픈
			if(loginSuccess) { // 로그인 성공한 경우	
				long startTime = System.currentTimeMillis(); 
				userLoggedIn.updateSolvedProblemList(); // 백준 해결한 문제 업데이트				
				mainFrame.logInComponents(userLoggedIn); // 메인 프레임 로그인관련 컴포넌트 업데이트 및 유저 인스턴스 전달
				dispose(); // 로그인 창 닫기				
				System.out.println(userLoggedIn.getUsername() + " 로그인 완료");				
				System.out.println(userLoggedIn);
				System.out.println("로그인 소요 시간 : " + (System.currentTimeMillis() - startTime) + "ms");
			}
		}
	}

	class ResetButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e2) {   
			// 비밀번호 초기화 1단계 유저이름, 이메일 확인용 프레임 생성
			new PasswordResetUsernameEmailCheckFrame(); 
		}
	}


	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginFrame frame = new LoginFrame(new MainFrame());
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}


}

