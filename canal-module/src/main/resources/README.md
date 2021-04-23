# springboot整合cannal

## 1、安装canal

### 1.1 docker安装canal

#### 1.1.1 docker 下载 canal

```shell
docker pull canal/canal-server
```

#### 1.1.2 docker 启动 canal

```shell
docker run -p 11111:11111 --name canal -id canal/canal-server
```

#### 1.1.3 进入 canal 容器

```shell
docker exec -it canal bash
```

#### 1.1.4 创建 canal 实例配置文件

```shell
## 进入配置文件目录
cd ./canal-server/conf/
## 创建example实例
mkdir example & cd example
## 创建配置文件
vi ./instance.properties
```

#### 1.1.5 添加配置文件内容

```properties
#################################################
## mysql serverId , v1.0.26+ will autoGen
# canal.instance.mysql.slaveId=0

# enable gtid use true/false
canal.instance.gtidon=false

# 主节点地址
canal.instance.master.address=192.168.100.130:3306
canal.instance.master.journal.name=
canal.instance.master.position=
canal.instance.master.timestamp=
canal.instance.master.gtid=

# rds oss binlog
canal.instance.rds.accesskey=
canal.instance.rds.secretkey=
canal.instance.rds.instanceId=

# table meta tsdb info
canal.instance.tsdb.enable=true

# username/password
canal.instance.dbUsername=canal
canal.instance.dbPassword=canal
canal.instance.connectionCharset = UTF-8
# 开启 Druid 数据库解密密码
canal.instance.enableDruid=false

# 监听配置
# .*  		： 表示所有数据库
# .\\.. 	: 表示所有的表
# .*\\..* 	: 所有数据库中所有表
canal.instance.filter.regex=.*\\..*
# table black regex
canal.instance.filter.black.regex=mysql\\.slave_.*

# mq config
canal.mq.topic=example
canal.mq.partition=0
#################################################
```

#### 1.1.6 重启容器

```shell
docker restart canal
```

### 1.2 宿主机安装 canal

....

## 2、Springboot 整合 canal

### 2.1 引入依赖

```xml
<spring-boot.version>2.3.7.RELEASE</spring-boot.version>
	
<dependencies>
    <dependency>
        <groupId>org.apache.commons</groupId>
        <artifactId>commons-lang3</artifactId>
        <version>3.9</version>
        <exclusions>
            <exclusion>
                <groupId>commons-logging</groupId>
                <artifactId>commons-logging</artifactId>
            </exclusion>
        </exclusions>
    </dependency>
    <dependency>
        <groupId>org.projectlombok</groupId>
        <artifactId>lombok</artifactId>
        <optional>true</optional>
    </dependency>
    <!-- canal -->
    <dependency>
        <groupId>top.javatool</groupId>
        <artifactId>canal-spring-boot-starter</artifactId>
        <version>1.2.1-RELEASE</version>
    </dependency>
</dependencies>   
```

### 2.2 配置文件

```yml
server:
  port: 8080

canal:
  # 对应 canal-server 中创建的实例
  destination: example
  server: 192.168.100.130:11111
```

### 2.3 创建用户实体类

```java
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
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
@ToString
@Table(name = "t_user")
public class User implements Serializable {

    @Id
    private Integer id;

    private String userName;

    private Integer gender;

    private Integer countryId;

    private Date birthday;

    private Date createTime;

}
```

### 2.4创建canal 监听器

```java

import com.example.ali.common.entiy.User;
import org.springframework.stereotype.Component;
import top.javatool.canal.client.annotation.CanalTable;
import top.javatool.canal.client.handler.EntryHandler;


@Component
@CanalTable(value = "t_user")
public class UserHandler implements EntryHandler<User> {

    @Override
    public void insert(User user) {
        System.out.println("insert : " + user);
    }

    @Override
    public void update(User before, User after) {
        System.out.println("before : " + before);
        System.out.println("after : " + after);
    }

    @Override
    public void delete(User user) {
        System.out.println("delete : " + user);
    }

}
```
## 3 Springboot整合Canal + RabbitMQ + Redis

### 3.1 MQ管理页面添加 Exchange 和 Queue 并进行绑定

#### 3.1.1 添加 Exchange

> name : canal_topic 
> type   : topic

#### 3.1.2 添加 Queue

