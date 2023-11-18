package gui;

import java.awt.Checkbox;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;

import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import users.EvaluationQuestion;
import users.Question;
import users.User;

public class SurveyFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	
	// ����� �н� ���� Ȯ�ο� ������ 1�� �ۿ� ������ Ȯ�强�� ���� ������ �����ϴ� ArrayList�� ����
	private ArrayList<Question> learningStyleCheckQuestions = new ArrayList<>(); 
	private ArrayList<Checkbox[]> learningStyleCheckBtnGroupList = new ArrayList<>();
	
	private ArrayList<EvaluationQuestion> evalQuestions = new ArrayList<>();
    private ArrayList<ButtonGroup> evalChoiceRadioBtnGroupList = new ArrayList<>();   
    private User user;
    
	public SurveyFrame(User user) {	
		this.user = user; // ���� �������縦 �����ϴ� ���� ������ �޾ƿ���
		
		// �н����� Ȯ�� ���� �ҷ�����		
		learningStyleCheckQuestions = Question.loadQuestionList();
		// ���� �׽�Ʈ ���� �ҷ�����
		evalQuestions = EvaluationQuestion.loadEvaluationQuestionList();
		
		// ������Ʈ�� ���� ����Ʈ �� ����
		contentPane = new JPanel();		
		contentPane.setBorder(new EmptyBorder(15, 15, 15, 15));	
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		//  ����Ʈ��  ���̾ƿ� ����
		gbl_contentPane.columnWidths = new int[]{853, 0};
		gbl_contentPane.rowHeights = new int[]{277, 442, 35, 0};
		gbl_contentPane.columnWeights = new double[]{0.0, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		contentPane.setLayout(gbl_contentPane);
		

		// < �н����� Ȯ�� >		
		
		// �н�����Ȯ�� �׸��� ���� �г�
		JPanel learningStyleCheckPanel = new JPanel();
		learningStyleCheckPanel.setLayout(null);		
		GridBagConstraints gbc_learningStyleCheckPanel = new GridBagConstraints();
		//  learningStyleCheckPanel ���̾ƿ� ����
		gbc_learningStyleCheckPanel.weighty = 1.0;
		gbc_learningStyleCheckPanel.fill = GridBagConstraints.BOTH;
		gbc_learningStyleCheckPanel.insets = new Insets(0, 0, 5, 0);
		gbc_learningStyleCheckPanel.gridx = 0;
		gbc_learningStyleCheckPanel.gridy = 0;
		contentPane.add(learningStyleCheckPanel, gbc_learningStyleCheckPanel);
		
		// �н�����Ȯ�� ������ ���� �г�
		JPanel learningStyleCheckTitlePanel = new JPanel();
		learningStyleCheckTitlePanel.setBounds(0, 0, 870, 96);
		learningStyleCheckTitlePanel.setLayout(new GridLayout(0, 1, 0, 0));
		// 	����Ʈ�ҿ� �߰�
		learningStyleCheckPanel.add(learningStyleCheckTitlePanel);
		
		// �н�����Ȯ�� ����
		JLabel learningStyleCheckTitleLabel = new JLabel("�н����� ����");
		learningStyleCheckTitleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		learningStyleCheckTitleLabel.setFont(new Font("����", Font.PLAIN, 30));
		learningStyleCheckTitlePanel.add(learningStyleCheckTitleLabel);		
		
		// �н�����Ȯ�ο� ������ ���� ��ũ����
		JScrollPane learningStyleCheckContentScrollPane = new JScrollPane();
		learningStyleCheckContentScrollPane.setBounds(0, 95, 870, 172); // �� ������ ����
		learningStyleCheckContentScrollPane.getVerticalScrollBar().setUnitIncrement(12); // ��ũ�ѹ� �ӵ� ����
		learningStyleCheckPanel.add(learningStyleCheckContentScrollPane);
		
		// �н�����Ȯ�ο� �������� ���� �г�
		JPanel learningStyleQeustionListPanel = new JPanel();
		learningStyleQeustionListPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
		learningStyleQeustionListPanel.setLayout(new GridLayout(1, 0, 0, 0));		
		setLearningStyleCheckAndChoiceButton(learningStyleQeustionListPanel);		
		// �������� �н�����Ȯ�� �׸��� ���� �гο� �߰�
		learningStyleCheckContentScrollPane.setViewportView(learningStyleQeustionListPanel);
		

		// < ���� �׽�Ʈ >		
		
		// ���� �׽�Ʈ�� ���� �г�
		JPanel evalPanel = new JPanel();
		GridBagConstraints gbc_evalPanel = new GridBagConstraints();
		gbc_evalPanel.weightx = 1.0;
		gbc_evalPanel.fill = GridBagConstraints.BOTH;
		gbc_evalPanel.insets = new Insets(0, 0, 5, 0);
		gbc_evalPanel.gridx = 0;
		gbc_evalPanel.gridy = 1;
		contentPane.add(evalPanel, gbc_evalPanel);
		evalPanel.setLayout(null);
		
		// ���� �׽�Ʈ ������ ���� �г�
		JPanel evalTitlePanel = new JPanel();
		evalTitlePanel.setBounds(0, 0, 870, 96); // �� ������ ���� 
		evalPanel.add(evalTitlePanel);
		evalTitlePanel.setLayout(new GridLayout(0, 1, 0, 0));
		
		// ���� �׽�Ʈ ���� ��
		JLabel evalTitleLabel = new JLabel("�˰���, �ڷᱸ�� ���� �׽�Ʈ(��� �׸� �ʼ� �Է�)");
		evalTitleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		evalTitleLabel.setFont(new Font("����", Font.PLAIN, 30));
		evalTitlePanel.add(evalTitleLabel);
				
		// ���� �׽�Ʈ ����(����)�� ���� ��ũ����
		JScrollPane evalContentScrollPane = new JScrollPane();
		evalContentScrollPane.setBounds(0, 95, 870, 342); // �� ������ ����
		evalContentScrollPane.getVerticalScrollBar().setUnitIncrement(12); // ��ũ�ѹ� �ӵ� ����
		evalPanel.add(evalContentScrollPane);
		

		
		// ���� �׽�Ʈ ����(����)�� ���� �г�
		JPanel evalQuestionListPanel = new JPanel();
		evalQuestionListPanel.setBorder(new EmptyBorder(10, 10, 15, 10));
		evalContentScrollPane.setViewportView(evalQuestionListPanel);
		// ���� �����ŭ �׸��� ���̾ƿ� row�� ����
		evalQuestionListPanel.setLayout(new GridLayout(evalQuestions.size(), 1, 0, 0));
		// �� ����, ��ư ���
		setEvalQuestionAndChoiceButton(evalQuestionListPanel);
		
		// ���� ��ư
		JButton submitButton = new JButton("����");
		submitButton.addActionListener(new SubmitButtonListener()); // ������ ���		
		submitButton.setFont(new Font("����", Font.PLAIN, 20));
		GridBagConstraints gbc_submitButton = new GridBagConstraints();
		gbc_submitButton.anchor = GridBagConstraints.EAST;
		gbc_submitButton.fill = GridBagConstraints.VERTICAL;
		gbc_submitButton.gridx = 0;
		gbc_submitButton.gridy = 2;
		contentPane.add(submitButton, gbc_submitButton);		
		
		// ������ �Ӽ� ����
		setTitle("��������");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null); // â ��� ��ġ
		setBounds(100, 100, 920, 850);		
		setVisible(true);	
	}
	
	// �н� ���� Ȯ�� ���� �� �������� �гο� �߰�
	void setLearningStyleCheckAndChoiceButton(JPanel learningStyleQuestionListPanel) {
        // ����, ������(üũ �ڽ�) ����        
        for (int i = 0 ; i < learningStyleCheckQuestions.size(); i++ ) {        	 
        	JPanel questionPanel = new JPanel(new GridLayout(2, 0, 5, 5));
        	// ���� �� �߰�
        	Question question = learningStyleCheckQuestions.get(i);
        	questionPanel.add(new JLabel(String.format("(%d��) ", i+1) + question.getQuestion()));
        	
        	// ������ üũ �ڽ� �߰�
        	JPanel choiceListPanel = new JPanel(new GridLayout(2, 1, 5, 5)); // ������ ����� �г�        	
        	ArrayList<String> choiceStringList = question.getChoiceList(); // ������ ���ڿ� ��������       	
        	Checkbox[] choiceList = new Checkbox[choiceStringList.size()]; // ������ ������ŭ üũ�ڽ� �迭 ����        	
        	// üũ �ڽ��� �������� �ش��ϴ� ���ڿ��� �־ Checkbox[]�� �гο� �߰�
        	for(int j = 0 ; j < choiceStringList.size(); j++ ) {
        		Checkbox choiceBtn = new Checkbox(choiceStringList.get(j));
        		choiceList[j] = (choiceBtn);
        		choiceListPanel.add(choiceBtn); // �гο��� ��ư �߰�       		
        	}
        	learningStyleCheckBtnGroupList.add(choiceList); // ��������ư�׷츮��Ʈ�� ������ üũ�ڽ� �迭 �߰�
        	questionPanel.add(choiceListPanel); // ���� �гο� ��������ư����Ʈ �߰�
        	learningStyleQuestionListPanel.add(questionPanel); // ��������Ʈ�гο� �� �����г� �߰�
        }
	}
	
	// ���� �׽�Ʈ ���� �� �������� �гο� �߰�
	void setEvalQuestionAndChoiceButton(JPanel evalQuestionListPanel) {
        // ����, ������(���� ��ư) ����        
        for (int i = 0 ; i < evalQuestions.size(); i++ ) {        	
        	JPanel questionPanel = new JPanel(new GridLayout(2, 0, 5, 5));
        	// ���� �� �߰�
        	EvaluationQuestion question = evalQuestions.get(i);
        	questionPanel.add(new JLabel(String.format("(%d��) ", i+1) + question.getQuestion()));
        	
        	// ������ ���� ��ư �߰�
        	JPanel choiceListPanel = new JPanel(new GridLayout(2, 1, 5, 5));
        	ArrayList<String> choiceList = question.getChoiceList(); // ������ ����� �г�        	
        	ButtonGroup choiceRadioBtnGroup = new ButtonGroup();// �� ������ ���� ��ư�� ������ ��ư �׷� 
        	// ���� ��ư�� �������� �ش��ϴ� ���ڿ��� �־ ��ư �׷�� �гο� �߰�
        	for(int j = 0 ; j < choiceList.size(); j++ ) {
        		JRadioButton choiceBtn = new JRadioButton(choiceList.get(j));
        		choiceRadioBtnGroup.add(choiceBtn);
        		choiceListPanel.add(choiceBtn); // �гο��� ��ư �߰�       		
        	}
        	evalChoiceRadioBtnGroupList.add(choiceRadioBtnGroup); // ��������ư�׷츮��Ʈ�� ��������ư�׷� �߰�
        	questionPanel.add(choiceListPanel); // ���� �гο� ��������ư����Ʈ �߰�
        	evalQuestionListPanel.add(questionPanel); // ��������Ʈ�гο� �� �����г� �߰�
        }        
	}
	
 
    
	class SubmitButtonListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {			
			
			int totalPoint = 0;
			try {
				totalPoint = gradeEvaluationResult();
			} catch (IOException err) {
				JOptionPane.showMessageDialog(null, err.getMessage(), "���� ����", JOptionPane.WARNING_MESSAGE);
				return; // ������ ����
			}
			// ������ ���� ����Ʈ��ŭ ��ũ ����Ʈ�� �ø�
			user.addRankPoint(totalPoint);
			System.out.println("RankPoint : "+user.getRankPoint());
			JOptionPane.showMessageDialog(null, String.format("�����մϴ�.\n %d��ŭ ��ũ����Ʈ�� �����Ǿ����ϴ�.", totalPoint), "���� ����", JOptionPane.INFORMATION_MESSAGE);
			
			// ������ ������ ��ȣ�ϴ� �˰��� ������ ���������Ϳ� �߰�
			addPreferredAlgorithmType_ToUser(learningStyleCheckBtnGroupList.get(0));
			System.out.println("AlgorithmType : "+ user.getPreferredAlgorithmTypeSet());
			
			user.updateUserFile(); // ������ ���� ������ DB ���� ���Ͽ� ����
			dispose(); // â ����
		}
	}	
	
	private void addPreferredAlgorithmType_ToUser(Checkbox[] checkboxList){
        for (int i = 0; i < checkboxList.length; i++) {
            if (checkboxList[i].getState()) { // üũ�ڽ��� ���õ� ���
            	System.out.println("���� : " + checkboxList[i].getLabel());
            	user.addPreferredAlgorithmType(checkboxList[i].getLabel()); // ���õ� �˰��� ���� ���������Ϳ� �߰�            	
            }
        }
	}
	
	// ���� �׽�Ʈ���� ���� �׸��� ���� ����� ���ϸ� ȹ���� �� ����Ʈ�� ����Ͽ� ��ȯ
	private int gradeEvaluationResult() throws IOException{
		int totalPoint = 0;	// ȹ���� �� ����Ʈ
        for (int i =0 ; i < evalChoiceRadioBtnGroupList.size() ; i++) {
        	ButtonGroup group = evalChoiceRadioBtnGroupList.get(i);
        	int index = getSelectedIndexInButtonGroup(group);
        	if(index != -1) { // ���õ� ��ư�� �ִ� ��� 
        		// ������ ���� ���
        		if(index == evalQuestions.get(i).getAnswerNum()) {
        			totalPoint += evalQuestions.get(i).getPoint(); // ����Ʈ ����
        		}        
        	} else { // ���õ� ��ư�� ���� ���
        		throw new IOException(String.format("���� �׽�Ʈ (%d��) �׸� ���� ���õ��� �ʾҽ��ϴ�.", i+1));
        	}             
        }
        return totalPoint;
	}
	
	// ButtonGroup���� ���õ� �׸��� �ε����� ��ȯ
    private int getSelectedIndexInButtonGroup(ButtonGroup group) {
        Enumeration<AbstractButton> buttons = group.getElements();
        int index = 0;

        // iterator�� �̿��� ���������� ���õ� �׸��� �ε��� ��ȯ
        while (buttons.hasMoreElements()) {
            if (buttons.nextElement().isSelected()) {
                return index+1; // �������� 1���� �����ϹǷ� +1 ����
            }
            index++;
        }
        return -1; // ���õ� �׸��� ���� ���
    }  
    
    
   
	
}
