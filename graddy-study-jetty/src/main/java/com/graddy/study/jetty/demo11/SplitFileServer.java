package com.graddy.study.jetty.demo11;
import java.io.File;

import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.server.handler.ContextHandlerCollection;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.toolchain.test.MavenTestingUtils;
import org.eclipse.jetty.util.resource.Resource;

public class SplitFileServer {
    public static void main(String[] args) throws Exception {
        Server server = new Server();
        ServerConnector connector = new ServerConnector(server);
        connector.setPort(8090);
        server.setConnectors(new Connector[] { connector });
        
     // create a resource handler, set "/" root path,
        ResourceHandler rh0 = new ResourceHandler();

        ContextHandler context0 = new ContextHandler();
        context0.setContextPath("/");
        File dir0 = MavenTestingUtils.getTestResourceDir("dir0");
        context0.setBaseResource(Resource.newResource(dir0));
        context0.setHandler(rh0);

     // create another resource handler and specify another folder
        ResourceHandler rh1 = new ResourceHandler();

        ContextHandler context1 = new ContextHandler();
        context1.setContextPath("/2");
        File dir1 = MavenTestingUtils.getTestResourceDir("dir1");
        context1.setBaseResource(Resource.newResource(dir1));
        context1.setHandler(rh1);

     // create a collection of ContextHandlerCollection to contain all resource handlers, in order if all are matched
        ContextHandlerCollection contexts = new ContextHandlerCollection();
        contexts.setHandlers(new Handler[] { context0, context1 });

        server.setHandler(contexts);

        server.start();

     // output server state
        System.out.println(server.dump());
        server.join();
    }
}