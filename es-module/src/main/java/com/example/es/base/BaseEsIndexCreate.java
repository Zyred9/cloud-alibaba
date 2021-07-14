package com.example.es.base;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;

import java.io.IOException;

/**
 * <p>
 *      创建索引
 * </p>
 *
 * @author zyred
 * @since v 0.1
 **/
public class BaseEsIndexCreate {

    public static void main(String[] args) throws IOException {
        // 创建客户端
        RestHighLevelClient client = new RestHighLevelClient(
                RestClient.builder(new HttpHost("localhost", 9200, "http"))
        );

        // 创建索引
        CreateIndexResponse moto = client.indices().create(
                new CreateIndexRequest("user"),
                RequestOptions.DEFAULT
        );

        if (moto.isAcknowledged()) {
            System.out.println("响应成功!");
        } else {
            System.out.println("响应失败!");
        }

        // 关闭客户端连接
        client.close();
    }

}
