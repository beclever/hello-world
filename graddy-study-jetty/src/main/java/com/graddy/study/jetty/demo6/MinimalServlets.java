package com.graddy.study.jetty.demo6;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletHandler;

public class MinimalServlets {
    public static void main(String[] args) throws Exception {
        
        Server server = new Server(8080);
        //ServletHandler creates a very simple context handler with a servlet
        // this handler needs to be registered with the Server
        ServletHandler handler = new ServletHandler();
        server.setHandler(handler);

        // pass in a path that matches this servlet
        // hint: this is a raw servlet, not configured via web.xml or @webservlet annotations or otherwise
        handler.addServletWithMapping(HelloServlet.class, "/*");

        server.start();
        server.join();
    }

    @SuppressWarnings("serial")
    public static class HelloServlet extends HttpServlet {
        @Override
        protected void doGet(HttpServletRequest request, HttpServletResponse response)
                throws ServletException, IOException {
            response.setContentType("text/html");
            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().println("<h1>Hello from HelloServlet</h1>");
        }
    }
}