package com.example.mongo.dao;

import com.example.mongo.entity.Book;
import org.springframework.data.mongodb.repository.MongoRepository;
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
public interface BookRepository extends MongoRepository<Book, Long> {
}
