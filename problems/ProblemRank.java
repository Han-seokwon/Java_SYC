package problems;

import users.User;
import users.RANK;

import java.io.Serializable;

public class ProblemRank implements Serializable {
	
	private int ID;
	private User user;
	private RANK Rank;
	private String Comment;
	
	private static final long serialVersionUID = 1L;
	
	public ProblemRank() {}
	public ProblemRank(int ID, User user, RANK Rank, String Comment) {
		this.ID = ID;
		this.user = user;
		this.Rank = Rank;
		this.Comment = Comment;
	}
	
	public RANK getRANK() {
		return this.Rank;
	}
	public String getComment() {
		return this.Comment;
	}
	public int getID(){
		return this.ID;
	}
	public User getuser() {
		return this.user;
	}
	
	public boolean isValid() {
		if (this.Rank == null || this.Comment == null || this.user == null || this.ID == 0) {
			return false;
		}
		else {
			return true;
		}
	}
	
}
