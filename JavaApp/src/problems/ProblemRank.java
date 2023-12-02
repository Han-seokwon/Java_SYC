package problems;

import users.User;
import users.RANK;

import java.io.Serializable;

public class ProblemRank implements Serializable {
	
	// ���� ���̵�, ���� �̸�, ������ ���� ��ũ, ������ ���� ����Ʈ, �ڸ�Ʈ
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
		sb.append(" (").append(Rank).append(" ���� ) ").append(Comment);
		
	    return sb.toString();
	}
	
	// ������
	public ProblemRank() {}
	public ProblemRank(int ID, User user, RANK Rank, int Point, String Comment) {
		this.ID = ID;
		this.user = user;
		this.Rank = Rank;
		this.RankPoint = Point;
		this.Comment = Comment;
	}
	
	// ������
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