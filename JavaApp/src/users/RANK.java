package users;


// 사용자, 문제의 랭크를 구분하는 열거형
public enum RANK {
	// 랭크 포인트 차이는 모두 같아야 함
    RANK5(0, 2), RANK4(100, 4), RANK3(200, 6), RANK2(300, 8), RANK1(400, 10);
    
    private int requireRankPoint; // 해당 랭크가 되기 위해 필요한 랭크포인트
    private int pointGain; // 문제 해결시 사용자가 얻을 수 있는 포인트
    
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

	// 현재 열거 타입이 전체 열거타입 길이보다 작은 경우 다음 열거형을 반환, 그렇지 않으면 현재 열거 타입이 마지막 타입이므로 그대로 마지막 타입을 반환함
    public RANK getNextRank() {
        return ordinal() < values().length - 1 ? values()[ordinal() + 1] : values()[-1];
    }
    
    public static int getMaxRequireRankPoint() {
    	int gap = RANK1.getRequireRankPoint() - RANK1.getRequireRankPoint();
        return RANK1.getRequireRankPoint() + gap;
    }
    
    // 특정 포인트를 입력하면 그에 맞는 RANK 열거형 반환
    public static RANK getRankForPoint(int point) {
    	RANK matchingRank = RANK5;
    	// for문을 돌며 최대 랭크로 결정됨
        for (RANK rank : values()) {
            if (point >= rank.getRequireRankPoint()) { 
            	matchingRank = rank; 
            }
        }
        return matchingRank;
    }
}
