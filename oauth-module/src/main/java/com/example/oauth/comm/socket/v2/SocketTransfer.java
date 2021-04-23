package com.example.oauth.comm.socket.v2;

import java.io.Serializable;

/**
 * <p>
 *      socket 之间相互传输采用的对象
 * </p>
 *
 * @author Zyred
 * @date 2021/4/20 20:59
 **/
public class SocketTransfer implements Serializable {

    private static final long serialVersionUID = 7773886045081750050L;

    private String key;

    private String to;

    private String content;

    public SocketTransfer() {
    }

    public SocketTransfer(String key) {
        this.key = key;
    }

    public SocketTransfer(String key, String to, String content) {
        this.key = key;
        this.to = to;
        this.content = content;
    }


    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
