package com.example;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
        
        Logger logger = LoggerFactory.getLogger(HelloServlet.class);
        logger.info("We have some servlet stuff");
        
        out.write("Hello Heroku - now with changes!".getBytes());
        out.flush();
        out.close();
    }
    
}
