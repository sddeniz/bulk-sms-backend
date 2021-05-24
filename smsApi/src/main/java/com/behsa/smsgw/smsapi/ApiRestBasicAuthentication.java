package com.behsa.smsgw.smsapi;

import com.sun.net.httpserver.Authenticator;
import com.sun.net.httpserver.BasicAuthenticator;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpPrincipal;

import java.net.PasswordAuthentication;

public class ApiRestBasicAuthentication extends BasicAuthenticator {

    private String username = "deniz";
    private String password = "123";


    public ApiRestBasicAuthentication(String realm) {
        super(realm);
    }

    @Override
    public Authenticator.Result authenticate(HttpExchange exch) {
        Authenticator.Result result = super.authenticate(exch);

        return result;
    }

    @Override
    public boolean checkCredentials(String user, String pwd) {
        System.out.println(user + "----" + pwd);
        return username.equals(user) && pwd.equals(password);
    }

}