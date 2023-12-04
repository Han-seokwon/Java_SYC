package gui;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Map.Entry;

import gui.HintAddFrame;
import gui.LearningReferenceAddFrame;
import gui.LearningReferenceViewFrame;

import problems.Problem;
import problems.ProblemDBManager;
import users.User;

public class LearningReferenceListFrame extends DesignedJFrame {
	
	JPanel infopanel, referencelistpanel, referencebuttonpanel;
	JLabel problemName, frametitle;
	JTable Referencelist;
	JButton ReferenceAddbtn;
	
	public LearningReferenceListFrame(Problem problem, User user) { 
		setTitle("LearningReferenceViewFrame");
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		DesignedContentPane background = new DesignedContentPane(this);
		setContentPane(background);
		background.setLayout(null);
		
		addinfopanel(problem); // �⺻����
		addlearningreferenceListpanel(problem); // �н��ڷ� ����Ʈ
		addLearningReferenceButtonpanel(problem, user);
		
		setVisible(true);
	}
	
	public void addinfopanel(Problem problem) { // �⺻���� 
		infopanel = new JPanel(); 
		infopanel.setLayout(new GridLayout(2,1));
		infopanel.setLocation(120, 50);
		infopanel.setSize(750, 100); 
		
		JLabel problemName = new JLabel(problem.getProblemName()); // �����ʿ�
		JLabel title = new JLabel("�н� �ڷ� ����Ʈ");
		problemName.setFont(new Font("Sunflower Medium", Font.BOLD,35));
		title.setFont(new Font("Sunflower Medium",Font.PLAIN,18));
	
		infopanel.add(problemName);
		infopanel.add(title);
		infopanel.setOpaque(false);
		getContentPane().add(infopanel);

	} 
	
	
	public void addlearningreferenceListpanel(Problem problem) { // �н��ڷ� ����Ʈ
		referencelistpanel = new JPanel();
		referencelistpanel.setBorder(new LineBorder(Color.black, 2));
		referencelistpanel.setLayout(new GridLayout(1,1)); 
		referencelistpanel.setLocation(120,150); // ��ġ
		referencelistpanel.setSize(getDefalutWindowWidth() - 200, 500); // ũ��
		
		// null ���� ��� ����Ʈ �ʱ�ȭ
		ArrayList<String[]> referenceList = problem.getProblemReferences();
		referenceList = (referenceList != null) ? referenceList : new ArrayList<String[]>();

		// �н��ڷ� ����
		int row = problem.getProblemReferences().size();
		String header[] = {"�ۼ���", "�н��ڷ� ����"};
		String contentlist[][] = new String[row][2];
		
		
		for (int i = 0; i < row; i++) {
	         contentlist[i][0] = referenceList.get(i)[0]; // �ۼ���
	         contentlist[i][1] = referenceList.get(i)[1]; // ����
	    }
		
		// �н��ڷ� ǥ
		DefaultTableModel dtm = new DefaultTableModel(contentlist, header) { //�����Ұ����ϵ���
			public boolean isCellEditable(int row, int  column) {
		        return false; 
		      }
		}; 
		 
		JTable referencetable = new JTable(dtm);
		referencetable.getColumnModel().getColumn(0).setPreferredWidth(120); //ù��° �� ũ�� ����
		referencetable.getColumnModel().getColumn(1).setPreferredWidth(referencelistpanel.getWidth()-120); //�ι�° �� ũ�� ����
		referencetable.setRowHeight(50); // ���� ����  
		referencetable.setFont(new Font("Sunflower Medium",Font.PLAIN,15));
		// ���̺� ��� ����
		JTableHeader referenceHeader = referencetable.getTableHeader();
		referenceHeader.setBackground(COLOR.AQUA_ISLAND.getColor());
		referenceHeader.setBackground(COLOR.AQUA_ISLAND.getColor());
		referenceHeader.setFont(new Font("Sunflower Medium",Font.BOLD,18));
		Dimension headerSize = referencetable.getTableHeader().getPreferredSize();
		headerSize.height = 40;
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
		
		JScrollPane referencelistscrollPane = new JScrollPane(referencetable); // ��ũ����
		referencelistpanel.add(referencelistscrollPane);
		referencelistpanel.setOpaque(false);
		getContentPane().add(referencelistpanel);
		
	}
 
	public void addLearningReferenceButtonpanel(Problem problem, User user) { // �н��ڷ��ۼ���ư
		// �г� ����
		referencebuttonpanel = new JPanel();
		referencebuttonpanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 30, 10)); 
		referencebuttonpanel.setLocation(850, 670); // ��ġ
		referencebuttonpanel.setSize(380, 70); // ũ��
		// �н��ڷ� �ۼ���ư �߰�
		DesignedButton referenceAddbtn = new DesignedButton("�ۼ��ϱ�", 150, 40, COLOR.MEDIUM_SLATE_BLUE);
		referenceAddbtn.setLocation(getDefalutWindowWidth() - 230, 680);
		referenceAddbtn.setSize(150, 40);
		
		referenceAddbtn.addActionListener (new ActionListener() { // �͸�Ŭ���� �н��ڷ��ۼ���ư ������
			public void actionPerformed(ActionEvent e) {					
				new LearningReferenceAddFrame(problem, user);
				dispose();
			}
		});
		
		// �ڷΰ��� ��ư
		DesignedButton referencebackBtn = new DesignedButton("�ڷΰ���", 150, 40, COLOR.AQUA_ISLAND);
		referencebackBtn.setLocation(getDefalutWindowWidth() - 370, 680);
		referencebackBtn.setSize(120, 40);
		
		referencebackBtn.addActionListener (new ActionListener() { //�͸�Ŭ���� �н��ڷḮ��Ʈ �ݱ��ư ������
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		
		//�����ӿ� �߰�
		referencebuttonpanel.add(referenceAddbtn);
		referencebuttonpanel.add(referencebackBtn);
		referencebuttonpanel.setOpaque(false);
		getContentPane().add(referencebuttonpanel);
	}
}