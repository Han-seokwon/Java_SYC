package gui;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import problems.Problem;
import users.RANK;
import users.User;


public class HintAddFrame extends DesignedJFrame{
	
	private JPanel infopanel, hintGuidepanel, selectSteppanel, hintWritepanel, hintAddButtonpanel; 
	private JLabel problemName, frameTitle;
	private JTable hintGuide;
	private JComboBox<String> selectStep;
	private JTextArea hintWriteField;
	private DesignedButton hintRegistbtn, hintCancelbtn;
	int step = 1;
	

	public HintAddFrame(Problem problem, User user) { // ������
		setTitle("HintAddFrame"); // �������̸�
		DesignedContentPane background = new DesignedContentPane(this);
		setContentPane(background);
		background.setLayout(null);
		
		addInfoPanel(problem); // �⺻�����г��߰�
		addHintGuidePanel(); // ��Ʈ�ۼ����̵��г��߰�
		addSelectStepPanel(); // step �����г� �߰�
		addHintWritePanel(); // ��Ʈ �ۼ��г� �߰�
		addHintViewButtonPanel(problem, user); //��ư �г�
		
		setVisible(true); 
	}
	
	public void addInfoPanel(Problem problem) { // �⺻���� 
		// �⺻���� �г�
		infopanel = new JPanel();
		infopanel.setLayout(new GridLayout(2,1));
		infopanel.setLocation(120, 50);
		infopanel.setSize(750, 100); 
		
		problemName = new JLabel(problem.getProblemName()); // ���� ����
		frameTitle = new JLabel("   ��Ʈ �ۼ��ϱ�"); // ������ �ȳ���
		problemName.setFont(new Font("Sunflower Medium",Font.BOLD,35)); // ���� ���� ��Ʈ����
		frameTitle.setFont(new Font("Sunflower Medium",Font.PLAIN,18)); // ������ �ȳ��� ��Ʈ����
		
		// �����ӿ� �ش� �г� �߰�
		infopanel.add(problemName);
		infopanel.add(frameTitle);
		infopanel.setOpaque(false);
		getContentPane().add(infopanel);
	} 
	
	
	public void addHintGuidePanel() { // ��Ʈ �ۼ� ���̵�
		// ��Ʈ �ۼ� ���̵� �г�
		hintGuidepanel = new JPanel();
		hintGuidepanel.setBorder(new TitledBorder(new LineBorder(Color.black, 3), "��Ʈ �ۼ� ���̵�")); // �׵θ���
		hintGuidepanel.setLayout(new GridLayout(1,1)); 
		hintGuidepanel.setLocation(150,150); // ��ġ
		hintGuidepanel.setSize(450, 170); // ũ��
		
		// ��Ʈ �ܰ躰 ���̵�
		String step1guide = " ���� ���� ��� ����";
		String step2guide = " ���Ǵ� �ٽ� �˰��� �� �Ϻ� ���� ����";
		String step3guide = " ��ü �ذ� ��� ����";
		String header[] = {"STEP", "��Ʈ �ۼ� ���̵�"};
		String content[][] = { {" STEP 1", step1guide},{" STEP 2", step2guide}, {" STEP 3", step3guide}}; //��Ʈ ���̵� ����
		DefaultTableModel dtm = new DefaultTableModel(content, header) { 
			public boolean isCellEditable(int row, int  column) {
		        return false; //��Ʈ �ۼ� ���̵� ǥ ���� �����Ұ����ϵ���
		      } 
		};
		
		// ��Ʈ �ۼ����̵� ǥ
		hintGuide = new JTable(dtm);
		hintGuide.setFont(new Font("Sunflower Medium",Font.PLAIN,15)); // ��Ʈ ����
		hintGuide.getColumnModel().getColumn(0).setPreferredWidth(100); //ù��° �� ũ�� ����
		hintGuide.getColumnModel().getColumn(1).setPreferredWidth(500); //�ι�° �� ũ�� ����
		hintGuide.setRowHeight(50); // ���� ����
		
		// �ۼ����̵� �г��� �����ӿ� �߰�
		hintGuidepanel.add(hintGuide);
		hintGuidepanel.setOpaque(false);
		getContentPane().add(hintGuidepanel);
}
	
