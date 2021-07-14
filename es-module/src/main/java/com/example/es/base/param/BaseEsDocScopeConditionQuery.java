package com.example.es.base.param;

import org.apache.http.HttpHost;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;

import java.io.IOException;

/**
 * <p>
 *      范围查询
 * </p>
 *
 * @author zyred
 * @since v 0.1
 **/
public class BaseEsDocScopeConditionQuery {

    public static void main(String[] args) throws IOException {
        // 创建客户端
        RestHighLevelClient client = new RestHighLevelClient(
                RestClient.builder(new HttpHost("localhost", 9200, "http"))
        );

        SearchRequest request = new SearchRequest();
        request.indices("user");

        SearchSourceBuilder builder = new SearchSourceBuilder();
        RangeQueryBuilder range = QueryBuilders.rangeQuery("age");
        range.gte(30);
        range.lte(40);

        builder.query(range);
        request.source(builder);
        SearchResponse search = client.search(request, RequestOptions.DEFAULT);

        for (SearchHit hit : search.getHits()) {
            System.out.println(hit.getSourceAsString());
        }

        // 关闭客户端连接
        client.close();
    }

}
