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
	ID(0, "문제 번호", 100), TITLE(1, "문제 제목", 300), RANK(2, "난이도", 100), URL(3, "URL", 300);

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
	private ArrayList<Problem> recommendedProblemList = new ArrayList<>(); // 추천된 문제 리스트
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
		for(TABLE_HEADER header : TABLE_HEADER.values()) {
			TableColumn col = getColumnModel().getColumn(header.getIdx());
			col.setPreferredWidth(header.getWidth()); // 너비 설정
			col.setCellRenderer(new TableCellRenderer()); // 지정한 텍스트 폰트 적용해주는 TableCellRenderer 생성 후 적용
		}		
		
		setRowHeight(ROW_HEIGHT); // 기본 행 높이 설정		
		addMouseListener(new tableClickListener()); // 테이블 클릭에 대한 이벤트 리스너 등록

		try { // 폰트 설정
			Font font = FileManager.createFontFromFile("contentFont");
			getTableHeader().setFont(font.deriveFont(20f));
			setFont(font.deriveFont(17f));
		} catch (IOException e) { 
			System.out.println(e.getMessage());				
		}
		getTableHeader().setBackground(COLOR.AQUA_ISLAND.getColor());
		
	}
	
	// 지정한 텍스트 폰트 적용해주는 CellRenderer 생성 
	class TableCellRenderer extends DefaultTableCellRenderer{
		public TableCellRenderer() {
			super();
			setHorizontalAlignment(JLabel.CENTER);
		}
		@Override
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
			Component component = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);              
			if(column == TABLE_HEADER.URL.getIdx()) { // url 열
				setText("<html><u>" + value + "</u></html>");// 밑줄 적용
	            setForeground(Color.BLUE);// 파란색 적용
			}
			if(column == TABLE_HEADER.RANK.getIdx()) { // rank 열
	            RANK rank = recommendedProblemList.get(row).getProblemRank();
	            ImageIcon rankIcon = new ImageIcon(getClass().getResource("/sources/" + rank.getRankName() + ".png"));
	            rankIcon = new ImageIcon(rankIcon.getImage().getScaledInstance(ROW_HEIGHT - 5, ROW_HEIGHT - 5, Image.SCALE_SMOOTH)); // 이미지 사이즈 변경
	            setIcon(rankIcon); // 아이콘 추가
			}			
			return this;
		}
	}

	class tableClickListener extends MouseAdapter{ // 테이블 클릭에 대한 이벤트 리스너		
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
				// 해당 열에 맞는 문제 정보 조회 프레임 생성
				Problem selectedProblem = recommendedProblemList.get(row);
				System.out.println("문제 선택됨\n" + selectedProblem);
				//	new ProblemViewerFrame(selectedProblem, user); // 클래스 완성시 코드 추가
			}
		}
	}


	//  문제리스트로 테이블 업데이트
	public void updateProblemListToTable( List<Problem> recommendedProblemList) throws ClassCastException{
		long start = System.currentTimeMillis();
		
		tableModel.setNumRows(0); // 기존에 입력된 테이블 행 초기화
		this.recommendedProblemList = new ArrayList<>(recommendedProblemList);
		Iterator<Problem> it = recommendedProblemList.iterator();
		String[] tableRowValues = new String[TABLE_HEADER.length()];
		while(it.hasNext()) {
			Problem problem = it.next();
			// Problem 객체에서 테이블 헤더에 맞게 필요한 데이터만 가져와 행데이터 구성 
			tableRowValues[TABLE_HEADER.ID.getIdx()] = String.valueOf(problem.getProblemID());
			tableRowValues[TABLE_HEADER.TITLE.getIdx()] = problem.getProblemName();
			tableRowValues[TABLE_HEADER.RANK.getIdx()] = problem.getProblemRank().getRankName();
			tableRowValues[TABLE_HEADER.URL.getIdx()] = problem.getProblemURL();	
			tableModel.addRow(tableRowValues); // row 데이터 테이블에 추가
		}
		
		System.out.println(System.currentTimeMillis() - start);
	}


}





