> name : canal_queue 

#### 3.1.3 绑定Queue和 Exchange

> 1. 点击添加好的 canal_queue 
> 2. From exchange : canal_topic
> 3. Routing key : user.# 

### 3.2 添加 MQ 和 Redis 依赖
```xml
<!-- redis -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-redis</artifactId>
</dependency>

<!-- rabbit mq -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-amqp</artifactId>
</dependency>
```
### 3.3 添加配置文件 
```yml
server:
  port: 8080

spring:
  redis:
    host: 192.168.100.130
    port: 6379
  rabbitmq:
    host: 192.168.100.130
    port: 5672
    username: admin
    password: admin

canal:
  destination: example
  server: 192.168.100.130:11111
```
### 3.4 Redis 配置项
```java
/**
 * <p>
 *      redis 自动配置类
 * </p>
 *
 * @author zyred
 * @since v 0.1
 **/
@Configuration
public class RedisConfig {

    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(factory);
        // 普通 k v
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new StringRedisSerializer());

        // hash k v
        template.setHashKeySerializer(new StringRedisSerializer());
        template.setHashValueSerializer(new StringRedisSerializer());

        return template;
    }

}
```
### 3.5 RabbitMQ监听器
```java
/**
 * <p>
 *          监听MQ，如果canal监听到数据库变化，那么canal将会发送一条消息到MQ中
 *          由MQ发送消息
 * </p>
 *
 * @author zyred
 * @since v 0.1
 **/
@Configuration
@RabbitListener(queues = "canal_queue")
public class CanalMqListener {

    @Autowired private RedisTemplate<String, String> redisTemplate;

    @RabbitHandler
    public void updateForRedis (User user) {
        String key = String.valueOf(user.getId());
        this.redisTemplate.opsForValue().set(key, JSON.toJSONString(user));
    }

}
```
### 3. 6 Canal 监听器

```java
@Component
@CanalTable(value = "t_user")
public class UserHandler implements EntryHandler<User> {

    @Autowired private RabbitTemplate rabbitTemplate;

    private static final String exchange = "canal_topic";

    @Override
    public void insert(User user) {
        String routingKey = "user.insert";
        this.rabbitTemplate.convertAndSend(exchange, routingKey, user);
    }

    @Override
    public void update(User before, User after) {
        String routingKey = "user.update";
        this.rabbitTemplate.convertAndSend(exchange, routingKey, after);
    }

    @Override
    public void delete(User user) {
        String routingKey = "user.delete";
        this.rabbitTemplate.convertAndSend(exchange, routingKey, user);
    }

}
```

### 3.7 启动项目变动数据库内容

#### 3.7.1 修改 t_user 表中内容

```txt
将user_name（王9） -> （王8）
```

| id   | user_name | gender | country_id | birthday   | create_time         |
| ---- | --------- | ------ | ---------- | ---------- | ------------------- |
| 5    | 王9       | 1      | 1          | 2020-04-12 | 2021-04-09 03:29:33 |

| id   | user_name | gender | country_id | birthday   | create_time         |
| ---- | --------- | ------ | ---------- | ---------- | ------------------- |
| 5    | 王8       | 1      | 1          | 2020-04-12 | 2021-04-09 03:29:33 |

> Redis 中内容发生变化

```json
{
  "birthday": 1586620800000,
  "countryId": 1,
  "createTime": 1617938973000,
  "gender": 1,
  "id": 5,
  "userName": "王8"
}
```

#### 3.7.2 新增 t_user 表中内容

| id   | user_name | gender | country_id | birthday   | create_time         |
| ---- | --------- | ------ | ---------- | ---------- | ------------------- |
| 5    | 王8       | 1      | 1          | 2020-04-12 | 2021-04-09 03:29:33 |
| 6    | 李刚      | 1      | 1          | 2020-04-12 | 2021-04-10 06:43:25 |

> Redis 中内容发生变化

```json
## key = 5
{
  "birthday": 1586620800000,
  "countryId": 1,
  "createTime": 1617938973000,
  "gender": 1,
  "id": 5,
  "userName": "王8"
}

## key = 6
{
  "birthday": 1586620800000,
  "countryId": 0,
  "createTime": 1618037005000,
  "gender": 1,
  "id": 6,
  "userName": "李刚"
}
```

