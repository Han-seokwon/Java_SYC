package gui;

import java.awt.CardLayout;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import users.User;
//비밀번호 초기화 과정 1단계 : 유저이름, 이메일 확인 -> 2단계 : 초기화 질문에 대한 답변 확인 -> 3 단계 : 새롭게 변경할 비밀번호 입력

// << 비밀번호 초기화 과정 2단계 >>
// 유저이름, 이메일을 토대로 확인된 사용자의 비번 초기화 질문을 가져와 답변을 입력받는 프레임
public class PasswordResetAnswerCheckFrame extends JFrame {
	
	private String resetQuestion, resetAnswer;
	private JTextField answerField;
	private JButton nextButton;
	private User user;
	
	// 현재 비밀번호를 초기화하려는 User 인스턴스를 인자로 받음
	public PasswordResetAnswerCheckFrame(User user) {   
		this.user = user;
		
		resetQuestion = user.getPwResetQuestion(); // 등록된 질문을 가져옴
		resetAnswer = user.getPwResetAnswer(); // 등록된 답변 가져옴
		
		// 컴포넌트 생성 및 초기화
		JLabel resetQuestionJLabel = new JLabel("질문: " + resetQuestion);
		System.out.println(user.toString());
		
		answerField = new JTextField();
		nextButton = new JButton("다음");
		// 등록 버튼에 이벤트 핸들러 적용
		nextButton.addActionListener(new CheckAnswerListener());
		
		// 레이아웃
		setLayout(new GridLayout(3, 2, 70, 70));
		
		// 각 컴포넌트 프레임에 추가
		add(resetQuestionJLabel);
		add(answerField);
		add(new CancelButton()); 
		add(nextButton);
		
		// 프레임 속성 설정
		setTitle("비밀번호 초기화");
		setSize(500, 500);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); 
		setVisible(true);
	}
	
	class CheckAnswerListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			// 답변이 맞는지 확인
			if(resetAnswer.equals(answerField.getText())) {
				dispose();
				// 비밀번호 초기화 프레임 생성
				new PasswordResetInputCheckFrame(user);
			} else {
				System.out.println(resetAnswer);
				System.out.println(answerField.getText());
				
				JOptionPane.showMessageDialog(null, "답변이 틀립니다.", "인증 실패", JOptionPane.WARNING_MESSAGE);
			}
		}
	}

}
