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
public class RateProblemFrame extends DesignedJFrame {
	
	private JPanel centerPanel; // �������� ������Ʈ���� ��ġ�� ����Ʈ��
	private JComboBox<RANK> rankComboBox; // ��ũ ���� �޺��ڽ�
	private JLabel rankPointLabel; // ��ũ ����Ʈ �� ( ��ũ ���ÿ� ���� �Է� ���� �ִ�, �ּ� ����Ʈ �ȳ����� ����ϱ� ���� ���� �ʵ�� ����)
	private JTextField rankPointField; // ��ũ ����Ʈ�� �Է¹޴� �ʵ�(������ �Է¹���)
	private int minRankPoint = 0; 	// �Է��� �� �ִ� �ּ� ��ũ ����Ʈ
	private int maxRankPoint = RANK.getMaxRequireRankPoint(); 	// �Է��� �� �ִ� �ִ� ��ũ ����Ʈ
	private JTextField commentField; // �ڸ�Ʈ�� �Է¹޴� �ʵ�
	private User user; // �α��ε� ����
	private Problem problem; // ���̵� �⿩�� �� ����
	
    public RateProblemFrame(User user, Problem problem) {
    	super("���� ���̵� �⿩");
    	this.user = user; // ���� �α��ε� ���������� ��������
    	this.problem = problem; // ���� ���õ� ���������� ��������
    	
        // contentPane ����
    	DesignedContentPane contentPane = new DesignedContentPane();
    	setContentPane(contentPane);
    	contentPane.setBorder(new EmptyBorder(80, 80, 80, 80)); // �е��� ��輱 ���� 
        BorderLayout borderLayout = new BorderLayout(); 
        contentPane.setLayout(borderLayout);
        
    	// �⺻ ������ �߰��� �߾� �г� ����
    	centerPanel = new JPanel();               
        // centerPanel GridBagLayout ����
        GridBagLayout gbl_contentPane = new GridBagLayout();
        gbl_contentPane.columnWidths = new int[] {500};
        gbl_contentPane.rowHeights = new int[] {100};
        gbl_contentPane.columnWeights = new double[]{1.0};
        gbl_contentPane.rowWeights = new double[]{0.0};
        centerPanel.setLayout(gbl_contentPane);
        contentPane.add(centerPanel, BorderLayout.CENTER); // ����Ʈ �ҿ� �߰�
                
        // 1��. ���� ���̵� ���� �޺��ڽ� �� �߰�
        rankComboBox = new JComboBox<RANK>(RANK.values());
        rankComboBox.setSelectedIndex(-1); // �� �� ���ú��� ����        
        rankComboBox.addActionListener(new RankComboBoxListener());
        addRowPanelToContentPane(0, new JLabel("���� ���̵�"),rankComboBox);
       
        // 2��. ���� ��ũ ����Ʈ �� �߰�   
        rankPointField = new JTextField();
        rankPointField.setHorizontalAlignment(SwingConstants.CENTER);
        rankPointField.setColumns(20);       
        // ���� �̿� �� �Է� �� �ִ밪 �ʰ� �ڸ��� �Է� ���� ������ ���
        rankPointField.addKeyListener(new IntegerInputKeyListener());  
        
        rankPointLabel = new JLabel("��ũ ����Ʈ");
        addRowPanelToContentPane(1, rankPointLabel, rankPointField);
        
        // 3��. ��ũ �⿩ �ڸ�Ʈ �� �߰�  
        commentField = new JTextField();
        commentField.setHorizontalAlignment(SwingConstants.CENTER);
        commentField.setColumns(20);
        addRowPanelToContentPane(2, new JLabel("�ڸ�Ʈ"), commentField);
        
        // ��ư �г� ����
        JPanel buttonPanel = new JPanel();
        // ��ư �г� ���̾ƿ� ����
        FlowLayout flowLayout = (FlowLayout) buttonPanel.getLayout();
        flowLayout.setVgap(30);
        flowLayout.setHgap(30);
        flowLayout.setAlignment(FlowLayout.RIGHT);
        // ��ư �г� contentPane�� �߰�
        contentPane.add(buttonPanel, BorderLayout.SOUTH);
        
        // ��� ��ư �гο� �߰�
        buttonPanel.add(new CancelButton());
        // ���� ��ư �гο� �߰�
        JButton submitButton = new DesignedButton("����", COLOR.SUNFLOWER); 
        submitButton.addActionListener(new SubmitButtonListener()); // ������ ���
        buttonPanel.add(submitButton);

        
        contentPane.applyFontAndBackgroundToAllComponents(); // ��ü ��Ʈ ���� �� �г� ���� �����ϰ� ����
        setVisible(true); // ������ display
    }
    
