package users;


// �����, ������ ��ũ�� �����ϴ� ������
public enum RANK {
	// ��ũ ����Ʈ ���̴� ��� ���ƾ� ��
    RANK5(0, 2), RANK4(100, 4), RANK3(200, 6), RANK2(300, 8), RANK1(400, 10);
    
    private int requireRankPoint; // �ش� ��ũ�� �Ǳ� ���� �ʿ��� ��ũ����Ʈ
    private int pointGain; // ���� �ذ�� ����ڰ� ���� �� �ִ� ����Ʈ
    
    private RANK(int requireRankPoint, int pointGain) {
        this.requireRankPoint = requireRankPoint;
        this.pointGain = pointGain;
    }
    
    public int getRequireRankPoint() {
        return this.requireRankPoint;
    }
    public int getPointGain() {
        return this.pointGain;
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
