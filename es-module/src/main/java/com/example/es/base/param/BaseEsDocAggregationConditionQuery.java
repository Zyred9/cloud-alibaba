package com.example.es.base.param;

import org.apache.http.HttpHost;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.aggregations.metrics.AvgAggregationBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;

import java.io.IOException;

/**
 * <p>
 *      聚合查询
 * </p>
 *
 * @author zyred
 * @since v 0.1
 **/
public class BaseEsDocAggregationConditionQuery {

    public static void main(String[] args) throws IOException {
        // 创建客户端
        RestHighLevelClient client = new RestHighLevelClient(
                RestClient.builder(new HttpHost("localhost", 9200, "http"))
        );

        SearchRequest request = new SearchRequest();
        request.indices("user");

        SearchSourceBuilder builder = new SearchSourceBuilder();

        // 求平均值
        // AvgAggregationBuilder b = AggregationBuilders.avg("avg_age").field("age");
        // 分组
        TermsAggregationBuilder b = AggregationBuilders.terms("age_group").field("age");

        builder.aggregation(b);


        request.source(builder);
        SearchResponse search = client.search(request, RequestOptions.DEFAULT);

        for (SearchHit hit : search.getHits()) {
            System.out.println(hit.getSourceAsString());
        }

        // 关闭客户端连接
        client.close();
    }

}
