package com.example.es.base.batch;

import org.apache.http.HttpHost;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;

import java.io.IOException;
import java.util.Arrays;

/**
 * <p>
 *
 * </p>
 *
 * @author zyred
 * @since v 0.1
 **/
public class BaseExBatchDelete {

    public static void main(String[] args) throws IOException {
        // 创建客户端
        RestHighLevelClient client = new RestHighLevelClient(
                RestClient.builder(new HttpHost("localhost", 9200, "http"))
        );

        BulkRequest request = new BulkRequest();
        DeleteRequest indexRequest1 = new DeleteRequest().index("user").id("1001");
        DeleteRequest indexRequest2 = new DeleteRequest().index("user").id("1002");
        DeleteRequest indexRequest3 = new DeleteRequest().index("user").id("1003");
        DeleteRequest indexRequest4 = new DeleteRequest().index("user").id("1004");
        DeleteRequest indexRequest5 = new DeleteRequest().index("user").id("1005");
        DeleteRequest indexRequest6 = new DeleteRequest().index("user").id("1006");
        request.add(indexRequest1);
        request.add(indexRequest2);
        request.add(indexRequest3);
        request.add(indexRequest4);
        request.add(indexRequest5);
        request.add(indexRequest6);

        BulkResponse bulk = client.bulk(request, RequestOptions.DEFAULT);
        System.out.println(bulk.getTook());
        System.out.println(Arrays.toString(bulk.getItems()));

        // 关闭客户端连接
        client.close();
    }
}