    /*
     * contentPane�� Label�� Component�� ������ �߰��� row �г��� �߰�
     * @param  rowNum : �߰��� row ��ȣ,   labelString : �� �ؽ�Ʈ, component : �߰��� ������Ʈ(�� �����ʿ� ��ġ��)
     * */    
    private void addRowPanelToContentPane(int row, JLabel label, Component component) {
    	// contentPane�� row�� �߰��� �г� ����
        JPanel rowPanel = new JPanel();
        // �г� ��ġ(GridBagConstraints) ����
        GridBagConstraints gbc_rowPanel = new GridBagConstraints();
        gbc_rowPanel.weighty = 0.1;
        gbc_rowPanel.insets = new Insets(0, 0, 5, 0);
        gbc_rowPanel.fill = GridBagConstraints.BOTH;
        gbc_rowPanel.gridx = 0;
        gbc_rowPanel.gridy = row;
        // �гο� contentPane�� �߰�
        centerPanel.add(rowPanel, gbc_rowPanel);
        
        // �г� ���̾ƿ� ����
        GridBagLayout gbl_rowPanel = new GridBagLayout();
        gbl_rowPanel.columnWidths = new int[] {200, 170};
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
        component.setPreferredSize(new Dimension(160, 30));
        // �߰��� ������Ʈ ��ġ ����
        GridBagConstraints gbc_component = new GridBagConstraints();
        gbc_component.weightx = 1.0;
        gbc_component.gridx = 1;
        gbc_component.gridy = 0;
        // ������Ʈ �гο� �߰�
        rowPanel.add(component, gbc_component);
    }
    
    
    // �Է°� ��ȿ�� Ȯ��
    private void inputCheck() throws IOException{
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
    
    // ��ũ �޺��ڽ��� ������ ��� �߻��ϴ� �����ʷ�, ���õ� ��ũ(RANK)�� �°� ��ũ ����Ʈ �Է� �󺧿� �Է� ������ �ּ�, �ִ� ����Ʈ�� ������ �߰���
    class RankComboBoxListener implements ActionListener {    	
        @Override
        public void actionPerformed(ActionEvent e) {
        	RANK selectedRANK = (RANK)rankComboBox.getSelectedItem();
        	minRankPoint = selectedRANK.getRequireRankPoint();
        	maxRankPoint = minRankPoint + RANK.getRankPointGap();
            // �󺧿� �Է� �ּ�, �ִ밪 ���̵� ���
            rankPointLabel.setText(String.format("��ũ ����Ʈ (%d ~ %d)", minRankPoint, maxRankPoint));
        }
    }
    
    
 // ���� �̿� �� �Է� �� �ڸ��� �Է� ���� ������
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
    
    
	class SubmitButtonListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				inputCheck();// �Է°� ��ȿ�� Ȯ��
			} catch (IOException err) {
				Dialog.showAlertDialog("�Է� ����", err.getMessage()); 
				return; // ������ ����
			}
			// �Է°��� ��ȿ�� ���
			// �޺� �ڽ����� ���õ� RANK ��������
			RANK selectedRANK =  (RANK)rankComboBox.getSelectedItem();
			// ��ũ �Է� �ʵ忡�� �Էµ� ��ũ ����Ʈ�� ������ ��������
			int rankPoint = Integer.valueOf(rankPointField.getText().trim());
			// �Է��� �ڸ�Ʈ �� �� ���� ���� �� ��������
			String comment = commentField.getText().trim();
			// �Է°��� ���� ���� ��ũ ������ ����
			ProblemRank problemRank = new ProblemRank(problem.getProblemID(), user, selectedRANK, rankPoint, comment); //rankPoint �ʵ�� �߰����� 
			System.out.println(problemRank);			
			
			ProblemRankManager.createRank(problemRank); // ���� ��ũ ������ ����
			ProblemRankManager.PrintProblemRankMap();
			
			Dialog.showInfoDialog("���̵� �⿩ ����", "�Է��Ͻ� ������ ���������� �ݿ��Ǿ����ϴ�.");
			dispose();
		}
	}
    
 
}

