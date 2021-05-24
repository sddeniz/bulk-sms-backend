package com.behsa.smsgw.smsapi;

import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpServer;

import java.net.InetSocketAddress;


public class Main {

    public static void main(String[] args) throws Exception {
        int serverPort = 8002;
        HttpServer server = HttpServer.create(new InetSocketAddress(serverPort), 0);
        HttpContext context = server.createContext("/", new HttpServerHandler());
        context.setAuthenticator(new ApiRestBasicAuthentication("deniz"));
        server.setExecutor(null);
        server.start();
        System.out.println("server with port " + serverPort + " started.");
    }


}



