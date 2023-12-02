package gui;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import problems.Problem;
import problems.RecommendProblems;
import users.RANK;
import users.User;


public class RecommendProblemFrame extends DesignedJFrame {
	private static final long serialVersionUID = 1L;

	private DesignedContentPane contentPane;
	private JButton recommendByRankButton, recommendByAlgorithmTypeButton ; // 난이도별 추천 버튼, 알고리즘별 추천 버튼
	private JTextPane seletedBtnDisplayArea; // 선택된 추천방식 버튼 결과를 보여줌
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
		final int MENU_PANEL_WIDTH = 300; // 메뉴 패널 너비
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
		seletedBtnDisplayArea = new JTextPane();
		seletedBtnDisplayArea.setText("\n\n문제 추천방식을 선택해주세요.");
		alignCenter(seletedBtnDisplayArea); // 가운데 정렬
		seletedBtnDisplayArea.setBorder(createCompoundBorder(5, 20)); // 경계선 설정
		GridBagConstraints gbc_seletedBtnDisplayLabel = new GridBagConstraints();
		gbc_seletedBtnDisplayLabel.fill = GridBagConstraints.BOTH;
		gbc_seletedBtnDisplayLabel.gridx = 2;
		gbc_seletedBtnDisplayLabel.gridy = 0;
		recommendMenuPanel.add(seletedBtnDisplayArea, gbc_seletedBtnDisplayLabel);

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

	// JTextPane 가운데 정렬
	private void alignCenter(JTextPane pane) {
		StyledDocument doc = pane.getStyledDocument();
		SimpleAttributeSet center = new SimpleAttributeSet();
		StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
		doc.setParagraphAttributes(0, doc.getLength(), center, false);
	}

	// 경계선 설정
	private CompoundBorder createCompoundBorder(int borderWidth, int borderPadding) {
		return new CompoundBorder(new LineBorder(COLOR.CHET_WODE_BLUE.getColor(), borderWidth, true),
				new EmptyBorder(borderPadding, borderPadding, borderPadding, borderPadding));
	}

	// 선호알고리즘 종료를 모은 HashSet<String>을 하나의 문자열로 만들음
	private String getPreferredAlgorithmTypeRecommendGuideString( String buttonText, HashSet<String> preferredAlgorithmTypeSet) {
		String guideString = "\n" + buttonText + "\n추천 종류 : ";
		Iterator<String> it = preferredAlgorithmTypeSet.iterator();
		while(it.hasNext()) {
			guideString += (it.next() + ", ");			
		}		
		return guideString;
	}
	
	private String getRankRecommendGuideString( String buttonText, RANK rank, int rankPoint) {
		String guideString = "\n" + buttonText + "\n" + String.format("사용자 랭크 : %s(%d)\n", rank.getRankName(), rankPoint);
		// 최소 랭크 포인트가 음수인 경우 0으로
		int minPoint = (rankPoint - 25) >= 0 ? (rankPoint - 25) : 0; 		
		// 최대 랭크 포인트 최대값을 초과하면 최대값으로
		int maxPoint = (rankPoint + 25) <= RANK.getMaxRequireRankPoint() ? (rankPoint + 25) : RANK.getMaxRequireRankPoint();
		guideString += String.format("추천 범위 : %d ~ %d rank point", minPoint, maxPoint);
		return guideString;		
	}
	


	class ButtonActionListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {			
			List<Problem> recommendedProblemList = new ArrayList<>();

			// 선택된 추천 방식에 따른 안내문
			String seletedButtonGuideString = seletedBtnDisplayArea.getText();

			JButton selectedButton = (JButton)e.getSource(); 
			if(selectedButton== recommendByAlgorithmTypeButton) {		
				// 유저가 선택한 선호 알고리즘 종류가 없는 경우
				if(user.getPreferredAlgorithmTypeSet().size() == 0) { 
					seletedBtnDisplayArea.setText("\n\n선호하는 알고리즘 유형이 없습니다.\n 설문조사를 다시 실시해주세요.");
					recommendedProblemTable.updateProblemListToTable(recommendedProblemList);
					return;
				}
				// 유저가 선호하는 알고리즘에 맞추어 문제 추천
				recommendedProblemList = RecommendProblems.recommendProblemsByAlgorithm(user);
				//알고리즘 추천 안내문
				seletedButtonGuideString = getPreferredAlgorithmTypeRecommendGuideString(selectedButton.getText(), user.getPreferredAlgorithmTypeSet());			

			} else if(selectedButton == recommendByRankButton) {				
				// 유저의 랭크 포인트에 맞게 문제 추천
				recommendedProblemList = RecommendProblems.recommendProblemsByRank(user);
				// 랭크 추천 안내문
				seletedButtonGuideString =  getRankRecommendGuideString(selectedButton.getText(), user.getRank(), user.getRankPoint());	
			}
			seletedBtnDisplayArea.setText(seletedButtonGuideString); // 클릭된 버튼에 맞게 안내문 내용을 변경
			System.out.println(recommendedProblemList.toString());
			recommendedProblemTable.updateProblemListToTable(recommendedProblemList); // 추천된 문제리스트를 테이블에 추가
		}
	}




}
