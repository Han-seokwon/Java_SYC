package gui;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

// JButton을 상속하는 클래스로 버튼 클릭 시, 버튼이 추가된 프레임을 닫는 리스너를 추가한 버튼 클래스
public class CancelButton extends DesignedButton{
	// 생성자
	public CancelButton() {
		this("취소");
	}
	public CancelButton(String btnName) {
		super(btnName, COLOR.MEDIUM_SLATE_BLUE);
		addActionListener(new CancleButtonListener());
	}
	public CancelButton(int width, int height) {
		super("취소", width, height, COLOR.MEDIUM_SLATE_BLUE);
	}
	
	class CancleButtonListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			System.out.println("취소버튼 클릭!!");
			Component source = (Component) e.getSource();
			JFrame frame = (JFrame) SwingUtilities.getRoot(source);  
			frame.dispose(); // 취소 버튼이 포함된 프레임 닫기
		}
		
	}
}
