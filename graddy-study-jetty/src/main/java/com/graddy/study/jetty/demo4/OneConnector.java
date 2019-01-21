package com.graddy.study.jetty.demo4;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;

import com.graddy.study.jetty.demo2.step2.HelloHandler;

/**
 * 有一个连接的Server
 */
public class OneConnector {
    public static void main(String[] args) throws Exception {
        Server server = new Server();

        // create an HTTP connection, configure the listener host, port, and timeout
        ServerConnector http = new ServerConnector(server);
        http.setHost("localhost");
        http.setPort(8080);
        http.setIdleTimeout(30000);

        // add this connection to the Server
        server.addConnector(http);

        // set up a processor
        server.setHandler(new HelloHandler());

        // start the Server
        server.start();
        server.join();
    }
}