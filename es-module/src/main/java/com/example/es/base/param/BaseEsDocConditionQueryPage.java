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
import org.elasticsearch.search.sort.SortOrder;

import java.io.IOException;

/**
 * <p>
 *      索引查询
 * </p>
 *
 * @author zyred
 * @since v 0.1
 **/
public class BaseEsDocConditionQueryPage {

    public static void main(String[] args) throws IOException {
        // 创建客户端
        RestHighLevelClient client = new RestHighLevelClient(
                RestClient.builder(new HttpHost("localhost", 9200, "http"))
        );

        SearchRequest request = new SearchRequest();
        request.indices("user");


        // 查询所有全部数据
        SearchSourceBuilder query = new SearchSourceBuilder().query(QueryBuilders.matchAllQuery());
        // 分页
        query.from(1);
        query.size(3);
        // 排序
        query.sort("age", SortOrder.DESC);
        // 字段显示 excludes移除  includes包含
        String[] excludes = {"age"}, includes = {};
        query.fetchSource(includes, excludes);

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
