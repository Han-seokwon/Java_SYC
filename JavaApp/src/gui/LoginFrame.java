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

// �α����� �����ϴ� ������, ��й�ȣ �ʱ�ȭ ��ư Ŭ���� �ʱ�ȭ�� �������� ������
public class LoginFrame extends JFrame{
	private JTextField emailField;
	private JPasswordField passwordField;
	// �α��� ������ ��� ������������ �α��� ���� ������Ʈ�� ������Ʈ �ϱ����� ����� ���������� ��ü�� ����
	private MainFrame mainFrame;  
	public LoginFrame(MainFrame mainFrame) {
		// ������ �Ӽ� ����
		setTitle("�α���");
		setSize(500, 500);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setLocationRelativeTo(null); // â ��� ��ġ
		
		// ���� ������ ��ü�� ������Ʈ
		this.mainFrame = mainFrame; 
		
		// ������Ʈ ���� �� �ʱ�ȭ
		JLabel emailLabel = new JLabel("�̸���:");
		emailField = new JTextField(20);

		JLabel passwordLabel = new JLabel("��й�ȣ:");
		passwordField = new JPasswordField(20);

		JButton loginButton = new JButton("�α���");
		JButton resetPasswordButton = new JButton("��й�ȣ �ʱ�ȭ");

		// �α���, ��� �ʱ�ȭ ��ư�� �̺�Ʈ �ڵ鷯 ����
		loginButton.addActionListener(new LoginButtonListener());
		resetPasswordButton.addActionListener(new ResetButtonListener());

		// ���̾ƿ�
		setLayout(new GridLayout(3, 2, 70, 70));

		// �� ������Ʈ �����ӿ� �߰�
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
			String dialogMsg = "�α��� ����";	
			User userLoggedIn = new User();
			try {
				userLoggedIn = AccountManager.checklogin(email, password);	// �α��� ���� ��ȿ�� Ȯ��					
			} catch (NullPointerException err) {					
				dialogMsg = err.getMessage();
				loginSuccess = false;					
			}	                
			JOptionPane.showMessageDialog(null, dialogMsg); // �α��� ����, ���� ���θ� �˷��ִ� �˾�â ����
			if(loginSuccess) { // �α��� ������ ���	
				long startTime = System.currentTimeMillis(); 
				userLoggedIn.updateSolvedProblemList(); // ���� �ذ��� ���� ������Ʈ				
				mainFrame.logInComponents(userLoggedIn); // ���� ������ �α��ΰ��� ������Ʈ ������Ʈ �� ���� �ν��Ͻ� ����
				dispose(); // �α��� â �ݱ�				
				System.out.println(userLoggedIn.getUsername() + " �α��� �Ϸ�");				
				System.out.println(userLoggedIn);
				System.out.println("�α��� �ҿ� �ð� : " + (System.currentTimeMillis() - startTime) + "ms");
			}
		}
	}

	class ResetButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e2) {   
			// ��й�ȣ �ʱ�ȭ 1�ܰ� �����̸�, �̸��� Ȯ�ο� ������ ����
			new PasswordResetUsernameEmailCheckFrame(); 
		}
	}




}
