package gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import file.FileManager;
import problems.Problem;
import users.RANK;
import users.User;


//테이블 헤더 정보를 저장하는 열거형
enum TABLE_HEADER{ 
	ID(0, "문제 번호", 100), TITLE(1, "문제 제목", 250), RANK(2, "난이도", 150), URL(3, "URL", 300);

	private int idx; // 헤더 인덱스
	private String name; // 헤더 이름
	private int width; // 헤더 너비
	
	private TABLE_HEADER(int idx, String name, int width) {
		this.idx = idx;
		this.name = name;
		this.width = width;

	}
	public int getIdx() {
		return idx;
	}
	public String getName() {
		return name;
	}
	public int getWidth() {
		return width;
	}
	// 헤더요소(열거형)의 개수를 반환 
	public static int length() {
		return TABLE_HEADER.values().length;
	}
	// 헤더요소(열거형)의 문자열 이름을 벡터로 반환, (DefaultTableModel에서 컬럼이름(헤더)를 Vector로 받아 Vector로 컬렉션 설정)
	public static Vector<String> getHeaderNameList() {
		Vector<String> headerNames = new Vector<>(TABLE_HEADER.length());
		for(TABLE_HEADER header : TABLE_HEADER.values()) {
			headerNames.add(header.getName());
		}
		return headerNames;    	
	}
}


// 문제리스트를 동적으로 할당받아 테이블을 구성하는 클래스
public class ProblemTable extends JTable{
	private static final long serialVersionUID = 1L;
	
	private final static int ROW_HEIGHT = 40;  // 행 높이
	// 테이블에 동적으로 데이터를 추가하기 위한 모델 객체 생성
	private DefaultTableModel tableModel;
	private ArrayList<Problem> problemList = new ArrayList<>(); // 추가할 문제 리스트
	private User user;// 현재 로그인된 유저

	public ProblemTable(User user) {
		super();// JTable 멤버 상속받기
		
		this.user = user; // 현재 로그인된 유저데이터 가져오기

		// 테이블에 동적으로 데이터를 추가하기 위한 테이블 모델 객체 생성
		tableModel = new DefaultTableModel(TABLE_HEADER.getHeaderNameList(), 0) { // 테이블 헤더, row 개수
			private static final long serialVersionUID = 1L;
			@Override
			public boolean isCellEditable(int row, int column) { return false; } // 셀 내용 수정할 수 없게 오버라이딩
		};
		setModel(tableModel); // 테이블 모델 적용

		// 헤더 높이 설정
		Dimension headerSize = getTableHeader().getPreferredSize();
		headerSize.height = ROW_HEIGHT;
		getTableHeader().setPreferredSize(headerSize);
		
		// 테이블 높이, 너비, 폰트 설정		
		for(TABLE_HEADER header : TABLE_HEADER.values()) { // 헤더로 각 열 순회
			TableColumn col = getColumnModel().getColumn(header.getIdx()); // 현재 열 가져오기
			col.setPreferredWidth(header.getWidth()); // 너비 설정
			col.setCellRenderer(new TableCellRenderer()); // 열다마 셀 렌더링 설정 -> url 폰트 및 랭크 아이콘 추가
		}		
		
		setRowHeight(ROW_HEIGHT); // 기본 행 높이 설정		
		addMouseListener(new TableClickListener()); // 테이블 클릭에 대한 이벤트 리스너 등록

		try { // 테이블 폰트 지정
			Font font = FileManager.createFontFromFile("contentFont");
			getTableHeader().setFont(font.deriveFont(20f)); // 테이블 헤더 폰트 지정
			setFont(font.deriveFont(17f)); // 테이블 셀 폰트 지정
		} catch (IOException e) {  // 폰트를 가져오지 못한 경우
			System.out.println(e.getMessage());				
		}
		// 테이블헤더 배경색 지정
		getTableHeader().setBackground(COLOR.AQUA_ISLAND.getColor());
		
	}
	
