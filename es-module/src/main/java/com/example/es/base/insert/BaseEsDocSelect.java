package com.example.es.base.insert;

import org.apache.http.HttpHost;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;

import java.io.IOException;

/**
 * <p>
 *      局部更新
 * </p>
 *
 * @author zyred
 * @since v 0.1
 **/
public class BaseEsDocSelect {

    public static void main(String[] args) throws IOException {
        // 创建客户端
        RestHighLevelClient client = new RestHighLevelClient(
                RestClient.builder(new HttpHost("localhost", 9200, "http"))
        );

        GetRequest getRequest = new GetRequest();
        getRequest.index("user").id("1001");

        GetResponse resp = client.get(getRequest, RequestOptions.DEFAULT);
        String source = resp.getSourceAsString();
        System.out.println(source);

        // 关闭客户端连接
        client.close();
    }

}
