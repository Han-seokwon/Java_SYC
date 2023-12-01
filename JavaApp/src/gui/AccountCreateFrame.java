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

// ���� ������ �����ϴ� ������
public class AccountCreateFrame extends DesignedJFrame{

	// ������Ʈ ����
	private JTextField nameField, solvedAcNameField, emailField, resetPwAnswerField;
	private JPasswordField passwordField, passwordConfirmField;
	private JComboBox<String> resetPwQuestionComboBox; 
	private String[] resetPwQuestionList; // ��� �ʱ�ȭ�� ������ ������ ����Ʈ -> �޺��ڽ��� ��ȯ
	private MainFrame mainFrame;
	// ������
	public AccountCreateFrame(MainFrame mainFrame) {
		super(900, 900, "ȸ������");
		this.mainFrame = mainFrame;
		
		DesignedContentPane contentPane = new DesignedContentPane(this);
		contentPane.setBorder(new EmptyBorder(50, 50, 50, 50));
		setContentPane(contentPane);		
		
		// ������Ʈ ���� �� �ʱ�ȭ
		// �� ����
		JLabel nameLabel = new JLabel("*����(Ư�� ���� �Է� �Ұ�): ");
		nameLabel.setHorizontalAlignment(SwingConstants.CENTER);
		
		JLabel emailLabel = new JLabel("*�̸���:");
		emailLabel.setHorizontalAlignment(SwingConstants.CENTER);
		
		JLabel solvedNameLabel = new JLabel("*solved.ac ������ �̸�:");
		solvedNameLabel.setHorizontalAlignment(SwingConstants.CENTER);
		
		JLabel passwordLabel = new JLabel("*��й�ȣ(����, ���� ���� 8�ڸ� �̻�): ");
		passwordLabel.setHorizontalAlignment(SwingConstants.CENTER);
		
		JLabel passwordConfirmLabel = new JLabel("*��й�ȣ Ȯ��:");
		passwordConfirmLabel.setHorizontalAlignment(SwingConstants.CENTER);
		
		JLabel resetPwQuestionLabel = new JLabel("*��й�ȣ �ʱ�ȭ ����:");
		resetPwQuestionLabel.setHorizontalAlignment(SwingConstants.CENTER);
		
		JLabel resetPwAnswerLabel = new JLabel("*�亯 : "); 
		resetPwAnswerLabel.setHorizontalAlignment(SwingConstants.CENTER);
		
		// �Է� �ʵ� ����
		nameField = new JTextField(20);
		solvedAcNameField = new JTextField(20); // solved.ac�� ��ϵ� ������ �̸�
		emailField = new JTextField(20);
		passwordField = new JPasswordField(20);
		passwordConfirmField = new JPasswordField(20);

		// ������ ����� �ؽ�Ʈ ���Ͽ��� List<String>���·� �� �������� �޾ƿ��� �̸� �迭�� ��ȯ
		String formatFilepath = "\\users\\resetPwQuestions.txt";
		resetPwQuestionList = FileManager.readLinesFromFile(formatFilepath).toArray(new String[0]);
		resetPwQuestionComboBox = new JComboBox<>(resetPwQuestionList);

		resetPwAnswerField = new JTextField(20);
		JButton submitButton = new DesignedButton("���", COLOR.SUNFLOWER);
		// ��� ��ư�� �̺�Ʈ �ڵ鷯 ����
		submitButton.addActionListener( new SubmitButtonListener());

		// ���̾ƿ�
		contentPane.setLayout(new GridLayout(8, 2, 50, 50));

		// �� ������Ʈ �����ӿ� �߰�
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

		contentPane.add(new CancelButton()); // ��ҹ�ư �߰�
		contentPane.add(submitButton); 

		contentPane.applyFontAndBackgroundToAllComponents(); // ��ü ��Ʈ ���� �� �г� ���� �����ϰ� ����
		setVisible(true);
	}


	class SubmitButtonListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			// �Էµ� �ʵ��� �����͸� ���� ȸ���������� Ŭ���� ����
			ResistrationFormat format = new ResistrationFormat( // �̸�, solvedac�̸�, �̸���, ���, ���Ȯ��, ����ʱ�ȭ����, �亯
					nameField.getText(), solvedAcNameField.getText(), emailField.getText(),
					new String(passwordField.getPassword()), new String(passwordConfirmField.getPassword()),
					resetPwQuestionList[resetPwQuestionComboBox.getSelectedIndex()], resetPwAnswerField.getText());

			try {
				format.isVaildFormat(); // ȸ������ ���� ��ȿ�� Ȯ��
			} catch (IOException err) { // ȸ������ ������ ��ȿ���� ���� ��� (���� ����)
				Dialog.showAlertDialog("ȸ������ ����", err.getMessage());
				return; // ������ ����
			} 
			// ȸ�� ���� ������ ��ȿ�� ���
			User newUser; // ������ ����
			try {
				newUser = AccountManager.createAccount(format); // ���� ���� �� DB�� ����
			} catch (IOException e2) { // �ý��� �󿡼� ������ �������� ���ϰų� DB�� �������� ���ϴ� ��� (�ý��� ����)
				System.out.println(e2.getMessage()); // ���� �޽��� ���	
				Dialog.showAlertDialog("ȸ������ ����", Dialog.USER_FILE_SAVING_ERROR);
				return; // ������ ����
			}
			// ���� ������ ���������� DB�� ����� ���
			Dialog.showInfoDialog("ȸ������ ����", "���������� ȸ����ϵǾ����ϴ�.\n �ذ��� ������ ������Ʈ �ϰ� �������縦 �����մϴ�."); // ���Ϸα� ����
			mainFrame.logInComponents(newUser); // ���� ������ �α��� ������Ʈ ������Ʈ
			
			int solvedProblemCnt_added = 0;
			try { // ���ؿ��� �ذ��� ���� ������Ʈ
				solvedProblemCnt_added = newUser.updateSolvedProblemList(); 				
			} catch (IOException e2) { // �ý��� �󿡼� ���� �����͸� DB�� �������� ���ϴ� ��� (�ý��� ����)
				System.out.println(e2.getMessage()); // ���� �޽��� ���	
				Dialog.showAlertDialog("���� ������ �ֽ�ȭ ����", Dialog.USER_FILE_SAVING_ERROR);					
			}
			if(solvedProblemCnt_added > 0) { // �ذ��� ������ �ִ� ���
				Dialog.showInfoDialog("�ذ��� ���� ������Ʈ ����", String.format("%d���� �ذ��� ������ ������Ʈ �Ǿ����ϴ�.", solvedProblemCnt_added));
			} 			
			
			new SurveyFrame(newUser);// ���� ���� ������ ����
			dispose(); // â ����
				
		}
	}	
}

