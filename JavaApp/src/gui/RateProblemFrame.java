package gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import problems.Problem;
import problems.ProblemRank;
import problems.ProblemRankManager;
import users.RANK;
import users.User;
// ����ڰ� ������ ��ũ, ��ũ ����Ʈ, �׿� ���� �ڸ�Ʈ�� �Է��ϸ� �ش� �����͸� ���� Ŭ������ �����ϴ� Ŭ����
public class RateProblemFrame extends JFrame {
	private JPanel centerPanel; // �������� ������Ʈ���� ��ġ�� ���� �г�
	private JComboBox<RANK> rankComboBox; // ��ũ ���� �޺��ڽ�
	private JLabel rankPointLabel; // ��ũ ����Ʈ �� ( ��ũ ���ÿ� ���� �Է� ���� �ִ�, �ּ� ����Ʈ �ȳ����� ����ϱ� ���� ���� �ʵ�� ����)
	private JTextField rankPointField; // ��ũ ����Ʈ�� �Է¹޴� �ʵ�(������ �Է¹���)
	private int minRankPoint = 0; 	// �Է��� �� �ִ� �ּ� ��ũ ����Ʈ
	private int maxRankPoint = RANK.getMaxRequireRankPoint(); 	// �Է��� �� �ִ� �ִ� ��ũ ����Ʈ
	private JTextField commentField; // �ڸ�Ʈ�� �Է¹޴� �ʵ�
	private JButton submitButton; 
	private User user;
	private Problem problem;
	
