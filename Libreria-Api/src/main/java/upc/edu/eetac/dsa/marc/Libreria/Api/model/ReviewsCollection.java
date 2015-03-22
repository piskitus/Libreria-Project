package upc.edu.eetac.dsa.marc.Libreria.Api.model;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.Link;

import org.glassfish.jersey.linking.Binding;
import org.glassfish.jersey.linking.InjectLink;
import org.glassfish.jersey.linking.InjectLinks;
import org.glassfish.jersey.linking.InjectLink.Style;

import upc.edu.eetac.dsa.marc.Libreria.Api.MediaType;
import upc.edu.eetac.dsa.marc.Libreria.Api.ReviewResource;

public class ReviewsCollection {
	@InjectLinks({
			@InjectLink(resource = ReviewResource.class, style = Style.ABSOLUTE, rel = "create-review", title = "Crear review", type = MediaType.LIBRERIA_API_REVIEWS),
			@InjectLink(value = "/reviews?before={before}", style = Style.ABSOLUTE, rel = "previous", title = "Libros anteriores", type = MediaType.LIBRERIA_API_REVIEWS_COLLECTION, bindings = { @Binding(name = "before", value = "${instance.firstReview}") }),
			@InjectLink(value = "/reviews?after={after}", style = Style.ABSOLUTE, rel = "following", title = "Libros posteriores", type = MediaType.LIBRERIA_API_REVIEWS_COLLECTION, bindings = { @Binding(name = "after", value = "${instance.lastReview}") }) 
			})
	private List<Link> links;
	private int firstReview;
	private int lastReview;

	private List<Reviews> reviews;
	
	public ReviewsCollection() {
		super();
		reviews = new ArrayList<>();
	}

	public List<Link> getLinks() {
		return links;
	}

	public void setLinks(List<Link> links) {
		this.links = links;
	}

	public int getFirstReview() {
		return firstReview;
	}

	public void setFirstReview(int firstReview) {
		this.firstReview = firstReview;
	}

	public int getLastReview() {
		return lastReview;
	}

	public void setLastReview(int lastReview) {
		this.lastReview = lastReview;
	}

	public List<Reviews> getReviews() {
		return reviews;
	}

	public void setReviews(List<Reviews> reviews) {
		this.reviews = reviews;
	}

	public void addReview(Reviews review) {
		reviews.add(review);
	}
}