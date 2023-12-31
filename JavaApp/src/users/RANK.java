package users;

import java.util.ArrayList;
import java.util.Vector;

//사용자의 랭크 및 문제의 랭크를 정하는데 사용되는 열거형 클래스 
public enum RANK {
    RANK1(0, 2, "Rank1"),
    RANK2(1, 4, "Rank2"),
    RANK3(2, 6, "Rank3"),
    RANK4(3, 8, "Rank4"),
    RANK5(4, 10, "Rank5");    
    
	private final static int RANK_POINT_GAP = 100; // 각 랭크 당 포인트 차이
    private int requireRankPoint; // 해당 랭크가 되기 위해 필요한 랭크포인트
    private int pointGain; // 문제 해결시 사용자가 얻을 수 있는 포인트
    private String rankName; // 열거형에 대한 문자열 이름
    
    private RANK(int requireRankPoint, int pointGain, String rankName) {
        this.requireRankPoint = requireRankPoint * RANK_POINT_GAP;
        this.pointGain = pointGain;
        this.rankName = rankName;
    }
    
    // 각 랭크 당 포인트 차이 반환
    public static int getRankPointGap() {
    	return RANK_POINT_GAP;
    }
    
    // 해당 랭크를 달성하기 위해 요구되는 랭크포인트
    public int getRequireRankPoint() {
        return requireRankPoint;
    }
    // 해당 랭크 문제를 해결할 경우 얻을 수 있는 랭크포인트
    public int getPointGain() {
        return pointGain;
    }
    // 해당 랭크의 문자열 이름
    public String getRankName() {
        return rankName;
    }

	// 현재 열거 타입이 전체 열거타입 길이보다 작은 경우 다음 열거형을 반환, 그렇지 않으면 현재 열거 타입이 마지막 타입이므로 그대로 마지막 타입을 반환함
    public RANK getNextRank() {    	
        return ordinal() < values().length - 1 ? values()[ordinal() + 1] : this;
    }
    
    // 모을 수 있는 랭크 포인트 최대치 
    public static int getMaxRequireRankPoint() {
        return RANK5.getRequireRankPoint() + RANK_POINT_GAP;
    }
    
    // 특정 포인트를 입력하면 그에 맞는 RANK 열거형 반환
    public static RANK getRankForPoint(int point) {
    	RANK matchingRank = RANK1; // 최소 랭크부터 시작
    	// for문을 돌며 최대 랭크로 결정됨
        for (RANK rank : values()) {
            if (point >= rank.getRequireRankPoint()) { 
            	matchingRank = rank; 
            }
        }
        return matchingRank;
    }
    // 모든 랭크의 문자열 이름을 벡터로 반환한다.
    public static Vector<String> getRankNameList() {
    	Vector<String>  rankNames = new Vector<>();
    	for(RANK rank : RANK.values()) {
    		rankNames.add(rank.getRankName());
    	}
    	return rankNames;
    	
    }
    
}
