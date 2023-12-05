package gui;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import problems.Problem;
import users.User;


public class HintAddFrame extends DesignedJFrame{
	
	JPanel infopanel, hintguidepanel, selectsteppanel, hintwritepanel, hintAddbuttonpanel; 
	JLabel problemName;
	JTable hintguide;
	JButton hintregistbtn;
	String[] steplist;
	JComboBox selectstep;
	JTextArea hintwritefield;
	DesignedContentPane background;
	int step = 1;
	
	User user = new User();
	Problem problem = new Problem();

	public HintAddFrame(Problem problem, User user) {
		setTitle("HintAddFrame");
		DesignedContentPane background = new DesignedContentPane(this);
		setContentPane(background);
		background.setLayout(null);
		
		addinfopanel(problem); // �⺻����
		addhintguidepanel(); // ��Ʈ�ۼ����̵�
		addselectsteppanel(); // step ����
		addhintwritepanel(); // ��Ʈ �ۼ��ʵ�
		addHintViewButtonpanel(problem, user); //��ư �г�
		
		
		setVisible(true); 
	}
	
	public void addinfopanel(Problem problem) { // �⺻���� 
		infopanel = new JPanel();
		infopanel.setLayout(new GridLayout(2,1));
		infopanel.setLocation(120, 50);
		infopanel.setSize(750, 100); 
		
		JLabel problemName = new JLabel(problem.getProblemName());
		JLabel title = new JLabel("   ��Ʈ �ۼ��ϱ�"); 
		problemName.setFont(new Font("Sunflower Medium",Font.BOLD,35));
		title.setFont(new Font("Sunflower Medium",Font.PLAIN,18));
		
		infopanel.add(problemName);
		infopanel.add(title);
		infopanel.setOpaque(false);
		getContentPane().add(infopanel);
	} 
	
	
	public void addhintguidepanel() { // ��Ʈ �ۼ� ���̵�
		hintguidepanel = new JPanel();
		hintguidepanel.setBorder(new TitledBorder(new LineBorder(Color.black, 3), "��Ʈ �ۼ� ���̵�"));
		hintguidepanel.setLayout(new GridLayout(1,1)); 
		hintguidepanel.setLocation(150,150); // ��ġ
		hintguidepanel.setSize(450, 170); // ũ��
		
		
		String step1guide = " ���� ���� ��� ����";
		String step2guide = " ���Ǵ� �ٽ� �˰��� �� �Ϻ� ���� ����";
		String step3guide = " ��ü �ذ� ��� ����";
		String header[] = {"STEP", "��Ʈ �ۼ� ���̵�"};
		String content[][] = { {" STEP 1", step1guide},{" STEP 2", step2guide}, {" STEP 3", step3guide}}; //��Ʈ ���̵� ����
		DefaultTableModel dtm = new DefaultTableModel(content, header) { //�����Ұ����ϵ���
			public boolean isCellEditable(int row, int  column) {
		        return false;
		      } 
		};
		 
		JTable hintguide = new JTable(dtm);
		hintguide.setFont(new Font("Sunflower Medium",Font.PLAIN,15)); // ��Ʈ ����
		hintguide.getColumnModel().getColumn(0).setPreferredWidth(100); //ù��° �� ũ�� ����
		hintguide.getColumnModel().getColumn(1).setPreferredWidth(500); //�ι�° �� ũ�� ����
		hintguide.setRowHeight(50); // ���� ����  
		
		hintguidepanel.add(hintguide);
		hintguidepanel.setOpaque(false);
		getContentPane().add(hintguidepanel);
	}
	
