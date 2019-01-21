package com.graddy.study.jetty.demo5;
import java.io.File;
import java.io.FileNotFoundException;

import org.eclipse.jetty.http.HttpVersion;
import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.HttpConfiguration;
import org.eclipse.jetty.server.HttpConnectionFactory;
import org.eclipse.jetty.server.SecureRequestCustomizer;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.server.SslConnectionFactory;
import org.eclipse.jetty.util.ssl.SslContextFactory;

import com.graddy.study.jetty.demo2.step2.HelloHandler;

/**
 * an example of Jetty with multiple connections
 */
public class ManyConnectors {
    public static void main(String[] args) throws Exception {

    	// this example shows how to configure SSL, we need a secret key library, will be found under jetty.home
        String jettyDistKeystore = "C:/graddy/work/jetty-9.4.14.v20181114/demo-base/etc/keystore";
        String keystorePath = System.getProperty("example.keystore", jettyDistKeystore);
        File keystoreFile = new File(keystorePath);
        if (!keystoreFile.exists()) {
            throw new FileNotFoundException(keystoreFile.getAbsolutePath());
        }

        // create a Server that does not specify a port, and then configure the connection and port directly
        Server server = new Server();
        
        // the HTTP configuration
        //HttpConfiguration is a collection of HTTP and HTTPS properties, and the default configuration is HTTP
        // secured UI to configure HTTPS,
        HttpConfiguration http_config = new HttpConfiguration();
        http_config.setSecureScheme("https");
        http_config.setSecurePort(8443);
        http_config.setOutputBufferSize(32768);
        
        // HTTP connections
        // the first connection you create is an HTTP connection. You can pass in the configuration information 
        // you just created, or you can reset the new configuration, such as port, timeout, etc
        ServerConnector http = new ServerConnector(server, new HttpConnectionFactory(http_config));
        http.setPort(8080);
        http.setIdleTimeout(30000);

        // use SslContextFactory to create HTTP
        //SSL requires a certificate, so we configure a factory to get what we need
        SslContextFactory sslContextFactory = new SslContextFactory();
        sslContextFactory.setKeyStorePath(keystoreFile.getAbsolutePath());
        sslContextFactory.setKeyStorePassword("OBF:1vny1zlo1x8e1vnw1vn61x8g1zlu1vn4");
        sslContextFactory.setKeyManagerPassword("OBF:1u2u1wml1z7s1z7a1wnl1u2g");

        //HTTPS configuration class
        HttpConfiguration https_config = new HttpConfiguration(http_config);
        SecureRequestCustomizer src = new SecureRequestCustomizer();
        src.setStsMaxAge(2000);
        src.setStsIncludeSubDomains(true);
        https_config.addCustomizer(src);

        // HTTPS connection
        // create a second connection,
        ServerConnector https = new ServerConnector(server,
                new SslConnectionFactory(sslContextFactory, HttpVersion.HTTP_1_1.asString()),
                new HttpConnectionFactory(https_config));
        https.setPort(8443);
        https.setIdleTimeout(500000);

        // sets a collection of connections
        server.setConnectors(new Connector[] { http, https });

        // set up a processor
        server.setHandler(new HelloHandler());

        // start the server
        server.start();
        server.join();
    }
}