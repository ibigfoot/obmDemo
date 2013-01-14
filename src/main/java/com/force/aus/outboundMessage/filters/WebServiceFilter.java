package com.force.aus.outboundMessage.filters;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.persistence.EntityManager;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.force.aus.outboundMessage.listeners.EMFListener;

public class WebServiceFilter implements Filter {

	private Logger logger;
	private EntityManager em;
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {

		logger = LoggerFactory.getLogger(WebServiceFilter.class);
		logger.info("Initialsing {}", WebServiceFilter.class);
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		
		em = EMFListener.createEntityManager();

		logger.info("Should be parsing out some xml here {}", request.toString());
	
		em.close();
		chain.doFilter(request, response);
	}

	@Override
	public void destroy() {
		logger.info("Destroy in {}", WebServiceFilter.class);
	}

	
}
