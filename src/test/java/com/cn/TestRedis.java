package com.cn;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * @Author： zjr
 * @Effect:
 * @Date: 2020/11/29
 */
@SpringBootTest
public class TestRedis {

    @Autowired
    RedisTemplate redisTemplate;

    @Test
    public void t() {

        Object name = redisTemplate.opsForValue().get("name");
        System.out.println(name);
    }



}
