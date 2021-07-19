package com.example.mongo.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

/**
 * <p>
 *  CompoundIndex 复合索引
 * </p>
 *
 * @author zyred
 * @since v 0.1
 **/
@Setter
@Getter
@ToString
@Accessors(chain = true)
@Document("comments")
@CompoundIndex(def = "{'author':1, 'lang':-1}")
public class Book implements Serializable {

    private static final long serialVersionUID = -2602613934576864590L;

    @Id
    private String id;

    private Long bookId;

    private String bookName;

    private String author;

    private String lang;

    private int pageSize;

    private String pubTime;

    private String detailsUrl;

    private String imageUrl;

}
