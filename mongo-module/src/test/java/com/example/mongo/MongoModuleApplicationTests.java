package com.example.mongo;

import com.example.mongo.dao.BookRepository;
import com.example.mongo.entity.Book;
import com.mongodb.client.result.UpdateResult;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import java.util.List;

@SpringBootTest
class MongoModuleApplicationTests {

    @Autowired private BookRepository bookRepository;

    @Autowired private MongoTemplate mongoTemplate;

    @Test
    public void save (Book book) {
        this.bookRepository.save(book);
    }

    @Test
    public void update (Book book) {
        this.bookRepository.save(book);
    }

    @Test
    public void delete (Long id) {
        this.bookRepository.deleteById(id);
    }

    @Test
    public void getById () {
        Query q = Query.query(Criteria.where("bookId").is(1));
        List<Book> books = this.mongoTemplate.find(q, Book.class);
        books.forEach(System.out::println);
    }

    @Test
    public void getPage () {
        Sort sort = Sort.by(Sort.Direction.ASC, "bookId");
        PageRequest of = PageRequest.of(0, 40, sort);
        Page<Book> all = this.bookRepository.findAll(of);
        all.get().forEach(System.out::println);
    }

    @Test
    public void pageSizeInc () {
        Query query = Query.query(Criteria.where("bookId").is(1));
        Update update = new Update();
        update.inc("pageSize");
        UpdateResult result = this.mongoTemplate.updateFirst(query, update, Book.class);
        System.out.println("更新成功条数：" + result.getModifiedCount());
    }

}
