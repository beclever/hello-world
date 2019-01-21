package com.graddy.study.jetty.demo3;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.DefaultHandler;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.server.handler.gzip.GzipHandler;

public class FileServer {
    public static void main(String[] args) throws Exception {
    	// create a base Jetty service listening on port 8080
    	// if the port is set to 0, a random port is available. 
    	//The port information can be obtained from the log or written to the test method
        Server server = new Server(8080);

        // create a ResourceHandler that handles the request by providing a resource file
        // this is a built-in processor in Jetty, so it's perfect for forming a processing 
        //chain with other processors
        ResourceHandler resource_handler = new ResourceHandler();
	    // configure the ResourceHandler to set which file should be provided to the requester
	    // in this example, the files are configured in the current path,
        //but can actually be configured anywhere the JVM can access them
        resource_handler.setDirectoriesListed(true);
        resource_handler.setWelcomeFiles(new String[] { "index.html" });
        resource_handler.setResourceBase(".");

        // add the resource_handler to the GzipHandler and supply the GzipHandler to the Server
        GzipHandler gzip = new GzipHandler();
        server.setHandler(gzip);
        HandlerList handlers = new HandlerList();
        handlers.setHandlers(new Handler[] { resource_handler, new DefaultHandler() });
        gzip.setHandler(handlers);

        server.start();
        server.join();
    }
}