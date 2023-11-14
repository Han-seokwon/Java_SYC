package gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import file.FileManager;
import gui.AccountCreateFrame.SubmitButtonListener;
import users.AccountManager;
import users.User;

public class MainFrame extends JFrame{
	// 컴포넌트 선언
	private JLabel usernameLabel;
	private JButton accountCreateButton, loginButton, problemListButton, problemRecommendButton, myPageButton;
	private User user = new User();
	private boolean isLoggedin = false;

	public void logInComponents(User user) {
		isLoggedin = true;
		this.user = user;
		usernameLabel.setText(user.getUsername());		
		loginButton.setText("로그아웃");
		accountCreateButton.setVisible(false); // 회원가입 불가능
	}
	public void logOutComponents() {
		isLoggedin = false;
		this.user = new User();
		usernameLabel.setText("");		
		loginButton.setText("로그인");
		accountCreateButton.setVisible(true); // 회원가입 가능
	}

	// 생성자
	public MainFrame() {
		String siteName = "사이트 이름";
		// 컴포넌트 생성 및 초기화
		usernameLabel = new JLabel();
		JLabel siteNameLabel = new JLabel(siteName);
		JLabel introLabel = new JLabel("사이트 소개 글");

		accountCreateButton = new JButton("계정 생성");
		accountCreateButton.addActionListener(new ButtonActionListener(this));

		loginButton = new JButton("로그인");
		loginButton.addActionListener(new ButtonActionListener(this));

		problemListButton = new JButton("문제 리스트 확인");
		problemListButton.addActionListener(new ButtonActionListener());

		problemRecommendButton = new JButton("문제 추천 받기");
		problemRecommendButton.addActionListener(new ButtonActionListener());

		myPageButton = new JButton("마이 페이지");
		myPageButton.addActionListener(new ButtonActionListener());

		// 계정 관련 버튼 담는 패널
		JPanel accountPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		accountPanel.add(usernameLabel);
		accountPanel.add(accountCreateButton);
		accountPanel.add(loginButton);

		// 사이트 로고 및 소개글 담는 패널 (중앙 배치)
		JPanel introPanel = new JPanel(new FlowLayout()); // GridLayout을 정중앙에 위치시키기 위한 패널 
		JPanel innerPanel = new JPanel(new GridLayout(2,1));
		innerPanel.add(siteNameLabel);
		innerPanel.add(introLabel);
		introPanel.add(innerPanel);

		// 사이트 기능 버튼 담는 패널		
		JPanel functionPanel = new JPanel(new FlowLayout());
		functionPanel.add(problemListButton);
		functionPanel.add(problemRecommendButton);
		functionPanel.add(myPageButton);

		// 레이아웃
		setLayout(new BorderLayout(50, 50));

		// 각 컴포넌트 프레임에 추가
		add(introPanel, BorderLayout.CENTER);
		add(accountPanel, BorderLayout.NORTH);
		add(functionPanel, BorderLayout.SOUTH);

		// 프레임 속성 설정
		setTitle(siteName);
		setSize(600, 400);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
		setVisible(true);
	}


	class ButtonActionListener implements ActionListener{
		public MainFrame mainFrame;
		public ButtonActionListener() {}
		public ButtonActionListener(MainFrame mainFrame) {
			this.mainFrame = mainFrame;
		}
		@Override
		public void actionPerformed(ActionEvent e) {
			if(!isLoggedin) { // 로그인 안 된 경우
				if(e.getSource() == accountCreateButton) { // 계정 생성 
					new AccountCreateFrame(mainFrame);				
				} else if (e.getSource() == loginButton) { // 로그인
					new LoginFrame(mainFrame);	// 로그인 프레임 생성				
				} else {
					JOptionPane.showMessageDialog(null, "로그인이 필요합니다.", "해당 기능 사용 불가", JOptionPane.WARNING_MESSAGE);	
				}
			} else { // 로그인 된 경우
				if (e.getSource() == loginButton) {
					logOutComponents(); // 로그아웃
				} else if (e.getSource() == problemListButton) {
					System.out.println("problemListButton clicked!");
				} else if (e.getSource() == problemRecommendButton) {
					System.out.println("problemRecommendButton clicked!");
				} else if (e.getSource() == myPageButton) {
					System.out.println("myPageButton clicked!");
				}
			}
		}
	}
}















