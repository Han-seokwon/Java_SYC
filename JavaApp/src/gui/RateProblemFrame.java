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
// 사용자가 문제의 랭크, 랭크 포인트, 그에 대한 코멘트를 입력하면 해당 데이터를 관련 클래스로 전달하는 클래스
public class RateProblemFrame extends DesignedJFrame {
	
	private JPanel centerPanel; // 프레임의 컴포넌트들을 배치할 컨텐트팬
	private JComboBox<RANK> rankComboBox; // 랭크 선택 콤보박스
	private JLabel rankPointLabel; // 랭크 포인트 라벨 ( 랭크 선택에 따라 입력 가능 최대, 최소 포인트 안내문을 출력하기 위해 별도 필드로 설정)
	private JTextField rankPointField; // 랭크 포인트를 입력받는 필드(정수만 입력받음)
	private int minRankPoint = 0; 	// 입력할 수 있는 최소 랭크 포인트
	private int maxRankPoint = RANK.getMaxRequireRankPoint(); 	// 입력할 수 있는 최대 랭크 포인트
	private JTextField commentField; // 코멘트를 입력받는 필드
	private User user; // 로그인된 유저
	private Problem problem; // 난이도 기여를 할 문제
	
    public RateProblemFrame(User user, Problem problem) {
    	super("문제 난이도 기여");
    	this.user = user; // 현재 로그인된 유저데이터 가져오기
    	this.problem = problem; // 현재 선택된 문제데이터 가져오기
    	
        // contentPane 생성
    	DesignedContentPane contentPane = new DesignedContentPane();
    	setContentPane(contentPane);
    	contentPane.setBorder(new EmptyBorder(80, 80, 80, 80)); // 패딩용 경계선 설정 
        BorderLayout borderLayout = new BorderLayout(); 
        contentPane.setLayout(borderLayout);
        
    	// 기본 내용을 추가할 중앙 패널 생성
    	centerPanel = new JPanel();               
        // centerPanel GridBagLayout 설정
        GridBagLayout gbl_contentPane = new GridBagLayout();
        gbl_contentPane.columnWidths = new int[] {500};
        gbl_contentPane.rowHeights = new int[] {100};
        gbl_contentPane.columnWeights = new double[]{1.0};
        gbl_contentPane.rowWeights = new double[]{0.0};
        centerPanel.setLayout(gbl_contentPane);
        contentPane.add(centerPanel, BorderLayout.CENTER); // 컨텐트 팬에 추가
                
        // 1행. 문제 난이도 선택 콤보박스 행 추가
        rankComboBox = new JComboBox<RANK>(RANK.values());
        rankComboBox.setSelectedIndex(-1); // 빈 값 선택부터 시작        
        rankComboBox.addActionListener(new RankComboBoxListener());
        addRowPanelToContentPane(0, new JLabel("문제 난이도"),rankComboBox);
       
        // 2행. 문제 랭크 포인트 행 추가   
        rankPointField = new JTextField();
        rankPointField.setHorizontalAlignment(SwingConstants.CENTER);
        rankPointField.setColumns(20);       
        // 정수 이외 값 입력 및 최대값 초과 자릿수 입력 제한 리스너 등록
        rankPointField.addKeyListener(new IntegerInputKeyListener());  
        
        rankPointLabel = new JLabel("랭크 포인트");
        addRowPanelToContentPane(1, rankPointLabel, rankPointField);
        
        // 3행. 랭크 기여 코멘트 행 추가  
        commentField = new JTextField();
        commentField.setHorizontalAlignment(SwingConstants.CENTER);
        commentField.setColumns(20);
        addRowPanelToContentPane(2, new JLabel("코멘트"), commentField);
        
        // 버튼 패널 생성
        JPanel buttonPanel = new JPanel();
        // 버튼 패널 레이아웃 설정
        FlowLayout flowLayout = (FlowLayout) buttonPanel.getLayout();
        flowLayout.setVgap(30);
        flowLayout.setHgap(30);
        flowLayout.setAlignment(FlowLayout.RIGHT);
        // 버튼 패널 contentPane에 추가
        contentPane.add(buttonPanel, BorderLayout.SOUTH);
        
        // 취소 버튼 패널에 추가
        buttonPanel.add(new CancelButton());
        // 제출 버튼 패널에 추가
        JButton submitButton = new DesignedButton("제출", COLOR.SUNFLOWER); 
        submitButton.addActionListener(new SubmitButtonListener()); // 리스너 등록
        buttonPanel.add(submitButton);

        
        contentPane.applyFontAndBackgroundToAllComponents(); // 전체 폰트 적용 및 패널 배경색 투명하게 적용
        setVisible(true); // 윈도우 display
    }
    
