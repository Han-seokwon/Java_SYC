package gui;


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
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import users.EvaluationQuestion;
import users.SurveyQuestion;
import users.User;

// ���� ���� �� ������� ��ȣ �˰��� ���� �� �⺻ ���� �׽�Ʈ�� �����ϴ� Ŭ����
public class SurveyFrame extends DesignedJFrame {

	private static final long serialVersionUID = 1L;
	private DesignedContentPane contentPane;
	
	// ����� �н� ���� Ȯ�ο� ������ 1�� �ۿ� ������ Ȯ�强�� ���� ������ �����ϴ� ArrayList�� ����
	private ArrayList<SurveyQuestion> learningStyleCheckQuestions = new ArrayList<>(); 
	private ArrayList<JCheckBox[]> learningStyleCheckBtnGroupList = new ArrayList<>();
	
	private ArrayList<EvaluationQuestion> evalQuestions = new ArrayList<>();
    private ArrayList<ButtonGroup> evalChoiceRadioBtnGroupList = new ArrayList<>();   
    private User user;
    
	public SurveyFrame(User user) {	
		super();
		int panelWidth = getWindowWidth() - 250; // �� �г��� �ʺ�
		this.user = user; // ���� �������縦 �����ϴ� ���� ������ �޾ƿ���		
		// �н����� Ȯ�� ���� �ҷ�����		
		learningStyleCheckQuestions = SurveyQuestion.loadQuestionList();
		// ���� �׽�Ʈ ���� �ҷ�����
		evalQuestions = EvaluationQuestion.loadEvaluationQuestionList();
		
		// ������Ʈ�� ���� ����Ʈ �� ����
		contentPane = new DesignedContentPane();		
		contentPane.setBorder(new EmptyBorder(15, 100, 15, 15));	
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		//  ����Ʈ��  ���̾ƿ� ����
		gbl_contentPane.columnWidths = new int[]{panelWidth};
		gbl_contentPane.rowHeights = new int[]{277, 442, 35, 0};
		GridBagConstraints gbc_contentPane  = new GridBagConstraints();
		gbc_contentPane.fill = GridBagConstraints.BOTH;
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
		learningStyleCheckTitlePanel.setBounds(0, 0, panelWidth, 96);
		learningStyleCheckTitlePanel.setLayout(new GridLayout(0, 1, 0, 0));
		// ����Ʈ�ҿ� �߰�
		learningStyleCheckPanel.add(learningStyleCheckTitlePanel);
		
		// �н�����Ȯ�� ����
		JLabel learningStyleCheckTitleLabel = new JLabel("�н����� ����");
		learningStyleCheckTitleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		learningStyleCheckTitleLabel.setFont(new Font("����", Font.PLAIN, 30));
		learningStyleCheckTitlePanel.add(learningStyleCheckTitleLabel);		
		
		// �н�����Ȯ�ο� ������ ���� ��ũ����
		JScrollPane learningStyleCheckContentScrollPane = new JScrollPane();
		learningStyleCheckContentScrollPane.setBounds(0, 95, panelWidth, 200); // �� ������ ����
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
		evalTitlePanel.setBounds(0, 0, panelWidth, 96); // �� ������ ���� 
		evalPanel.add(evalTitlePanel);
		evalTitlePanel.setLayout(new GridLayout(0, 1, 0, 0));
		
		// ���� �׽�Ʈ ���� ��
		JLabel evalTitleLabel = new JLabel("�˰���, �ڷᱸ�� ���� �׽�Ʈ(��� �׸� �ʼ� �Է�)");
		evalTitleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		evalTitlePanel.add(evalTitleLabel);
				
		// ���� �׽�Ʈ�� �г��� ���� ��ũ����
		JScrollPane evalContentScrollPane = new JScrollPane();
		evalContentScrollPane.setBounds(0, 95, panelWidth, 342); // �� ������ ����
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
		JButton submitButton = new DesignedButton("����", COLOR.SUNFLOWER);		
		submitButton.setHorizontalAlignment(SwingConstants.RIGHT);
		submitButton.addActionListener(new SubmitButtonListener()); // ������ ���		
		GridBagConstraints gbc_submitButton = new GridBagConstraints();
		gbc_submitButton.insets = new Insets(0, 0, 0, 50);
		gbc_submitButton.anchor = GridBagConstraints.EAST;
		gbc_submitButton.fill = GridBagConstraints.VERTICAL;
		gbc_submitButton.gridx = 0;
		gbc_submitButton.gridy = 2;
		contentPane.add(submitButton, gbc_submitButton);		
		
		contentPane.applyFontAndBackgroundToAllComponents(); // ��ü ��Ʈ ���� �� �г� ���� �����ϰ� ����
		setVisible(true);	
	}
	
