package com.example.oauth.comm.utils;

import lombok.SneakyThrows;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *          根据 Class 字节码创建 List 集合
 * </p>
 *
 * @author zyred
 * @since v 0.1
 **/
public class ListUtils {

    private final static int DEFAULT_CONTAINER_SIZE = 16;

    @SneakyThrows
    public <T> List<T> createDefaultList (Class<T> clazz, int size) {
        List<T> arr = new ArrayList<>(size);

        for (int i = 0; i < size; i++) {
            T instance = clazz.newInstance();
            arr.add(instance);
        }
        return arr;
    }

    public <T> List<T> createDefaultList (Class<T> clazz) {
        return createDefaultList(clazz, DEFAULT_CONTAINER_SIZE);
    }

}
