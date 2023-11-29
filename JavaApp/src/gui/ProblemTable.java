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


//���̺� ��� ������ �����ϴ� ������
enum TABLE_HEADER{ 
	ID(0, "���� ��ȣ", 100), TITLE(1, "���� ����", 300), RANK(2, "���̵�", 100), URL(3, "URL", 300);

	private int idx; // ��� �ε���
	private String name; // ��� �̸�
	private int width; // ��� �ʺ�
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
	// ������(������)�� ������ ��ȯ 
	public static int length() {
		return TABLE_HEADER.values().length;
	}
	// ������(������)�� ���ڿ� �̸��� ���ͷ� ��ȯ, (DefaultTableModel���� �÷��̸�(���)�� Vector�� �޾� Vector�� �÷��� ����)
	public static Vector<String> getHeaderNameList() {
		Vector<String> headerNames = new Vector<>(TABLE_HEADER.length());
		for(TABLE_HEADER header : TABLE_HEADER.values()) {
			headerNames.add(header.getName());
		}
		return headerNames;    	
	}
}

// ��������Ʈ�� �������� �Ҵ�޾� ���̺��� �����ϴ� Ŭ����
public class ProblemTable extends JTable{
	private static final long serialVersionUID = 1L;
	private final static int ROW_HEIGHT = 40;  // �� ����
	// ���̺� �������� �����͸� �߰��ϱ� ���� �� ��ü ����
	private DefaultTableModel tableModel;
	private ArrayList<Problem> recommendedProblemList = new ArrayList<>(); // ��õ�� ���� ����Ʈ
	private User user;// ���� �α��ε� ����

	public ProblemTable(User user) {
		super();// JTable ��� ��ӹޱ�
		
		this.user = user; // ���� �α��ε� ���������� ��������

		// ���̺� �������� �����͸� �߰��ϱ� ���� ���̺� �� ��ü ����
		tableModel = new DefaultTableModel(TABLE_HEADER.getHeaderNameList(), 0) { // ���̺� ���, row ����
			private static final long serialVersionUID = 1L;
			@Override
			public boolean isCellEditable(int row, int column) { return false; } // �� ���� ������ �� ���� �������̵�
		};
		setModel(tableModel); // ���̺� �� ����

		// ��� ���� ����
		Dimension headerSize = getTableHeader().getPreferredSize();
		headerSize.height = ROW_HEIGHT;
		getTableHeader().setPreferredSize(headerSize);
		
		// ���̺� ����, �ʺ�, ��Ʈ ����		
		for(TABLE_HEADER header : TABLE_HEADER.values()) {
			TableColumn col = getColumnModel().getColumn(header.getIdx());
			col.setPreferredWidth(header.getWidth()); // �ʺ� ����
			col.setCellRenderer(new TableCellRenderer()); // ������ �ؽ�Ʈ ��Ʈ �������ִ� TableCellRenderer ���� �� ����
		}		
		
		setRowHeight(ROW_HEIGHT); // �⺻ �� ���� ����		
		addMouseListener(new tableClickListener()); // ���̺� Ŭ���� ���� �̺�Ʈ ������ ���

		try { // ��Ʈ ����
			Font font = FileManager.createFontFromFile("contentFont");
			getTableHeader().setFont(font.deriveFont(20f));
			setFont(font.deriveFont(17f));
		} catch (IOException e) { 
			System.out.println(e.getMessage());				
		}
		getTableHeader().setBackground(COLOR.AQUA_ISLAND.getColor());
		
	}
	
	// ������ �ؽ�Ʈ ��Ʈ �������ִ� CellRenderer ���� 
	class TableCellRenderer extends DefaultTableCellRenderer{
		public TableCellRenderer() {
			super();
			setHorizontalAlignment(JLabel.CENTER);
		}
		@Override
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
			Component component = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);              
			if(column == TABLE_HEADER.URL.getIdx()) { // url ��
				setText("<html><u>" + value + "</u></html>");// ���� ����
	            setForeground(Color.BLUE);// �Ķ��� ����
			}
			if(column == TABLE_HEADER.RANK.getIdx()) { // rank ��
	            RANK rank = recommendedProblemList.get(row).getProblemRank();
	            ImageIcon rankIcon = new ImageIcon(getClass().getResource("/sources/" + rank.getRankName() + ".png"));
	            rankIcon = new ImageIcon(rankIcon.getImage().getScaledInstance(ROW_HEIGHT - 5, ROW_HEIGHT - 5, Image.SCALE_SMOOTH)); // �̹��� ������ ����
	            setIcon(rankIcon); // ������ �߰�
			}			
			return this;
		}
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
				//	new ProblemViewerFrame(selectedProblem, user); // Ŭ���� �ϼ��� �ڵ� �߰�
			}
		}
	}


	//  ��������Ʈ�� ���̺� ������Ʈ
	public void updateProblemListToTable( List<Problem> recommendedProblemList) throws ClassCastException{
		long start = System.currentTimeMillis();
		
		tableModel.setNumRows(0); // ������ �Էµ� ���̺� �� �ʱ�ȭ
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
		
		System.out.println(System.currentTimeMillis() - start);
	}


}





















