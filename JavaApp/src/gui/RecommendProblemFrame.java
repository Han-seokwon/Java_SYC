package gui;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

import problems.Problem;
import problems.ProblemDBManager;
import users.User;


// ���̺� ��� ������ �����ϴ� ������
enum TABLE_HEADER{ 
	ID(0, "���� ��ȣ"), TITLE(1, "���� ����"), RANK(2, "���̵�"), URL(3, "URL");

	private int idx; // ��� �ε���
	private String name; // ��� �̸�
	private TABLE_HEADER(int idx, String name) {
		this.idx = idx;
		this.name = name;
	}
	public int getIdx() {
		return idx;
	}
	public String getName() {
		return name;
	}
	// ������(������)�� ������ ��ȯ 
	public static int length() {
		return TABLE_HEADER.values().length;
	}
	// ������(������)�� ���ڿ� �̸��� ���ͷ� ��ȯ, DefaultTableModel���� �÷��̸�(���)�� Vector�� ����
	public static Vector<String> getHeaderNames() {
		Vector<String> headerNames = new Vector<>(TABLE_HEADER.length());
		for(TABLE_HEADER header : TABLE_HEADER.values()) {
			headerNames.add(header.getName());
		}
		return headerNames;    	
	}
}


public class RecommendProblemFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JButton recommendByRankButton, recommendByAlgorithmTypeButton ; // ���̵��� ��õ ��ư, �˰��� ��õ ��ư
	private JLabel seletedBtnDisplayLabel; 	// ���õ� ��õ��� ��ư ����� �����ִ� ��
	private ProblemTable recommendedProblemTable;//��õ ���� ���̺�
	// ���̺� �������� �����͸� �߰��ϱ� ���� �� ��ü ����
	private DefaultTableModel tableModel = new DefaultTableModel(TABLE_HEADER.getHeaderNames(), 0) { // ���̺� ���, row ����
		@Override
		public boolean isCellEditable(int row, int column) { return true; } // �� ���� ������ �� ���� �������̵�
	};  
	private User user;// ���� �α��ε� ����
	private ArrayList<Problem> recommendedProblemList = new ArrayList<>(); // ��õ�� ���� ����Ʈ

	public RecommendProblemFrame(User user) {	
		this.user= user; // ���� �α��ε� ����

		// ������ �Ӽ� ����
		final int FRAME_WIDTH = 1200;
		final int FRAME_HEIGHT = 800;
		setTitle("���� ��õ");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, FRAME_WIDTH, FRAME_HEIGHT);
		
		// contentPane ���̾ƿ� ����
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{FRAME_WIDTH};
		final int CONTENT_ROW_HEIGHT = 550;
		gbl_contentPane.rowHeights = new int[] {FRAME_HEIGHT - FRAME_HEIGHT, CONTENT_ROW_HEIGHT};
		contentPane.setLayout(gbl_contentPane);

		// ���� ��õ �޴� ���̾ƿ� ����
		JPanel recommendMenuPanel = new JPanel();
		contentPane.add(recommendMenuPanel);
		GridBagLayout gbl_recommendMenuPanel = new GridBagLayout();
		gbl_recommendMenuPanel.columnWidths = new int[] {300, 300, 300};
		gbl_recommendMenuPanel.rowHeights = new int[] {150};
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
		recommendByAlgorithmTypeButton = new JButton("�˰��� ��õ");
		recommendByAlgorithmTypeButton.addActionListener(new ButtonActionListener()); // �̺�Ʈ ������ �߰�
		// �˰��� ��õ ��ư ���̾ƿ� ����
		GridBagConstraints gbc_recommendByAlgorithmTypeButton = new GridBagConstraints();
		gbc_recommendByAlgorithmTypeButton.fill = GridBagConstraints.BOTH;
		gbc_recommendByAlgorithmTypeButton.gridx = 0;
		gbc_recommendByAlgorithmTypeButton.gridy = 0;
		recommendMethodButtonPanel.add(recommendByAlgorithmTypeButton, gbc_recommendByAlgorithmTypeButton);


		// ���̵��� ��õ ��ư �߰�
		recommendByRankButton = new JButton("���̵��� ��õ");
		recommendByRankButton.addActionListener(new ButtonActionListener()); // �̺�Ʈ ������ �߰�		
		// ���̵��� ��õ ��ư ���̾ƿ� ����
		GridBagConstraints gbc_recommendByRankButton = new GridBagConstraints();
		gbc_recommendByRankButton.fill = GridBagConstraints.BOTH;
		gbc_recommendByRankButton.gridx = 0;
		gbc_recommendByRankButton.gridy = 2;
		recommendMethodButtonPanel.add(recommendByRankButton, gbc_recommendByRankButton);

		// ȭ��ǥ �̹��� �� �߰�
		JLabel arrowImgLabel = new JLabel("->"); // ���߿� �̹����� ��ü
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

		recommendedProblemTable = new ProblemTable(user); // ���̺� ���̺� �� ����
		scrollPane.setViewportView(recommendedProblemTable);

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
			recommendedProblemTable.clearTableRows(); // ������ �Էµ� ���̺� �� �ʱ�ȭ
			List<Problem> recommendedProblemList = new ArrayList<>();

			JButton selectedButton = (JButton)e.getSource(); 
			if(selectedButton== recommendByAlgorithmTypeButton) { 
				System.out.println("�˰��� ��õ ��ư  clicked!");
				// �׽�Ʈ�� -> ���� ����
				recommendedProblemList = ProblemDBManager.findProblemSearch("����");
				System.out.println(recommendedProblemList);

				//				recommendedProblemList = RecommendProblem.�޼����̸� // Ŭ���� �ϼ��� �߰�
			} else if(selectedButton == recommendByRankButton) {				
				System.out.println("���̵��� ��õ ��ư clicked!");
				// �׽�Ʈ�� -> ���� ����
				recommendedProblemList = ProblemDBManager.findProblemSearch("�Լ�");
				System.out.println(recommendedProblemList);				
				// recommendedProblemList = RecommendProblem.�޼����̸�(user) // Ŭ���� �ϼ��� �߰�
			}
			seletedBtnDisplayLabel.setText(selectedButton.getText()); // Ŭ���� ��ư�� �°� �� ������ ����
			recommendedProblemTable.addProblemListToTable(recommendedProblemList); // ��õ�� ��������Ʈ�� ���̺� �߰�

		}
	}


}
