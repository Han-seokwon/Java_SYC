package gui;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import gui.HintAddFrame;
import gui.DesignedJFrame;
import gui.DesignedButton;

import problems.*;
import users.User;

public class HintViewFrame extends DesignedJFrame {
	
	private JPanel infopanel, hintListpanel, hintViewbuttonpanel;
	private JLabel problemName, frameTitle;
	private ArrayList<Hint> hintList;
	private JTable hintTable;
	private JScrollPane hintlistscrollPane;
	private DesignedButton hintAddbtn, hintBackbtn;
	private int step;
	
	// type : ������ ��Ʈ ���� ��ȣ
	public HintViewFrame(int type, Problem problem, User user) { // ������
		setTitle("HintViewFrame");
		DesignedContentPane background = new DesignedContentPane(this);
		setContentPane(background);
		background.setLayout(null);
		this.step = type;
		
		addInfoPanel(problem); // �⺻���� �г� �߰�
		addHintListPanel(problem, step); // ��Ʈ ����Ʈ �г� �߰�
		addHintViewButtonPanel(step, problem, user); // ��ư �г� �߰�
		setVisible(true);
		
	}
	
	public void addInfoPanel(Problem problem) { // �⺻���� 
		// �⺻���� �г�
		infopanel = new JPanel();
		infopanel.setLayout(new GridLayout(2,1));
		infopanel.setLocation(120, 50);
		infopanel.setSize(750, 100); 
		
		// �⺻����
		problemName = new JLabel(problem.getProblemName()); // ��������
		frameTitle = new JLabel("   STEP"+step+" ��Ʈ ����Ʈ"); // ������ �ȳ���
		// ��Ʈ����
		problemName.setFont(new Font("Sunflower Medium",Font.BOLD,35));
		frameTitle.setFont(new Font("Sunflower Medium",Font.PLAIN,18));
		
		// �⺻���� �г��� �����ӿ� �߰�
		infopanel.add(problemName);
		infopanel.add(frameTitle);
		infopanel.setOpaque(false);
		getContentPane().add(infopanel);
	} 
	
	
	public void addHintListPanel(Problem problem, int step) { // ��Ʈ ����Ʈ
		// ��Ʈ����Ʈ �г�
		hintListpanel = new JPanel();
		hintListpanel.setBorder(new LineBorder(Color.black, 2)); // �׵θ� ��
		hintListpanel.setLayout(new GridLayout(1,1)); 
		hintListpanel.setLocation(120,150); // ��ġ
		hintListpanel.setSize(getDefalutWindowWidth() - 200, 500); // ũ��
		
		// ��Ʈ ���
		hintList = problem.getProblemHint(step);
		// null ���� ��� ����Ʈ �ʱ�ȭ
		hintList = (hintList != null) ? hintList : new ArrayList<>();
		
		String header[] = {"�ۼ���", "STEP "+step+"  ��Ʈ ����"};
		String contentlist[][] = new String[hintList.size()][2];
		
		// ���̺� ��Ʈ ����Ʈ �߰�
		int row = 0;
		for(Hint hint : hintList) {
			contentlist[row][0] = hint.getWriter().getUsername();
			contentlist[row][1] = hint.getContent();
			row++;
		}
		
		DefaultTableModel dtm = new DefaultTableModel(contentlist, header) { //�����Ұ����ϵ���
			public boolean isCellEditable(int row, int  column) {
		        return false; //���̺� ���� �����Ұ����ϵ���
		      }
		};
		
		//��Ʈ ����Ʈ ���̺� ����
		hintTable = new JTable(dtm);
		hintTable.getColumnModel().getColumn(0).setPreferredWidth(120); //ù��° �� ũ�� ����
		hintTable.getColumnModel().getColumn(1).setPreferredWidth(hintListpanel.getWidth()-120); //�ι�° �� ũ�� ����
		hintTable.setRowHeight(50); // ���� ����  
		hintTable.setFont(new Font("Sunflower Medium",Font.PLAIN,15)); // ��Ʈ ����
		// ���̺� ��� ����
		JTableHeader hintlistHeader = hintTable.getTableHeader();
		hintlistHeader.setBackground(COLOR.AQUA_ISLAND.getColor());// ��� ���� ����
		hintlistHeader.setFont(new Font("Sunflower Medium",Font.BOLD,18));// ��� ��Ʈ ����
		Dimension headerSize = hintlistHeader.getPreferredSize();
		headerSize.height = 40; // ���̺� ��� ����
		hintlistHeader.setPreferredSize(headerSize);
		
		// �����ӿ� �߰�
		hintlistscrollPane = new JScrollPane(hintTable); // ��ũ�� ��
		hintTable.setOpaque(false);
		hintListpanel.add(hintlistscrollPane);
		hintListpanel.setOpaque(false);
		getContentPane().add(hintListpanel);
		
	}
 
	public void addHintViewButtonPanel(int step, Problem problem, User user) { // ��Ʈ���������ӹ�ư�г�
		// �г� ����
		hintViewbuttonpanel = new JPanel();
		hintViewbuttonpanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 30, 10)); 
		hintViewbuttonpanel.setLocation(850, 670); // ��ġ
		hintViewbuttonpanel.setSize(380, 70); // ũ��
		
		// ��Ʈ �ۼ���ư �߰�
		hintAddbtn = new DesignedButton("�ۼ��ϱ�", 150, 40, COLOR.MEDIUM_SLATE_BLUE);
		hintAddbtn.setLocation(getDefalutWindowWidth() - 230, 680);
		hintAddbtn.setSize(150, 40);
		
		//�͸�Ŭ���� ��Ʈ�ۼ���ư ������
		hintAddbtn.addActionListener (new ActionListener() { 
			public void actionPerformed(ActionEvent e) {
				// ��Ʈ ����Ʈ�� �߰�
				new HintAddFrame(problem, user);
				dispose();  
			}
		});
		
		// �ݱ� ��ư
		hintBackbtn = new DesignedButton("��  ��", 150, 40, COLOR.AQUA_ISLAND);
		hintBackbtn.setLocation(getDefalutWindowWidth() - 370, 680);
		hintBackbtn.setSize(120, 40);
		
		//�͸�Ŭ���� ��Ʈ����Ʈ������ �ݱ��ư ������
		hintBackbtn.addActionListener (new ActionListener() { //�͸�Ŭ���� �н��ڷḮ��Ʈ �ݱ��ư ������
			public void actionPerformed(ActionEvent e) {
				dispose(); 
			}
		});
		
		//�����ӿ� �߰�
		hintViewbuttonpanel.add(hintAddbtn); // ��Ʈ�ۼ���ư �гο� �߰�
		hintViewbuttonpanel.add(hintBackbtn); // �����Ӵݱ��ư �гο� �߰�
		hintViewbuttonpanel.setOpaque(false); // ���� ����
		getContentPane().add(hintViewbuttonpanel); // �г��� �����ӿ� �߰�
	}
}