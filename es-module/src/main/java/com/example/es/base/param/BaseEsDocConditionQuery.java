package com.example.es.base.param;

import org.apache.http.HttpHost;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;

import java.io.IOException;

/**
 * <p>
 *      索引查询
 * </p>
 *
 * @author zyred
 * @since v 0.1
 **/
public class BaseEsDocConditionQuery {

    public static void main(String[] args) throws IOException {
        // 创建客户端
        RestHighLevelClient client = new RestHighLevelClient(
                RestClient.builder(new HttpHost("localhost", 9200, "http"))
        );

        SearchRequest request = new SearchRequest();
        request.indices("user");


        // 查询所有全部数据
        SearchSourceBuilder query = new SearchSourceBuilder().query(QueryBuilders.termQuery("sex", "男"));
        request.source(query);

        SearchResponse search = client.search(request, RequestOptions.DEFAULT);
        SearchHits hits = search.getHits();
        System.out.println(hits.getTotalHits());

        for (SearchHit hit : hits) {
            System.out.println("source: " + hit.getSourceAsString());
        }

        // 关闭客户端连接
        client.close();
    }

}
