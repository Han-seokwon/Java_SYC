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
	private static int heightGapBetweenFrameToContentPane = 30; // �����Ӱ� ContentPane���� ���� ����
	private int backgroundWidth; // ���ȭ�� �ʺ�
	private int backgroundHeight; // ���ȭ�� ����
	private Font font; // ��Ʈ 
	
	public DesignedContentPane(){	
		this(DesignedJFrame.getDefalutWindowWidth(), DesignedJFrame.getDefalutWindowHeight()); // �⺻ ���ȭ�� ������� �⺻ ������ ����� �°� ��
	}
	
	public DesignedContentPane(DesignedJFrame frame){	
		this(frame.getWindowWidth(), frame.getWindowHeight()); // ���޵� �������� �ʺ�, ���̿� �°� ���ȭ�� ũ�� ����
	}
	
	public DesignedContentPane(int width, int height){
		super();				
		this.backgroundWidth = width;
		this.backgroundHeight = height - heightGapBetweenFrameToContentPane; // �����Ӱ� ContentPane���� ���� ���� �ݿ�
	}
	
	@Override
	protected void paintComponent(Graphics g) {				
		try { // ���ȭ�� ����
			Image backgroundImage = ImageIO.read(getClass().getResource("/sources/bg.png"));
			backgroundImage = backgroundImage.getScaledInstance(backgroundWidth, backgroundHeight, Image.SCALE_SMOOTH); // ��� �̹��� �г� ����� �°� ����
			g.drawImage(backgroundImage, 0, 0, null); // ��� �̹��� �г� ����� �°� ����
            setOpaque(false); // ��� �̹����� ��Ÿ���� �⺻ �г� ������ �����ϰ� ��       
            super.paintComponent(g);
		} catch (IOException e) {
			e.printStackTrace();
		}				
	}
	
	
	
	/*
	 * ��Ʈ ������ �������� ���� ��� �⺻ ��Ʈ(SansSerif)�� ����
	 * param  : ��Ʈ ���� �̸�
	 * */ 
	private Font setDefalutFont(String fontFilename, int size) { 
		try {
			return FileManager.createFontFromFile(fontFilename).deriveFont((float)size); // �ش� ������� ��Ʈ ��������
		} catch (IOException e) { 
			System.out.println(e.getMessage());
			return new Font("SansSerif", 0, size); // �⺻ ��Ʈ
		}
	}
	
	// ���� ��ü�� applyFontToAllComponents(Container container)�� ����, �⺻ ��Ʈ ������� 20���� ��
	// �ݵ��, ContentPane�� ��� ������Ʈ���� �߰��� ���Ŀ� �����ؾ� ��
	public void applyFontAndBackgroundToAllComponents() {
		applyFontAndBackgroundToAllComponents(20);
    }
	public void applyFontAndBackgroundToAllComponents(int size) {
		font = setDefalutFont("contentFont", size); // �⺻ ��Ʈ ����
		applyFontAndBackgroundToAllComponents(this);
    }
		
	
	// Container ������ҿ� ��Ʈ�� �����ϰ� ����� �����ϰ� ��
	private void applyFontAndBackgroundToAllComponents(Container container) {
        for (Component component : container.getComponents()) {
            if (component instanceof JLabel ||  // �ش� ������Ʈ�� �ٸ� ���� ������Ʈ ��ü�� ���
            		component instanceof AbstractButton ||
            		(component instanceof JTextComponent  && !(component instanceof JPasswordField)) ||
            		component instanceof JComboBox){ 
	                ((JComponent) component).setFont(font); // ��Ʈ ����
	                
            } else if (component instanceof JComponent) { // �ش� ������Ʈ�� �ٸ� ������Ʈ�� �����ϴ� ���(Container �� ���)
            	((JComponent) component).setOpaque(false); // ���� ����̹����� �� ���̰�, ���� �����ϰ� ���� 
        		applyFontAndBackgroundToAllComponents((Container) component); // ���� ������Ʈ�� Container�� ��� ȣ�� -> ���� ������Ʈ Ž��
            }
        }
    }
}