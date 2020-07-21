package com.bxc.socket;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Tomcat {

    public static void main(String[] args) throws IOException {
        System.out.println("正在连接服务器...");
        Socket Socket=new Socket("192.168.61.95",8080);
        System.out.println("连接成功...");
        OutputStream outputStream=Socket.getOutputStream();
        InputStream inputStream = Socket.getInputStream();
        Scanner scanner1=new Scanner(inputStream);
        PrintStream printStream=new PrintStream(outputStream);
        Scanner scanner=new Scanner(System.in);
        while (true)
        {
            if (scanner.hasNext())
            {
                String msg=scanner.nextLine();
                printStream.println(msg);
                printStream.flush();
            }
          if (scanner1.hasNext())
          {
              System.out.println(scanner1.next());
          }
        }
    }
}
