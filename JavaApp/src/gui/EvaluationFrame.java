package gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;

import users.EvaluationQuestion;

public class EvaluationFrame extends JFrame {

    private ArrayList<EvaluationQuestion> evaluationQuestions = new ArrayList<>();
    private ArrayList<ButtonGroup> choiceRadioBtnGroupList = new ArrayList<>();    

    public EvaluationFrame() {
    	// �Ƿ� �׽�Ʈ ���� �ҷ�����
        evaluationQuestions = EvaluationQuestion.loadEvaluationQuestionList();

        // �Ƿ� �׽�Ʈ �г�
        JPanel evaluationPanel = new JPanel(new GridBagLayout()); 
        
        // �Ƿ� �׽�Ʈ Ÿ��Ʋ�� �г�
        JPanel evaluationTitlePanel = new JPanel();
        evaluationTitlePanel.add(new JLabel("�˰��� �� �ڷᱸ�� ���� �׽�Ʈ"));
        evaluationTitlePanel.setSize(800, 500);
        GridBagConstraints gbc = new GridBagConstraints(); // ���̾ƿ� ���� ���� ����
        gbc.gridy = 0;
        evaluationPanel.add(evaluationTitlePanel, gbc);
        
        // �Ƿ� �׽�Ʈ ���� �г�
        JPanel evaluationQuestionListPanel = new JPanel(new GridLayout(evaluationQuestions.size() , 1, 15, 15));
        evaluationQuestionListPanel.setSize(800, 800);
        GridBagConstraints gbcQuestion = new GridBagConstraints(); // ���̾ƿ� ���� ���� ����
        gbcQuestion.gridy = 1; // evaluationTitlePanel ���� �࿡ ����
        evaluationPanel.add(evaluationQuestionListPanel, gbcQuestion);
        
        
        // ����, ������(���� ��ư) ����        
        for (int i = 0 ; i < evaluationQuestions.size(); i++ ) {        	
        	JPanel questionPanel = new JPanel(new GridLayout(2, 0, 5, 5));
        	// ���� �� �߰�
        	EvaluationQuestion question = evaluationQuestions.get(i);
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
        	choiceRadioBtnGroupList.add(choiceRadioBtnGroup); // ��������ư�׷츮��Ʈ�� ��������ư�׷� �߰�
        	questionPanel.add(choiceListPanel); // ���� �гο� ������ �߰�
        	evaluationQuestionListPanel.add(questionPanel); // ��������Ʈ�𵨿� �����г� �߰�
        }
        
     // JScrollPane�� evaluationQuestionPanel �߰�
        JScrollPane scrollPane = new JScrollPane(evaluationPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        // ���� ��ư
        JButton submitButton = new JButton("Submit");        
        submitButton.addActionListener(new SubmitButtonListener());

        // �� �׽�Ʈ �г� �����ӿ� �߰�
        add(scrollPane);
//        add(submitButton);

                
     // ������ �Ӽ� ����
        setTitle("���� ����");
        setSize(1000, 1000);
        this.setLocationRelativeTo(null); // â ��� ��ġ
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }
    
    
	class SubmitButtonListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			if(areAllEvaluationQuestionsAnswered()) { // ��� �׽�Ʈ �׸� ���� ��� 
				
			} else { // �ϳ��� ���õ��� ���� �׸��� �ִ� ���
				JOptionPane.showMessageDialog(null, "��� �׸� ���� �������ּ���.", "���� ����", JOptionPane.WARNING_MESSAGE);
			}
		}
	}
    
    
    private boolean areAllEvaluationQuestionsAnswered() {
        for (ButtonGroup group : choiceRadioBtnGroupList) {
            if (group.getSelection() == null) { // �ϳ��� ���õ��� ���� �׸��� �ִ� ���
                return false; 
            }
        }
        return true; // ��� �׸��� ����ehla
    }

}