    public RateProblemFrame(User user, Problem problem) {
    	this.user = user; // ���� �α��ε� ���������� ��������
    	this.problem = problem; // ���� ���õ� ���������� ��������
    	
    	// ������ �Ӽ� ����
    	setSize(800, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // centerPanel ����
        centerPanel = new JPanel();         
        // �е��� ��輱 ����
        centerPanel.setBorder(new EmptyBorder(80, 80, 80, 80)); 
        BorderLayout borderLayout = new BorderLayout(); 
        getContentPane().setLayout(borderLayout);

        // centerPanel GridBagLayout ����
        getContentPane().add(centerPanel, BorderLayout.CENTER);
        GridBagLayout gbl_centerPanel = new GridBagLayout();
        gbl_centerPanel.columnWidths = new int[] {500};
        gbl_centerPanel.rowHeights = new int[] {100};
        gbl_centerPanel.columnWeights = new double[]{1.0};
        gbl_centerPanel.rowWeights = new double[]{0.0, 1.0};
        centerPanel.setLayout(gbl_centerPanel);
                
        // 1��. ���� ���̵� ���� �޺��ڽ� �� �߰�
        rankComboBox = new JComboBox<RANK>(RANK.values());
        rankComboBox.setSelectedIndex(-1); // �� �� ���ú��� ����        
        rankComboBox.addActionListener(new RankComboBoxListener());
        addRowPanelToCenterPanel(0, new JLabel("���� ���̵�"),rankComboBox);
       
        // 2��. ���� ��ũ ����Ʈ �� �߰�   
        rankPointField = new JTextField();
        rankPointField.setHorizontalAlignment(SwingConstants.CENTER);
        rankPointField.setColumns(10);       
        rankPointField.addKeyListener(new IntegerInputKeyListener());  // ���� �̿� �� �Է� �� �ִ밪 �ʰ� �ڸ��� �Է� ���� ������ ���
        
        rankPointLabel = new JLabel("���� ��ũ ����Ʈ");
        addRowPanelToCenterPanel(1, rankPointLabel, rankPointField);
        
        // 3��. ��ũ �⿩ �ڸ�Ʈ �� �߰�  
        commentField = new JTextField();
        commentField.setHorizontalAlignment(SwingConstants.CENTER);
        commentField.setColumns(20);
        addRowPanelToCenterPanel(2, new JLabel("�ڸ�Ʈ"), commentField);
        
        // ��ư �г� ����
        JPanel buttonPanel = new JPanel();
        // ��ư �г� ���̾ƿ� ����
        FlowLayout flowLayout = (FlowLayout) buttonPanel.getLayout();
        flowLayout.setVgap(30);
        flowLayout.setHgap(30);
        flowLayout.setAlignment(FlowLayout.RIGHT);
        // ��ư �г� centerPanel�� �߰�
        getContentPane().add(buttonPanel, BorderLayout.SOUTH);
        
        // ��� ��ư �гο� �߰�
        buttonPanel.add(new CancelButton());
        // ���� ��ư �гο� �߰�
        submitButton = new JButton("����"); 
        submitButton.addActionListener(new SubmitButtonListener()); // ������ ���
        buttonPanel.add(submitButton);

        setVisible(true); // ������ display
    }
    
    /*
     * centerPanel�� Label�� Component�� ������ �߰��� row �г��� �߰�
     * @param  rowNum : �߰��� row ��ȣ,   labelString : �� �ؽ�Ʈ, component : �߰��� ������Ʈ(�� �����ʿ� ��ġ��)
     * */    
    void addRowPanelToCenterPanel(int row, JLabel label, Component component) {
    	// centerPanel�� row�� �߰��� �г� ����
        JPanel rowPanel = new JPanel();
        // �г� ��ġ(GridBagConstraints) ����
        GridBagConstraints gbc_rowPanel = new GridBagConstraints();
        gbc_rowPanel.weighty = 0.1;
        gbc_rowPanel.insets = new Insets(0, 0, 5, 0);
        gbc_rowPanel.fill = GridBagConstraints.BOTH;
        gbc_rowPanel.gridx = 0;
        gbc_rowPanel.gridy = row;
        // �гο� centerPanel�� �߰�
        centerPanel.add(rowPanel, gbc_rowPanel);
        
        // �г� ���̾ƿ� ����
        GridBagLayout gbl_rowPanel = new GridBagLayout();
        gbl_rowPanel.columnWidths = new int[] {100, 170, 0};
        gbl_rowPanel.rowHeights = new int[] {80};
        rowPanel.setLayout(gbl_rowPanel);
        
        // �гο� �� �߰�
        label.setHorizontalAlignment(SwingConstants.CENTER);
        // �� ��ġ ����
        GridBagConstraints gbc_label = new GridBagConstraints();
        gbc_label.weightx = 0.3;
        gbc_label.fill = GridBagConstraints.BOTH;
        gbc_label.insets = new Insets(0, 0, 0, 5);
        gbc_label.gridx = 0;
        gbc_label.gridy = 0;
        // �� �гο� �߰�
        rowPanel.add(label, gbc_label);
        
        
        // ������Ʈ ������ ����
        component.setPreferredSize(new Dimension(150, 30));
        // �߰��� ������Ʈ ��ġ ����
        GridBagConstraints gbc_component = new GridBagConstraints();
        gbc_component.weightx = 1.0;
        gbc_component.gridx = 1;
        gbc_component.gridy = 0;
        // ������Ʈ �гο� �߰�
        rowPanel.add(component, gbc_component);
    }
    
    
    class RankComboBoxListener implements ActionListener {    	
        @Override
        public void actionPerformed(ActionEvent e) {
        	RANK selectedRANK = (RANK)rankComboBox.getSelectedItem();
        	minRankPoint = selectedRANK.getRequireRankPoint();
        	maxRankPoint = minRankPoint + RANK.getRankPointGap();
            // �󺧿� �Է� �ּ�, �ִ밪 ���̵� ���
            rankPointLabel.setText(String.format("<html>���� ��ũ ����Ʈ<br>(�ּ� : %d, �ִ� : %d)</html>", minRankPoint, maxRankPoint));       
        }
    }
    
    
 // ���� �̿� �� �Է� �� �ִ밪 �ʰ� �ڸ��� �Է� ���� ������
    class IntegerInputKeyListener extends KeyAdapter {
        @Override
        public void keyTyped(KeyEvent ke) {
            char c = ke.getKeyChar();
            int len = ((JTextField)ke.getSource()).getText().length(); // �Է��� ���� ����
            int maxDigit =  (int)Math.log10(RANK.getMaxRequireRankPoint()); // ������ �ִ� ���� �ڸ���
            // ����, �齺���̽�, ����Ʈ Ű�� �ƴϰų� ���̰� maxDigit�� �ʰ��ϴ� ��� ke ����
            if (!(Character.isDigit(c) || (c == KeyEvent.VK_BACK_SPACE) || (c == KeyEvent.VK_DELETE)) || len > maxDigit) {
            	ke.consume();
            }
        }
    }
    
    // �Է°� ��ȿ�� Ȯ��
    void inputCheck() throws IOException{
    	String errMsg = "";
    	int comboBoxIdx = rankComboBox.getSelectedIndex();
		if(comboBoxIdx == -1) {
			errMsg += "��ũ �޺��ڽ��� �������ּ���.\n";
		} 
		if(rankPointField.getText().trim().isEmpty()) {
			errMsg += "��ũ ����Ʈ�� �Է����ּ���.\n";
		} else if(comboBoxIdx != -1){ // ��ũ ����Ʈ�� �Է��Ͽ��� ��ũ �޺��ڽ��� ���õ� ���
			// �ּ�, �ִ� �Է� �������� ������� Ȯ��
			int inputedRankPoint = Integer.valueOf(rankPointField.getText().trim());
			if(inputedRankPoint < minRankPoint || inputedRankPoint > maxRankPoint) {
				errMsg += String.format("��ũ ����Ʈ �Է� ������ ������ϴ�. (�Է°� = %d) \n �Է°��� ���� : (%d ~ %d)\n",
						inputedRankPoint, minRankPoint, maxRankPoint);
				
			}
		}
		
		if(commentField.getText().trim().isEmpty()) {
			errMsg += "�ڸ�Ʈ�� �Է����ּ���.\n";
		}
		if(!errMsg.isEmpty()) { // �Է°��� ��ȿ���� ���� ��� ����� �����޽����� �Բ� ���� �߻�
			throw new IOException(errMsg);
		}		
    }
    
	class SubmitButtonListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				inputCheck();	
			} catch (IOException err) {
				JOptionPane.showMessageDialog(null, err.getMessage(), "�Է� ����",  JOptionPane.WARNING_MESSAGE);
				return; // ������ ����
			}
			RANK selectedRANK =  (RANK)rankComboBox.getSelectedItem();
			int rankPoint = Integer.valueOf(rankPointField.getText().trim());
			String comment = commentField.getText().trim();
			// �Է°��� ���� ���� ��ũ ������ ����
			ProblemRank problemRank = new ProblemRank(problem.getProblemID(), user, selectedRANK, comment); //rankPoint �ʵ�� �߰����� 
			System.out.println(problemRank + String.format(" rankPoint = %d", rankPoint));	
			
			ProblemRankManager.addRank(problem.getProblemID(), problemRank); // ������ ���� ��ũ ������ �߰�
			
		}
	}
    
 
}

