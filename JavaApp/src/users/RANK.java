package users;

import java.util.ArrayList;
import java.util.Vector;

//������� ��ũ �� ������ ��ũ�� ���ϴµ� ���Ǵ� ������ Ŭ���� 
public enum RANK {
    RANK1(0, 2, "Rank1"),
    RANK2(1, 4, "Rank2"),
    RANK3(2, 6, "Rank3"),
    RANK4(3, 8, "Rank4"),
    RANK5(4, 10, "Rank5");    
    
	private final static int RANK_POINT_GAP = 100; // �� ��ũ �� ����Ʈ ����
    private int requireRankPoint; // �ش� ��ũ�� �Ǳ� ���� �ʿ��� ��ũ����Ʈ
    private int pointGain; // ���� �ذ�� ����ڰ� ���� �� �ִ� ����Ʈ
    private String rankName; // �������� ���� ���ڿ� �̸�
    
    private RANK(int requireRankPoint, int pointGain, String rankName) {
        this.requireRankPoint = requireRankPoint * RANK_POINT_GAP;
        this.pointGain = pointGain;
        this.rankName = rankName;
    }
    
    // �� ��ũ �� ����Ʈ ���� ��ȯ
    public static int getRankPointGap() {
    	return RANK_POINT_GAP;
    }
    
    // �ش� ��ũ�� �޼��ϱ� ���� �䱸�Ǵ� ��ũ����Ʈ
    public int getRequireRankPoint() {
        return requireRankPoint;
    }
    // �ش� ��ũ ������ �ذ��� ��� ���� �� �ִ� ��ũ����Ʈ
    public int getPointGain() {
        return pointGain;
    }
    // �ش� ��ũ�� ���ڿ� �̸�
    public String getRankName() {
        return rankName;
    }

	// ���� ���� Ÿ���� ��ü ����Ÿ�� ���̺��� ���� ��� ���� �������� ��ȯ, �׷��� ������ ���� ���� Ÿ���� ������ Ÿ���̹Ƿ� �״�� ������ Ÿ���� ��ȯ��
    public RANK getNextRank() {    	
        return ordinal() < values().length - 1 ? values()[ordinal() + 1] : this;
    }
    
    // ���� �� �ִ� ��ũ ����Ʈ �ִ�ġ 
    public static int getMaxRequireRankPoint() {
        return RANK5.getRequireRankPoint() + RANK_POINT_GAP;
    }
    
    // Ư�� ����Ʈ�� �Է��ϸ� �׿� �´� RANK ������ ��ȯ
    public static RANK getRankForPoint(int point) {
    	RANK matchingRank = RANK1; // �ּ� ��ũ���� ����
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
