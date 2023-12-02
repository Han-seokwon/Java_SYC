package gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import file.FileManager;
import users.User;

// ���α׷� Ȩ������
public class MainFrame extends DesignedJFrame{
	// ������Ʈ ����
	private JLabel usernameLabel; // �α����� ��� ���� �̸��� ǥ���ϴ� ��
	private JButton accountCreateButton, loginButton, problemListButton, problemRecommendButton, myPageButton;
	private User user = new User();
	private boolean isLoggedin = false;

	/* 
	 * ����ڰ� �α����� ��� ������������ �������� ������Ʈ�� ������Ʈ
	 * 1. �����̸� display ,  2. �α��� -> �α׾ƿ�,  3. �������� ��ư ���� 
	 * */
	public void logInComponents(User user) {
		isLoggedin = true;
		this.user = user;
		usernameLabel.setText(user.getUsername());		
		loginButton.setText("�α׾ƿ�");
		accountCreateButton.setVisible(false); // ȸ������ �Ұ���
	}

	/* 
	 * ����ڰ� �α׾ƿ��� ��� ������������ �������� ������Ʈ�� ������Ʈ
	 * 1. �����̸� undisplay,  2. �α׾ƿ� -> �α��� 3. ���� ���� ��ư Ȱ��ȭ 
	 * */
	public void logOutComponents() {
		isLoggedin = false;
		this.user = new User();
		usernameLabel.setText("");		
		loginButton.setText("�α���");
		accountCreateButton.setVisible(true); // ȸ������ ����
	}

	// ������
	public MainFrame() {
		super();
		// ����Ʈ�� ���� �� ����
		DesignedContentPane contentPane = new DesignedContentPane(); // ������ ����� ����Ʈ�� ����		
		contentPane.setBorder(new EmptyBorder(50, 50, 50, 50));// �е� ����
		contentPane.setLayout(new BorderLayout(50, 50)); // ����Ʈ �� ���̾ƿ� ����
		setContentPane(contentPane);

		// ������Ʈ ���� �� �ʱ�ȭ
		usernameLabel = new JLabel();
		
		JLabel logoLabel = new JLabel("Core"); // �ΰ� �ҷ����� ���� ��� �ؽ�Ʈ ����
		try {
			ImageIcon icon = new ImageIcon(getClass().getResource("/sources/icon.png"));
			logoLabel = new JLabel(icon);
		}catch (Exception e) {
			System.out.println("����Ʈ �ΰ� �ҷ����� ���߽��ϴ�.");
		}
		JLabel introLabel = new JLabel("�� ���� �����ڿ�, �� ������ ���ʶ�");
		introLabel.setHorizontalAlignment(SwingConstants.CENTER);

		accountCreateButton = new DesignedButton("���� ����");
		accountCreateButton.addActionListener(new ButtonActionListener(this));

		loginButton = new DesignedButton("�α���");
		loginButton.addActionListener(new ButtonActionListener(this));

		problemListButton = new DesignedButton("���� ����Ʈ Ȯ��", COLOR.AQUA_ISLAND);
		problemListButton.addActionListener(new ButtonActionListener());

		problemRecommendButton = new DesignedButton("���� ��õ �ޱ�", COLOR.AQUA_ISLAND);
		problemRecommendButton.addActionListener(new ButtonActionListener());

		myPageButton = new DesignedButton("���� ������", COLOR.AQUA_ISLAND);
		myPageButton.addActionListener(new ButtonActionListener());

		// ���� ���� ��ư ��� �г�
		FlowLayout fl_accountPanel = new FlowLayout(FlowLayout.RIGHT); // ���̾ƿ�
		fl_accountPanel.setHgap(50);
		JPanel accountPanel = new JPanel(fl_accountPanel);
		accountPanel.add(usernameLabel);
		accountPanel.add(accountCreateButton);
		accountPanel.add(loginButton);

		// ����Ʈ �ΰ� �� �Ұ��� ��� �г� (�߾� ��ġ)
		JPanel introPanel = new JPanel(new FlowLayout()); // ���̾ƿ�
		// introPanel ���� �г�
		JPanel innerPanel = new JPanel(new GridLayout(2,1));  
		innerPanel.add(logoLabel);
		innerPanel.add(introLabel);
		// introPanel�� innerPanel �г� �߰�
		introPanel.add(innerPanel);

		// ����Ʈ ��� ��ư ��� �г�		
		FlowLayout fl_functionPanel = new FlowLayout();
		fl_functionPanel.setHgap(100);
		JPanel functionPanel = new JPanel(fl_functionPanel);
		functionPanel.add(problemListButton);
		functionPanel.add(problemRecommendButton);
		functionPanel.add(myPageButton);

		// ����Ʈ �� ���̾ƿ� ����
		contentPane.setLayout(new BorderLayout(50, 50));

		// �� ������Ʈ ����Ʈ �ҿ� �߰�
		contentPane.add(introPanel, BorderLayout.CENTER);
		contentPane.add(accountPanel, BorderLayout.NORTH);
		contentPane.add(functionPanel, BorderLayout.SOUTH);

		// ��Ʈ ����
		contentPane.applyFontAndBackgroundToAllComponents(); // ��ü ��Ʈ ���� �� �г� ���� �����ϰ� ����
		// �Ұ��� ��Ʈ ����
		try { 
			introLabel.setFont(FileManager.createFontFromFile("introFont").deriveFont(30f)); // 30 ������� ��Ʈ ����
		} catch (IOException e) { 
			System.out.println(e.getMessage());				
		}

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // ������ ���α׷� ����
		setVisible(true);
	}



	// �� ��ư Ŭ�� �̺�Ʈ�� ���� ������ ó��
	class ButtonActionListener implements ActionListener{
		public MainFrame mainFrame;
		public ButtonActionListener() {}
		public ButtonActionListener(MainFrame mainFrame) {
			this.mainFrame = mainFrame;
		}
		@Override
		public void actionPerformed(ActionEvent e) {
			if(!isLoggedin) { // �α��� �� �� ���
				if(e.getSource() == accountCreateButton) { // ���� ���� ��ư Ŭ�� 
					new AccountCreateFrame(mainFrame); // ���� ���� ������ ����
				} else if (e.getSource() == loginButton) { // �α���
					new LoginFrame(mainFrame);	// �α��� ������ ����				
				} else { // ���� ����Ʈ ��ȸ, ���� ��õ, ���������� ��ư�� Ŭ��
					JOptionPane.showMessageDialog(null, "�α����� �ʿ��մϴ�.", "�ش� ��� ��� �Ұ�", JOptionPane.WARNING_MESSAGE);	
				}
			} else { // �α��� �� ���
				if (e.getSource() == loginButton) { // �α׾ƿ� ��ư Ŭ��
					logOutComponents(); // �α׾ƿ�
					
				} else if (e.getSource() == problemListButton) { // ���� ����Ʈ ��ȸ ��ư Ŭ��
					System.out.println("problemListButton clicked!");
					new ProblemViewerFrame(user);
					
				} else if (e.getSource() == problemRecommendButton) {  // ���� ��õ ��ư Ŭ��
					System.out.println("problemRecommendButton clicked!");
					new RecommendProblemFrame(user); // ���� ��õ ������ ����
					
				} else if (e.getSource() == myPageButton) { // ���� ������ ��ư Ŭ��
					System.out.println("myPageButton clicked!");
					MyPage myPageFrame = new MyPage(mainFrame, user, true);
					myPageFrame.setVisible(true);
				}
			}
		}
	}
}
