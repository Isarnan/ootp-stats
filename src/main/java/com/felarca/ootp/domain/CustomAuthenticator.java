package com.felarca.ootp.domain;

import java.net.Authenticator;
import java.net.InetAddress;
import java.net.PasswordAuthentication;
import java.net.URL;

public class CustomAuthenticator extends Authenticator 
    {
  
        protected PasswordAuthentication getPasswordAuthentication() 
        {
  
            String prompt = getRequestingPrompt();
  
            String hostname = getRequestingHost();
  
            InetAddress ipaddr = getRequestingSite();
  
            int port = getRequestingPort();
  
            String protocol = getRequestingProtocol();
  
            String scheme = getRequestingScheme();
  
            URL u = getRequestingURL();
  
            RequestorType rtype = getRequestorType();
  
            System.out.println("prompt:" + prompt);
            System.out.println("hostname:" + hostname);
            System.out.println("ipaddress:" + ipaddr);
            System.out.println("port:" + port);
            System.out.println("protocolo:" + protocol);
            System.out.println("scheme:" + scheme);
            System.out.println("URL:" + u);
            System.out.println("Requester Type:" + rtype);
  
            String username = "isarnan@felarca.com"; //hardcoded user
            String password = "u$!mq%J*gc29"; //hardcoded pwd
  
            return new PasswordAuthentication(username, password.toCharArray());
  
        }
  
    }