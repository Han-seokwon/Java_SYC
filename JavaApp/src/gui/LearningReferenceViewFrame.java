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

	private JPanel infopanel, referencetitlepanel, referencecontentpanel, referenceViewButtonpanel; 
	private String referenceTitle, referenceContent;
	private JLabel problemName, writer, title;
	private JButton referenceclosebtn;

	public LearningReferenceViewFrame(Problem problem, int row, String userName) {
		setTitle("LearningReferenceViewFrame");
		DesignedContentPane background = new DesignedContentPane(this);
		setContentPane(background);
		background.setLayout(null);
		this.referenceTitle = problem.getProblemReferences().get(row).getTitle(); // �н��ڷ� ����
		this.referenceContent = problem.getProblemReferences().get(row).getContent(); // �н��ڷ� ����

		addinfopanel(userName, problem); // �⺻����
		addlearningreferencecontentpanel(); // �н��ڷ�
		addreferenceViewButtonpanel(); // �н��ڷ�ݱ��ư

		setVisible(true); 
	}

	public void addinfopanel(String userName, Problem problem) { // �⺻���� 
		infopanel = new JPanel();
		infopanel.setBackground(new Color(255, 255, 255));
		infopanel.setLayout(new GridLayout(3,1));
		infopanel.setLocation(120, 50);
		infopanel.setSize(1070, 100); 

		problemName = new JLabel(problem.getProblemName());
		writer = new JLabel("�ۼ��� : " + userName); 
		title = new JLabel("�� ��: "+referenceTitle);
		problemName.setFont(new Font("Sunflower Medium",Font.BOLD,35));
		title.setFont(new Font("Sunflower Medium",Font.PLAIN,25));
		writer.setFont(new Font("Sunflower Medium",Font.PLAIN,18));

		infopanel.add(problemName);
		infopanel.add(title);
		infopanel.add(writer);
		getContentPane().add(infopanel);
	} 


	public void addlearningreferencecontentpanel() { // �н��ڷ�

		// �гλ���
		referencecontentpanel = new JPanel();
		referencecontentpanel.setBackground(new Color(255, 255, 255));
		referencecontentpanel.setLayout(new GridLayout(1,1)); 
		referencecontentpanel.setBounds(120, 160, 1070, 493);
		referencecontentpanel.setBorder(new TitledBorder(new LineBorder(Color.black, 3), "�� ��"));

		getContentPane().add(referencecontentpanel);
		JScrollPane scrollPane = new JScrollPane();

		JTextArea referenceContentField = new JTextArea(this.referenceContent);
		referencecontentpanel.add(referenceContentField);
		referenceContentField.add(scrollPane); // ��ũ����
		referenceContentField.setWrapStyleWord(true);
		referenceContentField.setLineWrap(true);
		referenceContentField.setEditable(false);

	}

	public void addreferenceViewButtonpanel() { // �н��ڷẸ���ư�г�
		// �г� ����
		referenceViewButtonpanel = new JPanel();
		referenceViewButtonpanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 20, 10)); 
		referenceViewButtonpanel.setLocation(770, 670); // ��ġ
		referenceViewButtonpanel.setSize(420, 70); // ũ��
		// �н��ڷ� �ݱ��ư
		DesignedButton referenceViewClosebtn = new DesignedButton("��    ��", 150, 40, COLOR.AQUA_ISLAND);
		referenceViewClosebtn.setLocation(getDefalutWindowWidth() - 370, 680);
		referenceViewClosebtn.setSize(120, 40);

		referenceViewClosebtn.addActionListener (new ActionListener() { //�͸�Ŭ���� �н��ڷ�ݱ��ư ������
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});

		//�����ӿ� �߰�
		referenceViewButtonpanel.add(referenceViewClosebtn);
		referenceViewButtonpanel.setOpaque(false);
		getContentPane().add(referenceViewButtonpanel);

	}
}