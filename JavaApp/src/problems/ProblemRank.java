package problems;

import users.User;
import users.RANK;

import java.io.Serializable;

public class ProblemRank implements Serializable {
	
	// 문제 아이디, 유저 이름, 선택한 문제 랭크, 선택한 문제 포인트, 코멘트
	private int ID;
	private User user;
	private RANK Rank;
	private int RankPoint;
	private String Comment;
	
	private static final long serialVersionUID = 1L;
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(" - ").append(user.getUsername()).append(" : ");
		sb.append(" (").append(Rank).append(" 선택 ) ").append(Comment);
		
	    return sb.toString();
	}
	
	// 생성자
	public ProblemRank() {}
	public ProblemRank(int ID, User user, RANK Rank, int Point, String Comment) {
		this.ID = ID;
		this.user = user;
		this.Rank = Rank;
		this.RankPoint = Point;
		this.Comment = Comment;
	}
	
	// 접근자
	public RANK getRANK() {
		return this.Rank;
	}
	public String getComment() {
		return this.Comment;
	}
	public int getID(){
		return this.ID;
	}
	public int getRankPoint() {
		return this.RankPoint;
	}
	public User getuser() {
		return this.user;
	}
	
	public boolean isValid() {
		if (this.Rank == null || this.Comment == null 
				|| this.user == null || this.ID == 0 || this.RankPoint == 0) {
			return false;
		}
		else {
			return true;
		}
	}
	
}