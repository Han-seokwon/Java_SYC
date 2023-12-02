package gui;

import java.awt.Graphics;
import java.awt.Image;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

import file.FileManager;

public class DesignedJFrame extends JFrame{
	
	private static int defalutWidth = 1300;
	private static int defalutHeight = 900;
	private static String defalutSiteName = "Core";
	private int width; // ������ �ʺ�
	private int height; // ������ ����
	private String title; // // ������ Ÿ��Ʋ
	
	public DesignedJFrame() {
		this(defalutWidth, defalutHeight, defalutSiteName);
	}
	public DesignedJFrame(String siteName) {
		this(defalutWidth, defalutHeight, siteName);
	}
	public DesignedJFrame(int width, int height) {
		this(width, height, defalutSiteName);
	}
	public DesignedJFrame(int width, int height, String title) {
		super();
		this.width = width;
		this.height = height;
		setTitle(title);
		setSize(width, height);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null); // â ��� ��ġ
		setResizable(false); // â ũ�� ���� ���ϰ� ����
		try {
			Image icon = ImageIO.read(getClass().getResource("/sources/icon.png"));
			setIconImage(icon);
		}catch (IOException e) {
			System.out.println("����Ʈ �������� �ҷ����� ���߽��ϴ�.");
		}
	}
	
	public static int getDefalutWindowWidth() {
		return defalutWidth;
	}
	public static int getDefalutWindowHeight() {
		return defalutHeight;
	}
	public static String getDefalutWindowTitle() {
		return defalutSiteName;
	}
	public int getWindowWidth() {
		return width;
	}
	public int getWindowHeight() {
		return height;
	}
	public String getSiteName() {
		return title;
	}	
}