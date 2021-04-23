package com.example.oauth.comm.socket.v2.server;


import com.alibaba.fastjson.JSON;
import com.example.oauth.comm.socket.v2.ScannerUtils;
import com.example.oauth.comm.socket.v2.SocketTransfer;
import com.example.oauth.comm.socket.v2.SocketUtils;
import com.example.oauth.comm.socket.v2.WriteMessageUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * <p>
 *      消息处理器，这里是运行在新的一个线程，切记
 *      如果该类发生了异常，那么这个类所运行的线程
 *      也结束了，但是服务并未停止，因为 main 函数
 *      中创建的线程依然在运行
 * </p>
 *
 * @author Zyred
 * @date 2021/4/20 21:36
 **/
public class ServerSocketHandler extends Thread{

    /** 校验用户是否需要回复客户端法来的消息，如果输入0，则需要回复 **/
    private static final String REPLY = "0";

    private ServerSocket ss;
    private String key;

    public ServerSocketHandler(ServerSocket ss, String key) {
        this.ss = ss;
        this.key = key;
    }

    @Override
    public void run() {

        try {
            while (true) {
                // 这里因为是另一个线程，那么这个线程阻塞了，并不会影响 main 函数中线程的运行
                // 所以，通过新线程来等待客户端连接，新客户端连接后，会进入此逻辑
                Socket accept = ss.accept();
                InputStreamReader isr = new InputStreamReader(accept.getInputStream());
                BufferedReader br = new BufferedReader(isr);
                // 读取出来的 ""{\"key\":\"\", \"to\":\"\", \"content\":\"\"}""
                String msg = br.readLine();

                // 去掉第一个 "， 变成 "{\"key\":\"\", \"to\":\"\", \"content\":\"\"}""
                msg = msg.replaceFirst("\"", "");
                // 反转：变成 ""{\"\":\"content\", \"\":\"to\", \"\":\"key\"}"
                // 再去掉第一个 ", 变成 "{\"\":\"content\", \"\":\"to\", \"\":\"key\"}"
                msg = new StringBuffer(msg).reverse().toString().replaceFirst("\"", "");
                // 最后再反转回来  变成 "{\"key\":\"\", \"to\":\"\", \"content\":\"\"}"
                msg = new StringBuffer(msg).reverse().toString();

                // 去掉 \ 变成 "{"key":"", "to":"", "content":""}", 这样的格式才能被 FastJson 解析
                msg = msg.replaceAll("\\\\", "");

                SocketTransfer socketTransfer = JSON.parseObject(msg, SocketTransfer.class);

                System.out.println("收到[" + SocketUtils.getKey(accept) + "]消息: " + socketTransfer.getContent());

                // 如果输入 0 那么，需要执行回复操作
                String reply = ScannerUtils.getConsoleMessage("回复(0), 不回复(其他)：");

                // 执行回复
                if (REPLY.equals(reply)) {
                    String content = ScannerUtils.getConsoleMessage("请输入回复内容：");

                    SocketTransfer transfer = new SocketTransfer();

                    // 从得到的消息中，解析出发送者是谁
                    transfer.setTo(socketTransfer.getKey());
                    transfer.setContent(content);
                    transfer.setKey(this.key);

                    // 创建新 socket 发送消息
                    WriteMessageUtils.write(transfer, SocketUtils.newSocket(socketTransfer.getKey()));
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
