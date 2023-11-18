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
	private final static int ROW_HEIGHT = 30;  // �� ����
	// ���̺� �������� �����͸� �߰��ϱ� ���� �� ��ü ����
	private DefaultTableModel tableModel;
	private ArrayList<Problem> recommendedProblemList = new ArrayList<>(); // ��õ�� ���� ����Ʈ
	private User user;// ���� �α��ε� ����
	
	public ProblemTable(User user) {
		super();// JTable ��� ��ӹޱ�
		this.user = user; // ���� �α��ε� ���������� ��������
		
		// ���̺� �������� �����͸� �߰��ϱ� ���� ���̺� �� ��ü ����
		tableModel = new DefaultTableModel(TABLE_HEADER.getHeaderNames(), 0) { // ���̺� ���, row ����
			private static final long serialVersionUID = 1L;
			@Override
			public boolean isCellEditable(int row, int column) { return true; } // �� ���� ������ �� ���� �������̵�
		};
		setModel(tableModel); // ���̺� �� ����		
		setRowHeight(ROW_HEIGHT); // �� ���� ����
		addMouseListener(new tableClickListener()); // ���̺� Ŭ���� ���� �̺�Ʈ ������ ���
	}
	
	class tableClickListener extends MouseAdapter{ // ���̺� Ŭ���� ���� �̺�Ʈ ������		
		public void mouseClicked(MouseEvent e) {
			int row = getSelectedRow();
			int col = getSelectedColumn();
			if(col == TABLE_HEADER.URL.getIdx()) { // url ���� Ŭ���� ���
				String url = (String) getValueAt(row, col); // �ش� ��ġ�� ������ url���ڿ��� ������
				try {
					Desktop.getDesktop().browse(URI.create(url)); // �ش� url �ּҷ� ������ ����
				} catch (IOException ex) {
					ex.printStackTrace();
				}
			} else if(col != -1 && row != -1 ){ // url ���� �ƴϰ� Ŭ���� ���� �ప�� ��ȿ�� ��� 
				// �ش� ���� �´� ���� ���� ��ȸ ������ ����
				Problem selectedProblem = recommendedProblemList.get(row);
				System.out.println("���� ���õ�\n" + selectedProblem);
//				new ProblemViewerFrame(selectedProblem, user); // Ŭ���� �ϼ��� �ڵ� �߰�
			}
		}
	}
	
	public void clearTableRows() {
		tableModel.setNumRows(0); // ������ �Էµ� ���̺� �� �ʱ�ȭ
	}
	
	// ��õ�� ��������Ʈ�� ���̺� �߰�
	public void addProblemListToTable( List<Problem> recommendedProblemList) throws ClassCastException{
		this.recommendedProblemList = new ArrayList<>(recommendedProblemList);
		Iterator<Problem> it = recommendedProblemList.iterator();
		String[] tableRowValues = new String[TABLE_HEADER.length()];
		while(it.hasNext()) {
			Problem problem = it.next();
			// Problem ��ü���� ���̺� ����� �°� �ʿ��� �����͸� ������ �൥���� ���� 
			tableRowValues[TABLE_HEADER.ID.getIdx()] = String.valueOf(problem.getProblemID());
			tableRowValues[TABLE_HEADER.TITLE.getIdx()] = problem.getProblemName();
			tableRowValues[TABLE_HEADER.RANK.getIdx()] = problem.getProblemRank().getRankName();
			tableRowValues[TABLE_HEADER.URL.getIdx()] = problem.getProblemURL();	
	        tableModel.addRow(tableRowValues); // row ������ ���̺� �߰�
		}
	}
	
	
}





















