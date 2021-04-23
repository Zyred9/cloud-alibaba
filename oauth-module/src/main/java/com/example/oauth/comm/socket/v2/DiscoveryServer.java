package com.example.oauth.comm.socket.v2;

import com.alibaba.fastjson.JSON;
import lombok.SneakyThrows;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * <p>
 *      服务发现，服务注册，服务注销都将会在该类中容器内被体现
 * </p>
 * @author Zyred
 * @date 2021/4/20 21:07
 **/
public class DiscoveryServer {

    /** 文件位置 **/
    private final static String FILE_PATH = "D:\\register\\container.txt";

    /** 文件中 key val 分隔符 **/
    private final static String CONCAT_CHAR = "->";

    /** 如果在不出现意外的情况，所有启动后的服务内，该容器保存的内容是相同的 **/
    protected volatile static Map<String, ServerSocketInfo> registerServerContainer = new ConcurrentHashMap<>();

    /**
     * 每个服务启动后，都会调用该内容，进行加载其他服务到本机容器内
     */
    public static void initContainer () {
        init();
        // 创建一个线程，监听文件变化
        FileFilter filter = new FileFilter();
        new Thread(filter).start();
    }

    /**
     * 注册服务
     * @param key       服务身份
     * @param service   服务本身
     */
    public static void registerServer (String key,  ServerSocketInfo service) {
        registerServerContainer.put(key, service);
        // 写入文件
        writeFile(key, service);
        System.out.println("服务注册：" + key);
    }

    /**
     * 服务是否已经被注册进来
     *
     * @param key       服务身份
     * @return          true: 已注册  false: 未注册
     */
    public static boolean hasServer (String key) {
        return registerServerContainer.containsKey(key);
    }

    /**
     * 每次写文件的时间，先写入常量
     */
    private static void init () {
        File file = new File(FILE_PATH);

        // JDK 7 提供的一种，自动关闭文件流的写法
        try (
                FileInputStream inputStream = new FileInputStream(file);
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))
        ){

            String data = null;
            while (( data = reader.readLine()) != null) {
                // 读取文件的内容   0.0.0.0->{"key":"", "to":"", "content":""}
                String[] split = data.split(CONCAT_CHAR);
                if (!hasServer(split[0])) {
                    registerServerContainer.put(split[0], JSON.parseObject(split[1], ServerSocketInfo.class));
                    System.out.println("服务启动，发现其他服务，现注册到本机 :" + data);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 写文件，表示有服务注册时候才会进行
     *
     * @param key       服务身份
     * @param service   服务本身
     */
    private static void writeFile (String key,  ServerSocketInfo service) {
        // 准备好要写入的内容
        String content = key.concat(CONCAT_CHAR).concat(JSON.toJSONString(service)) + "\r\n";

        File file = new File(FILE_PATH);

        // 如果文件不存在，则创建
        if (!file.exists()) {
            file.mkdirs();
        }

        try (FileWriter writer = new FileWriter(FILE_PATH, true)) {
            writer.write(content);
            writer.flush();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    /**
     * 文件监听类  1000ms 运行一次 init 方法，主要是监听文件，是否发生了变化
     */
    static class FileFilter implements Runnable {

        @SneakyThrows
        @Override
        public void run() {
            while (true) {
                Thread.sleep(1000);
                init();
            }
        }
    }

}
