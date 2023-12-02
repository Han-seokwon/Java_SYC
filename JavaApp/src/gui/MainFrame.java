package gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import file.FileManager;
import users.User;

// 프로그램 홈페이지
public class MainFrame extends DesignedJFrame{
	// 컴포넌트 선언
	private JLabel usernameLabel; // 로그인한 경우 유저 이름을 표시하는 라벨
	private JButton accountCreateButton, loginButton, problemListButton, problemRecommendButton, myPageButton;
	private User user = new User();
	private boolean isLoggedin = false;

	/* 
	 * 사용자가 로그인한 경우 메인페이지의 계정관련 컴포넌트를 업데이트
	 * 1. 유저이름 display ,  2. 로그인 -> 로그아웃,  3. 계정생성 버튼 삭제 
	 * */
	public void logInComponents(User user) {
		isLoggedin = true;
		this.user = user;
		usernameLabel.setText(user.getUsername());		
		loginButton.setText("로그아웃");
		accountCreateButton.setVisible(false); // 회원가입 불가능
	}

	/* 
	 * 사용자가 로그아웃한 경우 메인페이지의 계정관련 컴포넌트를 업데이트
	 * 1. 유저이름 undisplay,  2. 로그아웃 -> 로그인 3. 계정 생성 버튼 활성화 
	 * */
	public void logOutComponents() {
		isLoggedin = false;
		this.user = new User();
		usernameLabel.setText("");		
		loginButton.setText("로그인");
		accountCreateButton.setVisible(true); // 회원가입 가능
	}

	// 생성자
	public MainFrame() {
		super();
		// 컨텐트팬 생성 및 설정
		DesignedContentPane contentPane = new DesignedContentPane(); // 디자인 적용된 컨텐트팬 생성		
		contentPane.setBorder(new EmptyBorder(50, 50, 50, 50));// 패딩 설정
		contentPane.setLayout(new BorderLayout(50, 50)); // 컨텐트 팬 레이아웃 설정
		setContentPane(contentPane);

		// 컴포넌트 생성 및 초기화
		usernameLabel = new JLabel();
		
		JLabel logoLabel = new JLabel("Core"); // 로고를 불러오지 못할 경우 텍스트 띄우기
		try {
			ImageIcon icon = new ImageIcon(getClass().getResource("/sources/icon.png"));
			logoLabel = new JLabel(icon);
		}catch (Exception e) {
			System.out.println("사이트 로고를 불러오지 못했습니다.");
		}
		JLabel introLabel = new JLabel("길 잃은 개발자여, 이 곳으로 오너라");
		introLabel.setHorizontalAlignment(SwingConstants.CENTER);

		accountCreateButton = new DesignedButton("계정 생성");
		accountCreateButton.addActionListener(new ButtonActionListener(this));

		loginButton = new DesignedButton("로그인");
		loginButton.addActionListener(new ButtonActionListener(this));

		problemListButton = new DesignedButton("문제 리스트 확인", COLOR.AQUA_ISLAND);
		problemListButton.addActionListener(new ButtonActionListener());

		problemRecommendButton = new DesignedButton("문제 추천 받기", COLOR.AQUA_ISLAND);
		problemRecommendButton.addActionListener(new ButtonActionListener());

		myPageButton = new DesignedButton("마이 페이지", COLOR.AQUA_ISLAND);
		myPageButton.addActionListener(new ButtonActionListener());

		// 계정 관련 버튼 담는 패널
		FlowLayout fl_accountPanel = new FlowLayout(FlowLayout.RIGHT); // 레이아웃
		fl_accountPanel.setHgap(50);
		JPanel accountPanel = new JPanel(fl_accountPanel);
		accountPanel.add(usernameLabel);
		accountPanel.add(accountCreateButton);
		accountPanel.add(loginButton);

		// 사이트 로고 및 소개글 담는 패널 (중앙 배치)
		JPanel introPanel = new JPanel(new FlowLayout()); // 레이아웃
		// introPanel 안의 패널
		JPanel innerPanel = new JPanel(new GridLayout(2,1));  
		innerPanel.add(logoLabel);
		innerPanel.add(introLabel);
		// introPanel에 innerPanel 패널 추가
		introPanel.add(innerPanel);

		// 사이트 기능 버튼 담는 패널		
		FlowLayout fl_functionPanel = new FlowLayout();
		fl_functionPanel.setHgap(100);
		JPanel functionPanel = new JPanel(fl_functionPanel);
		functionPanel.add(problemListButton);
		functionPanel.add(problemRecommendButton);
		functionPanel.add(myPageButton);

		// 컨텐트 팬 레이아웃 설정
		contentPane.setLayout(new BorderLayout(50, 50));

		// 각 컴포넌트 컨텐트 팬에 추가
		contentPane.add(introPanel, BorderLayout.CENTER);
		contentPane.add(accountPanel, BorderLayout.NORTH);
		contentPane.add(functionPanel, BorderLayout.SOUTH);

		// 폰트 설정
		contentPane.applyFontAndBackgroundToAllComponents(); // 전체 폰트 적용 및 패널 배경색 투명하게 적용
		// 소개글 폰트 설정
		try { 
			introLabel.setFont(FileManager.createFontFromFile("introFont").deriveFont(30f)); // 30 사이즈로 폰트 설정
		} catch (IOException e) { 
			System.out.println(e.getMessage());				
		}

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // 닫으면 프로그램 종료
		setVisible(true);
	}



	// 각 버튼 클릭 이벤트에 대한 리스너 처리
	class ButtonActionListener implements ActionListener{
		public MainFrame mainFrame;
		public ButtonActionListener() {}
		public ButtonActionListener(MainFrame mainFrame) {
			this.mainFrame = mainFrame;
		}
		@Override
		public void actionPerformed(ActionEvent e) {
			if(!isLoggedin) { // 로그인 안 된 경우
				if(e.getSource() == accountCreateButton) { // 계정 생성 버튼 클릭 
					new AccountCreateFrame(mainFrame); // 계정 생성 프레임 생성
				} else if (e.getSource() == loginButton) { // 로그인
					new LoginFrame(mainFrame);	// 로그인 프레임 생성				
				} else { // 문제 리스트 조회, 문제 추천, 마이페이지 버튼이 클릭
					JOptionPane.showMessageDialog(null, "로그인이 필요합니다.", "해당 기능 사용 불가", JOptionPane.WARNING_MESSAGE);	
				}
			} else { // 로그인 된 경우
				if (e.getSource() == loginButton) { // 로그아웃 버튼 클릭
					logOutComponents(); // 로그아웃
					
				} else if (e.getSource() == problemListButton) { // 문제 리스트 조회 버튼 클릭
					System.out.println("problemListButton clicked!");
					new ProblemViewerFrame(user);
					
				} else if (e.getSource() == problemRecommendButton) {  // 문제 추천 버튼 클릭
					System.out.println("problemRecommendButton clicked!");
					new RecommendProblemFrame(user); // 문제 추천 프레임 생성
					
				} else if (e.getSource() == myPageButton) { // 마이 페이지 버튼 클릭
					System.out.println("myPageButton clicked!");
					MyPage myPageFrame = new MyPage(mainFrame, user, true);
					myPageFrame.setVisible(true);
				}
			}
		}
	}
}
