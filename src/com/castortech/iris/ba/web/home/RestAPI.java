package com.castortech.iris.ba.web.home;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.osgi.service.component.annotations.Component;
import static org.osgi.service.jaxrs.whiteboard.JaxrsWhiteboardConstants.JAX_RS_APPLICATION_BASE;

import java.util.Collections;
import java.util.Set;
/*
@Component(
		immediate = true,
		service = Application.class,
    property = {
    		JAX_RS_APPLICATION_BASE + "=/old",
    		"osgi.jaxrs.name=RestAPI"
    }
)
*/
@Path("/common")
public final class RestAPI extends Application {
	public static final Response EMPTY_RESPONSE = Response.noContent().type(MediaType.TEXT_HTML_TYPE).build();

	@Override
  public Set<Object> getSingletons() {
      return Collections.singleton(this);
  }

	@GET
	@Path("/getObject")
	@Produces(MediaType.APPLICATION_JSON)
	public String getObject() {
		return "Rest call"; //$NON-NLS-1$
	}
}