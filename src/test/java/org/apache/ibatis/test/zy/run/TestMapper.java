/**
 *    Copyright 2009-2019 the original author or authors.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package org.apache.ibatis.test.zy.run;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.test.zy.run.mybatis.MyBatisUtil;
import org.apache.ibatis.test.zy.run.mybatis.User;
import org.apache.ibatis.test.zy.run.mybatis.UserMapper;
import org.junit.Test;

import java.util.List;

public class TestMapper {
    static SqlSessionFactory sqlSessionFactory = null;
    static {
        sqlSessionFactory = MyBatisUtil.getSqlSessionFactory();
    }

    @Test
    public void testAdd() {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        try {
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
            User user = new User("tom", new Integer(5));
            userMapper.insertUser(user);
            sqlSession.commit();// 这里一定要提交，不然数据进不去数据库中
        } finally {

            sqlSession.close();
        }
    }

    @Test
    public void getUser() {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        try {
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
            User user = userMapper.getUser(5);
            System.out.println("name: " + user.getName() + "|age: " + user.getAge());
        } finally {
            sqlSession.close();
        }
    }
    @Test
    public void getUserByName() {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        try {
            List<User> user = sqlSession.getMapper(UserMapper.class).getUserByName("tom");
            for (User user1 : user) {
                System.out.println(user1);
            }
        } finally {
            sqlSession.close();
        }

        SqlSession sqlSession2 = sqlSessionFactory.openSession();
        try {
            List<User> user = sqlSession2.getMapper(UserMapper.class).getUserByName("tom");
            for (User user1 : user) {
                System.out.println(user1);
            }
        } finally {
            sqlSession.close();
        }

    }
}
