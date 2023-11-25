package gui;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import users.AccountManager;
import users.User;

// 로그인을 진행하는 프레임, 비밀번호 초기화 버튼 클릭시 초기화용 프레임을 오픈함
public class LoginFrame extends JFrame{
	private JTextField emailField;
	private JPasswordField passwordField;
	// 로그인 성공한 경우 메인프레임의 로그인 관련 컴포넌트를 업데이트 하기위해 멤버로 메인프레임 객체를 가짐
	private MainFrame mainFrame;  
	public LoginFrame(MainFrame mainFrame) {
		// 프레임 속성 설정
		setTitle("로그인");
		setSize(500, 500);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setLocationRelativeTo(null); // 창 가운데 위치
		
		// 메인 프레임 객체로 업데이트
		this.mainFrame = mainFrame; 
		
		// 컴포넌트 생성 및 초기화
		JLabel emailLabel = new JLabel("이메일:");
		emailField = new JTextField(20);

		JLabel passwordLabel = new JLabel("비밀번호:");
		passwordField = new JPasswordField(20);

		JButton loginButton = new JButton("로그인");
		JButton resetPasswordButton = new JButton("비밀번호 초기화");

		// 로그인, 비번 초기화 버튼에 이벤트 핸들러 적용
		loginButton.addActionListener(new LoginButtonListener());
		resetPasswordButton.addActionListener(new ResetButtonListener());

		// 레이아웃
		setLayout(new GridLayout(3, 2, 70, 70));

		// 각 컴포넌트 프레임에 추가
		add(emailLabel);
		add(emailField);
		add(passwordLabel);
		add(passwordField);
		add(loginButton);
		add(resetPasswordButton);
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




}
