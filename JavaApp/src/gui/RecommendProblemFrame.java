package gui;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

import problems.Problem;
import problems.ProblemDBManager;
import users.User;


// 테이블 헤더 정보를 저장하는 열거형
enum TABLE_HEADER{ 
	ID(0, "문제 번호"), TITLE(1, "문제 제목"), RANK(2, "난이도"), URL(3, "URL");

	private int idx; // 헤더 인덱스
	private String name; // 헤더 이름
	private TABLE_HEADER(int idx, String name) {
		this.idx = idx;
		this.name = name;
	}
	public int getIdx() {
		return idx;
	}
	public String getName() {
		return name;
	}
	// 헤더요소(열거형)의 개수를 반환 
	public static int length() {
		return TABLE_HEADER.values().length;
	}
	// 헤더요소(열거형)의 문자열 이름을 벡터로 반환, DefaultTableModel에서 컬럼이름(헤더)를 Vector로 받음
	public static Vector<String> getHeaderNames() {
		Vector<String> headerNames = new Vector<>(TABLE_HEADER.length());
		for(TABLE_HEADER header : TABLE_HEADER.values()) {
			headerNames.add(header.getName());
		}
		return headerNames;    	
	}
}


public class RecommendProblemFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JButton recommendByRankButton, recommendByAlgorithmTypeButton ; // 난이도별 추천 버튼, 알고리즘별 추천 버튼
	private JLabel seletedBtnDisplayLabel; 	// 선택된 추천방식 버튼 결과를 보여주는 라벨
	private ProblemTable recommendedProblemTable;//추천 문제 테이블
	// 테이블에 동적으로 데이터를 추가하기 위한 모델 객체 생성
	private DefaultTableModel tableModel = new DefaultTableModel(TABLE_HEADER.getHeaderNames(), 0) { // 테이블 헤더, row 개수
		@Override
		public boolean isCellEditable(int row, int column) { return true; } // 셀 내용 수정할 수 없게 오버라이딩
	};  
	private User user;// 현재 로그인된 유저
	private ArrayList<Problem> recommendedProblemList = new ArrayList<>(); // 추천된 문제 리스트

	public RecommendProblemFrame(User user) {	
		this.user= user; // 현재 로그인된 유저

		// 프레임 속성 설정
		final int FRAME_WIDTH = 1200;
		final int FRAME_HEIGHT = 800;
		setTitle("문제 추천");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, FRAME_WIDTH, FRAME_HEIGHT);
		
		// contentPane 레이아웃 설정
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{FRAME_WIDTH};
		final int CONTENT_ROW_HEIGHT = 550;
		gbl_contentPane.rowHeights = new int[] {FRAME_HEIGHT - FRAME_HEIGHT, CONTENT_ROW_HEIGHT};
		contentPane.setLayout(gbl_contentPane);

		// 문제 추천 메뉴 레이아웃 설정
		JPanel recommendMenuPanel = new JPanel();
		contentPane.add(recommendMenuPanel);
		GridBagLayout gbl_recommendMenuPanel = new GridBagLayout();
		gbl_recommendMenuPanel.columnWidths = new int[] {300, 300, 300};
		gbl_recommendMenuPanel.rowHeights = new int[] {150};
		recommendMenuPanel.setLayout(gbl_recommendMenuPanel);

		// 추천방식 선택 버튼용 패널 추가
		JPanel recommendMethodButtonPanel = new JPanel();
		recommendMethodButtonPanel.setBorder(createCompoundBorder(5, 20)); // 경계선 설정
		GridBagConstraints gbc_recommendMethodButtonPanel = new GridBagConstraints();
		gbc_recommendMethodButtonPanel.fill = GridBagConstraints.BOTH;
		recommendMenuPanel.add(recommendMethodButtonPanel, gbc_recommendMethodButtonPanel);
		// 추천방식 선택 버튼용 패널 레이아웃 설정
		GridBagLayout gbl_recommendMethodButtonPanel = new GridBagLayout();
		gbl_recommendMethodButtonPanel.columnWidths = new int[]{250, 0};
		gbl_recommendMethodButtonPanel.rowHeights = new int[] {50, 50, 50, 0};
		recommendMethodButtonPanel.setLayout(gbl_recommendMethodButtonPanel);

		// 알고리즘별 추천 버튼 추가
		recommendByAlgorithmTypeButton = new JButton("알고리즘별 추천");
		recommendByAlgorithmTypeButton.addActionListener(new ButtonActionListener()); // 이벤트 리스너 추가
		// 알고리즘별 추천 버튼 레이아웃 설정
		GridBagConstraints gbc_recommendByAlgorithmTypeButton = new GridBagConstraints();
		gbc_recommendByAlgorithmTypeButton.fill = GridBagConstraints.BOTH;
		gbc_recommendByAlgorithmTypeButton.gridx = 0;
		gbc_recommendByAlgorithmTypeButton.gridy = 0;
		recommendMethodButtonPanel.add(recommendByAlgorithmTypeButton, gbc_recommendByAlgorithmTypeButton);


		// 난이도별 추천 버튼 추가
		recommendByRankButton = new JButton("난이도별 추천");
		recommendByRankButton.addActionListener(new ButtonActionListener()); // 이벤트 리스너 추가		
		// 난이도별 추천 버튼 레이아웃 설정
		GridBagConstraints gbc_recommendByRankButton = new GridBagConstraints();
		gbc_recommendByRankButton.fill = GridBagConstraints.BOTH;
		gbc_recommendByRankButton.gridx = 0;
		gbc_recommendByRankButton.gridy = 2;
		recommendMethodButtonPanel.add(recommendByRankButton, gbc_recommendByRankButton);

		// 화살표 이미지 라벨 추가
		JLabel arrowImgLabel = new JLabel("->"); // 나중에 이미지로 대체
		GridBagConstraints gbc_arrowImgLabel = new GridBagConstraints();
		gbc_arrowImgLabel.gridx = 1;
		gbc_arrowImgLabel.gridy = 0;
		recommendMenuPanel.add(arrowImgLabel, gbc_arrowImgLabel);

		// 선택된 추천방식 버튼 결과를 보여주는 라벨
		seletedBtnDisplayLabel = new JLabel("문제 추천방식을 선택해주세요.");
		seletedBtnDisplayLabel.setHorizontalAlignment(SwingConstants.CENTER);
		seletedBtnDisplayLabel.setBorder(createCompoundBorder(5, 20)); // 경계선 설정
		GridBagConstraints gbc_seletedBtnDisplayLabel = new GridBagConstraints();
		gbc_seletedBtnDisplayLabel.fill = GridBagConstraints.BOTH;
		gbc_seletedBtnDisplayLabel.gridx = 2;
		gbc_seletedBtnDisplayLabel.gridy = 0;
		recommendMenuPanel.add(seletedBtnDisplayLabel, gbc_seletedBtnDisplayLabel);

		// 추천된 문제 리스트를 담는 패널 레이아웃 설정
		JPanel recommendedProblemListPanel = new JPanel();
		GridBagConstraints gbc_recommendedProblemListPanel = new GridBagConstraints();
		gbc_recommendedProblemListPanel.gridx = 0;
		gbc_recommendedProblemListPanel.gridy = 1;
		contentPane.add(recommendedProblemListPanel, gbc_recommendedProblemListPanel);
		GridBagLayout gbl_recommendedProblemListPanel = new GridBagLayout();
		gbl_recommendedProblemListPanel.columnWidths = new int[] {900, 0};
		gbl_recommendedProblemListPanel.rowHeights = new int[] {50, 400};
		recommendedProblemListPanel.setLayout(gbl_recommendedProblemListPanel);

		// 문제리스트용 소제목 라벨 추가		
		JLabel subtitleLabel = new JLabel("추천된 문제 리스트");
		subtitleLabel.setFont(new Font("굴림", Font.PLAIN, 25));
		subtitleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_subtitleLabel = new GridBagConstraints();
		gbc_subtitleLabel.fill = GridBagConstraints.BOTH;
		recommendedProblemListPanel.add(subtitleLabel, gbc_subtitleLabel);

		// 문제리스트용 스크롤팬 추가
		JScrollPane scrollPane = new JScrollPane();
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 0;
		gbc_scrollPane.gridy = 1;
		recommendedProblemListPanel.add(scrollPane, gbc_scrollPane);

		recommendedProblemTable = new ProblemTable(user); // 테이블에 테이블 모델 설정
		scrollPane.setViewportView(recommendedProblemTable);

		setVisible(true); // 윈도우 show
	}

	// 경계선 설정
	private CompoundBorder createCompoundBorder(int borderWidth, int borderPadding) {
		return new CompoundBorder(new LineBorder(new Color(0, 0, 0), borderWidth, true),
				new EmptyBorder(borderPadding, borderPadding, borderPadding, borderPadding));
	}

	class ButtonActionListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			recommendedProblemTable.clearTableRows(); // 기존에 입력된 테이블 행 초기화
			List<Problem> recommendedProblemList = new ArrayList<>();

			JButton selectedButton = (JButton)e.getSource(); 
			if(selectedButton== recommendByAlgorithmTypeButton) { 
				System.out.println("알고리즘별 추천 버튼  clicked!");
				// 테스트용 -> 추후 삭제
				recommendedProblemList = ProblemDBManager.findProblemSearch("정렬");
				System.out.println(recommendedProblemList);

				//				recommendedProblemList = RecommendProblem.메서드이름 // 클래스 완성시 추가
			} else if(selectedButton == recommendByRankButton) {				
				System.out.println("난이도별 추천 버튼 clicked!");
				// 테스트용 -> 추후 삭제
				recommendedProblemList = ProblemDBManager.findProblemSearch("함수");
				System.out.println(recommendedProblemList);				
				// recommendedProblemList = RecommendProblem.메서드이름(user) // 클래스 완성시 추가
			}
			seletedBtnDisplayLabel.setText(selectedButton.getText()); // 클릭된 버튼에 맞게 라벨 내용을 변경
			recommendedProblemTable.addProblemListToTable(recommendedProblemList); // 추천된 문제리스트를 테이블에 추가

		}
	}


}
