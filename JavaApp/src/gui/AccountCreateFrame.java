package gui;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import file.FileManager;
import users.AccountManager;
import users.ResistrationFormat;
import users.User;

// 계정 생성을 진행하는 페이지
public class AccountCreateFrame extends JFrame{

	// 컴포넌트 선언
	private JTextField nameField, solvedNameField, emailField, resetPwAnswerField;
	private JPasswordField passwordField, passwordConfirmField;
	private JComboBox<String> resetPwQuestionComboBox; 
	private String[] resetPwQuestionList; // 비번 초기화용 질문을 저장할 리스트 -> 콤보박스로 변환
	private MainFrame mainFrame;
	// 생성자
	public AccountCreateFrame(MainFrame mainFrame) {
		this.mainFrame = mainFrame;
		
		// 컴포넌트 생성 및 초기화
		JLabel nameLabel = new JLabel("<html>*성명: <br> (특수 문자 입력 불가)<html>");
		JLabel emailLabel = new JLabel("*이메일:");
		JLabel solvedNameLabel = new JLabel("*solved.ac 프로필 이름:");
		JLabel passwordLabel = new JLabel("<html>*비밀번호: <br> (문자, 숫자 포함 8자리 이상)<html>");
		JLabel passwordConfirmLabel = new JLabel("*비밀번호 확인:");
		JLabel resetPwQuestionLabel = new JLabel("*비밀번호 초기화 질문:");
		JLabel resetPwAnswerLabel = new JLabel("*답변 : "); 
		
		
		nameField = new JTextField(20);
		solvedNameField = new JTextField(20); // solved.ac에 등록된 프로필 이름
		emailField = new JTextField(20);
		passwordField = new JPasswordField(20);
		passwordConfirmField = new JPasswordField(20);

		String filepath = "\\users\\resetPwQuestions.txt";
		// 질문이 저장된 텍스트 파일에서 List<String>형태로 각 질문들을 받아오고 이를 배열로 변환
		resetPwQuestionList = FileManager.readLinesFromFile(filepath).toArray(new String[0]);
		resetPwQuestionComboBox = new JComboBox<>(resetPwQuestionList);

		resetPwAnswerField = new JTextField(20);
		JButton submitButton = new JButton("등록");
		// 등록 버튼에 이벤트 핸들러 적용
		submitButton.addActionListener( new SubmitButtonListener());

		// 레이아웃
		setLayout(new GridLayout(8, 2, 50, 50));

		// 각 컴포넌트 프레임에 추가
		add(nameLabel);
		add(nameField);
		add(solvedNameLabel);
		add(solvedNameField);
		add(emailLabel);
		add(emailField);
		add(passwordLabel);
		add(passwordField);
		add(passwordConfirmLabel);
		add(passwordConfirmField);

		add(resetPwQuestionLabel);
		add(resetPwQuestionComboBox);   
		add(resetPwAnswerLabel);
		add(resetPwAnswerField);   

		add(submitButton);		add(new CancelButton()); // 취소버튼 추가

		// 프레임 속성 설정
		setTitle("회원 가입");
		setSize(1000, 700);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); 
		setVisible(true);
	}


	class SubmitButtonListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			// 입력된 필드의 데이터를 토대로 회원가입정보 클래스 생성
			ResistrationFormat format = new ResistrationFormat( // 이름, solvedac이름, 이메일, 비번, 비번확인, 비번초기화질문, 답변
					nameField.getText(), solvedNameField.getText(), emailField.getText(),
					new String(passwordField.getPassword()), new String(passwordConfirmField.getPassword()),
					resetPwQuestionList[resetPwQuestionComboBox.getSelectedIndex()], resetPwAnswerField.getText());

			// 디버깅용 추후 삭제
			System.out.println(format.toString());


			boolean isVaildInput = true;
			String dialogMsg = "정상적으로 회원등록되었습니다.";
			try {
				// 회원가입 정보 유효성 확인
				AccountManager.registerInputCheck(format);
			} catch (IOException err) {
				dialogMsg = err.getMessage();		
				isVaildInput = false;
			} 
			
			int msgType = isVaildInput ? JOptionPane.INFORMATION_MESSAGE : JOptionPane.WARNING_MESSAGE;
			String title =  isVaildInput ? "회원가입 성공" : "회원가입 실패";
			JOptionPane.showMessageDialog(null, dialogMsg, title, msgType);	
			
			if(isVaildInput) {	// 회원 정보가 유효한 경우 
				// TODO : UserDB 폴더, 유저 해시맵 저장 실패에 대한 예외 처리하기 
				User newUser = new User(format); // 새로운 User 객체 생성
				AccountManager.createAccount(newUser);
				mainFrame.logInComponents(newUser); // 메인 프레임 로그인 컴포넌트 업데이트
				new SurveyFrame(newUser);// 설문 조사 프레임 생성
				dispose(); // 창 닫음
			}	
		}
	}
	
}














