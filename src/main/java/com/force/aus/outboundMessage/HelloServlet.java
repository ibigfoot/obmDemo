package com.force.aus.outboundMessage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.Map;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HelloServlet extends HttpServlet {

	/**
	 * serialVersionUID = 4781779936081278961L;
	 */
	private static final long serialVersionUID = 4781779936081278961L;

	@Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

		ServletOutputStream out = resp.getOutputStream();
        
		Context initialContext = null;
		Connection conn = null;
		
        Logger logger = LoggerFactory.getLogger(HelloServlet.class);
        logger.info("We have some servlet stuff");
        try {
        	initialContext = new InitialContext();
        	DataSource ds = (DataSource)initialContext.lookup("jdbc/obmDS");
        	conn = ds.getConnection();
  
        	out.write(("Catalog name ["+conn.getCatalog()+"]<br />").getBytes());
        	
        	conn.close();
        	initialContext.close();
        } catch (NamingException ne) {
        	ne.printStackTrace();
        	throw new RuntimeException(ne.getMessage());
        } catch (SQLException sqle) {
        	sqle.printStackTrace();
        	throw new RuntimeException(sqle.getMessage());
        } finally {
        	try {
        		conn.close();
        		initialContext.close();
        	} catch (Exception e) {
        		System.out.println("Totally stuffed ---- "+e.getMessage());
        	}
        }
        
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("com.force.aus.outboundMessage.obmpu");
        EntityManager em = emf.createEntityManager();
        Map<String, Object> emProps = em.getProperties();
        Iterator<String> it = emProps.keySet().iterator();
        while(it.hasNext()) {
        	out.write(("EM Prop ["+emProps.get(it.next())+"]").getBytes());
        }
        out.write("Hello Heroku - now with changes!".getBytes());
        out.flush();
        out.close();
    }
    
}
