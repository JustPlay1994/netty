package com.justplay1994.github.netty.BIO;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class BIOServer {

    public BIOServer(){
        int port = 8080;
//        BufferedReader strin=new BufferedReader(new InputStreamReader(System.in));
//        System.out.print("请输入端口：");
        String str="8080";

//        try {
//            str = strin.readLine();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        if(str != null && str.length() > 0){
            port = Integer.valueOf(str);
        }
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(port);
            System.out.println("本次服务器的启动端口为："+port);
            Socket socket = null;
            BIOServerHandlerExecutePool serverHandlerExecutePool = new BIOServerHandlerExecutePool(50,10000);
            while(true){
                socket = serverSocket.accept();
//                new Thread(new BIOServerHandler(socket)).start();
                serverHandlerExecutePool.execute(new BIOServerHandler(socket));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(serverSocket !=null){
                System.out.println("本次服务已关闭");
                try {
                    serverSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                serverSocket = null;
            }
        }
    }

    public static void main(String[] args) throws IOException{
        BIOServer bioServer = new BIOServer();
    }
}
