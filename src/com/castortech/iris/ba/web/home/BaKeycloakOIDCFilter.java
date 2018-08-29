package com.castortech.iris.ba.web.home;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.keycloak.adapters.servlet.KeycloakOIDCFilter;

public class BaKeycloakOIDCFilter extends KeycloakOIDCFilter {

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {
		// TODO Auto-generated method stub
		int t = 1;
		super.doFilter(req, res, chain);
		int k = 2;
	}
	
	@Override
  public void init(final FilterConfig filterConfig) throws ServletException {
		super.init(filterConfig);
	}
}
