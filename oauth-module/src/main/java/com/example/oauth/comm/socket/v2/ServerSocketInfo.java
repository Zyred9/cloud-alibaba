package com.example.oauth.comm.socket.v2;

import java.io.Serializable;

/**
 * <p>
 *      用来承载服务发现的类
 * </p>
 *
 * @author Zyred
 * @date 2021/4/20 21:26
 **/
public class ServerSocketInfo implements Serializable {

    private static final long serialVersionUID = -5244270238974732840L;

    private String ip;

    private int port;

    public ServerSocketInfo(String key) {
        String[] split = key.split(JsonUtils.SPLIT_COMMAND);
        this.ip = split[0];
        this.port = Integer.parseInt(split[1]);
    }

    public ServerSocketInfo(String ip, int port) {
        this.ip = ip;
        this.port = port;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }
}
