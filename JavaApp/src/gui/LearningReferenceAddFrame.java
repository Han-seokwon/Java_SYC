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
	
	private JPanel infopanel, referenceTitlepanel, referenceWritepanel, ReferenceRegistButtonpanel; 
	private JLabel problemName, frameTitle;
	private DesignedButton referenceRegistbtn, referenceCancelbtn;
	private JTextField referenceTitleField;
	private JTextArea referenceWriteField;
	
	public LearningReferenceAddFrame(Problem problem, User user) { // ������
		setTitle("�н� �ڷ� �ۼ�");
		DesignedContentPane background = new DesignedContentPane(this);
		setContentPane(background);
		background.setLayout(null);
		
		addInfoPanel(problem); // �⺻���� �г� �߰�
		addLearningReferenceWritePanel(); // �н��ڷ� �����ۼ��г� �߰�
		addReferenceTitlePanel(); // �н��ڷ� �����ۼ��г� �߰�
		addReferenceRegistButtonPanel(problem, user); // �н��ڷ� ��ư �г� �߰�
		
		setVisible(true); 
	}
	
	public void addInfoPanel(Problem problem) { // �⺻���� 
		// �⺻���� �г� ����
		infopanel = new JPanel();
		infopanel.setLayout(new GridLayout(2,1));
		infopanel.setLocation(120, 50);
		infopanel.setSize(1000, 100); 
		
		problemName = new JLabel(problem.getProblemName()); // ���� ����
		frameTitle = new JLabel("���� �н� �ڷ� �ۼ��ϱ�."); // ������ �ȳ���
		// ��Ʈ����
		problemName.setFont(new Font("Sunflower Medium",Font.BOLD,35));
		frameTitle.setFont(new Font("Sunflower Medium",Font.PLAIN,18));
		
		// �����ӿ� �߰�
		infopanel.add(problemName);
		infopanel.add(frameTitle);
		infopanel.setOpaque(false);
		getContentPane().add(infopanel);
	}
	
	public void addReferenceTitlePanel() { // �н��ڷ� ���� �ۼ�
		// �н��ڷ� �����ۼ� �г� ����
		referenceTitlepanel = new JPanel();
		referenceTitlepanel.setLayout(new GridLayout(1,1)); 
		referenceTitlepanel.setLocation(150,170); // ��ġ
		referenceTitlepanel.setSize(1000, 50); // ũ��
		
		// ���� �Է� ĭ
		referenceTitleField = new JTextField(" ������ �Է��ϼ���.");
		
		// �����ӿ� �߰�
		referenceTitlepanel.add(referenceTitleField);
		getContentPane().add(referenceTitlepanel);

	}
	 
	public void addLearningReferenceWritePanel() { // �н��ڷ� �ۼ��ʵ�
		// �н��ڷ� �����ۼ� �г� ����
		referenceWritepanel = new JPanel();
		referenceWritepanel.setLayout(new GridLayout(1,1)); 
		referenceWritepanel.setLocation(150,250); // ��ġ
		referenceWritepanel.setSize(1000, 400); // ũ��
		
		// �н��ڷ� ���� �ۼ� ĭ
		referenceWriteField = new JTextArea(" ������ �Է��ϼ���."); // �н��ڷ� �ۼ�
		referenceWriteField.setLineWrap(true); // �ڵ��ٹٲ�
		referenceWriteField.setWrapStyleWord(true); //�ܾ� ������ �ٹٲ�(����)

		//�����ӿ� �߰�
		referenceWritepanel.add(new JScrollPane(referenceWriteField)); // ��ũ����
		getContentPane().add(referenceWritepanel);
		
	}

	public void addReferenceRegistButtonPanel(Problem problem, User user) { // �н��ڷ��Ϲ�ư �г�
		// �г� ����
		ReferenceRegistButtonpanel = new JPanel();
		ReferenceRegistButtonpanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 30, 10)); 
		ReferenceRegistButtonpanel.setLocation(850, 670); // ��ġ
		ReferenceRegistButtonpanel.setSize(380, 70); // ũ��
		
		// �н��ڷ� ��Ϲ�ư
		referenceRegistbtn = new DesignedButton("����ϱ�", 150, 40, COLOR.MEDIUM_SLATE_BLUE);
		referenceRegistbtn.setLocation(getDefalutWindowWidth() - 230, 680);
		referenceRegistbtn.setSize(150, 40);
		
		//�͸�Ŭ���� �н��ڷ��ۼ���ư ������
		referenceRegistbtn.addActionListener (new ActionListener() { 
			public void actionPerformed(ActionEvent e) {				
				//��Ϲ�ư ��� �ۼ�
				String addreferenceTitle = referenceTitleField.getText();
				String addreferenceContent = referenceWriteField.getText(); 
				problem.addProblemReferences(user, addreferenceTitle, addreferenceContent);				
				//â��ȯ
				new LearningReferenceListFrame(problem, user);				
				dispose();
			}
		});
		
		//�н��ڷ�ڷΰ����ư
		referenceCancelbtn = new DesignedButton("�ڷΰ���", 150, 40, COLOR.AQUA_ISLAND);
		referenceCancelbtn.setLocation(getDefalutWindowWidth() - 370, 680);
		referenceCancelbtn.setSize(120, 40);
		
		//�͸�Ŭ���� �н��ڷ�ݱ��ư ������
		referenceCancelbtn.addActionListener (new ActionListener() { 
			public void actionPerformed(ActionEvent e) {
				new LearningReferenceListFrame(problem, user);
				dispose();
			}
		});
		
		//�����ӿ� �߰�
		ReferenceRegistButtonpanel.add(referenceRegistbtn); // �н��ڷ� �߰���ư
		ReferenceRegistButtonpanel.add(referenceCancelbtn); // �н��ڷ� �ݱ� ��ư
		ReferenceRegistButtonpanel.setOpaque(false);
		getContentPane().add(ReferenceRegistButtonpanel);
	}
}