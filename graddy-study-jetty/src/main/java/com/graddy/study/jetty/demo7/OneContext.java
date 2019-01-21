package com.graddy.study.jetty.demo7;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ContextHandler;

import com.graddy.study.jetty.demo2.step2.HelloHandler;

/**
 * This OneContext example contains a HelloHandler handler that handles requests in the specified path
 * @author esnahow
 * @date 01/16/2019 17:06
 */
public class OneContext {
    public static void main(String[] args) throws Exception {
        Server server = new Server(8080);

        // add a handler to the /hello path
        ContextHandler context = new ContextHandler();
        context.setContextPath("/hello");
        context.setHandler(new HelloHandler());

        // can be accessed through http://localhost:8080/hello
        server.setHandler(context);

        server.start();
        server.join();
    }
}