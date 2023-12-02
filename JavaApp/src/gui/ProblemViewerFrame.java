package gui;

/*
 * 
 * �ؽ�Ʈ �ʵ忡 ���� �Է� �� ���� �Ǵ� ��ư Ŭ���� �Է°��� �ش�Ǵ�  
 * 
 * 
 * 
 */
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseAdapter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import problems.ProblemDBManager;
import problems.Problem;
import gui.MainFrame;
import gui.ProblemViewerFrame;
import problems.*;
import users.AccountManager;
import users.User;
import users.UserDBManager;
import java.net.*;
import fetcher.JsonFetcher;
import users.User;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.util.regex.PatternSyntaxException;

public class ProblemViewerFrame extends DesignedJFrame implements ItemListener {

    private JPanel panel; // �˻� ���� ������Ʈ�� ���� �г�
	private DesignedContentPane contentPane;
    private JLabel label; // "���� �˻�" ���̺�
    private JTextField textField; // �˻��� �Է��� ���� �ؽ�Ʈ �ʵ�
    private JButton button; // �˻� ��ư
    private ProblemTable table;
    private JTextField searchField;
    private JRadioButton number;
    private JRadioButton name;
    private JRadioButton rank;
    private JRadioButton sorted;
    private JRadioButton resorted;
    private JScrollPane scrollPane;
    private boolean sort;
    private boolean name1;
    private boolean id1;
    private boolean rank1;
    private boolean search1;
	private User user;// ���� �α��ε� ����
	private ProblemTable recommendedProblemTable;// ��õ�� ������ ��� ���̺�
	private List<Problem> ProblemList;
	private String text;


    public ProblemViewerFrame(User user) {
        super("���� ����Ʈ"); // �������� ���� ����
        
		contentPane = new DesignedContentPane();
		contentPane.setBorder(new EmptyBorder(20, 5, 5, 5));
		setContentPane(contentPane);
        panel = new JPanel(new GridLayout(3, 2));
        panel.setForeground(new Color(130, 141, 226));
        panel.setBounds(205, 101, 301, 144);
        panel.setOpaque(false);
        
        
        //���� ��ư ����
        MakeRadioButton();

        sort = true;
        id1 = true;
        search1 = false;

		//���� ���̺� ����
        table = new ProblemTable(user);
        ProblemList = ProblemDBManager.findProblemToID(sort);
        table.updateProblemListToTable(ProblemList);
		scrollPane = new JScrollPane(table);
        scrollPane.setBounds(205, 359, 867, 394);
        contentPane.add(scrollPane);
        
        
	    // �� ������ ������ Ȱ��ȭ
	    //table.setCellSelectionEnabled(false);
	    // JTable�� ����� ���� ���� ũ�⸦ �����ϴ� ���� ��Ȱ��ȭ
	    table.getTableHeader().setResizingAllowed(false);
	    // ���� ������ �����ϴ� ���� ��Ȱ��ȭ
	    table.getTableHeader().setReorderingAllowed(false);
	    // �⺻ �����⸦ �����ϰ�, null�� �����Ͽ� ���� ������ �� ���� ��
	    table.setDefaultEditor(Object.class, null);
	    table.addMouseListener(new MyMouseListener());
        
        //���� ���� �� ����
        JLabel lblNewLabel = new JLabel("���� ����");
        lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel.setBounds(273, 33, 117, 58);
        contentPane.add(lblNewLabel);
        
        
        ActionListener actionListener = new MyActionListener();
        //�˻� ��ư ����
        button = new JButton("�˻�");
        button.setForeground(new Color(130, 141, 226));
        button.setBackground(new Color(148, 212, 214));
        button.setBounds(1022, 147, 70, 35);
        contentPane.add(button);

        //�˻� �ؽ�Ʈ �ʵ� ����
        searchField = new JTextField();
        searchField.setBounds(857, 148, 165, 34);
        contentPane.add(searchField);
        searchField.setColumns(10);
        
        JLabel lblNewLabel_1 = new JLabel("���� �˻�");
        lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel_1.setBounds(888, 56, 117, 58);
        contentPane.add(lblNewLabel_1);
        
        //
        button.addActionListener(actionListener);
        searchField.addActionListener(actionListener);
        
    	contentPane.applyFontAndBackgroundToAllComponents(); // ��ü ��Ʈ ���� �� �г� ���� �����ϰ� ����
        
        //setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // �������� ���� �� ���α׷� ����
        setVisible(true); // ������ ǥ��
    }
    
