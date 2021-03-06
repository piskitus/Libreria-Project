package upc.edu.eetac.dsa.marc.Libreria.Api;

import javax.sql.DataSource;
import javax.ws.rs.Path;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.Consumes;
import javax.ws.rs.ForbiddenException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.DELETE;
import javax.ws.rs.PUT;
import javax.ws.rs.QueryParam;
import javax.ws.rs.ServerErrorException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import com.mysql.jdbc.Statement;

import upc.edu.eetac.dsa.marc.Libreria.Api.model.Reviews;
import upc.edu.eetac.dsa.marc.Libreria.Api.model.ReviewsCollection;

@Path("/reviews")
public class ReviewResource {
	private DataSource ds = DataSourceSPA.getInstance().getDataSource();

	private String INSERT_REVIEW = "insert into reviews (username, name, books, content) values(?, ?, ?, ?)";
	private String GET_REVIEW_BY_ID = "select * from reviews where reviewID = ?";
	private String DELETE_REVIEW = "delete from reviews where reviewID=?";
	private String UPDATE_REVIEW = "update reviews set content=ifnull(?, content) where reviewID=?";
	private String GET_REVIEW_BY_USER = "select * from reviews where username = ? and book = ?";
	private String GET_REVIEWS_BY_BOOK = "select*from reviews, books where reviews.book=books.bookID and books.title=?";

	// Obtener colección de reviews por libro
	@GET
	@Produces(MediaType.LIBRERIA_API_REVIEWS_COLLECTION)
	public ReviewsCollection getReviews(@QueryParam("title") String title) {
		ReviewsCollection reviews = new ReviewsCollection();

		Connection conn = null;
		try {
			conn = ds.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		PreparedStatement stmt = null;
		try {
			stmt = conn.prepareStatement(GET_REVIEWS_BY_BOOK);
			stmt.setString(1, title);

			ResultSet rs = stmt.executeQuery();
			int lastReview = 0;
			boolean first = true;
			while (rs.next()) {
				Reviews review = new Reviews();
				review.setBook(rs.getInt("bookID"));
				review.setContent(rs.getString("content"));
				review.setName(rs.getString("name"));
				review.setUsername(rs.getString("username"));
				lastReview = rs.getInt("reviewID");

				if (first) {
					first = false;
					reviews.setFirstReview(lastReview);
				}

				reviews.addReview(review);
			}
			reviews.setLastReview(lastReview);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (stmt != null)
					stmt.close();
				conn.close();
			} catch (SQLException e) {
			}
		}

		return reviews;
	}

	// Crear review
	@POST
	@Consumes(MediaType.LIBRERIA_API_REVIEWS)
	@Produces(MediaType.LIBRERIA_API_REVIEWS)
	public Reviews createReview(Reviews reviews) {
		validateNoPreviousReviews(reviews);

		Connection conn = null;
		try {
			conn = ds.getConnection();
		} catch (SQLException e) {
			throw new ServerErrorException("Could not connect to the database",
					Response.Status.SERVICE_UNAVAILABLE);
		}

		PreparedStatement stmt = null;
		try {
			stmt = conn.prepareStatement(INSERT_REVIEW,
					Statement.RETURN_GENERATED_KEYS);
			stmt.setString(1, reviews.getUsername());
			stmt.setString(2, reviews.getName());
			stmt.setInt(3, reviews.getBook());
			stmt.setString(4, reviews.getContent());
			stmt.executeUpdate();

			ResultSet rs = stmt.getGeneratedKeys();
			if (rs.next()) {
				int reviewid = rs.getInt(1);
				reviews = getReviewFromDatabase(reviewid);
			}
		} catch (SQLException e) {
			throw new ServerErrorException(e.getMessage(),
					Response.Status.INTERNAL_SERVER_ERROR);
		} finally {
			try {
				if (stmt != null)
					stmt.close();
				conn.close();
			} catch (SQLException e) {
			}
		}
		return reviews;
	}

	// Borrar una review
	@DELETE
	@Path("/{reviewid}")
	public void deleteSting(@PathParam("reviewid") int reviewid) {
		validateUser(reviewid);

		Connection conn = null;
		try {
			conn = ds.getConnection();
		} catch (SQLException e) {
			throw new ServerErrorException("Could not connect to the database",
					Response.Status.SERVICE_UNAVAILABLE);
		}

		PreparedStatement stmt = null;
		try {
			stmt = conn.prepareStatement(DELETE_REVIEW);
			stmt.setInt(1, reviewid);

			int rows = stmt.executeUpdate();
			if (rows == 0)
				throw new NotFoundException("There's no review with id="
						+ reviewid);
		} catch (SQLException e) {
			throw new ServerErrorException(e.getMessage(),
					Response.Status.INTERNAL_SERVER_ERROR);
		} finally {
			try {
				if (stmt != null)
					stmt.close();
				conn.close();
			} catch (SQLException e) {
			}
		}
	}

