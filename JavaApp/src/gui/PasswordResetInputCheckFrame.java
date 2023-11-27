package gui;

import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.SwingConstants;

import users.AccountManager;
import users.PasswordManager;
import users.User;

//	 ��й�ȣ �ʱ�ȭ ���� 3�ܰ�� �����̸�, �̸���, ��� �ʱ�ȭ �亯�� ��� ������ ����ڰ� ���������� ���Ӱ� ������ ��й�ȣ�� �Է��ϴ� ������
public class PasswordResetInputCheckFrame extends DesignedJFrame{	
	private JPasswordField newPasswordField;
	private JPasswordField passwordConfirmField;
	private JButton resetButton;
	private User user;
	// ���� ��й�ȣ�� �ʱ�ȭ�Ϸ��� User �ν��Ͻ��� ���ڷ� ����
	public PasswordResetInputCheckFrame(User user) {
		super(500, 500, "��й�ȣ �ʱ�ȭ");
		this.user = user;

		// ����Ʈ�� ����
		DesignedContentPane contentPane = new DesignedContentPane(this);
		setContentPane(contentPane);
		// ����Ʈ�� ���̾ƿ�
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[] {30, 200, 200};
		gbl_contentPane.rowHeights = new int[] {100, 100, 50, 50};
		gbl_contentPane.columnWeights = new double[]{0.0, 0.0, 0.0};
		gbl_contentPane.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0};
		contentPane.setLayout(gbl_contentPane);


		// ���ο� ��й�ȣ ��
		JLabel newPasswordLabel = new JLabel("���ο� ��й�ȣ:");
		// ���ο� ��й�ȣ ���̾ƿ�
		newPasswordLabel.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_newPasswordLabel = new GridBagConstraints();
		gbc_newPasswordLabel.fill = GridBagConstraints.BOTH;
		gbc_newPasswordLabel.insets = new Insets(0, 0, 5, 5);
		gbc_newPasswordLabel.gridx = 1;
		gbc_newPasswordLabel.gridy = 0;
		contentPane.add(newPasswordLabel, gbc_newPasswordLabel);
		
		// ���ο� ��й�ȣ �Է� �ʵ�
		newPasswordField = new JPasswordField();
		GridBagConstraints gbc_newPasswordField = new GridBagConstraints();
		gbc_newPasswordField.fill = GridBagConstraints.BOTH;
		gbc_newPasswordField.insets = new Insets(0, 0, 5, 5);
		gbc_newPasswordField.gridx = 2;
		gbc_newPasswordField.gridy = 0;
		contentPane.add(newPasswordField, gbc_newPasswordField);

		// ��й�ȣ Ȯ�� ��
		JLabel confirmPasswordLabel = new JLabel("��й�ȣ Ȯ��:");
		confirmPasswordLabel.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_confirmPasswordLabel = new GridBagConstraints();
		gbc_confirmPasswordLabel.fill = GridBagConstraints.BOTH;
		gbc_confirmPasswordLabel.insets = new Insets(0, 0, 5, 5);
		gbc_confirmPasswordLabel.gridx = 1;
		gbc_confirmPasswordLabel.gridy = 1;
		contentPane.add(confirmPasswordLabel, gbc_confirmPasswordLabel);

		resetButton = new DesignedButton("��й�ȣ �ʱ�ȭ", COLOR.SUNFLOWER);
		// �ʱ�ȭ ��ư�� �̺�Ʈ �ڵ鷯 ����
		resetButton.addActionListener(new CheckNewPasswordListener());
		passwordConfirmField = new JPasswordField();
		GridBagConstraints gbc_passwordConfirmField = new GridBagConstraints();
		gbc_passwordConfirmField.fill = GridBagConstraints.BOTH;
		gbc_passwordConfirmField.insets = new Insets(0, 0, 5, 5);
		gbc_passwordConfirmField.gridx = 2;
		gbc_passwordConfirmField.gridy = 1;
		contentPane.add(passwordConfirmField, gbc_passwordConfirmField);
		
		
		// ��ҹ�ư ���̾ƿ�
		GridBagConstraints gbc_cancelButton = new GridBagConstraints();
		gbc_cancelButton.fill = GridBagConstraints.BOTH;
		gbc_cancelButton.insets = new Insets(0, 0, 0, 5);
		gbc_cancelButton.gridx = 1;
		gbc_cancelButton.gridy = 3;

		// ��� ��ư �߰�
		contentPane.add(new CancelButton(), gbc_cancelButton);
		
		// �ʱ�ȭ ��ư
		GridBagConstraints gbc_resetButton = new GridBagConstraints();
		gbc_resetButton.insets = new Insets(0, 0, 0, 5);
		gbc_resetButton.fill = GridBagConstraints.BOTH;
		gbc_resetButton.gridx = 2;
		gbc_resetButton.gridy = 3;
		contentPane.add(resetButton, gbc_resetButton);

		contentPane.applyFontAndBackgroundToAllComponents();  // ��ü ��Ʈ ���� �� �г� ���� �����ϰ� ����
		setVisible(true);

	}

	class CheckNewPasswordListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			boolean isValidPw = true;
			String newPassword = new String(newPasswordField.getPassword());
			String newPasswordConfirm = new String(passwordConfirmField.getPassword());

			try {// �ʱ�ȭ ��й�ȣ ��ȿ�� Ȯ��
				AccountManager.checkPasswordVaildity(newPassword, newPasswordConfirm);
			} catch (IOException err) {
				JOptionPane.showMessageDialog(null, err.getMessage(), "��й�ȣ �ʱ�ȭ ����", JOptionPane.WARNING_MESSAGE);
				isValidPw = false;
			} 
			if(isValidPw) {
				//				 ��й�ȣ �ʱ�ȭ�� �Ϸ�Ǿ��ٴ� �˾�â�� ���� ��й�ȣ�� ������Ʈ��
				JOptionPane.showMessageDialog(null, "��й�ȣ�� �ʱ�ȭ�Ǿ����ϴ�.", "��й�ȣ �ʱ�ȭ ����", JOptionPane.INFORMATION_MESSAGE);
				PasswordManager.updatePassword(user, newPassword);// ��й�ȣ ������Ʈ
				dispose(); 
			}
		}
	}

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PasswordResetInputCheckFrame frame = new PasswordResetInputCheckFrame(new User("qwer", "hoh9170", "qwr@na.com", "d1dd", "1. �����ϴ� ������?", "����"));
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

}


