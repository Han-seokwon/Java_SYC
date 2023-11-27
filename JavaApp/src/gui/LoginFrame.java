package gui;

import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import users.AccountManager;
import users.User;

// �α����� �����ϴ� ������, ��й�ȣ �ʱ�ȭ ��ư Ŭ���� �ʱ�ȭ�� �������� ������
public class LoginFrame extends DesignedJFrame{
	private JTextField emailField;
	private JPasswordField passwordField;
	// �α��� ������ ��� ������������ �α��� ���� ������Ʈ�� ������Ʈ �ϱ����� ����� ���������� ��ü�� ����
	private MainFrame mainFrame;  
	public LoginFrame(MainFrame mainFrame) {
		super(500, 500, "�α���");
		
		DesignedContentPane contentPane = new DesignedContentPane(this);
		setContentPane(contentPane);
		
		// ���� ������ ��ü�� ������Ʈ
		this.mainFrame = mainFrame; 
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] {200, 200};
		gridBagLayout.rowHeights = new int[] {100, 100, 50, 50};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0};
		contentPane.setLayout(gridBagLayout);

		// ������Ʈ ���� �� �ʱ�ȭ
		JLabel emailLabel = new JLabel("�̸���:");
		emailLabel.setHorizontalAlignment(SwingConstants.CENTER);

		// �� ������Ʈ �����ӿ� �߰�
		// �̸��� ��
		GridBagConstraints gbc_emailLabel = new GridBagConstraints();
		// �̸��� �� ���̾ƿ�
		gbc_emailLabel.fill = GridBagConstraints.BOTH;
		gbc_emailLabel.insets = new Insets(0, 0, 5, 5);
		gbc_emailLabel.gridx = 0;
		gbc_emailLabel.gridy = 0;
		contentPane.add(emailLabel, gbc_emailLabel);
		
		// �̸��� �Է� �ʵ� ����
		emailField = new JTextField(20);
		// �̸��� �Է� �ʵ� ���̾ƿ�
		GridBagConstraints gbc_emailField = new GridBagConstraints();
		gbc_emailField.fill = GridBagConstraints.BOTH;
		gbc_emailField.insets = new Insets(0, 0, 5, 0);
		gbc_emailField.gridx = 1;
		gbc_emailField.gridy = 0;
		contentPane.add(emailField, gbc_emailField);

		// ��й�ȣ ��
		JLabel passwordLabel = new JLabel("��й�ȣ:");
		// ��й�ȣ �� ���̾ƿ�
		passwordLabel.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_passwordLabel = new GridBagConstraints();
		gbc_passwordLabel.fill = GridBagConstraints.BOTH;
		gbc_passwordLabel.insets = new Insets(0, 0, 5, 5);
		gbc_passwordLabel.gridx = 0;
		gbc_passwordLabel.gridy = 1;
		contentPane.add(passwordLabel, gbc_passwordLabel);
		
		// ��й�ȣ �Է� �ʵ� ���̾ƿ�
		passwordField = new JPasswordField(20);
		// ��й�ȣ �Է� �ʵ� ���̾ƿ�
		GridBagConstraints gbc_passwordField = new GridBagConstraints();
		gbc_passwordField.fill = GridBagConstraints.BOTH;
		gbc_passwordField.insets = new Insets(0, 0, 5, 0);
		gbc_passwordField.gridx = 1;
		gbc_passwordField.gridy = 1;
		contentPane.add(passwordField, gbc_passwordField);
		
		// �α��� ��ư ����
		JButton loginButton = new DesignedButton("�α���", COLOR.AQUA_ISLAND);
		loginButton.addActionListener(new LoginButtonListener()); // �α��� ������ ���
		// �α��� ��ư ���̾ƿ�
		GridBagConstraints gbc_loginButton = new GridBagConstraints();
		gbc_loginButton.fill = GridBagConstraints.BOTH;
		gbc_loginButton.insets = new Insets(0, 0, 0, 5);
		gbc_loginButton.gridx = 0;
		gbc_loginButton.gridy = 3;
		contentPane.add(loginButton, gbc_loginButton);
		
		// ��� �ʱ�ȭ ��ư ����
		JButton resetPasswordButton = new DesignedButton("��й�ȣ �ʱ�ȭ", COLOR.SUNFLOWER);
		resetPasswordButton.addActionListener(new ResetButtonListener()); // ��� �ʱ�ȭ ������ ���
		// ��� �ʱ�ȭ ��ư ���̾ƿ�
		GridBagConstraints gbc_resetPasswordButton = new GridBagConstraints();
		gbc_resetPasswordButton.fill = GridBagConstraints.BOTH;
		gbc_resetPasswordButton.gridx = 1;
		gbc_resetPasswordButton.gridy = 3;
		
		// ����Ʈ �ҿ� �߰�
		contentPane.add(resetPasswordButton, gbc_resetPasswordButton);
		
		
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


	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginFrame frame = new LoginFrame(new MainFrame());
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}


}

