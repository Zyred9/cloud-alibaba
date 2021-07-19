package com.example.netty.basic.buffer;

import java.nio.ByteBuffer;

/**
 * <p>
 *          NIO buffer 基础使用
 * </p>
 *
 * @author zyred
 * @since v 0.1
 **/
public class BasicBuffer {

    public static void main(String[] args) {
        ByteBuffer bb = ByteBuffer.allocate(5);

        for (int i = 0; i < bb.capacity(); i++) {
            bb.put((byte) (i << 2));
        }

        // 读写反转
        bb.flip();

        while (bb.hasRemaining()) {
            System.out.println(bb.get());
        }
    }

}
