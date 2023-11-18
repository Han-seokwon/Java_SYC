package users;


// �����, ������ ��ũ�� �����ϴ� ������
public enum RANK {
	// ��ũ ����Ʈ ���̴� ��� ���ƾ� ��
    RANK5(0, 2, "Rank5"), RANK4(100, 4, "Rank4"), RANK3(200, 6, "Rank3"), RANK2(300, 8, "Rank2"), RANK1(400, 10, "Rank1");
    
    private int requireRankPoint; // �ش� ��ũ�� �Ǳ� ���� �ʿ��� ��ũ����Ʈ
    private int pointGain; // ���� �ذ�� ����ڰ� ���� �� �ִ� ����Ʈ
    private String rankName; // �������� ���� ���ڿ� �̸�
    
    private RANK(int requireRankPoint, int pointGain, String rankName) {
        this.requireRankPoint = requireRankPoint;
        this.pointGain = pointGain;
        this.rankName = rankName;
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
    
    public static int getMaxRequireRankPoint() {
    	int gap = RANK1.getRequireRankPoint() - RANK1.getRequireRankPoint();
        return RANK1.getRequireRankPoint() + gap;
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
    
}