	public void addselectsteppanel() { // ��Ʈ step ����
		selectsteppanel = new JPanel();
		selectsteppanel.setBorder(new TitledBorder(new LineBorder(Color.black, 3), "STEP ����"));
		selectsteppanel.setLayout(new GridLayout(1,1)); 
		selectsteppanel.setLocation(620,150); // ��ġ
		selectsteppanel.setSize(350, 60); // ũ��
		
		String[] steplist = {"Step 1", "Step 2", "Step 3"};
		JComboBox<String> selectstep = new JComboBox<String>(steplist);
		
		// �׼� ������
		selectstep.addActionListener(new ActionListener() {
			public void actionPerformed (ActionEvent e) {
				JComboBox<String> stp = (JComboBox<String>)e.getSource(); //Action�̺�Ʈ�� �߻��� �޺��ڽ� �˾Ƴ���
				int idx = stp.getSelectedIndex(); // ���õ� �������� �ε��� ��ȣ �˾Ƴ���
				step = idx + 1; //��Ʈ �ܰ�
			}
		});
		
		selectstep.setFont(new Font("Sunflower Medium",Font.PLAIN,15));
		selectsteppanel.add(selectstep);
		selectsteppanel.setOpaque(false);
		getContentPane().add(selectsteppanel);
		
	}
	
	public void addhintwritepanel() { // ��Ʈ �ۼ��ʵ�
		hintwritepanel = new JPanel();
		hintwritepanel.setLayout(new GridLayout(1,1)); 
		hintwritepanel.setLocation(140,350); // ��ġ
		hintwritepanel.setSize(getDefalutWindowWidth() - 230, 300); // ũ��
		
		hintwritefield = new JTextArea("������ �Է��� �ּ���."); // ��Ʈ �ۼ�
		hintwritefield.setLineWrap(true); // �ڵ��ٹٲ�
		hintwritefield.setWrapStyleWord(true); //�ܾ� ������ �ٹٲ�(����)

		hintwritepanel.add(new JScrollPane(hintwritefield)); // ��ũ����
		hintwritepanel.setOpaque(false);
		getContentPane().add(hintwritepanel);
		
	}
	 
		public void addHintViewButtonpanel(Problem problem, User user) { // ��Ʈ���������ӹ�ư�г�
			// �г� ����
			hintAddbuttonpanel = new JPanel();
			hintAddbuttonpanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 30, 10)); 
			hintAddbuttonpanel.setLocation(850, 670); // ��ġ
			hintAddbuttonpanel.setSize(380, 70); // ũ��
			
			// ��Ʈ �ۼ���ư �߰�
			DesignedButton hintRegistbtn = new DesignedButton("����ϱ�", 150, 40, COLOR.MEDIUM_SLATE_BLUE);
			hintRegistbtn.setLocation(getDefalutWindowWidth() - 230, 680);
			hintRegistbtn.setSize(150, 40);

			hintRegistbtn.addActionListener (new ActionListener() { //�͸�Ŭ���� ��Ʈ�ۼ���ư ������
				public void actionPerformed(ActionEvent e) {
					
					// ��Ʈ ����Ʈ�� �߰� ������
					String referenceContent = hintwritefield.getText();
					String hintstep = "Step " + String.valueOf(step);
					
					problem.addProblemHint(hintstep, user, referenceContent);
					
					// ��Ʈ ����Ʈ�� �ۼ��� ��Ʈ���� �߰�
					new HintViewFrame(step, problem, user);
					dispose();
				}
			});
			
						
			// �ݱ� ��ư
			DesignedButton hintCancelBtn = new DesignedButton("��  ��", 150, 40, COLOR.AQUA_ISLAND);
			hintCancelBtn.setLocation(getDefalutWindowWidth() - 370, 680);
			hintCancelBtn.setSize(120, 40);
			
			hintCancelBtn.addActionListener (new ActionListener() { //�͸�Ŭ���� �н��ڷ�ݱ��ư ������
				public void actionPerformed(ActionEvent e) {
					new HintViewFrame(step, problem, user);				
					dispose();
				}
			});
			
			
			//�����ӿ� �߰�
			hintAddbuttonpanel.add(hintRegistbtn);
			hintAddbuttonpanel.add(hintCancelBtn);
			hintAddbuttonpanel.setOpaque(false);
			getContentPane().add(hintAddbuttonpanel);
		}
}