package gui;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.DefaultTableModel;

import gui.HintViewFrame;
import problems.*;
import users.User;
import gui.DesignedButton;
import gui.DesignedJFrame;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class LearningReferenceAddFrame  extends DesignedJFrame{
	
	JPanel infopanel, referencetitlepanel, referencewritepanel, ReferenceRegistButtonpanel; 
	JLabel problemName;
	DesignedButton referenceRegistbtn, referenceCancelBtn;
	JTextField referencetitlefield;
	JTextArea referencewritefield;
	
	public LearningReferenceAddFrame(Problem problem, User user) {
		setTitle("�н� �ڷ� �ۼ�");
		DesignedContentPane background = new DesignedContentPane(this);
		setContentPane(background);
		background.setLayout(null);
		
		addinfopanel(problem); // �⺻����
		addreferencetitlepanel(); // �н��ڷ� �����ۼ��ʵ�
		addlearningreferencewritepanel(); // �н��ڷ� �ۼ��ʵ�
		addReferenceRegistButton(problem, user); // �н��ڷ��Ϲ�ư
		
		setVisible(true); 
	}
	
	public void addinfopanel(Problem problem) { // �⺻���� 
		infopanel = new JPanel();
		infopanel.setLayout(new GridLayout(2,1));
		infopanel.setLocation(120, 50);
		infopanel.setSize(1000, 100); 
		
		JLabel problemName = new JLabel(problem.getProblemName()); // �����ʿ�
		JLabel title = new JLabel("���� �н� �ڷḦ �ۼ����ּ���."); 
		problemName.setFont(new Font("Sunflower Medium",Font.BOLD,35));
		title.setFont(new Font("Sunflower Medium",Font.PLAIN,18));
	
		infopanel.add(problemName);
		infopanel.add(title);
		infopanel.setOpaque(false);
		getContentPane().add(infopanel);
	}
	public void addreferencetitlepanel() { // �н��ڷ� ���� �ۼ�
		referencetitlepanel = new JPanel();
		referencetitlepanel.setLayout(new GridLayout(1,1)); 
		referencetitlepanel.setLocation(150,170); // ��ġ
		referencetitlepanel.setSize(1000, 50); // ũ��
		
		referencetitlefield = new JTextField(" ������ �Է��ϼ���.");
		
		referencetitlepanel.add(referencetitlefield);
		getContentPane().add(referencetitlepanel);

	}
	 
	public void addlearningreferencewritepanel() { // �н��ڷ� �ۼ��ʵ�
		referencewritepanel = new JPanel();
		referencewritepanel.setLayout(new GridLayout(1,1)); 
		referencewritepanel.setLocation(150,250); // ��ġ
		referencewritepanel.setSize(1000, 400); // ũ��
		
		referencewritefield = new JTextArea(" ������ �Է��ϼ���."); // �н��ڷ� �ۼ�
		referencewritefield.setLineWrap(true); // �ڵ��ٹٲ�
		referencewritefield.setWrapStyleWord(true); //�ܾ� ������ �ٹٲ�(����)

		referencewritepanel.add(new JScrollPane(referencewritefield)); // ��ũ����
		getContentPane().add(referencewritepanel);
		
	}

	public void addReferenceRegistButton(Problem problem, User user) { // �н��ڷ��Ϲ�ư
		// �г� ����
		ReferenceRegistButtonpanel = new JPanel();
		ReferenceRegistButtonpanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 30, 10)); 
		ReferenceRegistButtonpanel.setLocation(850, 670); // ��ġ
		ReferenceRegistButtonpanel.setSize(380, 70); // ũ��
		// �н��ڷ� ��Ϲ�ư �߰�
		DesignedButton referenceRegistbtn = new DesignedButton("����ϱ�", 150, 40, COLOR.MEDIUM_SLATE_BLUE);
		referenceRegistbtn.setLocation(getDefalutWindowWidth() - 230, 680);
		referenceRegistbtn.setSize(150, 40);
		
		referenceRegistbtn.addActionListener (new ActionListener() { //�͸�Ŭ���� �н��ڷ��ۼ���ư ������
			public void actionPerformed(ActionEvent e) {				
				//��Ϲ�ư ��� �ۼ�
				String addreferenceTitle = referencetitlefield.getText();
				String addreferenceContent = referencewritefield.getText(); 
				problem.addProblemReferences(user, addreferenceTitle, addreferenceContent);				
				//â��ȯ
				new LearningReferenceListFrame(problem, user);				
				dispose();
			}
		});
		// �ݱ� ��ư
		DesignedButton referenceCancelBtn = new DesignedButton("��    ��", 150, 40, COLOR.AQUA_ISLAND);
		referenceCancelBtn.setLocation(getDefalutWindowWidth() - 370, 680);
		referenceCancelBtn.setSize(120, 40);
		
		referenceCancelBtn.addActionListener (new ActionListener() { //�͸�Ŭ���� �н��ڷ�ݱ��ư ������
			public void actionPerformed(ActionEvent e) {
				new LearningReferenceListFrame(problem, user);
				dispose();
			}
		});
		
		//�����ӿ� �߰�
		ReferenceRegistButtonpanel.add(referenceRegistbtn);
		ReferenceRegistButtonpanel.add(referenceCancelBtn);
		ReferenceRegistButtonpanel.setOpaque(false);
		getContentPane().add(ReferenceRegistButtonpanel);
	}
}