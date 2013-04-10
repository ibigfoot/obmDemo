/*
 * Copyright (c) 2013, salesforce.com, inc.
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification, are permitted provided
 * that the following conditions are met:
 *
 *    Redistributions of source code must retain the above copyright notice, this list of conditions and the
 *    following disclaimer.
 *
 *    Redistributions in binary form must reproduce the above copyright notice, this list of conditions and
 *    the following disclaimer in the documentation and/or other materials provided with the distribution.
 *
 *    Neither the name of salesforce.com, inc. nor the names of its contributors may be used to endorse or
 *    promote products derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A
 * PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
 * ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED
 * TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION)
 * HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */
package com.force.aus.outboundMessage;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Date;
import java.util.Random;

import javax.naming.NamingException;

import org.eclipse.jetty.nosql.mongodb.MongoSessionIdManager;
import org.eclipse.jetty.nosql.mongodb.MongoSessionManager;
import org.eclipse.jetty.plus.jndi.Resource;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.session.SessionHandler;
import org.eclipse.jetty.webapp.WebAppContext;
import org.postgresql.ds.PGSimpleDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mongodb.DB;
import com.mongodb.MongoURI;

/**
 * 
 * This class launches the web application in an embedded Jetty container.
 * This is the entry point to your application. The Java command that is used for
 * launching should fire this main method.
 *
 */
public class Main {
    
	private static Logger LOG;
	
	private static String[] configClasses = {
		 "org.eclipse.jetty.webapp.WebInfConfiguration",
		  "org.eclipse.jetty.webapp.WebXmlConfiguration",
		  "org.eclipse.jetty.webapp.MetaInfConfiguration",
		  "org.eclipse.jetty.webapp.FragmentConfiguration",
		  "org.eclipse.jetty.plus.webapp.EnvConfiguration", //add for jndi
		  "org.eclipse.jetty.plus.webapp.PlusConfiguration", // add for jndi
		  "org.eclipse.jetty.webapp.JettyWebXmlConfiguration",
		  "org.eclipse.jetty.webapp.TagLibConfiguration"
	    } ;
	
    /**
     * @param args
     */
    public static void main(String[] args) throws Exception{
    	
    	LOG = LoggerFactory.getLogger(Main.class);
    	
    	AppProperties.loadProperties();
        String webappDirLocation = "src/main/webapp/";
        
        //The port that we should run on can be set into an environment variable
        //Look for that variable and default to 8080 if it isn't there.
        String webPort = System.getenv("PORT");
        if(webPort == null || webPort.isEmpty()) {
            webPort = "8080";
        }
        
        System.setProperty("java.naming.factory.url","org.eclipse.jetty.jndi");
		System.setProperty("java.naming.factory.initial","org.eclipse.jetty.jndi.InitialContextFactory");

        Server server = new Server(Integer.valueOf(webPort));
        
        MongoURI mongoURI = new MongoURI(System.getenv("MONGOHQ_URL"));
        DB connectedDB = mongoURI.connectDB();
        
        if(mongoURI.getUsername() != null) {
        	connectedDB.authenticate(mongoURI.getUsername(), mongoURI.getPassword());
        }
        MongoSessionIdManager idManager = new MongoSessionIdManager(server, connectedDB.getCollection("sessions"));
        Random rand = new Random((new Date()).getTime());
        int workerNum = 1000 + rand.nextInt(8999);
        
        idManager.setWorkerName(String.valueOf(workerNum));
        server.setSessionIdManager(idManager);        

        SessionHandler sessionHandler = new SessionHandler();
        MongoSessionManager mongoMgr = new MongoSessionManager();
        mongoMgr.setSessionIdManager(server.getSessionIdManager());
        sessionHandler.setSessionManager(mongoMgr);
        
        WebAppContext root = new WebAppContext();
        root.setConfigurationClasses(configClasses);
        root.setContextPath("/");
        root.setDescriptor(webappDirLocation+"/WEB-INF/web.xml");
        root.setResourceBase(webappDirLocation);
        root.setSessionHandler(sessionHandler);
        root.setAttribute("obmDS", getJNDIResource());
        
        //Parent loader priority is a class loader setting that Jetty accepts.
        //By default Jetty will behave like most web containers in that it will
        //allow your application to replace non-server libraries that are part of the
        //container. Setting parent loader priority to true changes this behavior.
        //Read more here: http://wiki.eclipse.org/Jetty/Reference/Jetty_Classloading
        root.setParentLoaderPriority(true);

        server.setHandler(root);
        server.start();
        server.join();   
    }
      
    
    private static Resource getJNDIResource() throws URISyntaxException, NamingException {
    	
    	// database URL should be postgres://username:password@host:port/schema
    	
    	String databaseURL = System.getenv("DATABASE_URL") ;
    	if(databaseURL == null) {
    		databaseURL = AppProperties.getPropValue(AppProperties.DEV_DBASE_URL);
    	}
    	
     	URI dbUri = new URI(databaseURL);
    	String username = dbUri.getUserInfo().split(":")[0];
        String password = dbUri.getUserInfo().split(":")[1];
        
        LOG.info("DBURI ["+dbUri+"]");
        LOG.info("Username ["+username+"]");
        LOG.info("Password ["+password+"]");
        LOG.info("Host ["+dbUri.getHost()+"]");
        LOG.info("Port ["+dbUri.getPort()+"]");
        LOG.info("Path ["+dbUri.getPath()+"]");
        
        PGSimpleDataSource pgDS = new PGSimpleDataSource();
        pgDS.setDatabaseName(dbUri.getPath().replaceAll("/", ""));
        pgDS.setUser(username);
        pgDS.setPassword(password);
        pgDS.setServerName(dbUri.getHost());
        pgDS.setPortNumber(dbUri.getPort());
        
        return new Resource("jdbc/obmDS", pgDS);
        
    }

    public static void dumpClasspath() {
    	ClassLoader cl = ClassLoader.getSystemClassLoader();
    	LOG.info("ClassPath -------");
    	for(URL url : ((URLClassLoader)cl).getURLs()) {
    		LOG.info(url.toExternalForm());
    	}
    	LOG.info("------CLASSPATH END------");
    }
    
}
