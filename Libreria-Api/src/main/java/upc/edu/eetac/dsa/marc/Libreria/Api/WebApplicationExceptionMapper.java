package upc.edu.eetac.dsa.marc.Libreria.Api;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import upc.edu.eetac.dsa.marc.Libreria.Api.model.LibreriaError;

// Procesa el formato del error recibido
@Provider
public class WebApplicationExceptionMapper implements
		ExceptionMapper<WebApplicationException> {
	@Override
	public Response toResponse(WebApplicationException exception) {
		LibreriaError error = new LibreriaError(
				exception.getResponse().getStatus(), exception.getMessage());
		return Response.status(error.getStatus()).entity(error)
				.type(MediaType.LIBRERIA_API_ERROR).build();
	}

}