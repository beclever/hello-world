package com.graddy.study.jetty.demo1;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.AbstractHandler;

public class HelloWorld extends AbstractHandler {
    @Override
    public void handle(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        //Declare the encoding and file type of response
        response.setContentType("text/html; charset=utf-8");

        //Returns a status code
        response.setStatus(HttpServletResponse.SC_OK);

        // The return value of the request
        response.getWriter().println("<h1>Hello World</h1>");

        //Notify Jettyrequest to use this handler to process the request
        baseRequest.setHandled(true);
    }

    public static void main(String[] args) throws Exception {
        //create a server listening on port 8080
        Server server = new Server(8080);
        server.setHandler(new HelloWorld());

        //start server and wait for request
        server.start();
        server.join();
    }
}