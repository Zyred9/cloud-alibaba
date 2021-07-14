package com.example.es;

import com.example.es.entity.Product;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.*;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class EsApplicationTests {

    @Autowired private ElasticsearchRestTemplate restTemplate;

    @Test
    void init () {
        System.out.println("spring - es init.....");
    }

    @Test
    void deleteIndex () {
        // 过期API切勿使用
        // this.restTemplate.deleteIndex(Product.class);
        this.restTemplate.indexOps(Product.class).delete();
    }

    @Test
    void insertData () {
        Product p = new Product()
                .setId(1L)
                .setTitle("小米手机")
                .setCategory("手机")
                .setPrice(2999.00)
                .setImage("www.baidu.com/img/flexible/logo/pc/result.png");
        this.restTemplate.save(p);
    }

    @Test
    void batchInsert () {

        List<Product> ps = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            ps.add(new Product()
                    .setId((long) i)
                    .setTitle("["+i+"]小米手机")
                    .setCategory("手机")
                    .setPrice(2999.00 * i)
                    .setImage("www.baidu.com/img/flexible/logo/pc/result.png"));
        }
        this.restTemplate.save(ps);
    }

    @Test
    void termQuery () {
        TermQueryBuilder builder = QueryBuilders.termQuery("title", "1");
        Query query = new NativeSearchQuery(builder);
        SearchHits<Product> hits = this.restTemplate.search(query, Product.class);
        List<SearchHit<Product>> hitList = hits.getSearchHits();
        hitList.forEach(System.out::println);
    }

    @Test
    void pageQuery () {
        int from = 0, size = 5;
        // 创建排序
        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        // 创建分页
        PageRequest request = PageRequest.of(from, size, sort);
        // 创建查询条件
        TermQueryBuilder termQuery = QueryBuilders.termQuery("category", "手机");
        // 封装为Query对象
        NativeSearchQuery query = new NativeSearchQuery(termQuery);
        // 设置分页参数
        query.setPageable(request);
        // 执行查询
        SearchHits<Product> search = this.restTemplate.search(query, Product.class);
        search.get().forEach(
                System.out::println
        );
    }

    @Test
    void highlightQuery () {

        QueryBuilder queryBuilder = QueryBuilders.matchAllQuery();
        NativeSearchQuery query = new NativeSearchQuery(queryBuilder);

        HighlightBuilder builder = new HighlightBuilder();
        builder.field("title").preTags("<font color='red'>").postTags("</font");

        HighlightQuery highlightQuery = new HighlightQuery(builder);
        query.setHighlightQuery(highlightQuery);
        SearchHits<Product> search = this.restTemplate.search(query, Product.class);
        search.get().forEach(
                System.out::println
        );
    }

    @Test
    void onlyShowTitle () {

        QueryBuilder builder = QueryBuilders.termQuery("title", "手机");
        Query query = new NativeSearchQuery(builder);

        String[] includes = {"title"}, excludes = {};
        SourceFilter filter = new FetchSourceFilter(includes, excludes);
        query.addSourceFilter(filter);

        SearchHits<Product> search = this.restTemplate.search(query, Product.class);
        search.get().forEach(
                System.out::println
        );
    }

    @Test // 范围查询
    void scopeQuery () {

        RangeQueryBuilder rangeQueryBuilder = QueryBuilders.rangeQuery("id");
        rangeQueryBuilder.lte(6).gte(1);

        NativeSearchQuery query = new NativeSearchQuery(rangeQueryBuilder);
        query.addSort(Sort.by(Sort.Direction.DESC, "id"));

        SearchHits<Product> search = this.restTemplate.search(query, Product.class);
        search.get().forEach(
                System.out::println
        );
    }
}
