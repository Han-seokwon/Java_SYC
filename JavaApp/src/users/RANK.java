package users;

import java.util.ArrayList;
import java.util.Vector;

// �����, ������ ��ũ�� �����ϴ� ������
public enum RANK {
	// ��ũ ����Ʈ ���̴� ��� ���ƾ� ��
    RANK5(0, 2, "Rank5"), RANK4(1, 4, "Rank4"), RANK3(2, 6, "Rank3"), RANK2(3, 8, "Rank2"), RANK1(4, 10, "Rank1");
    
	private final static int RANK_POINT_GAP = 100; // �� ��ũ �� ����Ʈ ����
    private int requireRankPoint; // �ش� ��ũ�� �Ǳ� ���� �ʿ��� ��ũ����Ʈ
    private int pointGain; // ���� �ذ�� ����ڰ� ���� �� �ִ� ����Ʈ
    private String rankName; // �������� ���� ���ڿ� �̸�
    
    private RANK(int requireRankMultiple, int pointGain, String rankName) {
        this.requireRankPoint = requireRankMultiple * RANK_POINT_GAP;
        this.pointGain = pointGain;
        this.rankName = rankName;
    }
    
    public static int getRankPointGap() {
    	return RANK_POINT_GAP;
    }
    
    public int getRequireRankPoint() {
        return requireRankPoint;
    }
    public int getPointGain() {
        return pointGain;
    }
    public String getRankName() {
        return rankName;
    }

	// ���� ���� Ÿ���� ��ü ����Ÿ�� ���̺��� ���� ��� ���� �������� ��ȯ, �׷��� ������ ���� ���� Ÿ���� ������ Ÿ���̹Ƿ� �״�� ������ Ÿ���� ��ȯ��
    public RANK getNextRank() {
        return ordinal() < values().length - 1 ? values()[ordinal() + 1] : values()[-1];
    }
    
    public static int getMaxRequireRankPoint() { // ���� �� �ִ� ��ũ ����Ʈ �ִ�ġ
        return RANK1.getRequireRankPoint() + RANK_POINT_GAP;
    }
    
    // Ư�� ����Ʈ�� �Է��ϸ� �׿� �´� RANK ������ ��ȯ
    public static RANK getRankForPoint(int point) {
    	RANK matchingRank = RANK5;
    	// for���� ���� �ִ� ��ũ�� ������
        for (RANK rank : values()) {
            if (point >= rank.getRequireRankPoint()) { 
            	matchingRank = rank; 
            }
        }
        return matchingRank;
    }
    
    public static Vector<String> getRankNameList() {
    	Vector<String>  rankNames = new Vector<>();
    	for(RANK rank : RANK.values()) {
    		rankNames.add(rank.getRankName());
    	}
    	return rankNames;
    	
    }
    
}
