package gui;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

// JButton�� ����ϴ� Ŭ������ ��ư Ŭ�� ��, ��ư�� �߰��� �������� �ݴ� �����ʸ� �߰��� ��ư Ŭ����
public class CancelButton extends DesignedButton{
	// ������
	public CancelButton() {
		this("���");
	}
	public CancelButton(String btnName) {
		super(btnName, COLOR.MEDIUM_SLATE_BLUE);
		addActionListener(new CancleButtonListener());
	}
	public CancelButton(int width, int height) {
		super("���", width, height, COLOR.MEDIUM_SLATE_BLUE);
	}
	
	class CancleButtonListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			System.out.println("��ҹ�ư Ŭ��!!");
			Component source = (Component) e.getSource();
			JFrame frame = (JFrame) SwingUtilities.getRoot(source);  
			frame.dispose(); // ��� ��ư�� ���Ե� ������ �ݱ�
		}
		
	}
}