	/*
	 * 전달받은 문제 리스트로 테이블을 구성함
	 * @param recommendedProblemList : 추천된 문제 리스트 
	 * */
	public void updateProblemListToTable( List<Problem> problemList){		
		tableModel.setNumRows(0); // 기존에 입력된 테이블 행 초기화
		this.problemList = new ArrayList<>(problemList);
		
		// 추천된 문제를 가져
		Iterator<Problem> it = problemList.iterator();
		// 테이블 행을 구성할 데이터를 저장하는 배열 
		String[] tableRowValues = new String[TABLE_HEADER.length()];
		while(it.hasNext()) {
			Problem problem = it.next();
			// Problem 객체에서 테이블 헤더에 맞게 필요한 데이터만 가져와 행데이터 구성
			// 문제 번호 추가
			tableRowValues[TABLE_HEADER.ID.getIdx()] = String.valueOf(problem.getProblemID());
			// 문제 제목 추가
			tableRowValues[TABLE_HEADER.TITLE.getIdx()] = problem.getProblemName();
			// 랭크(포인트) 추가
			tableRowValues[TABLE_HEADER.RANK.getIdx()] = String.format("%s(%d)", problem.getProblemRank(), problem.getProblemRankPoint());
			// 문제 url 추가
			tableRowValues[TABLE_HEADER.URL.getIdx()] = problem.getProblemURL();			
			tableModel.addRow(tableRowValues); // row 데이터 테이블에 추가
		}
	}
	
	// 테이블 셀을 렌더링하는 방식을 결정 
	class TableCellRenderer extends DefaultTableCellRenderer{
		public TableCellRenderer() {
			super();
			setHorizontalAlignment(JLabel.CENTER); // 모든 글은 가운데 정렬
		}
		@Override
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
			super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column); // 셀 렌더링 부모 생성자 호출
			if(column == TABLE_HEADER.URL.getIdx()) { // url 열
				setText("<html><u>" + value + "</u></html>");// 밑줄 적용
	            setForeground(Color.BLUE);// 파란색 적용
			}
			if(column == TABLE_HEADER.RANK.getIdx()) { // rank 열
	            RANK rank = problemList.get(row).getProblemRank();
	            ImageIcon rankIcon = new ImageIcon(getClass().getResource("/sources/" + rank.getRankName() + ".png"));
	         // 이미지 사이즈 변경
	            rankIcon = new ImageIcon(rankIcon.getImage().getScaledInstance(ROW_HEIGHT - 5, ROW_HEIGHT - 5, Image.SCALE_AREA_AVERAGING)); 
	            setIcon(rankIcon); // 랭크 아이콘 추가
			}			
			return this;
		}
	}

	class TableClickListener extends MouseAdapter{ // 테이블 클릭에 대한 이벤트 리스너		
		public void mouseClicked(MouseEvent e) {
			int row = getSelectedRow();
			int col = getSelectedColumn();
			if(col == TABLE_HEADER.URL.getIdx()) { // url 열이 클릭된 경우
				String url = (String) getValueAt(row, col); // 해당 위치의 셀에서 url문자열을 가져옴
				try {
					Desktop.getDesktop().browse(URI.create(url)); // 해당 url 주소로 브라우저 열기
				} catch (IOException ex) {
					ex.printStackTrace();
				}
			} else if(col != -1 && row != -1 ){ // url 열이 아니고 클릭된 열과 행값이 유효한 경우				
				Problem selectedProblem = problemList.get(row); // 선택된 행의 문제 인스턴스를 가져옴				
				// 해당 행에 맞는 문제 정보 조회 프레임 생성
				new ProblemInfoFrame(selectedProblem, user); 		
			}
			Dialog.playEffectSound(Dialog.DEFALUT_POPUP_SOUND); // 사운드 재생
		}
	}

}





















