package upc.edu.eetac.dsa.marc.Libreria.Api.model;

import java.util.List;
import javax.ws.rs.core.Link;
import org.glassfish.jersey.linking.Binding;
import org.glassfish.jersey.linking.InjectLink;
import org.glassfish.jersey.linking.InjectLinks;
import org.glassfish.jersey.linking.InjectLink.Style;

import upc.edu.eetac.dsa.marc.Libreria.Api.BookResource;
import upc.edu.eetac.dsa.marc.Libreria.Api.MediaType;

public class Books {
	@InjectLinks({
		@InjectLink(resource = BookResource.class, style = Style.ABSOLUTE, rel = "books", title = "Colección de libros", type = MediaType.LIBRERIA_API_BOOKS_COLLECTION),
		@InjectLink(value = "/reviews?title={title}", style = Style.ABSOLUTE, rel = "reviews", title = "Reviews del libro", type = MediaType.LIBRERIA_API_REVIEWS_COLLECTION, bindings = { @Binding(name = "title", value = "${instance.title}") }),
		@InjectLink(resource = BookResource.class, style = Style.ABSOLUTE, rel = "self edit", title = "Libro", type = MediaType.LIBRERIA_API_BOOKS, method = "getBook", bindings = @Binding(name = "title", value = "${instance.title}")) 
		})
	
	private int bookid;
	private String title;
	private String language;
	private String edition;
	private String editonDate;
	private String printingDate;
	private String publisher;
	private List<Author> authors;
	private List<Link> links;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getEdition() {
		return edition;
	}

	public void setEdition(String edition) {
		this.edition = edition;
	}

	public String getEditonDate() {
		return editonDate;
	}

	public void setEditonDate(String editonDate) {
		this.editonDate = editonDate;
	}

	public String getPrintingDate() {
		return printingDate;
	}

	public void setPrintingDate(String printingDate) {
		this.printingDate = printingDate;
	}

	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	public int getBookid() {
		return bookid;
	}

	public void setBookid(int bookid) {
		this.bookid = bookid;
	}

	public List<Author> getAuthors() {
		return authors;
	}

	public void setAuthors(List<Author> authors) {
		this.authors = authors;
	}
	
	public List<Link> getLinks() {
		return links;
	}

	public void setLinks(List<Link> links) {
		this.links = links;
	}
}