package com.force.aus.outboundMessage;

import java.io.IOException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.force.aus.outboundMessage.entity.ReceivedMessage;
import com.google.gson.Gson;

public class GetOBMServlet extends HttpServlet {

	private Logger logger;
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		logger = LoggerFactory.getLogger(GetOBMServlet.class);
		
		ServletOutputStream out = resp.getOutputStream();
		
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("com.force.aus.outboundMessage.obmpu");
        EntityManager em = emf.createEntityManager();
		
		Query q = em.createQuery("from ReceivedMessage");
		List<ReceivedMessage> messages = (List<ReceivedMessage>)q.getResultList();

		Gson gson = new Gson();
		logger.info(gson.toJson(messages));
		out.write(gson.toJson(messages).getBytes());
		
		out.flush();
		out.close();
	}

	
	
	
}
