package org.example;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/**
 * You can build and run a web application at the same time, without having to war the project, using the jetty-maven-plugin command:
 * mvn jetty:run
 * then You can see the content of the static and dynamic at http://localhost:8080/hello.
 * @author esnahow
 * @date 01/16/2019 18:59
 */
public class HelloServlet extends HttpServlet
{
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        response.setContentType("text/html");
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().println("<h1>Hello Servlet,Hi world</h1>");
        response.getWriter().println("session=" + request.getSession(true).getId());
    }
}