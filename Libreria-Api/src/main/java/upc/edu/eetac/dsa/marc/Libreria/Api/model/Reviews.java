package upc.edu.eetac.dsa.marc.Libreria.Api.model;

public class Reviews {
	private int reviewID;
	private String username;
	private String name;
	private int book;
	private String content;
	private long lastModified;
	private long creationTimestamp;

	public int getReviewid() {
		return reviewID;
	}

	public void setReviewid(int reviewID) {
		this.reviewID = reviewID;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getBook() {
		return book;
	}

	public void setBook(int book) {
		this.book = book;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public long getLastModified() {
		return lastModified;
	}

	public void setLastModified(long lastModified) {
		this.lastModified = lastModified;
	}

	public long getCreationTimestamp() {
		return creationTimestamp;
	}

	public void setCreationTimestamp(long creationTimestamp) {
		this.creationTimestamp = creationTimestamp;
	}

}