package com.castortech.iris.ba.web.home;

import javax.ws.rs.core.Application;

import org.osgi.service.component.annotations.Component;
import static org.osgi.service.jaxrs.whiteboard.JaxrsWhiteboardConstants.JAX_RS_APPLICATION_BASE;

import java.util.Collections;
import java.util.Set;

@Component(
		//immediate = true,
		service = Application.class,
		property= {
				"osgi.jaxrs.name=RestApp",
				JAX_RS_APPLICATION_BASE + "=/rest",	
				"authentication.with=keycloak"
		}
	)
public class RestApiApp extends Application{

	@Override
  public Set<Object> getSingletons() {
      return Collections.singleton(this);
  }
}
