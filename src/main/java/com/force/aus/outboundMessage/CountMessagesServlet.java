package com.force.aus.outboundMessage;

import java.io.IOException;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.force.aus.outboundMessage.listeners.EMFListener;

public class CountMessagesServlet extends HttpServlet{

	private Logger logger;
	private EntityManager em;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		doPost(req,resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		em = EMFListener.createEntityManager();
		logger = LoggerFactory.getLogger(CountMessagesServlet.class);

		Query q = em.createQuery("select count(*) from ReceivedMessage");
		Long rowCount = (Long)q.getSingleResult();
		em.close();
		//logger.info("Currently there are {} ReceivedMessages in the database",rowCount);
		resp.getWriter().write(rowCount.toString());
	}

	@Override
	protected void doPut(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		throw new UnsupportedOperationException();
	}

	@Override
	protected void doDelete(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		throw new UnsupportedOperationException();
	}
	
	
	
}