	@PUT
	@Path("/{reviewid}")
	@Consumes(MediaType.LIBRERIA_API_REVIEWS)
	@Produces(MediaType.LIBRERIA_API_REVIEWS)
	public Reviews updateReview(@PathParam("reviewid") int reviewid,
			Reviews review) {
		validateUser(reviewid);

		validateUpdateReviews(review);

		Connection conn = null;
		try {
			conn = ds.getConnection();
		} catch (SQLException e) {
			throw new ServerErrorException("Could not connect to the database",
					Response.Status.SERVICE_UNAVAILABLE);
		}

		PreparedStatement stmt = null;
		try {
			stmt = conn.prepareStatement(UPDATE_REVIEW);
			stmt.setString(1, review.getContent());
			stmt.setInt(2, Integer.valueOf(reviewid));

			int rows = stmt.executeUpdate();
			if (rows == 1)
				review = getReviewFromDatabase(reviewid);
			else {
				throw new NotFoundException("There's no review with id="
						+ reviewid);
			}

		} catch (SQLException e) {
			throw new ServerErrorException(e.getMessage(),
					Response.Status.INTERNAL_SERVER_ERROR);
		} finally {
			try {
				if (stmt != null)
					stmt.close();
				conn.close();
			} catch (SQLException e) {
			}
		}

		return review;
	}

	// Método para recuperar review de la BD
	private Reviews getReviewFromDatabase(int reviewid) {
		Reviews review = new Reviews();

		Connection conn = null;
		try {
			conn = ds.getConnection();
		} catch (SQLException e) {
			throw new ServerErrorException("Could not connect to the database",
					Response.Status.SERVICE_UNAVAILABLE);
		}

		PreparedStatement stmt = null;
		try {
			stmt = conn.prepareStatement(GET_REVIEW_BY_ID);
			stmt.setInt(1, reviewid);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				review.setReviewid(rs.getInt("reviewid"));
				review.setBook(rs.getInt("book"));
				review.setUsername(rs.getString("username"));
				review.setContent(rs.getString("content"));
				review.setName(rs.getString("name"));
				review.setCreationTimestamp(rs.getTimestamp(
						"creation_timestamp").getTime());
				review.setLastModified(rs.getTimestamp("last_modified")
						.getTime());
			} else {
				throw new NotFoundException("There's no review with reviewid="
						+ reviewid);
			}
		} catch (SQLException e) {
			throw new ServerErrorException(e.getMessage(),
					Response.Status.INTERNAL_SERVER_ERROR);
		} finally {
			try {
				if (stmt != null)
					stmt.close();
				conn.close();
			} catch (SQLException e) {
			}
		}
		return review;
	}

	// Método de validación de Update
	private void validateUpdateReviews(Reviews review) {
		if (review.getContent() != null && review.getContent().length() > 500)
			throw new BadRequestException(
					"Content can't be greater than 500 characters.");
	}

	// Método de comprobación que el usuario es el correcto
	private void validateUser(int reviewid) {
		Reviews review = getReviewFromDatabase(reviewid);
		String username = review.getUsername();
		if (!security.getUserPrincipal().getName().equals(username))
			throw new ForbiddenException(
					"You are not allowed to modify this review.");
	}

	// Método para comprobar que el usuario no ha escrito ya una review
	private void validateNoPreviousReviews(Reviews review) {
		Connection conn = null;
		try {
			conn = ds.getConnection();
		} catch (SQLException e) {
			throw new ServerErrorException("Could not connect to the database",
					Response.Status.SERVICE_UNAVAILABLE);
		}

		PreparedStatement stmt = null;
		try {
			stmt = conn.prepareStatement(GET_REVIEW_BY_USER);
			stmt.setString(1, review.getUsername());
			stmt.setInt(2, review.getBook());
			ResultSet rs = stmt.executeQuery();
			if (rs.next())
				throw new BadRequestException(
						"Ya has publicado una review para este libro");
		} catch (SQLException e) {
			throw new ServerErrorException(e.getMessage(),
					Response.Status.INTERNAL_SERVER_ERROR);
		} finally {
			try {
				if (stmt != null)
					stmt.close();
				conn.close();
			} catch (SQLException e) {
			}
		}
	}

	@Context
	private SecurityContext security;
}