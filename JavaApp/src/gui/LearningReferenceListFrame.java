package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import problems.LearningReference;
import problems.Problem;
import users.User;

public class LearningReferenceListFrame extends DesignedJFrame {
	
	private JPanel infopanel, referencelistpanel, referencebuttonpanel;
	private JLabel problemName, frameTitle;
	private ArrayList<LearningReference> referenceList;
	private JTable referencetable;
	private JScrollPane referencelistscrollPane;
	private DesignedButton referenceAddbtn, referenceBackbtn;
	
	public LearningReferenceListFrame(Problem problem, User user) { //������
		// ������ ����
		setTitle("LearningReferenceViewFrame");
		DesignedContentPane background = new DesignedContentPane(this);
		setContentPane(background);
		background.setLayout(null);
		
		addInfoPanel(problem); // �⺻�����г� �߰�
		addLearningReferenceListPanel(problem); // �н��ڷ� ����Ʈ �г� �߰�
		addLearningReferenceButtonPanel(problem, user); // �н��ڷ� ��ư �г� �߰�
		
		setVisible(true);
	}
	
	public void addInfoPanel(Problem problem) { // �⺻���� 
		// �⺻���� �г� ����
		infopanel = new JPanel(); 
		infopanel.setLayout(new GridLayout(2,1));
		infopanel.setLocation(120, 50);
		infopanel.setSize(750, 100); 
		
		// �⺻����
		problemName = new JLabel(problem.getProblemName()); // ���� ����
		frameTitle = new JLabel("�н� �ڷ� ����Ʈ");
		// ��Ʈ ����
		problemName.setFont(new Font("Sunflower Medium", Font.BOLD,35));
		frameTitle.setFont(new Font("Sunflower Medium",Font.PLAIN,18));
		
		// �����ӿ� �߰�
		infopanel.add(problemName);
		infopanel.add(frameTitle);
		infopanel.setOpaque(false);
		getContentPane().add(infopanel);

	} 
	
	
	public void addLearningReferenceListPanel(Problem problem) { // �н��ڷ� ����Ʈ
		// �н��ڷ� ����Ʈ �г�
		referencelistpanel = new JPanel();
		referencelistpanel.setBorder(new LineBorder(Color.black, 2));
		referencelistpanel.setLayout(new GridLayout(1,1)); 
		referencelistpanel.setLocation(120,150); // ��ġ
		referencelistpanel.setSize(getDefalutWindowWidth() - 200, 500); // ũ��
		
		// null ���� ��� ����Ʈ �ʱ�ȭ
		referenceList = problem.getProblemReferences();
		referenceList = (referenceList != null) ? referenceList : new ArrayList<LearningReference>();

		// �н��ڷ� ����
		int row = problem.getProblemReferences().size();
		String header[] = {"�ۼ���", "�н��ڷ� ����"};
		String contentlist[][] = new String[row][2];
		
		for (int i = 0; i < row; i++) {
	         contentlist[i][0] = referenceList.get(i).getWriter().getUsername(); // �ۼ���
	         contentlist[i][1] = referenceList.get(i).getTitle(); // ����
	    }
		
		// �н��ڷ� ���̺� ǥ ����
		DefaultTableModel dtm = new DefaultTableModel(contentlist, header) { 
			public boolean isCellEditable(int row, int  column) {
		        return false; // ���̺� ���� �����Ұ����ϵ���
		      }
		}; 
		
		// �н��ڷ� ���̺� ����
		referencetable = new JTable(dtm);
		referencetable.getColumnModel().getColumn(0).setPreferredWidth(120); //ù��° �� ũ�� ����
		referencetable.getColumnModel().getColumn(1).setPreferredWidth(referencelistpanel.getWidth()-120); //�ι�° �� ũ�� ����
		referencetable.setRowHeight(50); // ���� ����
		referencetable.setFont(new Font("Sunflower Medium",Font.PLAIN,15));
		// ���̺� ��� ����
		JTableHeader referenceHeader = referencetable.getTableHeader();
		referenceHeader.setBackground(COLOR.AQUA_ISLAND.getColor()); // ���� ����
		referenceHeader.setFont(new Font("Sunflower Medium",Font.BOLD,18)); // ��Ʈ����
		Dimension headerSize = referencetable.getTableHeader().getPreferredSize(); 
		headerSize.height = 40; // ��� ���� ����
		referencetable.getTableHeader().setPreferredSize(headerSize);
		
		// ���콺 Ŭ�� �̺�Ʈ ó���� ���� MouseAdapter �߰�
		referencetable.addMouseListener(new MouseAdapter() {
			@Override
            public void mouseClicked(MouseEvent e) {
            	if(e.getClickCount() == 2) {
            		//����Ŭ�� ó��
            		// ���콺 Ŭ���� ��� �� �ε��� ��������
	                int row = referencetable.rowAtPoint(e.getPoint());
	                int col = referencetable.columnAtPoint(e.getPoint());
	
	                // ���õ� ���� �� ��������	                
	                String userName = (String) referencetable.getValueAt(row, 0);
	                
	                // â ����
	                new LearningReferenceViewFrame(problem, row, userName);
            	}
                
            }
        });
		
		// �����ӿ� �߰�
		referencelistscrollPane = new JScrollPane(referencetable); // ��ũ����
		referencelistpanel.add(referencelistscrollPane);
		referencelistpanel.setOpaque(false);
		getContentPane().add(referencelistpanel);
		
	}
 
	public void addLearningReferenceButtonPanel(Problem problem, User user) { // �н��ڷ��ۼ���ư �г�
		// ��ư �г� ����
		referencebuttonpanel = new JPanel();
		referencebuttonpanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 30, 10)); 
		referencebuttonpanel.setLocation(850, 670); // ��ġ
		referencebuttonpanel.setSize(380, 70); // ũ��
		
		// �н��ڷ� �ۼ���ư
		referenceAddbtn = new DesignedButton("�ۼ��ϱ�", 150, 40, COLOR.MEDIUM_SLATE_BLUE);
		referenceAddbtn.setLocation(getDefalutWindowWidth() - 230, 680);
		referenceAddbtn.setSize(150, 40);
		
		// �͸�Ŭ���� �н��ڷ��ۼ���ư ������
		referenceAddbtn.addActionListener (new ActionListener() { 
			public void actionPerformed(ActionEvent e) {					
				new LearningReferenceAddFrame(problem, user);
				dispose();
			}
		});
		
		// �ڷΰ��� ��ư
		referenceBackbtn = new DesignedButton("�ڷΰ���", 150, 40, COLOR.AQUA_ISLAND);
		referenceBackbtn.setLocation(getDefalutWindowWidth() - 370, 680);
		referenceBackbtn.setSize(120, 40);
		
		//�͸�Ŭ���� �н��ڷḮ��Ʈ �ڷΰ����ư ������
		referenceBackbtn.addActionListener (new ActionListener() { 
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		
		//�����ӿ� �߰�
		referencebuttonpanel.add(referenceAddbtn); // �߰� ��ư
		referencebuttonpanel.add(referenceBackbtn); // �ڷΰ��� ��ư
		referencebuttonpanel.setOpaque(false);
		getContentPane().add(referencebuttonpanel);
	}
}