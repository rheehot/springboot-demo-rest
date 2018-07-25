package com.springboot.demo;


import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisTest {


    @Resource(name="redisTemplate")
    private RedisTemplate redisTemplate;


    private final static String KEY_NAME = "users:info";

    @Before
    public void initKey(){

        redisTemplate.delete(this.KEY_NAME);
    }


    @Test
    public void testListOperationString() {

        ListOperations<String, String> listOps = redisTemplate.opsForList();

        listOps.leftPush(KEY_NAME,"willis");
        listOps.leftPush(KEY_NAME,"brown");
        listOps.leftPush(KEY_NAME, "endy");

        List<String> users = listOps.range(KEY_NAME,  0,-1);

        Assert.assertEquals(3, users.size());

        for(String user : users) {
            System.out.println(user);
        }
    }


    @Test
    public void testListOpenationObject() {

        ListOperations<String, User> listOps = redisTemplate.opsForList();

        // User 인스턴스 생성
        User user1 = new User();
        user1.setUser_name("willis");
        user1.setUser_sex("M");

        listOps.rightPush(KEY_NAME, user1);

        User user2 = new User();
        user2.setUser_name("andy");
        user2.setUser_sex("F");

        listOps.rightPush(KEY_NAME, user2);

        List<User> users = listOps.range(KEY_NAME,0,-1);

        for( User user : users) {
            System.out.println(user.getUser_name());
            System.out.println(user.getUser_sex());
        }

    }


    @Test
    public void testListQueue(){

        ListOperations<String, User> listOps = redisTemplate.opsForList();

        // User 인스턴스 생성
        User user1 = new User();
        user1.setUser_name("willis");
        user1.setUser_sex("M");

        listOps.rightPush(KEY_NAME, user1);

        User user2 = new User();
        user2.setUser_name("andy");
        user2.setUser_sex("F");

        listOps.rightPush(KEY_NAME, user2);

        // FIFO(First In First Out) left push 후 right pop
        User popUser1 = listOps.leftPop(KEY_NAME);
        Assert.assertEquals("willis", popUser1.getUser_name());

        // List Key에는 총개의 값, pop으로 하나 꺼내왔으므로 Queue에는 하나만 남아있음
        Assert.assertEquals(1, listOps.size(KEY_NAME).intValue());

    }

}