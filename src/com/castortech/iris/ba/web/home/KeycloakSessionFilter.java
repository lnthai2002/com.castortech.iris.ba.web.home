package com.castortech.iris.ba.web.home;

import java.io.IOException;
import java.util.concurrent.Callable;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.WebApplicationException;

import org.keycloak.KeycloakSecurityContext;
import org.keycloak.representations.IDToken;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import com.castortech.iris.ba.security.IUserSessionManager;
import com.castortech.iris.security.SecurityConstants;
import com.castortech.iris.security.SessionInfo;
import com.castortech.iris.session.SessionFactory;

@Component
public final class KeycloakSessionFilter implements Filter {
	@Reference
	SessionFactory sessionFactory;
	
	@Reference
	private IUserSessionManager userSessionManager;
	
	@Override
	public void doFilter(final ServletRequest request, final ServletResponse response, final FilterChain chain)
			throws IOException, ServletException {
		final HttpServletResponse httpResponse = (HttpServletResponse)response;
		
		Object kcSecurityContext = request.getAttribute(KeycloakSecurityContext.class.getName());
		
		if (kcSecurityContext != null)	{
			SessionInfo kcSessionInfo;
			try {
				KeycloakSecurityContext context = (KeycloakSecurityContext)kcSecurityContext;
				IDToken idToken = context.getIdToken();
				String kcSessionId = idToken.getSessionState();
				String email = idToken.getEmail();
				String userUuid = idToken.getSubject();
				String realm = context.getRealm();
				kcSessionInfo = new SessionInfo(kcSessionId, realm, userUuid, email);
			}
			catch(RuntimeException e) {
				throw new WebApplicationException("Cannot retrieve Keycloak session info from security context", e); //$NON-NLS-1$
			}

	  	HttpServletRequest req = (HttpServletRequest)request;
	  	req.getSession().setAttribute(SecurityConstants.SESSION__AUTHENTICATION_INFORMATION, kcSessionInfo);
	  	
	  	if (req.getSession().getAttribute(SecurityConstants.SESSION__TENANT_NAME) == null) {
	  		//TODO: redirect to tenant selection page if destination is not tenant selection page
	  		//TODO: here I hardcode the tenant for testing
	  		String selectedTenant = "client-1";  //clientId has to be string-number format or all numeric
	  		req.getSession().setAttribute(SecurityConstants.SESSION__TENANT_NAME, selectedTenant);
	  		kcSessionInfo.setClientId(selectedTenant);
	  	}
	  	else {
	  		String selectedTenant = (String)req.getSession().getAttribute(SecurityConstants.SESSION__TENANT_NAME);
	  		kcSessionInfo.setClientId(selectedTenant);
	  	}
	  	sessionFactory.createUserSession(kcSessionInfo.getSessionId(), kcSessionInfo.getRealm(), 
	  			kcSessionInfo.getClientId(), kcSessionInfo.getUserUUID(), kcSessionInfo.getEmail());
		}

		httpResponse.setHeader("Access-Control-Allow-Origin", "https://dev.biz-architect.com:8543");//KEYCLOAK server with SSL 
		httpResponse.setHeader("Access-Control-Allow-Methods", "GET, PUT, POST, DELETE, HEAD");
		httpResponse.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");
		chain.doFilter(request, response);
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
	}
}