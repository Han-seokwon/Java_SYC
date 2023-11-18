package gui;

import java.awt.Desktop;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

import problems.Problem;
import users.User;

public class ProblemTable extends JTable{
	private static final long serialVersionUID = 1L;
	private final static int ROW_HEIGHT = 30;  // 행 높이
	// 테이블에 동적으로 데이터를 추가하기 위한 모델 객체 생성
	private DefaultTableModel tableModel;
	private ArrayList<Problem> recommendedProblemList = new ArrayList<>(); // 추천된 문제 리스트
	private User user;// 현재 로그인된 유저
	
	public ProblemTable(User user) {
		super();// JTable 멤버 상속받기
		this.user = user; // 현재 로그인된 유저데이터 가져오기
		
		// 테이블에 동적으로 데이터를 추가하기 위한 테이블 모델 객체 생성
		tableModel = new DefaultTableModel(TABLE_HEADER.getHeaderNames(), 0) { // 테이블 헤더, row 개수
			private static final long serialVersionUID = 1L;
			@Override
			public boolean isCellEditable(int row, int column) { return true; } // 셀 내용 수정할 수 없게 오버라이딩
		};
		setModel(tableModel); // 테이블 모델 적용		
		setRowHeight(ROW_HEIGHT); // 행 높이 설정
		addMouseListener(new tableClickListener()); // 테이블 클릭에 대한 이벤트 리스너 등록
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
//				new ProblemViewerFrame(selectedProblem, user); // 클래스 완성시 코드 추가
			}
		}
	}
	
	public void clearTableRows() {
		tableModel.setNumRows(0); // 기존에 입력된 테이블 행 초기화
	}
	
	// 추천된 문제리스트를 테이블에 추가
	public void addProblemListToTable( List<Problem> recommendedProblemList) throws ClassCastException{
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
	}
	
	
}





















