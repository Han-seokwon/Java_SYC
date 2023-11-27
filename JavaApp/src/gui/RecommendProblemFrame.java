package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import problems.Problem;
import problems.ProblemDBManager;
import users.User;


public class RecommendProblemFrame extends DesignedJFrame {
	private static final long serialVersionUID = 1L;
	private DesignedContentPane contentPane;
	private JButton recommendByRankButton, recommendByAlgorithmTypeButton ; // ���̵��� ��õ ��ư, �˰��� ��õ ��ư
	private JLabel seletedBtnDisplayLabel; 	// ���õ� ��õ��� ��ư ����� �����ִ� ��
	private ProblemTable recommendedProblemTable;// ��õ�� ������ ��� ���̺�
	private User user;// ���� �α��ε� ����

	public RecommendProblemFrame(User user) {	
		super("���� ��õ");
		this.user= user; // ���� �α��ε� ����
		
		// contentPane ���̾ƿ� ����
		contentPane = new DesignedContentPane();
		contentPane.setBorder(new EmptyBorder(20, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{getWindowWidth()};
		gbl_contentPane.rowHeights = new int[] {300, getWindowHeight() - 300};
		contentPane.setLayout(gbl_contentPane);

		// ���� ��õ �޴� ���̾ƿ� ����
		JPanel recommendMenuPanel = new JPanel();
		GridBagConstraints gbc_recommendMenuPanel = new GridBagConstraints();
		gbc_recommendMenuPanel.fill = GridBagConstraints.BOTH;
		contentPane.add(recommendMenuPanel, gbc_recommendMenuPanel);
		GridBagLayout gbl_recommendMenuPanel = new GridBagLayout();
		final int MENU_PANEL_WIDTH = 300;
		gbl_recommendMenuPanel.columnWidths = new int[] {MENU_PANEL_WIDTH, MENU_PANEL_WIDTH, MENU_PANEL_WIDTH};
		gbl_recommendMenuPanel.rowHeights = new int[] {MENU_PANEL_WIDTH/2};
		recommendMenuPanel.setLayout(gbl_recommendMenuPanel);

		// ��õ��� ���� ��ư�� �г� �߰�
		JPanel recommendMethodButtonPanel = new JPanel();
		recommendMethodButtonPanel.setBorder(createCompoundBorder(5, 20)); // ��輱 ����
		GridBagConstraints gbc_recommendMethodButtonPanel = new GridBagConstraints();
		gbc_recommendMethodButtonPanel.fill = GridBagConstraints.BOTH;
		recommendMenuPanel.add(recommendMethodButtonPanel, gbc_recommendMethodButtonPanel);
		// ��õ��� ���� ��ư�� �г� ���̾ƿ� ����
		GridBagLayout gbl_recommendMethodButtonPanel = new GridBagLayout();
		gbl_recommendMethodButtonPanel.columnWidths = new int[]{250, 0};
		gbl_recommendMethodButtonPanel.rowHeights = new int[] {50, 50, 50, 0};
		recommendMethodButtonPanel.setLayout(gbl_recommendMethodButtonPanel);

		// �˰��� ��õ ��ư �߰�
		recommendByAlgorithmTypeButton = new DesignedButton("�˰��� ��õ", COLOR.CHET_WODE_BLUE);
		recommendByAlgorithmTypeButton.addActionListener(new ButtonActionListener()); // �̺�Ʈ ������ �߰�
		// �˰��� ��õ ��ư ���̾ƿ� ����
		GridBagConstraints gbc_recommendByAlgorithmTypeButton = new GridBagConstraints();
		gbc_recommendByAlgorithmTypeButton.fill = GridBagConstraints.BOTH;
		gbc_recommendByAlgorithmTypeButton.gridx = 0;
		gbc_recommendByAlgorithmTypeButton.gridy = 0;
		recommendMethodButtonPanel.add(recommendByAlgorithmTypeButton, gbc_recommendByAlgorithmTypeButton);


		// ���̵��� ��õ ��ư �߰�
		recommendByRankButton = new DesignedButton("���̵��� ��õ", COLOR.SUNFLOWER);
		recommendByRankButton.addActionListener(new ButtonActionListener()); // �̺�Ʈ ������ �߰�		
		// ���̵��� ��õ ��ư ���̾ƿ� ����
		GridBagConstraints gbc_recommendByRankButton = new GridBagConstraints();
		gbc_recommendByRankButton.fill = GridBagConstraints.BOTH;
		gbc_recommendByRankButton.gridx = 0;
		gbc_recommendByRankButton.gridy = 2;
		recommendMethodButtonPanel.add(recommendByRankButton, gbc_recommendByRankButton);

		// ȭ��ǥ �̹��� �� �߰�
		ImageIcon arrowImg = new ImageIcon(RecommendProblemFrame.class.getResource("/sources/right-arrow.png"));
		int imgWidth = (int)(MENU_PANEL_WIDTH*2/3);
		Image resizedImg = arrowImg.getImage().getScaledInstance(imgWidth, imgWidth/2, Image.SCALE_SMOOTH); // ������ ����		
		JLabel arrowImgLabel = new JLabel(new ImageIcon(resizedImg)); 
		arrowImgLabel.setBounds(0, 0, 150, 150);
		GridBagConstraints gbc_arrowImgLabel = new GridBagConstraints();
		gbc_arrowImgLabel.gridx = 1;
		gbc_arrowImgLabel.gridy = 0;
		recommendMenuPanel.add(arrowImgLabel, gbc_arrowImgLabel);

		// ���õ� ��õ��� ��ư ����� �����ִ� ��
		seletedBtnDisplayLabel = new JLabel("���� ��õ����� �������ּ���.");
		seletedBtnDisplayLabel.setHorizontalAlignment(SwingConstants.CENTER);
		seletedBtnDisplayLabel.setBorder(createCompoundBorder(5, 20)); // ��輱 ����
		GridBagConstraints gbc_seletedBtnDisplayLabel = new GridBagConstraints();
		gbc_seletedBtnDisplayLabel.fill = GridBagConstraints.BOTH;
		gbc_seletedBtnDisplayLabel.gridx = 2;
		gbc_seletedBtnDisplayLabel.gridy = 0;
		recommendMenuPanel.add(seletedBtnDisplayLabel, gbc_seletedBtnDisplayLabel);

		// ��õ�� ���� ����Ʈ�� ��� �г� ���̾ƿ� ����
		JPanel recommendedProblemListPanel = new JPanel();
		recommendedProblemListPanel.setBorder(createCompoundBorder(5, 20)); // ��輱 ����
		GridBagConstraints gbc_recommendedProblemListPanel = new GridBagConstraints();
		gbc_recommendedProblemListPanel.gridx = 0;
		gbc_recommendedProblemListPanel.gridy = 1;
		contentPane.add(recommendedProblemListPanel, gbc_recommendedProblemListPanel);
		GridBagLayout gbl_recommendedProblemListPanel = new GridBagLayout();
		gbl_recommendedProblemListPanel.columnWidths = new int[] {900, 0};
		gbl_recommendedProblemListPanel.rowHeights = new int[] {50, 400};
		recommendedProblemListPanel.setLayout(gbl_recommendedProblemListPanel);

		// ��������Ʈ�� ������ �� �߰�		
		JLabel subtitleLabel = new JLabel("��õ�� ���� ����Ʈ");
		subtitleLabel.setFont(new Font("����", Font.PLAIN, 25));
		subtitleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_subtitleLabel = new GridBagConstraints();
		gbc_subtitleLabel.fill = GridBagConstraints.BOTH;
		recommendedProblemListPanel.add(subtitleLabel, gbc_subtitleLabel);

		// ��������Ʈ�� ��ũ���� �߰�
		JScrollPane scrollPane = new JScrollPane();
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 0;
		gbc_scrollPane.gridy = 1;
		recommendedProblemListPanel.add(scrollPane, gbc_scrollPane);

		recommendedProblemTable = new ProblemTable(user); // ��õ�� �������� ��� ���̺� ����
		scrollPane.setViewportView(recommendedProblemTable);

		
		contentPane.applyFontAndBackgroundToAllComponents(); // ��ü ��Ʈ ���� �� �г� ���� �����ϰ� ����
		setVisible(true); // ������ show
	}

	// ��輱 ����
	private CompoundBorder createCompoundBorder(int borderWidth, int borderPadding) {
		return new CompoundBorder(new LineBorder(new Color(0, 0, 0), borderWidth, true),
				new EmptyBorder(borderPadding, borderPadding, borderPadding, borderPadding));
	}

	class ButtonActionListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {	
			
			List<Problem> recommendedProblemList = new ArrayList<>();

			JButton selectedButton = (JButton)e.getSource(); 
			if(selectedButton== recommendByAlgorithmTypeButton) {
				
				System.out.println("�˰��� ��õ ��ư  clicked!");
				// �׽�Ʈ�� -> ���� ����
				ArrayList<String> temp = new ArrayList<>(Arrays.asList("����"));
				recommendedProblemList = ProblemDBManager.findProblemAlgorithm(temp);
				// recommendedProblemList = RecommendProblem.�޼����̸� // Ŭ���� �ϼ��� �߰�
				
			} else if(selectedButton == recommendByRankButton) {				
				System.out.println("���̵��� ��õ ��ư clicked!");
				// �׽�Ʈ�� -> ���� ����
				ArrayList<String> temp = new ArrayList<>(Arrays.asList("��"));
				recommendedProblemList = ProblemDBManager.findProblemAlgorithm(temp);			
				// recommendedProblemList = RecommendProblem.�޼����̸�(user) // Ŭ���� �ϼ��� �߰�
			}
			seletedBtnDisplayLabel.setText(selectedButton.getText()); // Ŭ���� ��ư�� �°� �� ������ ����
			recommendedProblemTable.updateProblemListToTable(recommendedProblemList); // ��õ�� ��������Ʈ�� ���̺� �߰�
		}
	}


}
