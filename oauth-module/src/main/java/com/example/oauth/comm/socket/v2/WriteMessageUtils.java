package com.example.oauth.comm.socket.v2;

import com.alibaba.fastjson.JSON;

import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * <p>
 *          写消息工具，主要是写 JSON，且必须满足消息反序列化对象为 SocketTransfer
 * </p>
 *
 * @author Zyred
 * @date 2021/4/20 21:45
 **/
public class WriteMessageUtils {

    public static void write (SocketTransfer transfer, Socket socket) {
        String stringTransfer = JsonUtils.getStringTransfer(transfer);
        try {
            PrintWriter writer = new PrintWriter(socket.getOutputStream());
            writer.println(JSON.toJSONString(stringTransfer));
            writer.flush();
        }catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
