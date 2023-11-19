package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTable;
import java.util.ArrayList;

import users.User;
import fetcher.JsonFetcher;
import file.FileManager;
import problems.Problem;
import problems.SolvedProblem;

public class MyPage extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField searchField;
	private MainFrame mainFrame;
	private User user = new User();
	private JTable table;
	
	// ������ ������
	public MyPage(MainFrame mainFrame, User user) {
		this.user = user;
		this.mainFrame = mainFrame;
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 699, 501);
		
		contentPane = new JPanel();
		contentPane.setBackground(UIManager.getColor("Button.darkShadow"));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		// "���� ȭ��" ��ư
		JButton btnNewButton = new JButton("���� ȭ��");
	    btnNewButton.setBackground(UIManager.getColor("Button.highlight"));
	    btnNewButton.addActionListener(new MyPageButtonActionListener(mainFrame));
		btnNewButton.setBounds(12, 24, 143, 43);
		contentPane.add(btnNewButton);
		
		//�˻� ��(�˻��ϸ� ���� user�����Ϳ��� �ٸ� ���� �����͸� ������
		searchField = new JTextField();
		searchField.setBounds(479, 24, 192, 43);
		contentPane.add(searchField);
		searchField.setColumns(10);
		
		
		// ���� �ڵ� �����ϸ鼭 ���̺� �𵨿� ������ �߰�
		// ��� ����� ���� �� ���� ���� �ɰ��� ������ �Դϴ�.
		DefaultTableModel model = new DefaultTableModel();
		model.addColumn("���� ID");

		ArrayList<Problem> solvedProblems = new ArrayList<>(user.getSolvedProblemList());

		StringBuilder problemIDs = new StringBuilder();
		for (Problem problem : solvedProblems) {
			problemIDs.append(problem.getProblemID()).append(" ");
		}

		Object[] rowData = {
				problemIDs.toString().trim()
		};
		/* Ex. ����Ʈ��  [12564, 15567, 153556, 12345, 21513, 54546]�� ������
		 * toString()���� " [12564, 15567, 153556, 12345, 21513, 54546]" �̷������� ��ȯ�̵ǰ�
		 * trim()�� ����Ͽ� ���ںκи� �и�
		 */
		model.addRow(rowData);

		// ���̺� �ʱ�ȭ ���Ŀ� setModel ȣ��
		table = new JTable(model);

		// table.setModel(model); // �� �κ��� �� �̻� �ʿ����� �ʽ��ϴ�
		table.setBounds(12, 255, 455, 197);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		//table.setTableHeader(null);
		table.setShowVerticalLines(false);
		table.setShowHorizontalLines(false);
		contentPane.add(table);

		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(12, 255, 455, 197);
		contentPane.add(scrollPane);

		// ���� ��� ����
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
		for (int i = 0; i < table.getColumnCount(); i++) {
			table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
		}
		
		// ����� �̸��� ��ũ
		JPanel panel = new JPanel();
		panel.setBounds(12, 109, 285, 43);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel UserName = new JLabel(user.getUsername());
		UserName.setFont(new Font("����", Font.PLAIN, 20));
		UserName.setBounds(12, 10, 159, 23);
		panel.add(UserName);
		
		JLabel UserRank = new JLabel(user.getRank().toString());
		System.out.println(user.getRank().toString());
		UserRank.setFont(new Font("����", Font.PLAIN, 20));
		UserRank.setBounds(183, 10, 90, 23);
		panel.add(UserRank);
		
		// ����� �Ұ�(��� ���������� ���� ����)
		JTextArea textArea = new JTextArea();
		textArea.setBounds(12, 157, 659, 93);
		contentPane.add(textArea);
		JButton Save = new JButton("����");
		Save.setBounds(574, 129, 97, 23);
		contentPane.add(Save);
		
		//���� �⼮�� ��
		JLabel lblNewLabel = new JLabel(String.valueOf(user.getConsecutiveActivityDate()) + "��");
		lblNewLabel.setFont(new Font("����", Font.PLAIN, 55));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(479, 256, 192, 196);
		contentPane.add(lblNewLabel);
	}
	class MyPageButtonActionListener implements ActionListener {
        private MainFrame mainFrame;

        public MyPageButtonActionListener(MainFrame mainFrame) {
            this.mainFrame = mainFrame;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            dispose(); // ���� MyPage ������ �ݱ�
            if (mainFrame != null) { // mainFrame�� null�� �ƴ� ���
            	
                mainFrame.setVisible(true);
            }
        }
    }
}