package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import problems.Problem;
import problems.ProblemDBManager;
import users.User;


public class RecommendProblemFrame extends DesignedJFrame {
	private static final long serialVersionUID = 1L;
	private DesignedContentPane contentPane;
	private JButton recommendByRankButton, recommendByAlgorithmTypeButton ; // 난이도별 추천 버튼, 알고리즘별 추천 버튼
	private JLabel seletedBtnDisplayLabel; 	// 선택된 추천방식 버튼 결과를 보여주는 라벨
	private ProblemTable recommendedProblemTable;// 추천된 문제를 담는 테이블
	private User user;// 현재 로그인된 유저

	public RecommendProblemFrame(User user) {	
		super("문제 추천");
		this.user= user; // 현재 로그인된 유저
		
		// contentPane 레이아웃 설정
		contentPane = new DesignedContentPane();
		contentPane.setBorder(new EmptyBorder(20, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{getWindowWidth()};
		gbl_contentPane.rowHeights = new int[] {300, getWindowHeight() - 300};
		contentPane.setLayout(gbl_contentPane);

		// 문제 추천 메뉴 레이아웃 설정
		JPanel recommendMenuPanel = new JPanel();
		GridBagConstraints gbc_recommendMenuPanel = new GridBagConstraints();
		gbc_recommendMenuPanel.fill = GridBagConstraints.BOTH;
		contentPane.add(recommendMenuPanel, gbc_recommendMenuPanel);
		GridBagLayout gbl_recommendMenuPanel = new GridBagLayout();
		final int MENU_PANEL_WIDTH = 300;
		gbl_recommendMenuPanel.columnWidths = new int[] {MENU_PANEL_WIDTH, MENU_PANEL_WIDTH, MENU_PANEL_WIDTH};
		gbl_recommendMenuPanel.rowHeights = new int[] {MENU_PANEL_WIDTH/2};
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
		recommendByAlgorithmTypeButton = new DesignedButton("알고리즘별 추천", COLOR.CHET_WODE_BLUE);
		recommendByAlgorithmTypeButton.addActionListener(new ButtonActionListener()); // 이벤트 리스너 추가
		// 알고리즘별 추천 버튼 레이아웃 설정
		GridBagConstraints gbc_recommendByAlgorithmTypeButton = new GridBagConstraints();
		gbc_recommendByAlgorithmTypeButton.fill = GridBagConstraints.BOTH;
		gbc_recommendByAlgorithmTypeButton.gridx = 0;
		gbc_recommendByAlgorithmTypeButton.gridy = 0;
		recommendMethodButtonPanel.add(recommendByAlgorithmTypeButton, gbc_recommendByAlgorithmTypeButton);


		// 난이도별 추천 버튼 추가
		recommendByRankButton = new DesignedButton("난이도별 추천", COLOR.SUNFLOWER);
		recommendByRankButton.addActionListener(new ButtonActionListener()); // 이벤트 리스너 추가		
		// 난이도별 추천 버튼 레이아웃 설정
		GridBagConstraints gbc_recommendByRankButton = new GridBagConstraints();
		gbc_recommendByRankButton.fill = GridBagConstraints.BOTH;
		gbc_recommendByRankButton.gridx = 0;
		gbc_recommendByRankButton.gridy = 2;
		recommendMethodButtonPanel.add(recommendByRankButton, gbc_recommendByRankButton);

		// 화살표 이미지 라벨 추가
		ImageIcon arrowImg = new ImageIcon(RecommendProblemFrame.class.getResource("/sources/right-arrow.png"));
		int imgWidth = (int)(MENU_PANEL_WIDTH*2/3);
		Image resizedImg = arrowImg.getImage().getScaledInstance(imgWidth, imgWidth/2, Image.SCALE_SMOOTH); // 사이즈 조정		
		JLabel arrowImgLabel = new JLabel(new ImageIcon(resizedImg)); 
		arrowImgLabel.setBounds(0, 0, 150, 150);
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
		recommendedProblemListPanel.setBorder(createCompoundBorder(5, 20)); // 경계선 설정
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

		recommendedProblemTable = new ProblemTable(user); // 추천된 문제들을 담는 테이블 생성
		scrollPane.setViewportView(recommendedProblemTable);

		
		contentPane.applyFontAndBackgroundToAllComponents(); // 전체 폰트 적용 및 패널 배경색 투명하게 적용
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
			
			List<Problem> recommendedProblemList = new ArrayList<>();

			JButton selectedButton = (JButton)e.getSource(); 
			if(selectedButton== recommendByAlgorithmTypeButton) {
				
				System.out.println("알고리즘별 추천 버튼  clicked!");
				// 테스트용 -> 추후 삭제
				ArrayList<String> temp = new ArrayList<>(Arrays.asList("정렬"));
				recommendedProblemList = ProblemDBManager.findProblemAlgorithm(temp);
				// recommendedProblemList = RecommendProblem.메서드이름 // 클래스 완성시 추가
				
			} else if(selectedButton == recommendByRankButton) {				
				System.out.println("난이도별 추천 버튼 clicked!");
				// 테스트용 -> 추후 삭제
				ArrayList<String> temp = new ArrayList<>(Arrays.asList("수"));
				recommendedProblemList = ProblemDBManager.findProblemAlgorithm(temp);			
				// recommendedProblemList = RecommendProblem.메서드이름(user) // 클래스 완성시 추가
			}
			seletedBtnDisplayLabel.setText(selectedButton.getText()); // 클릭된 버튼에 맞게 라벨 내용을 변경
			recommendedProblemTable.updateProblemListToTable(recommendedProblemList); // 추천된 문제리스트를 테이블에 추가
		}
	}


}
