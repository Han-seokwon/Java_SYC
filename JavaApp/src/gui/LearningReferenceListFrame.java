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

import gui.HintAddFrame;
import gui.LearningReferenceAddFrame;
import gui.LearningReferenceViewFrame;

public class LearningReferenceListFrame extends DesignedJFrame {
	
	JPanel infopanel, referencelistpanel, referencebuttonpanel;
	JLabel problemName, frametitle;
	JTable Referencelist;
	JButton ReferenceAddbtn;
	
	public LearningReferenceListFrame() { 
		setTitle("LearningReferenceViewFrame");
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		DesignedContentPane background = new DesignedContentPane(this);
		setContentPane(background);
		background.setLayout(null);
		
		addinfopanel(); // �⺻����
		addlearningreferenceListpanel(); // �н��ڷ� ����Ʈ
		addLearningReferenceButtonpanel();
		
		setVisible(true);
	}
	
	public void addinfopanel() { // �⺻���� 
		infopanel = new JPanel(); 
		infopanel.setLayout(new GridLayout(2,1));
		infopanel.setLocation(120, 50);
		infopanel.setSize(750, 100); 
		
		JLabel problemName = new JLabel("(���� ����)"); // �����ʿ�
		JLabel title = new JLabel("   �н� �ڷ� ����Ʈ");
		problemName.setFont(new Font("Sunflower Medium", Font.BOLD,35));
		title.setFont(new Font("Sunflower Medium",Font.PLAIN,18));
	
		infopanel.add(problemName);
		infopanel.add(title);
		infopanel.setOpaque(false);
		getContentPane().add(infopanel);

	} 
	
	
	public void addlearningreferenceListpanel() { // �н��ڷ� ����Ʈ
		referencelistpanel = new JPanel();
		referencelistpanel.setBorder(new LineBorder(Color.black, 2));
		referencelistpanel.setLayout(new GridLayout(1,1)); 
		referencelistpanel.setLocation(120,150); // ��ġ
		referencelistpanel.setSize(getDefalutWindowWidth() - 200, 500); // ũ��
		
		// ���� �ʿ�
		String header[] = {"�ۼ���", "�н��ڷ� ����"};
		String contentlist[][] = { {"�ۼ���1", "����1"},{"�ۼ���2", "����2"}, {"�ۼ���3", "����3"}}; //����
				
		
		DefaultTableModel dtm = new DefaultTableModel(contentlist, header) { //�����Ұ����ϵ���
			public boolean isCellEditable(int row, int  column) {
		        return false; 
		      }
		}; 
		 
		JTable referencelist = new JTable(dtm);
		referencelist.getColumnModel().getColumn(0).setPreferredWidth(120); //ù��° �� ũ�� ����
		referencelist.getColumnModel().getColumn(1).setPreferredWidth(referencelistpanel.getWidth()-120); //�ι�° �� ũ�� ����
		referencelist.setRowHeight(50); // ���� ����  
		referencelist.setFont(new Font("Sunflower Medium",Font.PLAIN,15));
		// ���̺� ��� ����
		JTableHeader referenceHeader = referencelist.getTableHeader();
		referenceHeader.setBackground(COLOR.AQUA_ISLAND.getColor());
		referenceHeader.setBackground(COLOR.AQUA_ISLAND.getColor());
		referenceHeader.setFont(new Font("Sunflower Medium",Font.BOLD,18));
		Dimension headerSize = referencelist.getTableHeader().getPreferredSize();
		headerSize.height = 40;
		referencelist.getTableHeader().setPreferredSize(headerSize);
		
		// ���콺 Ŭ�� �̺�Ʈ ó���� ���� MouseAdapter �߰�
		referencelist.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
            	if(e.getClickCount() == 2) {
            		//����Ŭ�� ó��
            		// ���콺 Ŭ���� ��� �� �ε��� ��������
	                int row = referencelist.rowAtPoint(e.getPoint());
	                int col = referencelist.columnAtPoint(e.getPoint());
	
	                // ���õ� ���� �� ��������
	                String value = (String) referencelist.getValueAt(row, col);
	
	                // â ��ȯ
	                LearningReferenceViewFrame LRVF = new LearningReferenceViewFrame(value);
            	}
                
            }
        });
		
		JScrollPane referencelistscrollPane = new JScrollPane(referencelist); // ��ũ����
		referencelistpanel.add(referencelistscrollPane);
		referencelistpanel.setOpaque(false);
		getContentPane().add(referencelistpanel);
		
	}
 
	public void addLearningReferenceButtonpanel() { // �н��ڷ��ۼ���ư
		// �г� ����
		referencebuttonpanel = new JPanel();
		referencebuttonpanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 30, 10)); 
		referencebuttonpanel.setLocation(850, 670); // ��ġ
		referencebuttonpanel.setSize(380, 70); // ũ��
		// �н��ڷ� �ۼ���ư �߰�
		DesignedButton referenceAddbtn = new DesignedButton("�н��ڷ� �ۼ��ϱ�", 150, 40, COLOR.MEDIUM_SLATE_BLUE);
		referenceAddbtn.setLocation(getDefalutWindowWidth() - 230, 680);
		referenceAddbtn.setSize(150, 40);
		
		referenceAddbtn.addActionListener (new ActionListener() { //�͸�Ŭ���� �н��ڷ��ۼ���ư ������
			public void actionPerformed(ActionEvent e) {
					
				setVisible(false);
				new LearningReferenceAddFrame();
			}
		});
		
		// �ڷΰ��� ��ư
		DesignedButton referencebackBtn = new DesignedButton("�ڷΰ���", 150, 40, COLOR.AQUA_ISLAND);
		referencebackBtn.setLocation(getDefalutWindowWidth() - 370, 680);
		referencebackBtn.setSize(120, 40);
		
		referencebackBtn.addActionListener (new ActionListener() { //�͸�Ŭ���� �н��ڷḮ��Ʈ �ݱ��ư ������
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
			}
		});
		
		//�����ӿ� �߰�
		referencebuttonpanel.add(referenceAddbtn);
		referencebuttonpanel.add(referencebackBtn);
		referencebuttonpanel.setOpaque(false);
		getContentPane().add(referencebuttonpanel);
	}
	
	
	public static void main(String[] args) { // �����Լ�(�׽�Ʈ��)
		new LearningReferenceListFrame();
	}
}
