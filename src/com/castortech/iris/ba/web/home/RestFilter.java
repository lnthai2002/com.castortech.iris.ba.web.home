package com.castortech.iris.ba.web.home;

import java.io.IOException;

import javax.annotation.Priority;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.PreMatching;

import org.keycloak.jaxrs.OsgiJaxrsBearerTokenFilterImpl;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ServiceScope;
//import org.osgi.service.jaxrs.whiteboard.propertytypes.JaxrsExtensionSelect;

@Component(
		//immediate=true,
		service=ContainerRequestFilter.class,
		scope=ServiceScope.PROTOTYPE,
		property= {
			"osgi.jaxrs.name=RestFilter",
			"osgi.jaxrs.extension=true",
			"osgi.jaxrs.application.select=(osgi.jaxrs.name=RestApp)"
		}
)
//@JaxrsExtensionSelect("(serialize.to=JSON)")
//@PreMatching
//@Priority(Priorities.AUTHENTICATION)
public final class RestFilter implements ContainerRequestFilter {
	public static final int count = 1;
	@Override
	public void filter(ContainerRequestContext arg0) throws IOException {
		//super.filter(arg0);
		int n = 10;
	}
}