package gui;

import java.awt.Color;

// 팔레트 색 조합에 맞게 Color 객체 반환하는 열거형
public enum COLOR {
	MEDIUM_SLATE_BLUE(new Color(160,127,241)),
	CHET_WODE_BLUE(new Color(130,141,226)),
	AQUA_ISLAND(new Color(148,212,214)),
	JORDY_BLUE(new Color(125,184,242)),
	SUNFLOWER(new Color(255,206,73)),
	WHITE(new Color(255,255,255));
	
	private Color color;
	
	private COLOR(Color color) {
        this.color = color;
    }
	public Color getColor() {
		return color;
	}
	
}
