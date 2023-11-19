package users;

import java.util.ArrayList;
import java.util.Vector;

// 사용자, 문제의 랭크를 구분하는 열거형
public enum RANK {
	// 랭크 포인트 차이는 모두 같아야 함
    RANK5(0, 2, "Rank5"), RANK4(1, 4, "Rank4"), RANK3(2, 6, "Rank3"), RANK2(3, 8, "Rank2"), RANK1(4, 10, "Rank1");
    
	private final static int RANK_POINT_GAP = 100; // 각 랭크 당 포인트 차이
    private int requireRankPoint; // 해당 랭크가 되기 위해 필요한 랭크포인트
    private int pointGain; // 문제 해결시 사용자가 얻을 수 있는 포인트
    private String rankName; // 열거형에 대한 문자열 이름
    
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

	// 현재 열거 타입이 전체 열거타입 길이보다 작은 경우 다음 열거형을 반환, 그렇지 않으면 현재 열거 타입이 마지막 타입이므로 그대로 마지막 타입을 반환함
    public RANK getNextRank() {
        return ordinal() < values().length - 1 ? values()[ordinal() + 1] : values()[-1];
    }
    
    public static int getMaxRequireRankPoint() { // 모을 수 있는 랭크 포인트 최대치
        return RANK1.getRequireRankPoint() + RANK_POINT_GAP;
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
    
    public static Vector<String> getRankNameList() {
    	Vector<String>  rankNames = new Vector<>();
    	for(RANK rank : RANK.values()) {
    		rankNames.add(rank.getRankName());
    	}
    	return rankNames;
    	
    }
    
}
