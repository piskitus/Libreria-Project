package upc.edu.eetac.dsa.marc.Libreria.Api;

public interface MediaType {//cantidad de recursos q yo hago publicos a través del API
	public final static String LIBRERIA_API_USER = "application/vnd.libreria.api.user+json";
	public final static String LIBRERIA_API_USER_COLLECTION = "application/vnd.libreria.api.user.collection+json";
	public final static String LIBRERIA_API_REVIEWS = "application/vnd.libreria.api.reviews+json";
	public final static String LIBRERIA_API_REVIEWS_COLLECTION = "application/vnd.libreria.api.reviews.collection+json";
	public final static String LIBRERIA_API_BOOKS = "application/vnd.libreria.api.books+json";
	public final static String LIBRERIA_API_BOOKS_COLLECTION = "application/vnd.libreria.api.books.collection+json";
	public final static String LIBRERIA_API_AUTHOR = "application/vnd.libreria.api.author+json";
	public final static String LIBRERIA_API_ERROR = "application/vnd.libreria.api.error+json";

}