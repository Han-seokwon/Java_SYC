package problems;

import java.io.Serializable;

import users.User;

// �н� ���� �ڷḦ �����ϴ� Ŭ����
public class LearningReference implements Serializable{
	private User writer; // �ۼ���
	private String title; // ����
	private String content; // ����
	private static final long serialVersionUID = 1L;// ����ȭ ���� ����
	
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
