package ru.bellintegrator.practice.testdata.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;
import ru.bellintegrator.practice.Application;
import ru.bellintegrator.practice.testdata.dao.HouseDAO;
import ru.bellintegrator.practice.testdata.model.House;
import ru.bellintegrator.practice.testdata.model.Person;
import ru.bellintegrator.practice.user.controller.UserController;
import ru.bellintegrator.practice.user.dao.UserDAO;
import ru.bellintegrator.practice.user.model.User;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = {Application.class})
@WebAppConfiguration(value = "src/main/resources")
@Transactional
@DirtiesContext
public class HouseDAOTest {

    @Autowired
    private UserDAO userDAO;

    @Autowired
    UserController controller;

    @Test
    public void test() {
        List<User> users = userDAO.all();
        Assert.assertNotNull(users);

        User user = new User();
        user.setLogin("string");
        user.setPassword("string");
        user.setName("string");
        user.setUserActive(false);
        user.setEmail("S@S");
        userDAO.add(user);

        users = userDAO.all();
        int length = users.size();
        Assert.assertEquals(users.get(length - 1), user);
    }

    @Test
    public void test2() {
        long start = System.currentTimeMillis();
        for (int i=0;i<25;i++) {
            controller.users();
        }
        long finish = System.currentTimeMillis() - start;
        System.out.println("Один поток" + " --- " + finish);
    }

    @Test
    public void test3() {
        Thread[] thread = new Thread[25];
        for (int i=0;i<25;i++) {
            thread[i] = new Thread(new Runnable() {
                @Override
                public void run() {
                    controller.users();
                }
            });
        }

        long start = System.currentTimeMillis();
        for (int i=0;i<25;i++) {
            thread[i].start();
        }
        long finish = System.currentTimeMillis() - start;
        System.out.println("25 потоков" + " --- " + finish);
    }
}