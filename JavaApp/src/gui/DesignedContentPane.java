package gui;

import java.awt.Component;
import java.awt.Container;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.AbstractButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.text.JTextComponent;

import file.FileManager;

public class DesignedContentPane extends JPanel{
	private static int heightGapBetweenFrameToContentPane = 30; // 프레임과 ContentPane과의 높이 차이
	private int backgroundWidth; // 배경화면 너비
	private int backgroundHeight; // 배경화면 높이
	private Font font; // 폰트 
	
	public DesignedContentPane(){	
		this(DesignedJFrame.getDefalutWindowWidth(), DesignedJFrame.getDefalutWindowHeight()); // 기본 배경화면 사이즈는 기본 프레임 사이즈에 맞게 함
	}
	
	public DesignedContentPane(DesignedJFrame frame){	
		this(frame.getWindowWidth(), frame.getWindowHeight()); // 전달된 프레임의 너비, 높이에 맞게 배경화면 크기 설정
	}
	
	public DesignedContentPane(int width, int height){
		super();				
		this.backgroundWidth = width;
		this.backgroundHeight = height - heightGapBetweenFrameToContentPane; // 프레임과 ContentPane과의 높이 차이 반영
	}
	
	@Override
	protected void paintComponent(Graphics g) {				
		try { // 배경화면 적용
			Image backgroundImage = ImageIO.read(getClass().getResource("/sources/bg.png"));
			backgroundImage = backgroundImage.getScaledInstance(backgroundWidth, backgroundHeight, Image.SCALE_SMOOTH); // 배경 이미지 패널 사이즈에 맞게 조정
			g.drawImage(backgroundImage, 0, 0, null); // 배경 이미지 패널 사이즈에 맞게 설정
            setOpaque(false); // 배경 이미지가 나타나게 기본 패널 색상은 투명하게 함       
            super.paintComponent(g);
		} catch (IOException e) {
			e.printStackTrace();
		}				
	}
	
	
	
	/*
	 * 폰트 파일을 가져오고 없는 경우 기본 폰트(SansSerif)로 설정
	 * param  : 폰트 파일 이름
	 * */ 
	private Font setDefalutFont(String fontFilename, int size) { 
		try {
			return FileManager.createFontFromFile(fontFilename).deriveFont((float)size); // 해당 사이즈로 폰트 가져오기
		} catch (IOException e) { 
			System.out.println(e.getMessage());
			return new Font("SansSerif", 0, size); // 기본 폰트
		}
	}
	
	// 현재 객체에 applyFontToAllComponents(Container container)을 적용, 기본 폰트 사이즈는 20으로 함
	// 반드시, ContentPane에 모든 컴포넌트들이 추가된 이후에 실행해야 함
	public void applyFontAndBackgroundToAllComponents() {
		applyFontAndBackgroundToAllComponents(20);
    }
	public void applyFontAndBackgroundToAllComponents(int size) {
		font = setDefalutFont("contentFont", size); // 기본 폰트 적용
		applyFontAndBackgroundToAllComponents(this);
    }
		
	
	// Container 하위요소에 폰트를 적용하고 배경을 투명하게 함
	public void applyFontAndBackgroundToAllComponents(Container container) {
        for (Component component : container.getComponents()) {
            if (component instanceof JLabel ||  // 해당 컴포넌트가 다른 하위 컴포넌트 객체인 경우
            		component instanceof AbstractButton ||
            		(component instanceof JTextComponent  && !(component instanceof JPasswordField)) ||
            		component instanceof JComboBox){ 
	                ((JComponent) component).setFont(font); // 폰트 적용
	                
            } else if (component instanceof JComponent) { // 해당 컴포넌트가 다른 컴포넌트를 포함하는 경우(Container 인 경우)
            	((JComponent) component).setOpaque(false); // 메인 배경이미지가 잘 보이게, 배경색 투명하게 설정 
        		applyFontAndBackgroundToAllComponents((Container) component); // 현재 컴포넌트를 Container로 재귀 호출 -> 하위 컴포넌트 탐색
            }
        }
    }
}
