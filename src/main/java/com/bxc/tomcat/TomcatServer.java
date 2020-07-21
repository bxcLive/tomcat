package com.bxc.tomcat;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Scanner;

public class TomcatServer {

    public static void main(String[] args) throws Exception {
        ServerSocket serverSocket = new ServerSocket(8888);
        TomcatScan tomcatScan = new TomcatScan();
        HashMap<String, HttpServlet> stringObjectHashMap = tomcatScan.TomcatScan("com.bxc.servlet");
        while (true) {
            Socket accept = serverSocket.accept();
            new Thread(() -> {
                try {
                    InputStream inputStream = accept.getInputStream();
                    OutputStream outputStream = accept.getOutputStream();
                    Scanner scanner = new Scanner(inputStream);
                    StringBuffer stringBuffer = new StringBuffer();
                    int i = 0;
                    while (scanner.hasNext()) {

                        stringBuffer.append(scanner.next() + "c");
                        i++;
                        if (i == 2) {
                            break;
                        }
                    }
                    String getpath = stringBuffer.toString();
                    String[] split = getpath.split("c");
                    if (stringObjectHashMap.get(split[1]) != null) {
                        if (split[0].equalsIgnoreCase("GET")) {
                            HttpServlet httpServlet = stringObjectHashMap.get(split[1]);
                            httpServlet.doGet();
                        }
                        if (split[0].equalsIgnoreCase("POST")) {
                            HttpServlet httpServlet = stringObjectHashMap.get(split[1]);
                            httpServlet.doPost();
                        }
                        //---响应
                        String body = "<html><head></head><body><h1>HelloWorld</h1></body></html>";
                        String str = "HTTP/1.1 200 OK\n" +
                                // "Location: http://www.baidu.com\n"+
                                "Date: Mon, 27 Jul 2009 12:28:53 GMT\n" +
                                "Server: Apache\n" +
                                "Last-Modified: Wed, 22 Jul 2009 19:15:56 GMT\n" +
                                "ETag: \"34aa387-d-1568eb00\"\n" +
                                "Accept-Ranges: bytes\n" +
                                "Content-Length: " + body.length() + "\n" +
                                "Vary: Accept-Encoding\n" +
                                "Content-Type: text/html\n" +
                                "\n" + body;
                        outputStream.write(str.getBytes());
                        outputStream.flush();
                    } else {
                        String str = "HTTP/1.1 404\n" +
                                // "Location: http://www.baidu.com\n"+
                                "Date: Mon, 27 Jul 2009 12:28:53 GMT\n" +
                                "Server: Apache\n" +
                                "Last-Modified: Wed, 22 Jul 2009 19:15:56 GMT\n" +
                                "ETag: \"34aa387-d-1568eb00\"\n" +
                                "Accept-Ranges: bytes\n" +
                                "Content-Length: " + "\n" +
                                "Vary: Accept-Encoding\n" +
                                "Content-Type: text/html\n";
                        outputStream.write(str.getBytes());
                        outputStream.flush();
                    }
                    inputStream.close();
                    outputStream.close();
                    scanner.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }


}
