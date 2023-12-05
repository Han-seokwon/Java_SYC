package gui;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.DefaultTableModel;

import gui.HintViewFrame;
import problems.Problem;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class LearningReferenceViewFrame  extends DesignedJFrame{

	private JPanel infopanel, referenceContentpanel, referenceViewButtonpanel; 
	private String referenceTitle, referenceContent;
	private JLabel problemName, writer, title;
	private JTextArea referenceContentField;
	private JButton referenceViewClosebtn;

	public LearningReferenceViewFrame(Problem problem, int row, String userName) { // ������
		// ������ ����
		setTitle("LearningReferenceViewFrame");
		DesignedContentPane background = new DesignedContentPane(this);
		setContentPane(background);
		background.setLayout(null);
		this.referenceTitle = problem.getProblemReferences().get(row).getTitle(); // �н��ڷ� ����
		this.referenceContent = problem.getProblemReferences().get(row).getContent(); // �н��ڷ� ����

		addInfoPanel(userName, problem); // �⺻���� �г�
		addLearningReferenceContentPanel(); // �н��ڷ� �г�
		addLearningReferenceViewButtonPanel(); // ��ư �г�

		setVisible(true); 
	}

	public void addInfoPanel(String userName, Problem problem) { // �⺻���� 
		// �⺻���� �г� ����
		infopanel = new JPanel();
		infopanel.setBackground(new Color(255, 255, 255));
		infopanel.setLayout(new GridLayout(3,1));
		infopanel.setLocation(120, 50);
		infopanel.setSize(1070, 100); 
		
		// �⺻ ����
		problemName = new JLabel(problem.getProblemName()); // ���� ����
		writer = new JLabel("�ۼ��� : " + userName); // �н��ڷ� �ۼ���
		title = new JLabel("�� ��: "+referenceTitle); // �н��ڷ� ����
		// ��Ʈ ����
		problemName.setFont(new Font("Sunflower Medium",Font.BOLD,35));
		title.setFont(new Font("Sunflower Medium",Font.PLAIN,25));
		writer.setFont(new Font("Sunflower Medium",Font.PLAIN,18));
		
		// �����ӿ� �߰�
		infopanel.add(problemName);
		infopanel.add(title);
		infopanel.add(writer);
		getContentPane().add(infopanel);
	} 


	public void addLearningReferenceContentPanel() { // �н��ڷ� ���� 
		// �н��ڷ� �г� ����
		referenceContentpanel = new JPanel();
		referenceContentpanel.setBackground(new Color(255, 255, 255));
		referenceContentpanel.setLayout(new GridLayout(1,1)); 
		referenceContentpanel.setBounds(120, 160, 1070, 493);
		referenceContentpanel.setBorder(new TitledBorder(new LineBorder(Color.black, 3), "�� ��")); // �׵θ� ��
		
		// �н��ڷ� ���� �ʵ�
		referenceContentField = new JTextArea(this.referenceContent);
		referenceContentpanel.add(referenceContentField);
		referenceContentField.add(new JScrollPane()); // ��ũ����
		referenceContentField.setWrapStyleWord(true);
		referenceContentField.setLineWrap(true);
		referenceContentField.setEditable(false);
		
		// �����ӿ� �߰�
		getContentPane().add(referenceContentpanel);
	}

	public void addLearningReferenceViewButtonPanel() { // �н��ڷẸ���ư�г�
		// ��ư �г� ����
		referenceViewButtonpanel = new JPanel();
		referenceViewButtonpanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 20, 10)); 
		referenceViewButtonpanel.setLocation(770, 670); // ��ġ
		referenceViewButtonpanel.setSize(420, 70); // ũ��
		
		// �н��ڷ� �ݱ��ư
		referenceViewClosebtn = new DesignedButton("��    ��", 150, 40, COLOR.AQUA_ISLAND);
		referenceViewClosebtn.setLocation(getDefalutWindowWidth() - 370, 680);
		referenceViewClosebtn.setSize(120, 40);
		
		//�͸�Ŭ���� �н��ڷ�ݱ��ư ������
		referenceViewClosebtn.addActionListener (new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose(); // â �ݱ�
			}
		});

		//�����ӿ� �߰�
		referenceViewButtonpanel.add(referenceViewClosebtn);
		referenceViewButtonpanel.setOpaque(false);
		getContentPane().add(referenceViewButtonpanel);

	}
}