	public void addSelectStepPanel() { // ��Ʈ step ����
		// ��Ʈ step ���� �г�
		selectSteppanel = new JPanel();
		selectSteppanel.setBorder(new TitledBorder(new LineBorder(Color.black, 3), "STEP ����")); // �׵θ���
		selectSteppanel.setLayout(new GridLayout(1,1)); 
		selectSteppanel.setLocation(620,150); // ��ġ
		selectSteppanel.setSize(350, 60); // ũ��
		
		
		String[] steplist = {"Step 1", "Step 2", "Step 3"}; // ��Ʈ �ܰ� ���� ����Ʈ
		selectStep = new JComboBox<String>(steplist); // �޺��ڽ�
		
		// ��Ʈ �ܰ� ���� �޺��ڽ� �׼� ������
		selectStep.addActionListener(new ActionListener() {
			public void actionPerformed (ActionEvent e) {
				JComboBox<String> stp = (JComboBox<String>)e.getSource(); //Action�̺�Ʈ�� �߻��� �޺��ڽ� �˾Ƴ���
				int idx = stp.getSelectedIndex(); // ���õ� �������� �ε��� ��ȣ �˾Ƴ���
				step = idx + 1; //��Ʈ �ܰ�
			}
		});
		
		// ��Ʈ������ �����ӿ� �߰�
		selectStep.setFont(new Font("Sunflower Medium",Font.PLAIN,15));
		selectSteppanel.add(selectStep);
		selectSteppanel.setOpaque(false);
		getContentPane().add(selectSteppanel);
		
	}
	
	public void addHintWritePanel() { // ��Ʈ �ۼ��ʵ�
		int textCntLimit = 60; // ���� �� ����
		hintWritepanel = new JPanel();
		hintWritepanel.setLayout(new GridLayout(1,1)); 
		hintWritepanel.setLocation(140,350); // ��ġ
		hintWritepanel.setSize(getDefalutWindowWidth() - 230, 300); // ũ��
		
		hintWriteField = new JTextArea("������ �Է��� �ּ���." + "(���� �� ���� " + textCntLimit + "��)"); // ��Ʈ �ۼ�
		hintWriteField.setLineWrap(true); // �ڵ��ٹٲ�
		hintWriteField.setWrapStyleWord(true); //�ܾ� ������ �ٹٲ�(����)
		hintWriteField.addKeyListener(new TextCntLimitListener(textCntLimit));
		hintWritepanel.add(new JScrollPane(hintWriteField)); // ��ũ����
		hintWritepanel.setOpaque(false);
		getContentPane().add(hintWritepanel);
		
	}
	// �ִ� ���ڼ� ����
	class TextCntLimitListener extends KeyAdapter{
		private int maxTextCnt;
		public TextCntLimitListener() {
			this(60);
		}
		public TextCntLimitListener(int maxTextCnt) {
			this.maxTextCnt = maxTextCnt;
		}
		
		@Override
        public void keyTyped(KeyEvent ke) {
			//���� �� �ʰ��� ���� �ʰ��Ǵ� ���� �ڵ� ����
			if(hintWriteField.getText().length() > maxTextCnt) { 
				ke.consume();
				hintWriteField.setText(hintWriteField.getText().substring(0, maxTextCnt));
			}
        }
	}
	 
	public void addHintViewButtonPanel(Problem problem, User user) { // ��Ʈ�ۼ������ӹ�ư�г�
		// �г� ����
		hintAddButtonpanel = new JPanel();
		hintAddButtonpanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 30, 10)); 
		hintAddButtonpanel.setLocation(850, 670); // ��ġ
		hintAddButtonpanel.setSize(380, 70); // ũ��
		
		// ��Ʈ �ۼ���ư �߰�
		hintRegistbtn = new DesignedButton("����ϱ�", 150, 40, COLOR.MEDIUM_SLATE_BLUE);
		hintRegistbtn.setLocation(getDefalutWindowWidth() - 230, 680);
		hintRegistbtn.setSize(150, 40);
		
		//�͸�Ŭ���� ��Ʈ�ۼ���ư ������
		hintRegistbtn.addActionListener (new ActionListener() { 
			public void actionPerformed(ActionEvent e) {
				
				// ��Ʈ ����Ʈ�� �ۼ��� ��Ʈ���� �߰�
				String referenceContent = hintWriteField.getText();
				problem.addProblemHint(step, user, referenceContent);
				
				// â ��ȯ
				new HintViewFrame(step, problem, user);
				dispose();
			}
		});
					
		// �ڷΰ��� ��ư
		hintCancelbtn = new DesignedButton("�ڷΰ���", 150, 40, COLOR.AQUA_ISLAND);
		hintCancelbtn.setLocation(getDefalutWindowWidth() - 370, 680);
		hintCancelbtn.setSize(120, 40);
		
		//�͸�Ŭ���� �н��ڷ�ݱ��ư ������
		hintCancelbtn.addActionListener (new ActionListener() { 
			public void actionPerformed(ActionEvent e) {
				// â��ȯ
				new HintViewFrame(step, problem, user);				
				dispose();
			}
		});
		
		//�����ӿ� �߰�
		hintAddButtonpanel.add(hintRegistbtn);
		hintAddButtonpanel.add(hintCancelbtn);
		hintAddButtonpanel.setOpaque(false);
		getContentPane().add(hintAddButtonpanel);
	}
}