	// �н� ���� Ȯ�� ���� �� �������� �гο� �߰�
	private void setLearningStyleCheckAndChoiceButton(JPanel learningStyleQuestionListPanel) {
        // ����, ������(üũ �ڽ�) ����        
        for (int i = 0 ; i < learningStyleCheckQuestions.size(); i++ ) {        	 
        	JPanel questionPanel = new JPanel(new GridLayout(2, 0, 5, 5));
        	// ���� �� �߰�
        	SurveyQuestion question = learningStyleCheckQuestions.get(i);
        	questionPanel.add(new JLabel(String.format("(%d��) ", i+1) + question.getQuestion()));
        	
        	// ������ üũ �ڽ� �߰�
        	JPanel choiceListPanel = new JPanel(new GridLayout(3, 4, 5, 5)); // ������ ����� �г�        	
        	ArrayList<String> choiceStringList = question.getChoiceList(); // ������ ���ڿ� ��������       	
        	JCheckBox[] choiceList = new JCheckBox[choiceStringList.size()]; // ������ ������ŭ üũ�ڽ� �迭 ����        	
        	// üũ �ڽ��� �������� �ش��ϴ� ���ڿ��� �־ Checkbox[]�� �гο� �߰�
        	for(int j = 0 ; j < choiceStringList.size(); j++ ) {
        		JCheckBox choiceBtn = new JCheckBox(choiceStringList.get(j));
        		choiceList[j] = (choiceBtn);
        		choiceListPanel.add(choiceBtn); // �гο��� ��ư �߰� 
        		choiceBtn.setBackground(COLOR.AQUA_ISLAND.getColor());
        	}
        	learningStyleCheckBtnGroupList.add(choiceList); // ��������ư�׷츮��Ʈ�� ������ üũ�ڽ� �迭 �߰�
        	questionPanel.add(choiceListPanel); // ���� �гο� ��������ư����Ʈ �߰�
        	learningStyleQuestionListPanel.add(questionPanel); // ��������Ʈ�гο� �� �����г� �߰�
        }
	}
	
	// ���� �׽�Ʈ ���� �� �������� �гο� �߰�
	private void setEvalQuestionAndChoiceButton(JPanel evalQuestionListPanel) {
        // ����, ������(���� ��ư) ����        
        for (int i = 0 ; i < evalQuestions.size(); i++ ) {        	
        	JPanel questionPanel = new JPanel(new GridLayout(2, 0, 5, 5));
        	// ���� �� �߰�
        	EvaluationQuestion question = evalQuestions.get(i);
        	questionPanel.add(new JLabel(String.format("(%d��) ", i+1) + question.getQuestion()));
        	
        	// ������ ���� ��ư �߰�
        	JPanel choiceListPanel = new JPanel(new GridLayout(3, 2, 5, 5));
        	ArrayList<String> choiceList = question.getChoiceList(); // ������ ����� �г�        	
        	ButtonGroup choiceRadioBtnGroup = new ButtonGroup();// �� ������ ���� ��ư�� ������ ��ư �׷� 
        	// ���� ��ư�� �������� �ش��ϴ� ���ڿ��� �־ ��ư �׷�� �гο� �߰�
        	for(int j = 0 ; j < choiceList.size(); j++ ) {
        		JRadioButton choiceBtn = new JRadioButton(choiceList.get(j));
        		choiceRadioBtnGroup.add(choiceBtn);
        		choiceListPanel.add(choiceBtn); // �гο��� ��ư �߰�      
        		choiceBtn.setBackground(COLOR.JORDY_BLUE.getColor());
        	}
        	evalChoiceRadioBtnGroupList.add(choiceRadioBtnGroup); // ��������ư�׷츮��Ʈ�� ��������ư�׷� �߰�
        	questionPanel.add(choiceListPanel); // ���� �гο� ��������ư����Ʈ �߰�
        	evalQuestionListPanel.add(questionPanel); // ��������Ʈ�гο� �� �����г� �߰�
        }        
	} 
    
	
	private void addPreferredAlgorithmType_ToUser(JCheckBox[] checkboxList){
        for (int i = 0; i < checkboxList.length; i++) {
            if (checkboxList[i].isSelected()) { // üũ�ڽ��� ���õ� ���
            	System.out.println("���� : " + checkboxList[i].getText());
            	user.addPreferredAlgorithmType(checkboxList[i].getText()); // ���õ� �˰��� ���� ���������Ϳ� �߰�            	
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
    
	class SubmitButtonListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {	
			int totalPoint = 0;
			try {
				totalPoint = gradeEvaluationResult();
			} catch (IOException err) { // �� ���� �� �亯���� ���� �׸��� �ִ� ���
				Dialog.showAlertDialog("���� ����", err.getMessage());
				return; // ������ ����
			}
			// ��� ������ �亯�� ���
			
			// ������ ���� ����Ʈ��ŭ ��ũ ����Ʈ�� �ø�
			user.addRankPoint(totalPoint);
			System.out.println("RankPoint : "+user.getRankPoint());
			
			// ������ ������ ��ȣ�ϴ� �˰��� ������ ���������Ϳ� �߰�
			addPreferredAlgorithmType_ToUser(learningStyleCheckBtnGroupList.get(0));
			
			try {
				user.createUpdateUserFile(); // ������ ���� ������ DB ���� ���Ͽ� ����				
			} catch (IOException e2) {  // �ý��� �󿡼� ���� �����͸� DB�� �������� ���ϴ� ��� (�ý��� ����)
				Dialog.showAlertDialog("���� ����", Dialog.USER_FILE_SAVING_ERROR);
				return;  // ������ ����
			}
			// ���� �����Ͱ� DB�� ���������� ����� ���			
			Dialog.showInfoDialog("���� ����", String.format("�����մϴ�.\n %d��ŭ ��ũ����Ʈ�� �����Ǿ����ϴ�.", totalPoint));
			dispose(); // â ����
		}
	}	
   
	
}
