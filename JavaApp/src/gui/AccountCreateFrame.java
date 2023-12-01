package gui;

import java.awt.EventQueue;
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
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

// 계정 생성을 진행하는 페이지
public class AccountCreateFrame extends DesignedJFrame{

	// 컴포넌트 선언
	private JTextField nameField, solvedAcNameField, emailField, resetPwAnswerField;
	private JPasswordField passwordField, passwordConfirmField;
	private JComboBox<String> resetPwQuestionComboBox; 
	private String[] resetPwQuestionList; // 비번 초기화용 질문을 저장할 리스트 -> 콤보박스로 변환
	private MainFrame mainFrame;
	// 생성자
	public AccountCreateFrame(MainFrame mainFrame) {
		super(900, 900, "회원가입");
		this.mainFrame = mainFrame;
		
		DesignedContentPane contentPane = new DesignedContentPane(this);
		contentPane.setBorder(new EmptyBorder(50, 50, 50, 50));
		setContentPane(contentPane);		
		
		// 컴포넌트 생성 및 초기화
		// 라벨 생성
		JLabel nameLabel = new JLabel("*성명(특수 문자 입력 불가): ");
		nameLabel.setHorizontalAlignment(SwingConstants.CENTER);
		
		JLabel emailLabel = new JLabel("*이메일:");
		emailLabel.setHorizontalAlignment(SwingConstants.CENTER);
		
		JLabel solvedNameLabel = new JLabel("*solved.ac 프로필 이름:");
		solvedNameLabel.setHorizontalAlignment(SwingConstants.CENTER);
		
		JLabel passwordLabel = new JLabel("*비밀번호(문자, 숫자 포함 8자리 이상): ");
		passwordLabel.setHorizontalAlignment(SwingConstants.CENTER);
		
		JLabel passwordConfirmLabel = new JLabel("*비밀번호 확인:");
		passwordConfirmLabel.setHorizontalAlignment(SwingConstants.CENTER);
		
		JLabel resetPwQuestionLabel = new JLabel("*비밀번호 초기화 질문:");
		resetPwQuestionLabel.setHorizontalAlignment(SwingConstants.CENTER);
		
		JLabel resetPwAnswerLabel = new JLabel("*답변 : "); 
		resetPwAnswerLabel.setHorizontalAlignment(SwingConstants.CENTER);
		
		// 입력 필드 생성
		nameField = new JTextField(20);
		solvedAcNameField = new JTextField(20); // solved.ac에 등록된 프로필 이름
		emailField = new JTextField(20);
		passwordField = new JPasswordField(20);
		passwordConfirmField = new JPasswordField(20);

		// 질문이 저장된 텍스트 파일에서 List<String>형태로 각 질문들을 받아오고 이를 배열로 변환
		String formatFilepath = "\\users\\resetPwQuestions.txt";
		resetPwQuestionList = FileManager.readLinesFromFile(formatFilepath).toArray(new String[0]);
		resetPwQuestionComboBox = new JComboBox<>(resetPwQuestionList);

		resetPwAnswerField = new JTextField(20);
		JButton submitButton = new DesignedButton("등록", COLOR.SUNFLOWER);
		// 등록 버튼에 이벤트 핸들러 적용
		submitButton.addActionListener( new SubmitButtonListener());

		// 레이아웃
		contentPane.setLayout(new GridLayout(8, 2, 50, 50));

		// 각 컴포넌트 프레임에 추가
		contentPane.add(nameLabel);
		contentPane.add(nameField);
		contentPane.add(solvedNameLabel);
		contentPane.add(solvedAcNameField);
		contentPane.add(emailLabel);
		contentPane.add(emailField);
		contentPane.add(passwordLabel);
		contentPane.add(passwordField);
		contentPane.add(passwordConfirmLabel);
		contentPane.add(passwordConfirmField);

		contentPane.add(resetPwQuestionLabel);
		contentPane.add(resetPwQuestionComboBox);   
		contentPane.add(resetPwAnswerLabel);
		contentPane.add(resetPwAnswerField);   

		contentPane.add(new CancelButton()); // 취소버튼 추가
		contentPane.add(submitButton); 

		contentPane.applyFontAndBackgroundToAllComponents(); // 전체 폰트 적용 및 패널 배경색 투명하게 적용
		setVisible(true);
	}


	class SubmitButtonListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			// 입력된 필드의 데이터를 토대로 회원가입정보 클래스 생성
			ResistrationFormat format = new ResistrationFormat( // 이름, solvedac이름, 이메일, 비번, 비번확인, 비번초기화질문, 답변
					nameField.getText(), solvedAcNameField.getText(), emailField.getText(),
					new String(passwordField.getPassword()), new String(passwordConfirmField.getPassword()),
					resetPwQuestionList[resetPwQuestionComboBox.getSelectedIndex()], resetPwAnswerField.getText());

			try {
				format.isVaildFormat(); // 회원가입 정보 유효성 확인
			} catch (IOException err) { // 회원가입 정보가 유효하지 않은 경우 (유저 원인)
				Dialog.showAlertDialog("회원가입 실패", err.getMessage());
				return; // 리스너 종료
			} 
			// 회원 가입 정보가 유효한 경우
			User newUser; // 생성된 유저
			try {
				newUser = AccountManager.createAccount(format); // 유저 생성 및 DB에 저장
			} catch (IOException e2) { // 시스템 상에서 유저를 생성하지 못하거나 DB에 저장하지 못하는 경우 (시스템 원인)
				System.out.println(e2.getMessage()); // 에러 메시지 출력	
				Dialog.showAlertDialog("회원가입 실패", Dialog.USER_FILE_SAVING_ERROR);
				return; // 리스너 종료
			}
			// 유저 정보가 정상적으로 DB에 저장된 경우
			Dialog.showInfoDialog("회원가입 성공", "정상적으로 회원등록되었습니다.\n 해결한 문제를 업데이트 하고 설문조사를 시작합니다."); // 다일로그 생성
			mainFrame.logInComponents(newUser); // 메인 프레임 로그인 컴포넌트 업데이트
			
			int solvedProblemCnt_added = 0;
			try { // 백준에서 해결한 문제 업데이트
				solvedProblemCnt_added = newUser.updateSolvedProblemList(); 				
			} catch (IOException e2) { // 시스템 상에서 유저 데이터를 DB에 저장하지 못하는 경우 (시스템 원인)
				System.out.println(e2.getMessage()); // 에러 메시지 출력	
				Dialog.showAlertDialog("유저 데이터 최신화 실패", Dialog.USER_FILE_SAVING_ERROR);					
			}
			if(solvedProblemCnt_added > 0) { // 해결한 문제가 있는 경우
				Dialog.showInfoDialog("해결한 문제 업데이트 성공", String.format("%d개의 해결한 문제가 업데이트 되었습니다.", solvedProblemCnt_added));
			} 			
			
			new SurveyFrame(newUser);// 설문 조사 프레임 생성
			dispose(); // 창 닫음
				
		}
	}	
}

