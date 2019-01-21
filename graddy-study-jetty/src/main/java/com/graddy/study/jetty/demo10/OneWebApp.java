package com.graddy.study.jetty.demo10;
import java.io.File;
import java.lang.management.ManagementFactory;

import org.eclipse.jetty.jmx.MBeanContainer;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.AllowSymLinkAliasChecker;
import org.eclipse.jetty.webapp.WebAppContext;
/**
 * WebAppContext is an extension of ServletContextHandler with standard web application 
 * components and web.xml to configure servlets, filters and other features with web.xml
 *  and annotations.The following OneWebApp example deploys a simple web application.Web applications 
 * can use resources provided by the container, in which case a LoginService is required and configured
 * @author esnahow
 * @date 01/16/2019 17:25
 */
public class OneWebApp {
    public static void main(String[] args) throws Exception {
        Server server = new Server(8080);

        // set the JMX
        MBeanContainer mbContainer = new MBeanContainer(ManagementFactory.getPlatformMBeanServer());
        server.addBean(mbContainer);

       // the following web application is a complete web application, 
        //in this example set / as the root path, all the configuration of the web application is valid,
        WebAppContext webapp = new WebAppContext();
        webapp.setContextPath("/");
        File warFile = new File("C:/graddy/work/jetty-9.4.14.v20181114/demo-base/webapps/");
        webapp.setWar(warFile.getAbsolutePath());
        webapp.addAliasCheck(new AllowSymLinkAliasChecker());

        // set the web application to the server
        server.setHandler(webapp);

        server.start();
        server.join();
    }
}