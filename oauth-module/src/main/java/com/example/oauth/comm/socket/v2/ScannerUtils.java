package com.example.oauth.comm.socket.v2;

import java.util.Scanner;

/**
 * <p>
 *          该类是阻塞类，当两个线程同时执行 getConsoleMessage，只能有一个线程执行，
 *          另一个线程等待，只有进入的线程执行完毕了，等待的线程才会进入，并且是 非公平锁
 *
 *          什么是非公平锁：
 *
 *          公平锁： A  B  C 三个线程排队，第一次 A 获取到资源，那么 下一个一定是 B，以此内推
 *          非公平锁: A  B  C  三个线程，第一次 A 获取到资源，那么下一个获取资源的，由 B 和 C 竞争，不知道具体是谁能获取到资源
 *
 * </p>
 *
 * @author Zyred
 * @date 2021/4/20 21:42
 **/
public class ScannerUtils {

    /**
     * 非公平锁获取 console 输入权限
     * @param tip    提示
     * @return       输入的字符串
     */
    public static synchronized String getConsoleMessage (String tip) {
        System.out.println(tip);
        return new Scanner(System.in).next();
    }

}
