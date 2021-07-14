package com.example.es.dao;

import com.example.es.entity.Product;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

/**
 * <p>
 *
 * </p>
 *
 * @author zyred
 * @since v 0.1
 **/
@Repository
public interface ProductDao extends ElasticsearchRepository<Product, Long> {
}
