package com.example.oauth.comm.socket.v2;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;

/**
 * <p>
 *          创建和链接socket 的工具
 * </p>
 *
 * @author Zyred
 * @date 2021/4/20 20:55
 **/
public class SocketUtils {

    /**
     * 创建一个新的socket
     * @param ip        ip地址
     * @param port      端口号
     * @return          套接字对象
     */
    public static Socket newSocket (String ip, int port){
        try {
            return new Socket(ip, port);
        }catch (IOException ex) {
            ex.printStackTrace();
        }
        return null;
    }


    /**
     * 为 SocketServer 创建 实例
     * @param port      服务端端口
     * @return          ServerSocket
     */
    public static ServerSocket newServerSocket (int port){
        try {
            return new ServerSocket(port);
        }catch (IOException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    /**
     * 通过 key 的方式创建 socket
     * @param key       ip:port
     * @return          Socket
     */
    public static Socket newSocket (String key) {
        String[] split = key.split(JsonUtils.SPLIT_COMMAND);
        try {
            return new Socket(split[0], Integer.parseInt(split[1]));
        } catch (IOException e) {
            throw new RuntimeException("case: new socket error :" + key);
        }
    }


    /**
     * 通过 Socket 得到 key
     * @param socket        Socket
     * @return              ip:port
     */
    public static String getKey (Socket socket) {
        //socket.getLocalAddress().toString() ->  /127.0.0.1
        return socket.getLocalAddress().toString().replaceAll("/", "")
                + JsonUtils.SPLIT_COMMAND + socket.getLocalPort();
    }


}