    public void MakeRadioButton() {
        //������ư ����

        number = new JRadioButton("��ȣ");
        name = new JRadioButton("����");
        rank = new JRadioButton("���̵�");
        sorted = new JRadioButton("��������");
        resorted = new JRadioButton("��������");
        
        
        
        number.setOpaque(false);
        name.setOpaque(false);
        rank.setOpaque(false);
        sorted.setOpaque(false);
        resorted.setOpaque(false);
        
        number.setSelected(true);
        sorted.setSelected(true);
        
        //������ư ������ ���
        number.addItemListener(this);
        name.addItemListener(this);
        rank.addItemListener(this);
        sorted.addItemListener(this);
        resorted.addItemListener(this);
        
        //������ư �׷� ����
        ButtonGroup aa = new ButtonGroup();
        ButtonGroup bb = new ButtonGroup();

        //������ư �׷쿡 �߰�
        aa.add(number);
        aa.add(name);
        aa.add(rank);
        bb.add(sorted);
        bb.add(resorted);

        //�гο� ������ư �߰�
        panel.add(number);
        panel.add(sorted);
        panel.add(name);
        panel.add(resorted);
        panel.add(rank);
        
        //panel.applyFontAndBackgroundToAllComponents();
        
        contentPane.setLayout(null);
        contentPane.add(panel); // �г��� �������� ��ܿ� �߰�
    }

    public void itemStateChanged(ItemEvent e) { //���� ���� ��ư �̺�Ʈ ó��
        

        if (e.getStateChange() == ItemEvent.SELECTED) {

            if (e.getSource() == number) {
                name1 = false;
                id1 = true;
                rank1 = false;
            } else if (e.getSource() == name) {
                name1 = true;
                id1 = false;
                rank1 = false;
            } else if (e.getSource() == rank) {
                name1 = false;
                id1 = false;
                rank1 = true;
            }
            
            if (e.getSource() == sorted) {
                sort = true;
            }
            else if (e.getSource() == resorted) {
            	sort = false;
            }
            
        	if (search1 == false){
        		
                if (name1 == true) {
                    ProblemList = ProblemDBManager.findProblemToName(sort);
                    table.updateProblemListToTable(ProblemList);
                }
                else if (id1 == true) {
                	ProblemList = ProblemDBManager.findProblemToID(sort);
                	table.updateProblemListToTable(ProblemList);
                }
                else if (rank1 == true) {
                	ProblemList = ProblemDBManager.findProblemToRank(sort);
                	table.updateProblemListToTable(ProblemList);
                	
                }
        	}
        	
        	else {
        		if (sort == true){
                    if (name1 == true) {
                        ProblemList = ProblemDBManager.findProblemSearch(text,3);;
                        table.updateProblemListToTable(ProblemList);
                    }
                    else if (id1 == true) {
                    	ProblemList = ProblemDBManager.findProblemSearch(text,1);
                    	table.updateProblemListToTable(ProblemList);
                    }
                    else if (rank1 == true) {
                    	ProblemList = ProblemDBManager.findProblemSearch(text,5);
                    	table.updateProblemListToTable(ProblemList); 	
                    }	
        		}
        		else {
                    if (name1 == true) {
                        ProblemList = ProblemDBManager.findProblemSearch(text,4);
                        table.updateProblemListToTable(ProblemList);
                    }
                    else if (id1 == true) {
                    	ProblemList = ProblemDBManager.findProblemSearch(text,2);
                    	table.updateProblemListToTable(ProblemList);
                    }
                    else if (rank1 == true) {
                    	ProblemList = ProblemDBManager.findProblemSearch(text,6);
                    	table.updateProblemListToTable(ProblemList); 	
                    }	
        		}
        		}
        	}
        }
    
    private class MyMouseListener extends MouseAdapter { //���̺� Ŭ�� �̺�Ʈ ����
    	public void mouseClicked(MouseEvent e) {

    	if (e.getClickCount() == 2) { //����Ŭ���� ProblemInfoFrame ����
            int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) {
                Object selectedID = table.getValueAt(selectedRow, 0);
            	System.out.println(selectedID);
            	new ProblemInfoFrame(ProblemDBManager.findProblem(Integer.parseInt(String.valueOf(selectedID))), user);
            	} 
            

    		}
    	}
    }
    
    private class MyActionListener implements ActionListener { // �ؽ�Ʈ �ʵ�, �˻� ��ư �̺�Ʈ ����
        @Override
        public void actionPerformed(ActionEvent e) {
        	
            JTextField source = null;

            if (e.getSource() == button) {
                // �˻� ��ư Ŭ�� ��
                source = searchField;
            } else if (e.getSource() == searchField) {
                // ����Ű ������ ��
                source = (JTextField) e.getSource();
            }


            text = source.getText();
            
            contentPane.remove(panel);
            panel.remove(number);
            panel.remove(sorted);
            panel.remove(name);
            panel.remove(resorted);
            panel.remove(rank);

            
            if (text.isEmpty()) {
                ProblemList = ProblemDBManager.findProblemToID(sort);
                table.updateProblemListToTable(ProblemList);
            } else {
            	ProblemList = ProblemDBManager.findProblemSearch(text,1);
            	table.updateProblemListToTable(ProblemList);
            	search1 = true;
            }
            
            
            
            MakeRadioButton();
            contentPane.applyFontAndBackgroundToAllComponents();
            contentPane.revalidate();
            contentPane.repaint();

        }
    }
};





