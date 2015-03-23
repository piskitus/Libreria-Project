package upc.edu.eetac.dsa.marc.Libreria.Api.model;

//clase autor. Cada autor tiene su identificador
public class Author {
	private String name;
	private int authorID;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAuthorID() {
		return authorID;
	}

	public void setAuthorID(int authorID) {
		this.authorID = authorID;
	}
}
