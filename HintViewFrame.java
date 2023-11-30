package gui;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import gui.HintAddFrame;
import gui.DesignedJFrame;
import gui.DesignedButton;

public class HintViewFrame extends DesignedJFrame {
	
	JPanel infopanel, hintlistpanel, hintViewbuttonpanel;
	JLabel problemName, title;
	JTable hintlist;
	JButton hintAddbtn;
	private int step;
	
	public HintViewFrame(int step) { 
		setTitle("HintViewFrame");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		DesignedContentPane background = new DesignedContentPane(this);
		setContentPane(background);
		background.setLayout(null);
		this.step = step;
		
		addinfopanel(); // �⺻����
		addhintListpanel(); // ��Ʈ ����Ʈ
		addHintViewButtonpanel(); //��Ʈ�ۼ���ư
		setVisible(true);
	}
	
	public void addinfopanel() { // �⺻���� 
		infopanel = new JPanel();
		infopanel.setLayout(new GridLayout(2,1));
		infopanel.setLocation(120, 50);
		infopanel.setSize(750, 100); 
		
		JLabel problemName = new JLabel("(���� ����)"); // �����ʿ�
		JLabel title = new JLabel("   STEP"+step+" ��Ʈ ����Ʈ");
		problemName.setFont(new Font("Sunflower Medium",Font.BOLD,35));
		title.setFont(new Font("Sunflower Medium",Font.PLAIN,18));
	
		infopanel.add(problemName);
		infopanel.add(title);
		infopanel.setOpaque(false);
		getContentPane().add(infopanel);
 
	} 
	
	
	public void addhintListpanel() { // ��Ʈ ����Ʈ
		hintlistpanel = new JPanel();
		hintlistpanel.setBorder(new LineBorder(Color.black, 2));
		hintlistpanel.setLayout(new GridLayout(1,1)); 
		hintlistpanel.setLocation(120,150); // ��ġ
		hintlistpanel.setSize(getDefalutWindowWidth() - 200, 500); // ũ��
		
		
		String header[] = {"�ۼ���", "STEP "+step+"  ��Ʈ ����"};
		String contentlist[][] = { {"�ۼ���1", "����1"}, 
				                   {"�ۼ���2", "����2"}, 
								   {"�ۼ���3", "����3"}, 
								   {"�ۼ���1", "����1"}, 
								   {"�ۼ���1", "����1"},
								   {"�ۼ���1", "����1"},
								   {"�ۼ���1", "����1"},
								   {"�ۼ���1", "����1"},
								   {"�ۼ���1", "����1"},
								   {"�ۼ���1", "����1"},
								   {"�ۼ���1", "����1"},
								   {"�ۼ���1", "����1"},
								   {"�ۼ���1", "����1"},
								   {"�ۼ���1", "����1"},
								   {"�ۼ���1", "����1"}}; //����
				
		
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
 
	public void addHintViewButtonpanel() { // ��Ʈ���������ӹ�ư�г�
		// �г� ����
		hintViewbuttonpanel = new JPanel();
		hintViewbuttonpanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 30, 10)); 
		hintViewbuttonpanel.setLocation(850, 670); // ��ġ
		hintViewbuttonpanel.setSize(380, 70); // ũ��
		
		// ��Ʈ �ۼ���ư �߰�
		DesignedButton hintAddbtn = new DesignedButton("��Ʈ �ۼ��ϱ�", 150, 40, COLOR.MEDIUM_SLATE_BLUE);
		hintAddbtn.setLocation(getDefalutWindowWidth() - 230, 680);
		hintAddbtn.setSize(150, 40);

		hintAddbtn.addActionListener (new ActionListener() { //�͸�Ŭ���� ��Ʈ�ۼ���ư ������
			public void actionPerformed(ActionEvent e) {
				JButton hintadd = (JButton)e.getSource();
					// ��Ʈ ����Ʈ�� �߰� ������
					new HintAddFrame();
					setVisible(false);  
			}
		});
		
		// �ڷΰ��� ��ư
		DesignedButton hintbackBtn = new DesignedButton("�ڷΰ���", 150, 40, COLOR.AQUA_ISLAND);
		hintbackBtn.setLocation(getDefalutWindowWidth() - 370, 680);
		hintbackBtn.setSize(120, 40);
		
		hintbackBtn.addActionListener (new ActionListener() { //�͸�Ŭ���� �н��ڷḮ��Ʈ �ݱ��ư ������
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
			}
		});
		
		//�����ӿ� �߰�
		hintViewbuttonpanel.add(hintAddbtn);
		hintViewbuttonpanel.add(hintbackBtn);
		hintViewbuttonpanel.setOpaque(false);
		getContentPane().add(hintViewbuttonpanel);
	}
	
	
	public static void main(String[] args) { // �����Լ�
		new HintViewFrame(1); //�Ű����� - step
		//new HintViewFrame(2);
		//new HintViewFrame(3);
	}
}
