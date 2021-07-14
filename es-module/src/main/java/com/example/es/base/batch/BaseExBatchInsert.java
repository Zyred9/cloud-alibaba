package com.example.es.base.batch;

import com.example.es.base.insert.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpHost;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
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
public class BaseExBatchInsert {

    public static void main(String[] args) throws IOException {
        // 创建客户端
        RestHighLevelClient client = new RestHighLevelClient(
                RestClient.builder(new HttpHost("localhost", 9200, "http"))
        );

        BulkRequest request = new BulkRequest();
        IndexRequest indexRequest1 = new IndexRequest().index("user").id("1001").source(XContentType.JSON, "name", "张三", "age", 23, "sex", "男");
        IndexRequest indexRequest2 = new IndexRequest().index("user").id("1002").source(XContentType.JSON, "name", "李四", "age", 52, "sex", "男");
        IndexRequest indexRequest3 = new IndexRequest().index("user").id("1003").source(XContentType.JSON, "name", "王五", "age", 30, "sex", "男");
        IndexRequest indexRequest4 = new IndexRequest().index("user").id("1004").source(XContentType.JSON, "name", "王五1", "age", 18, "sex", "女");
        IndexRequest indexRequest5 = new IndexRequest().index("user").id("1005").source(XContentType.JSON, "name", "王五2", "age", 18, "sex", "男");
        IndexRequest indexRequest6 = new IndexRequest().index("user").id("1006").source(XContentType.JSON, "name", "王五3", "age", 18, "sex", "女");
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
