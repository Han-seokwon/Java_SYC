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
	
	JPanel infopanel, hintlistpanel, hintViewbuttonpanel;
	JLabel problemName, title;
	JTable hintlist;
	JButton hintAddbtn;
	private int step;
	
	// type : ������ ��Ʈ ���� ��ȣ
	public HintViewFrame(int type, Problem problem, User user) { 
		setTitle("HintViewFrame");
		DesignedContentPane background = new DesignedContentPane(this);
		setContentPane(background);
		background.setLayout(null);
		this.step = type;
		
		addinfopanel(problem); // �⺻����
		addhintListpanel(problem, step); // ��Ʈ ����Ʈ
		addHintViewButtonpanel(step, problem, user); //��Ʈ�ۼ���ư
		setVisible(true);
		
		System.out.println("HintViewFrame : " + user);
	}
	
	public void addinfopanel(Problem problem) { // �⺻���� 
		infopanel = new JPanel();
		infopanel.setLayout(new GridLayout(2,1));
		infopanel.setLocation(120, 50);
		infopanel.setSize(750, 100); 
		
		JLabel problemName = new JLabel(problem.getProblemName());
		JLabel title = new JLabel("   STEP"+step+" ��Ʈ ����Ʈ");
		problemName.setFont(new Font("Sunflower Medium",Font.BOLD,35));
		title.setFont(new Font("Sunflower Medium",Font.PLAIN,18));
	
		infopanel.add(problemName);
		infopanel.add(title);
		infopanel.setOpaque(false);
		getContentPane().add(infopanel);
 
	} 
	
	
	public void addhintListpanel(Problem problem, int step) { // ��Ʈ ����Ʈ
		hintlistpanel = new JPanel();
		hintlistpanel.setBorder(new LineBorder(Color.black, 2));
		hintlistpanel.setLayout(new GridLayout(1,1)); 
		hintlistpanel.setLocation(120,150); // ��ġ
		hintlistpanel.setSize(getDefalutWindowWidth() - 200, 500); // ũ��
		
		// ��Ʈ ���
		ArrayList<Hint> hintList = problem.getProblemHint(step);
		// null ���� ��� ����Ʈ �ʱ�ȭ
		hintList = (hintList != null) ? hintList : new ArrayList<>();
		
		String header[] = {"�ۼ���", "STEP "+step+"  ��Ʈ ����"};
		String contentlist[][] = new String[hintList.size()][2];
		
		int row = 0;
		for(Hint hint : hintList) {
			contentlist[row][0] = hint.getWriter().getUsername();
			contentlist[row][1] = hint.getContent();
			row++;
		}
		
		DefaultTableModel dtm = new DefaultTableModel(contentlist, header) { //�����Ұ����ϵ���
			public boolean isCellEditable(int row, int  column) {
		        return false;
		      }
		};
		 
		JTable hintlist = new JTable(dtm);
		hintlist.getColumnModel().getColumn(0).setPreferredWidth(100); //ù��° �� ũ�� ����
		hintlist.getColumnModel().getColumn(1).setPreferredWidth(700); //�ι�° �� ũ�� ����
		hintlist.setRowHeight(50); // ���� ����  
		hintlist.setFont(new Font("Sunflower Medium",Font.PLAIN,15)); // ��Ʈ ����
		// ���̺� ��� ����
		JTableHeader hintlistHeader = hintlist.getTableHeader();
		hintlistHeader.setBackground(COLOR.AQUA_ISLAND.getColor());
		hintlistHeader.setBackground(COLOR.AQUA_ISLAND.getColor());
		hintlistHeader.setFont(new Font("Sunflower Medium",Font.BOLD,18));
		Dimension headerSize = hintlistHeader.getPreferredSize();
		headerSize.height = 40;
		hintlistHeader.setPreferredSize(headerSize);
		
		// �����ӿ� �߰�
		JScrollPane hintlistscrollPane = new JScrollPane(hintlist);
		hintlist.setOpaque(false);
		hintlistpanel.add(hintlistscrollPane);
		hintlistpanel.setOpaque(false);
		getContentPane().add(hintlistpanel);
		
	}
 
	public void addHintViewButtonpanel(int step, Problem problem, User user) { // ��Ʈ���������ӹ�ư�г�
		// �г� ����
		hintViewbuttonpanel = new JPanel();
		hintViewbuttonpanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 30, 10)); 
		hintViewbuttonpanel.setLocation(850, 670); // ��ġ
		hintViewbuttonpanel.setSize(380, 70); // ũ��
		
		// ��Ʈ �ۼ���ư �߰�
		DesignedButton hintAddbtn = new DesignedButton("�ۼ��ϱ�", 150, 40, COLOR.MEDIUM_SLATE_BLUE);
		hintAddbtn.setLocation(getDefalutWindowWidth() - 230, 680);
		hintAddbtn.setSize(150, 40);

		hintAddbtn.addActionListener (new ActionListener() { //�͸�Ŭ���� ��Ʈ�ۼ���ư ������
			public void actionPerformed(ActionEvent e) {
				// ��Ʈ ����Ʈ�� �߰� ������
				new HintAddFrame(problem, user);
				dispose();  
			}
		});
		
		// �ڷΰ��� ��ư
		DesignedButton hintbackBtn = new DesignedButton("�ڷΰ���", 150, 40, COLOR.AQUA_ISLAND);
		hintbackBtn.setLocation(getDefalutWindowWidth() - 370, 680);
		hintbackBtn.setSize(120, 40);
		
		hintbackBtn.addActionListener (new ActionListener() { //�͸�Ŭ���� �н��ڷḮ��Ʈ �ݱ��ư ������
			public void actionPerformed(ActionEvent e) {
				dispose(); 
			}
		});
		
		//�����ӿ� �߰�
		hintViewbuttonpanel.add(hintAddbtn);
		hintViewbuttonpanel.add(hintbackBtn);
		hintViewbuttonpanel.setOpaque(false);
		getContentPane().add(hintViewbuttonpanel);
	}
}