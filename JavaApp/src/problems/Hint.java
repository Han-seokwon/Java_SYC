package problems;

import java.io.Serializable;

import users.User;

public class Hint  implements Serializable{
	private int step;
	private User writer;
	private String content;
	private static final long serialVersionUID = 1L;// 직렬화 버전 설정	
	
	public Hint() {}
	public Hint(int step, User writer, String content) {
		this.step = step;
		this.writer = writer;
		this.content = content;
	}
	
	
	@Override
	public String toString() {
		return "Hint [step=" + step + ", writer=" + writer + ", content=" + content + "]";
	}
	public int getStep() {
		return step;
	}
	public User getWriter() {
		return writer;
	}
	public String getContent() {
		return content;
	}
	
	
	
	
}
