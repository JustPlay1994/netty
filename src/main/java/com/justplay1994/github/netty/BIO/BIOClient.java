package com.justplay1994.github.netty.BIO;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class BIOClient {
    public static void main(String[] args){
        int port = 8080;
        if(args != null && args.length > 0){
            port = Integer.valueOf(args[0]);
        }
        Socket socket = null;
        BufferedReader in = null;
        PrintWriter out = null;
        try {
            socket = new Socket("223.71.98.214",6006);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(),true);
//            out.println("$721,京ABY721,0,C01,,$");
            out.println("");
            System.out.println("Send order 2 server succeed.");
            String resp;
            while ((resp = in.readLine()) != null){
                System.out.println(resp);
            }
            System.out.println("现在是："+resp);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(out != null){
                out.close();
                out=null;
            }
            if(in !=null){
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                in = null;
            }
            if(socket != null){
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                socket = null;
            }
        }
    }
}
