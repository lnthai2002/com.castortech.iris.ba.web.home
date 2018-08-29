package com.castortech.iris.ba.web.home;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.Servlet;
import javax.ws.rs.container.ContainerRequestFilter;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.servlet.ServletContainer;
import org.keycloak.adapters.servlet.KeycloakOIDCFilter;
import org.keycloak.jaxrs.OsgiJaxrsBearerTokenFilterImpl;
import org.ops4j.pax.web.extender.whiteboard.runtime.DefaultWelcomeFileMapping;
//import org.ops4j.pax.web.service.whiteboard.WelcomeFileMapping;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.http.HttpContext;
import org.osgi.service.http.whiteboard.HttpWhiteboardConstants;

public class Activator implements BundleActivator {

	//private ServiceRegistration<WelcomeFileMapping> welcomeFileRegistration;
	private ServiceRegistration<Servlet> servletReg;
	private ServiceRegistration<Filter> keycloakFilter;
	private ServiceRegistration<ContainerRequestFilter> keycloakRestFilter;
	private ServiceRegistration<Handler> restService;

	public void start(BundleContext context) throws Exception {
		
		// register Keycloak filter to serve as authentication layer
		Dictionary<String, Object> filterProps = new Hashtable<String, Object>();
		String[] urls = { "/*" }; //$NON-NLS-1$
		// String[] servlets = {"My Servlet", "Faces Servlet"};
		filterProps.put(HttpWhiteboardConstants.HTTP_WHITEBOARD_FILTER_NAME, "keycloakFilter"); //$NON-NLS-1$
		filterProps.put(HttpWhiteboardConstants.HTTP_WHITEBOARD_FILTER_PATTERN, urls);
		// filterProps.put("servletNames", servlets);
		keycloakFilter = context.registerService(Filter.class, new BaKeycloakOIDCFilter(), filterProps);
		
		/*
		//register Keycloak filter to serve as authentication layer
		Dictionary<String, String> restFilterProps = new Hashtable<String, String>();
		restFilterProps.put("osgi.jaxrs.extension", "true");
		keycloakRestFilter = context.registerService(ContainerRequestFilter.class, new OsgiJaxrsBearerTokenFilterImpl(), restFilterProps);
		*/

		/*//register welcome page
		DefaultWelcomeFileMapping welcomeFileMapping = new DefaultWelcomeFileMapping();
		//welcomeFileMapping.setHttpContextId(httpContextId);
		welcomeFileMapping.setRedirect(true);
		welcomeFileMapping.setWelcomeFiles(new String[] { "index.html" }); //$NON-NLS-1$
		welcomeFileRegistration = context.registerService(WelcomeFileMapping.class, welcomeFileMapping,	null);
		*/
		/*
		//register a test servlet
		Dictionary<String, String> props;
		props = new Hashtable<>();
		props.put("alias", "/whiteboard"); //$NON-NLS-1$ //$NON-NLS-2$
		servletReg = context.registerService(Servlet.class, new WhiteboardServlet("/whiteboard"), props); //$NON-NLS-1$
		*/
		/*//register a jetty handler
		//replacing WebViewerHandlerCollection: register REST services using Jersey
		//see http://ops4j.github.io/pax/web/6.x/User-Guide.html#advanced-jetty-configuration
		List<String> providerNamesList = new ArrayList<>();
		providerNamesList.add(RestAPI.class.getCanonicalName());
		String providerNames = StringUtils.join(providerNamesList, ","); //$NON-NLS-1$

		ServletHolder sh = new ServletHolder(ServletContainer.class);
		sh.setInitParameter("com.sun.jersey.config.property.resourceConfigClass", //$NON-NLS-1$
				"com.sun.jersey.api.core.PackagesResourceConfig"); //$NON-NLS-1$
		sh.setInitParameter("com.sun.jersey.api.json.POJOMappingFeature", "true"); //$NON-NLS-1$ //$NON-NLS-2$
		sh.setInitParameter("jersey.config.server.provider.classnames", providerNames); //$NON-NLS-1$
		sh.setInitParameter("cacheControl", "max-age=31536000"); //$NON-NLS-1$ //$NON-NLS-2$

		ServletContextHandler jerseyContext = new ServletContextHandler();
		jerseyContext.setContextPath("/ba"); //$NON-NLS-1$
		jerseyContext.addServlet(sh, "/rest/*"); //$NON-NLS-1$
		restService = context.registerService(Handler.class, jerseyContext, null);*/
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext context) throws Exception {
		if (keycloakFilter != null) {
			keycloakFilter.unregister();
			keycloakFilter = null;
		}
		
		/*if (welcomeFileRegistration != null) {
			welcomeFileRegistration.unregister();
			welcomeFileRegistration = null;
		}*/
		if (servletReg != null) {
			servletReg.unregister();
			servletReg = null;
		}
		if (restService != null) {
			restService.unregister();
			restService = null;
		}
	}

}
