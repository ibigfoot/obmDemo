package com.force.aus.outboundMessage;

import java.net.URI;
import java.net.URISyntaxException;

import javax.naming.NamingException;

import org.eclipse.jetty.plus.jndi.Resource;
import org.eclipse.jetty.server.Server;
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
        root.setAttribute("obmDS", getJNDIResource(System.getenv("DATABASE_URL")));
        
        
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
    
    private static Resource getJNDIResource(String databaseURL) throws URISyntaxException, NamingException {
    	
    	// database URL should be postgres://username:password@host:port/schema
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

}
