package com.force.aus.outboundMessage;

import java.net.URI;

import javax.naming.InitialContext;

import org.eclipse.jetty.plus.jndi.Resource;
import org.eclipse.jetty.plus.webapp.EnvConfiguration;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.Configuration;
import org.eclipse.jetty.webapp.WebAppContext;
import org.postgresql.ds.PGSimpleDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * This class launches the web application in an embedded Jetty container.
 * This is the entry point to your application. The Java command that is used for
 * launching should fire this main method.
 *
 */
public class Main {
    
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
    	
    	Logger logger = LoggerFactory.getLogger(Main.class);
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
        WebAppContext root = new WebAppContext();
        root.setConfigurationClasses(configClasses);
        root.setContextPath("/");
        root.setDescriptor(webappDirLocation+"/WEB-INF/web.xml");
        root.setResourceBase(webappDirLocation);

        
        //configure database properties
        URI dbUri = new URI(System.getenv("DATABASE_URL"));

        String username = dbUri.getUserInfo().split(":")[0];
        String password = dbUri.getUserInfo().split(":")[1];
        String dbUrl = "jdbc:postgresql://" + dbUri.getHost() + ':' + dbUri.getPort() + "/" + dbUri.getPath();
        
        logger.info("DBURI ["+dbUri+"]");
        logger.info("Username ["+username+"]");
        logger.info("Password ["+password+"]");
        logger.info("Host ["+dbUri.getHost()+"]");
        logger.info("Port ["+dbUri.getPort()+"]");
        logger.info("Path ["+dbUri.getPath()+"]");
        
        logger.info("DBRUL ["+dbUrl+"]");
        
        PGSimpleDataSource pgDS = new PGSimpleDataSource();
        pgDS.setDatabaseName(dbUri.getPath());
        pgDS.setUser(username);
        pgDS.setPassword(password);
        pgDS.setServerName(dbUri.getHost());
        pgDS.setPortNumber(dbUri.getPort());
        
        Resource resource = new Resource("jdbc/obmDS", pgDS);
        
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

}
