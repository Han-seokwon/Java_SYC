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
//��й�ȣ �ʱ�ȭ ���� 1�ܰ� : �����̸�, �̸��� Ȯ�� -> 2�ܰ� : �ʱ�ȭ ������ ���� �亯 Ȯ�� -> 3 �ܰ� : ���Ӱ� ������ ��й�ȣ �Է�

// << ��й�ȣ �ʱ�ȭ ���� 2�ܰ� >>
// �����̸�, �̸����� ���� Ȯ�ε� ������� ��� �ʱ�ȭ ������ ������ �亯�� �Է¹޴� ������
public class PasswordResetAnswerCheckFrame extends DesignedJFrame {

	private String resetQuestion, resetAnswer;
	private JTextField answerField;
	private JButton nextButton;
	private User user;

	// ���� ��й�ȣ�� �ʱ�ȭ�Ϸ��� User �ν��Ͻ��� ���ڷ� ����
	public PasswordResetAnswerCheckFrame(User user) {   
		super(500, 500, "��й�ȣ �ʱ�ȭ");
		this.user = user; 

		DesignedContentPane contentPane = new DesignedContentPane(this);
		setContentPane(contentPane);

		// ����Ʈ�� ���̾ƿ�
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] {20, 200, 200};
		gridBagLayout.rowHeights = new int[] {100, 50, 50};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 0.0};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0};
		contentPane.setLayout(gridBagLayout);
		
		resetQuestion = user.getPwResetQuestion(); // ��ϵ� ������ ������
		resetAnswer = user.getPwResetAnswer(); // ��ϵ� �亯 ������

		// ���� �� 
		JLabel resetQuestionJLabel = new JLabel("����: " + resetQuestion);
		// ���� �� ���̾ƿ�
		resetQuestionJLabel.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_resetQuestionJLabel = new GridBagConstraints();
		gbc_resetQuestionJLabel.fill = GridBagConstraints.BOTH;
		gbc_resetQuestionJLabel.insets = new Insets(0, 0, 5, 5);
		gbc_resetQuestionJLabel.gridx = 1;
		gbc_resetQuestionJLabel.gridy = 0;
		contentPane.add(resetQuestionJLabel, gbc_resetQuestionJLabel);

		// �亯 �ʵ�
		answerField = new JTextField();
		// �亯 �ʵ� ���̾ƿ�
		GridBagConstraints gbc_answerField = new GridBagConstraints();
		gbc_answerField.fill = GridBagConstraints.BOTH;
		gbc_answerField.insets = new Insets(0, 0, 5, 0);
		gbc_answerField.gridx = 2;
		gbc_answerField.gridy = 0;
		contentPane.add(answerField, gbc_answerField);

		// ��� ��ư
		CancelButton cancelButton = new CancelButton();
		// ��� ��ư ���̾ƿ�
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.BOTH;
		gbc.insets = new Insets(0, 0, 0, 5);
		gbc.gridx = 1;
		gbc.gridy = 2;
		contentPane.add(cancelButton, gbc); 

		// ���� ��ư
		nextButton = new DesignedButton("����", COLOR.SUNFLOWER);
		nextButton.addActionListener(new CheckAnswerListener()); // ���� ��ư�� �̺�Ʈ �ڵ鷯 ����
		// ���� ��ư ���̾ƿ�
		GridBagConstraints gbc_nextButton = new GridBagConstraints();
		gbc_nextButton.fill = GridBagConstraints.BOTH;
		gbc_nextButton.gridx = 2;
		gbc_nextButton.gridy = 2;
		contentPane.add(nextButton, gbc_nextButton);

		contentPane.applyFontAndBackgroundToAllComponents();  // ��ü ��Ʈ ���� �� �г� ���� �����ϰ� ����
		setVisible(true);
	}

	class CheckAnswerListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			// �亯�� �´��� Ȯ��
			if(resetAnswer.equals(answerField.getText())) {
				dispose();
				// ��й�ȣ �ʱ�ȭ ������ ����
				new PasswordResetInputCheckFrame(user);
			} else {
				System.out.println(resetAnswer);
				System.out.println(answerField.getText());

				JOptionPane.showMessageDialog(null, "�亯�� Ʋ���ϴ�.", "���� ����", JOptionPane.WARNING_MESSAGE);
			}
		}
	}
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PasswordResetAnswerCheckFrame frame = new PasswordResetAnswerCheckFrame(new User("qwer", "hoh9170", "qwr@na.com", "d1dd", "1. �����ϴ� ������?", "����"));
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}

