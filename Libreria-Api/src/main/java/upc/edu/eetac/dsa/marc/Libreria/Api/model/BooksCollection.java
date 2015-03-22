package upc.edu.eetac.dsa.marc.Libreria.Api.model;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.Link;

import org.glassfish.jersey.linking.Binding;
import org.glassfish.jersey.linking.InjectLink;
import org.glassfish.jersey.linking.InjectLinks;
import org.glassfish.jersey.linking.InjectLink.Style;

import upc.edu.eetac.dsa.marc.Libreria.Api.BookResource;
import upc.edu.eetac.dsa.marc.Libreria.Api.MediaType;

public class BooksCollection {
	@InjectLinks({
			@InjectLink(resource = BookResource.class, style = Style.ABSOLUTE, rel = "create-book", title = "Crear libro", type = MediaType.LIBRERIA_API_BOOKS),
			@InjectLink(value = "/books?before={before}", style = Style.ABSOLUTE, rel = "previous", title = "Libros anteriores", type = MediaType.LIBRERIA_API_BOOKS_COLLECTION, bindings = { @Binding(name = "before", value = "${instance.firstBook}") }),
			@InjectLink(value = "/books?after={after}", style = Style.ABSOLUTE, rel = "following", title = "Libros posteriores", type = MediaType.LIBRERIA_API_BOOKS_COLLECTION, bindings = { @Binding(name = "after", value = "${instance.lastBook}") }) 
			})
	private List<Link> links;
	private int firstBook;
	private int lastBook;

	private List<Books> books;

	public BooksCollection() {
		super();
		books = new ArrayList<>();
	}

	public List<Books> getBooks() {
		return books;
	}

	public void setBooks(List<Books> books) {
		this.books = books;
	}

	public void addBook(Books book) {
		books.add(book);
	}

	public List<Link> getLinks() {
		return links;
	}

	public void setLinks(List<Link> links) {
		this.links = links;
	}

	public int getFirstBook() {
		return firstBook;
	}

	public void setFirstBook(int firstBook) {
		this.firstBook = firstBook;
	}

	public int getLastBook() {
		return lastBook;
	}

	public void setLastBook(int lastBook) {
		this.lastBook = lastBook;
	}
}