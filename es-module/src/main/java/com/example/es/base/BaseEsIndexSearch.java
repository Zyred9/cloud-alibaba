package com.example.es.base;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.client.indices.GetIndexResponse;

import java.io.IOException;

/**
 * <p>
 *      索引查询
 * </p>
 *
 * @author zyred
 * @since v 0.1
 **/
public class BaseEsIndexSearch {

    public static void main(String[] args) throws IOException {
        // 创建客户端
        RestHighLevelClient client = new RestHighLevelClient(
                RestClient.builder(new HttpHost("localhost", 9200, "http"))
        );

        // 创建索引
        GetIndexResponse moto = client.indices().get(new GetIndexRequest("moto"), RequestOptions.DEFAULT);
        System.out.println(moto.getAliases());
        System.out.println(moto.getMappings());
        System.out.println(moto.getSettings());

        // 关闭客户端连接
        client.close();
    }

}
