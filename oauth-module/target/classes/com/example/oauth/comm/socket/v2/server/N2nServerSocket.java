package com.example.oauth.comm.socket.v2.server;


import com.example.oauth.comm.socket.v2.*;

import java.net.ServerSocket;

/**
 * <p>
 *          socket 相互之间通讯
 *
 * ******************************************************
 * 客户端也是服务端，A B C 三个服务，只要有一个服务注册或者是销毁
 * 那么均能发现服务。例如：A B 服务已经启动完毕，此时 C 还未启动，
 * 那么，A 和 B 服务都能相互发现，当 C 服务启动后，三个服务之间就
 * 建立好了相互通讯的通道
 * ******************************************************
 * </p>
 *
 * @author Zyred
 * @date 2021/4/20 21:01
 **/
public class N2nServerSocket implements Runnable {

    // 每次重新启动服务后，都需要修改该端口号
    private static int port = 8888;
    // 重中之重，这个 IP 地址一定要是通过 socket.getLocalAddress() 获取到的，否则会出现客户端和服务端无法通讯的问题
    private static final String address = "10.35.11.210";

    public static void main(String[] args) {
        // 这一步，是所有的服务启动前，将已经注册的服务，加载到自己的 JVM 中
        String key = address + JsonUtils.SPLIT_COMMAND + port;
        DiscoveryServer.initContainer();

        // 从注册完毕的服务中，获取最新且未被占用的端口号，如果被占用了，则 + 1
        if (DiscoveryServer.hasServer(key)) {
            port = port + 1;
            key = address + JsonUtils.SPLIT_COMMAND + port;
        }

        // 启动一个线程，来开启服务，后续的全部内容，运行在这条线程内
        new Thread(new N2nServerSocket()).start();
        // 主线程执行完毕，输出内容
        System.out.println("服务启动完毕：" + key);
    }

    @Override
    public void run() {
        init(port);
    }

    private static void init(int port) {

        ServerSocket ss = null;

        try {
            ss = SocketUtils.newServerSocket(port);
            // 获取到属于自己服务的 key（身份）
            String key = address + ":" + port;
            // 注册自己的服务到容器中
            DiscoveryServer.registerServer(key, new ServerSocketInfo(key));

            // 创建消息处理器，这也是一个线程，主要是异步处理客户端法来的消息
            new ServerSocketHandler(ss, key).start();

            // 这里使用 main 函数中启动的线程，向别的服务发送消息
            while (true) {

                String targetKey = ScannerUtils.getConsoleMessage("发送给谁:");

                // 在线校验
                if (!DiscoveryServer.hasServer(targetKey)) {
                    System.out.println("您要发送的人不在线！");
                    continue;
                }

                // 校验自己
                if (targetKey.equals(key)) {
                    System.out.println("您不能发送给您自己消息!");
                    continue;
                }

                String content = ScannerUtils.getConsoleMessage("内容:");

                SocketTransfer transfer = new SocketTransfer(key, targetKey, content);
                // 使用新的socket 发送消息
                WriteMessageUtils.write(transfer, SocketUtils.newSocket(targetKey));
            }

        }catch (Exception e) {
            e.printStackTrace();
        }

    }


}
