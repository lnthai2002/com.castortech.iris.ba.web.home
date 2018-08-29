package com.castortech.iris.ba.web.home;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.jaxrs.runtime.JaxrsServiceRuntime;
import org.osgi.service.jaxrs.runtime.dto.RuntimeDTO;

@Component(
	//immediate=true,
	service = Api.class,
	property = {
			"osgi.jaxrs.name=RestApi",
			"osgi.jaxrs.resource=true",
			"osgi.jaxrs.application.select=(osgi.jaxrs.name=RestApp)"
	}
)
@Path("/common")
public final class Api {
	public static final Response EMPTY_RESPONSE = Response.noContent().type(MediaType.TEXT_HTML_TYPE).build();

	@Reference
	JaxrsServiceRuntime runtimeRef;
	
	@Reference
	ContainerRequestFilter filter;

	@GET
	@Path("/getObject")
	@Produces(MediaType.APPLICATION_JSON)
	public String getObject() {
		RuntimeDTO runtimeDto = runtimeRef.getRuntimeDTO();
		try {
			filter.filter(null);
		}
		catch(Exception e) {
			int x =2;
		}
		
		return "Rest call 2"; //$NON-NLS-1$
	}
}