package upc.edu.eetac.dsa.marc.Libreria.Api;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

import upc.edu.eetac.dsa.marc.Libreria.Api.model.LibreriaRootAPI;

@Path("/")
public class LibreriaRootAPIResource {
	@GET
	public LibreriaRootAPI getRootAPI() {
		LibreriaRootAPI api = new LibreriaRootAPI();
		return api;
	}
}