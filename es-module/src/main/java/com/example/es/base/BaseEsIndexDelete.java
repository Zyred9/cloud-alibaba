package com.example.es.base;

import org.apache.http.HttpHost;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
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
public class BaseEsIndexDelete {

    public static void main(String[] args) throws IOException {
        // 创建客户端
        RestHighLevelClient client = new RestHighLevelClient(
                RestClient.builder(new HttpHost("localhost", 9200, "http"))
        );

        // 创建索引
        AcknowledgedResponse delete = client.indices().delete(
                new DeleteIndexRequest("user"),
                RequestOptions.DEFAULT
        );

        if (delete.isAcknowledged()) {
            System.out.println("删除成功");
        } else {
            System.out.println("删除失败");
        }

        // 关闭客户端连接
        client.close();
    }

}
