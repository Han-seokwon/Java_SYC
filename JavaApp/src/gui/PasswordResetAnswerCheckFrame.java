package gui;

import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import users.User;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.SwingConstants;
//비밀번호 초기화 과정 1단계 : 유저이름, 이메일 확인 -> 2단계 : 초기화 질문에 대한 답변 확인 -> 3 단계 : 새롭게 변경할 비밀번호 입력

// << 비밀번호 초기화 과정 2단계 >>
// 유저이름, 이메일을 토대로 확인된 사용자의 비번 초기화 질문을 가져와 답변을 입력받는 프레임
public class PasswordResetAnswerCheckFrame extends DesignedJFrame {

	private String resetQuestion, resetAnswer;
	private JTextField answerField;
	private JButton nextButton;
	private User user;

	// 현재 비밀번호를 초기화하려는 User 인스턴스를 인자로 받음
	public PasswordResetAnswerCheckFrame(User user) {   
		super(500, 500, "비밀번호 초기화");
		this.user = user; 

		DesignedContentPane contentPane = new DesignedContentPane(this);
		setContentPane(contentPane);

		// 컨텐트팬 레이아웃
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] {20, 200, 200};
		gridBagLayout.rowHeights = new int[] {100, 50, 50};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 0.0};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0};
		contentPane.setLayout(gridBagLayout);
		
		resetQuestion = user.getPwResetQuestion(); // 등록된 질문을 가져옴
		resetAnswer = user.getPwResetAnswer(); // 등록된 답변 가져옴

		// 질문 라벨 
		JLabel resetQuestionJLabel = new JLabel("질문: " + resetQuestion);
		// 질문 라벨 레이아웃
		resetQuestionJLabel.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_resetQuestionJLabel = new GridBagConstraints();
		gbc_resetQuestionJLabel.fill = GridBagConstraints.BOTH;
		gbc_resetQuestionJLabel.insets = new Insets(0, 0, 5, 5);
		gbc_resetQuestionJLabel.gridx = 1;
		gbc_resetQuestionJLabel.gridy = 0;
		contentPane.add(resetQuestionJLabel, gbc_resetQuestionJLabel);

		// 답변 필드
		answerField = new JTextField();
		// 답변 필드 레이아웃
		GridBagConstraints gbc_answerField = new GridBagConstraints();
		gbc_answerField.fill = GridBagConstraints.BOTH;
		gbc_answerField.insets = new Insets(0, 0, 5, 0);
		gbc_answerField.gridx = 2;
		gbc_answerField.gridy = 0;
		contentPane.add(answerField, gbc_answerField);

		// 취소 버튼
		CancelButton cancelButton = new CancelButton();
		// 취소 버튼 레이아웃
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.BOTH;
		gbc.insets = new Insets(0, 0, 0, 5);
		gbc.gridx = 1;
		gbc.gridy = 2;
		contentPane.add(cancelButton, gbc); 

		// 다음 버튼
		nextButton = new DesignedButton("다음", COLOR.SUNFLOWER);
		nextButton.addActionListener(new CheckAnswerListener()); // 다음 버튼에 이벤트 핸들러 적용
		// 다음 버튼 레이아웃
		GridBagConstraints gbc_nextButton = new GridBagConstraints();
		gbc_nextButton.fill = GridBagConstraints.BOTH;
		gbc_nextButton.gridx = 2;
		gbc_nextButton.gridy = 2;
		contentPane.add(nextButton, gbc_nextButton);

		contentPane.applyFontAndBackgroundToAllComponents();  // 전체 폰트 적용 및 패널 배경색 투명하게 적용
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
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PasswordResetAnswerCheckFrame frame = new PasswordResetAnswerCheckFrame(new User("qwer", "hoh9170", "qwr@na.com", "d1dd", "1. 좋아하는 동물은?", "사자"));
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}

