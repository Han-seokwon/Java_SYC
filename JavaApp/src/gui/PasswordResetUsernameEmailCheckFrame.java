package gui;

import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import users.User;
import users.UserDBManager;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.SwingConstants;

// ��й�ȣ �ʱ�ȭ ���� 1�ܰ� : �����̸�, �̸��� Ȯ�� -> 2�ܰ� : �ʱ�ȭ ������ ���� �亯 Ȯ�� -> 3 �ܰ� : ���Ӱ� ������ ��й�ȣ �Է�

// << ��й�ȣ �ʱ�ȭ ���� 1�ܰ� >>
// �����̸�, �̸��ϸ� �Է��Ͽ� ��й�ȣ�� �ʱ�ȭ�� ����ڸ� ã�� ������
public class PasswordResetUsernameEmailCheckFrame extends DesignedJFrame {


	private JTextField usernameField;
	private JTextField emailField;
	private User user;
	public PasswordResetUsernameEmailCheckFrame() {
		super(500, 500, "��й�ȣ �ʱ�ȭ");


		// ����Ʈ�� ����
		DesignedContentPane contentPane = new DesignedContentPane(this);
		setContentPane(contentPane);
		// ����Ʈ��  ���̾ƿ�
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[] {50, 200, 200};
		gbl_contentPane.rowHeights = new int[] {100, 100, 50, 50};
		gbl_contentPane.columnWeights = new double[]{0.0, 0.0, 0.0};
		gbl_contentPane.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0};
		contentPane.setLayout(gbl_contentPane);

		// �����̸� ��
		JLabel usernameLabel = new JLabel("���� �̸�:");
		usernameLabel.setHorizontalAlignment(SwingConstants.CENTER);

		// �����̸� �� ���̾ƿ�
		GridBagConstraints gbc_usernameLabel = new GridBagConstraints();
		gbc_usernameLabel.fill = GridBagConstraints.BOTH;
		gbc_usernameLabel.insets = new Insets(0, 0, 5, 5);
		gbc_usernameLabel.gridx = 1;
		gbc_usernameLabel.gridy = 0;
		getContentPane().add(usernameLabel, gbc_usernameLabel);
		
		// �����̸� �Է� �ʵ�
		usernameField = new JTextField();
		// �����̸� �Է� �ʵ� ���̾ƿ�
		GridBagConstraints gbc_usernameField = new GridBagConstraints();
		gbc_usernameField.fill = GridBagConstraints.BOTH;
		gbc_usernameField.insets = new Insets(0, 0, 5, 0);
		gbc_usernameField.gridx = 2;
		gbc_usernameField.gridy = 0;
		getContentPane().add(usernameField, gbc_usernameField);
		
		// �̸��� ��
		JLabel emailLabel = new JLabel("�̸���:");
		// �̸��� �� ���̾ƿ�
		emailLabel.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_emailLabel = new GridBagConstraints();
		gbc_emailLabel.fill = GridBagConstraints.BOTH;
		gbc_emailLabel.insets = new Insets(0, 0, 5, 5);
		gbc_emailLabel.gridx = 1;
		gbc_emailLabel.gridy = 1;
		getContentPane().add(emailLabel, gbc_emailLabel);
		
		// �̸��� �Է� �ʵ�
		emailField = new JTextField();
		// �̸��� �Է� �ʵ� ���̾ƿ�
		GridBagConstraints gbc_emailField = new GridBagConstraints();
		gbc_emailField.fill = GridBagConstraints.BOTH;
		gbc_emailField.insets = new Insets(0, 0, 5, 0);
		gbc_emailField.gridx = 2;
		gbc_emailField.gridy = 1;
		getContentPane().add(emailField, gbc_emailField);
		
		
		// ��� ��ư ���̾ƿ�
		GridBagConstraints gbc_cancleButton = new GridBagConstraints();
		gbc_cancleButton.fill = GridBagConstraints.BOTH;
		gbc_cancleButton.insets = new Insets(0, 0, 5, 5);
		gbc_cancleButton.gridx = 1;
		gbc_cancleButton.gridy = 3;
		// ��� ��ư ���� �� �߰�
		getContentPane().add(new CancelButton(), gbc_cancleButton); 		

		// ���� ��ư ����
		JButton nextButton = new DesignedButton("����", COLOR.SUNFLOWER);
		nextButton.addActionListener(new CheckNameEmailListener());	// ���� ��ư�� �̺�Ʈ �ڵ鷯 ����
		// ���� ��ư ���̾ƿ�
		GridBagConstraints gbc_nextButton = new GridBagConstraints();
		gbc_nextButton.insets = new Insets(0, 0, 5, 0);
		gbc_nextButton.fill = GridBagConstraints.BOTH;
		gbc_nextButton.gridx = 2;
		gbc_nextButton.gridy = 3;
		getContentPane().add(nextButton, gbc_nextButton);	

		contentPane.applyFontAndBackgroundToAllComponents();  // ��ü ��Ʈ ���� �� �г� ���� �����ϰ� ����
		setVisible(true);

	}
	class CheckNameEmailListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			String username = usernameField.getText();
			String email = emailField.getText();
			if(UserDBManager.isEmailExist(email)) {
				// ������ �����ϴ� ���� Ȯ�������Ƿ� ����ó�� �ʿ����
				user = UserDBManager.findUserByEmail(email); // ���� ���� -> ������ �����ϹǷ� �ٸ� �����ӿ��� ������
				if( username.equals(user.getUsername())) {
					dispose();
					// ��й�ȣ �ʱ�ȭ�� ����, �亯 Ȯ�� ������ ����
					new PasswordResetAnswerCheckFrame(user); 				
				} else {
					Dialog.showAlertDialog( "���� ����", "���� �̸��� ��ġ���� �ʽ��ϴ�.");
				}
			} else {
				Dialog.showAlertDialog( "���� ����", "�ش� ������ �������� �ʽ��ϴ�.");			}

		}

	}
}
