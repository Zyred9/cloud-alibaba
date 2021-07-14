package com.example.es.base.param;

import org.apache.http.HttpHost;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.Fuzziness;
import org.elasticsearch.index.query.FuzzyQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;

import java.io.IOException;

/**
 * <p>
 *      模糊查询
 * </p>
 *
 * @author zyred
 * @since v 0.1
 **/
public class BaseEsDocFuzzyConditionQuery {

    public static void main(String[] args) throws IOException {
        // 创建客户端
        RestHighLevelClient client = new RestHighLevelClient(
                RestClient.builder(new HttpHost("localhost", 9200, "http"))
        );

        SearchRequest request = new SearchRequest();
        request.indices("user");

        SearchSourceBuilder builder = new SearchSourceBuilder();
        FuzzyQueryBuilder fuzziness = QueryBuilders.fuzzyQuery("name", "王").fuzziness(Fuzziness.ONE);

        builder.query(fuzziness);
        request.source(builder);
        SearchResponse search = client.search(request, RequestOptions.DEFAULT);

        for (SearchHit hit : search.getHits()) {
            System.out.println(hit.getSourceAsString());
        }

        // 关闭客户端连接
        client.close();
    }

}
