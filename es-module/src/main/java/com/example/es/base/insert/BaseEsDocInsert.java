package com.example.es.base.insert;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpHost;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.client.indices.GetIndexResponse;
import org.elasticsearch.common.xcontent.XContentType;

import java.io.IOException;

/**
 * <p>
 *      索引查询
 * </p>
 *
 * @author zyred
 * @since v 0.1
 **/
public class BaseEsDocInsert {

    public static void main(String[] args) throws IOException {
        // 创建客户端
        RestHighLevelClient client = new RestHighLevelClient(
                RestClient.builder(new HttpHost("localhost", 9200, "http"))
        );

        // 插入数据
        IndexRequest user = new IndexRequest("user");
        user.index("user").id("1001");

        User doc = new User();
        doc.setName("zhangsan");
        doc.setAge(18);
        doc.setSex("男的");

        ObjectMapper mapper = new ObjectMapper();
        String jsonDoc = mapper.writeValueAsString(doc);

        user.source(jsonDoc, XContentType.JSON);

        IndexResponse response = client.index(user, RequestOptions.DEFAULT);

        System.out.println(response.getResult());

        // 关闭客户端连接
        client.close();
    }

}
