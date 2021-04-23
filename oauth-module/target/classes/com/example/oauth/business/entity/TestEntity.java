package com.example.oauth.business.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * <p>
 *
 * </p>
 *
 * @author zyred
 * @since v 0.1
 **/
@Setter
@Getter
@Accessors(chain = true)
public class TestEntity implements Serializable {

    private static final long serialVersionUID = -5701909972424743446L;

    private String name;

    private LocalDateTime localDateTime;

    private LocalDate localDate;

    private BigDecimal bigDecimal;

    private Date date;

    public static TestEntity getInstance () {
        return new TestEntity()
                .setBigDecimal(BigDecimal.valueOf(123.000))
                .setDate(new Date())
                .setLocalDate(LocalDate.now())
                .setLocalDateTime(LocalDateTime.now());
    }
}
