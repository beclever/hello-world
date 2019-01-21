package com.graddy.study.jetty.demo9;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.DefaultServlet;
import org.eclipse.jetty.servlet.ServletContextHandler;
/**
 * ServletContextHandler是一种特殊的ContextHandler，它可以支持标准的sessions 和Servlets。下面例子的OneServletContext 
   *  实例化了一个 DefaultServlet为/tmp/ 和DumpServlet 提供静态资源服务，DumpServlet 创建session并且应答请求信息
 * @author esnahow
 * @date 01/16/2019 17:15
 */
public class OneServletContext {
    public static void main(String[] args) throws Exception {
        Server server = new Server(8080);

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");
        context.setResourceBase(System.getProperty("java.io.tmpdir"));
        server.setHandler(context);

        // add a dump servlet
        context.addServlet(DumpServlet.class, "/dump/*");
        // add a default servlet
        context.addServlet(DefaultServlet.class, "/");

        server.start();
        server.join();
    }
}