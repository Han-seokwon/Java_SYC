package problems;

import java.io.Serializable;

import users.User;

// 학습 참고 자료를 저장하는 클래스
public class LearningReference implements Serializable{
	private User writer; // 작성자
	private String title; // 제목
	private String content; // 내용
	private static final long serialVersionUID = 1L;// 직렬화 버전 설정
	
	public LearningReference() {}
	public LearningReference(User writer, String title, String content) {
		super();
		this.writer = writer;
		this.title = title;
		this.content = content;
	}
	@Override
	public String toString() {
		return "LearningMaterial [writer=" + writer + ", title=" + title + ", content=" + content + "]";
	}
	public User getWriter() {
		return writer;
	}
	public String getTitle() {
		return title;
	}
	public String getContent() {
		return content;
	}
	
	
	
}
