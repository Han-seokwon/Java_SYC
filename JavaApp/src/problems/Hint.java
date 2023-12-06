package problems;

import java.io.Serializable;

import users.User;

// 문제에 대한 단계별 힌트 내용을 저장하는 클래스
public class Hint  implements Serializable{
	private int step; // 힌트 난이도
	//  STEP 1	 문제 접근 방법 제시
	//  STEP 2	 사용되는 핵심 알고리즘 및 일부 로직 공개
	//  STEP 3	 전체 해결 방법 공개	
	private User writer; // 작성자
	private String content; // 힌트 내용
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