    /*
     * contentPane에 Label과 Component을 나란히 추가한 row 패널을 추가
     * @param  rowNum : 추가할 row 번호,   labelString : 라벨 텍스트, component : 추가할 컴포넌트(라벨 오른쪽에 배치됨)
     * */    
    private void addRowPanelToContentPane(int row, JLabel label, Component component) {
    	// contentPane에 row로 추가할 패널 생성
        JPanel rowPanel = new JPanel();
        // 패널 배치(GridBagConstraints) 설정
        GridBagConstraints gbc_rowPanel = new GridBagConstraints();
        gbc_rowPanel.weighty = 0.1;
        gbc_rowPanel.insets = new Insets(0, 0, 5, 0);
        gbc_rowPanel.fill = GridBagConstraints.BOTH;
        gbc_rowPanel.gridx = 0;
        gbc_rowPanel.gridy = row;
        // 패널에 contentPane에 추가
        centerPanel.add(rowPanel, gbc_rowPanel);
        
        // 패널 레이아웃 설정
        GridBagLayout gbl_rowPanel = new GridBagLayout();
        gbl_rowPanel.columnWidths = new int[] {200, 170};
        gbl_rowPanel.rowHeights = new int[] {80};
        rowPanel.setLayout(gbl_rowPanel);
        
        // 패널에 라벨 추가
        label.setHorizontalAlignment(SwingConstants.CENTER);
        // 라벨 배치 설정
        GridBagConstraints gbc_label = new GridBagConstraints();
        gbc_label.weightx = 0.3;
        gbc_label.fill = GridBagConstraints.BOTH;
        gbc_label.insets = new Insets(0, 0, 0, 5);
        gbc_label.gridx = 0;
        gbc_label.gridy = 0;
        // 라벨 패널에 추가
        rowPanel.add(label, gbc_label);        
        
        // 컴포넌트 사이즈 설정
        component.setPreferredSize(new Dimension(160, 30));
        // 추가할 컴포넌트 배치 설정
        GridBagConstraints gbc_component = new GridBagConstraints();
        gbc_component.weightx = 1.0;
        gbc_component.gridx = 1;
        gbc_component.gridy = 0;
        // 컴포넌트 패널에 추가
        rowPanel.add(component, gbc_component);
    }
    
    
    // 입력값 유효성 확인
    private void inputCheck() throws IOException{
    	String errMsg = "";
    	int comboBoxIdx = rankComboBox.getSelectedIndex();
		if(comboBoxIdx == -1) {
			errMsg += "랭크 콤보박스를 선택해주세요.\n";
		} 
		if(rankPointField.getText().trim().isEmpty()) {
			errMsg += "랭크 포인트를 입력해주세요.\n";
		} else if(comboBoxIdx != -1){ // 랭크 포인트를 입력하였고 랭크 콤보박스가 선택된 경우
			// 최소, 최대 입력 범위에서 벗어났는지 확인
			int inputedRankPoint = Integer.valueOf(rankPointField.getText().trim());
			if(inputedRankPoint < minRankPoint || inputedRankPoint > maxRankPoint) {
				errMsg += String.format("랭크 포인트 입력 범위를 벗어났습니다. (입력값 = %d) \n 입력가능 범위 : (%d ~ %d)\n",
						inputedRankPoint, minRankPoint, maxRankPoint);				
			}
		}
		
		if(commentField.getText().trim().isEmpty()) {
			errMsg += "코멘트를 입력해주세요.\n";
		}
		if(!errMsg.isEmpty()) { // 입력값이 유효하지 않은 경우 저장된 에러메시지와 함께 에러 발생
			throw new IOException(errMsg);
		}		
    }
    
    // 랭크 콤보박스를 선택할 경우 발생하는 리스너로, 선택된 랭크(RANK)에 맞게 랭크 포인트 입력 라벨에 입력 가능한 최소, 최대 포인트를 정보를 추가함
    class RankComboBoxListener implements ActionListener {    	
        @Override
        public void actionPerformed(ActionEvent e) {
        	RANK selectedRANK = (RANK)rankComboBox.getSelectedItem();
        	minRankPoint = selectedRANK.getRequireRankPoint();
        	maxRankPoint = minRankPoint + RANK.getRankPointGap();
            // 라벨에 입력 최소, 최대값 가이드 출력
            rankPointLabel.setText(String.format("랭크 포인트 (%d ~ %d)", minRankPoint, maxRankPoint));
        }
    }
    
    
 // 정수 이외 값 입력 및 자릿수 입력 제한 리스너
    class IntegerInputKeyListener extends KeyAdapter {
        @Override
        public void keyTyped(KeyEvent ke) {
            char c = ke.getKeyChar();
            int len = ((JTextField)ke.getSource()).getText().length(); // 입력한 글자 길이
            int maxDigit =  (int)Math.log10(RANK.getMaxRequireRankPoint()); // 가능한 최대 숫자 자리수
            // 숫자, 백스페이스, 딜리트 키가 아니거나 길이가 maxDigit을 초과하는 경우 ke 삭제
            if (!(Character.isDigit(c) || (c == KeyEvent.VK_BACK_SPACE) || (c == KeyEvent.VK_DELETE)) || len > maxDigit) {
            	ke.consume();
            }
        }
    }
    
    
	class SubmitButtonListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				inputCheck();// 입력값 유효성 확인
			} catch (IOException err) {
				Dialog.showAlertDialog("입력 오류", err.getMessage()); 
				return; // 리스너 종료
			}
			// 입력값이 유효한 경우
			// 콤보 박스에서 선택된 RANK 가져오기
			RANK selectedRANK =  (RANK)rankComboBox.getSelectedItem();
			// 랭크 입력 필드에서 입력된 랭크 포인트값 정수로 가져오기
			int rankPoint = Integer.valueOf(rankPointField.getText().trim());
			// 입력한 코멘트 양 끝 공백 제거 후 가져오기
			String comment = commentField.getText().trim();
			// 입력값을 토대로 문제 랭크 데이터 생성
			ProblemRank problemRank = new ProblemRank(problem.getProblemID(), user, selectedRANK, rankPoint, comment); //rankPoint 필드는 추가예정 
			System.out.println(problemRank);			
			
			ProblemRankManager.createRank(problemRank); // 문제 랭크 데이터 생성
			ProblemRankManager.PrintProblemRankMap();
			
			Dialog.showInfoDialog("난이도 기여 성공", "입력하신 내용이 정상적으로 반영되었습니다.");
			dispose();
		}
	}
    
 
}

