package users;


// �����, ������ ��ũ�� �����ϴ� ������
public enum RANK {
    RANK5(0), RANK4(100), RANK3(200), RANK2(300), RANK1(400);
    
    private int requireRankPoint; // �ش� ��ũ�� �Ǳ� ���� �ʿ��� ��ũ����Ʈ
    
    private RANK(int rankPoint) {
        this.requireRankPoint = rankPoint;
    }
    
    public int getRequireRankPoint() {
        return this.requireRankPoint;
    }
	// ���� ���� Ÿ���� ��ü ����Ÿ�� ���̺��� ���� ��� ���� �������� ��ȯ, �׷��� ������ ���� ���� Ÿ���� ������ Ÿ���̹Ƿ� �״�� ������ Ÿ���� ��ȯ��
    public RANK getNextRank() {
        return ordinal() < values().length - 1 ? values()[ordinal() + 1] : values()[-1];
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
//        throw new IllegalArgumentException( point + " : �Էµ� ����Ʈ�� ��Ī�Ǵ� ��ũ�� �����ϴ�.");
    }
}
