package ru.bellintegrator.practice.testdata.service.user.controller;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import ru.bellintegrator.practice.Application;
import ru.bellintegrator.practice.user.view.LoginUserView;
import ru.bellintegrator.practice.user.view.UserView;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class UserControllerTest {

    private TestRestTemplate restTemplate = new TestRestTemplate();
    private HttpHeaders headers = new HttpHeaders();

    private final String url = "http://127.0.0.1:8888/api/";
    private final String expectedSuccess = "{\"result\":\"success\"}";

    @Test
    public void registerUserTest() {
        UserView view = new UserView();
        view.login = "new_user";
        view.password = "password";
        view.name = "some name";
        view.email = "some@email.com";

        HttpEntity<UserView> entity = new HttpEntity<>(view, headers);

        ResponseEntity<String> responseEntity = restTemplate.exchange(
                url + "/register",
                HttpMethod.POST, entity, String.class
        );

        Assert.assertEquals(expectedSuccess, responseEntity.getBody());
    }

    @Test
    public void registerUserTest_userAlreadyExist() {
        UserView view = new UserView();
        view.login = "user_1";
        view.password = "password";
        view.name = "some name";
        view.email = "some@email.com";

        HttpEntity<UserView> entity = new HttpEntity<>(view, headers);

        ResponseEntity<String> responseEntity = restTemplate.exchange(
                url + "/register",
                HttpMethod.POST, entity, String.class
        );

        Assert.assertEquals(responseEntity.getStatusCodeValue(), 409);
    }

    @Test
    public void registerUserTest_passwordNotValidParam() {
        UserView view = new UserView();
        view.login = "new_user";
        view.password = "1";
        view.name = "some name";
        view.email = "some@email.com";

        HttpEntity<UserView> entity = new HttpEntity<>(view, headers);

        ResponseEntity<String> responseEntity = restTemplate.exchange(
                url + "/register",
                HttpMethod.POST, entity, String.class
        );

        Assert.assertEquals(responseEntity.getStatusCodeValue(), 400);
    }

    @Test
    public void registerUserTest_nameNotValidParam() {
        UserView view = new UserView();
        view.login = "new_user";
        view.password = "password";
        view.email = "some@email.com";

        HttpEntity<UserView> entity = new HttpEntity<>(view, headers);

        ResponseEntity<String> responseEntity = restTemplate.exchange(
                url + "/register",
                HttpMethod.POST, entity, String.class
        );

        Assert.assertEquals(responseEntity.getStatusCodeValue(), 400);
    }

    @Test
    public void registerUserTest_emailNotValidParam() {
        UserView view = new UserView();
        view.login = "new_user";
        view.password = "password";
        view.name = "some name";
        view.email = "email";

        HttpEntity<UserView> entity = new HttpEntity<>(view, headers);

        ResponseEntity<String> responseEntity = restTemplate.exchange(
                url + "/register",
                HttpMethod.POST, entity, String.class
        );

        Assert.assertEquals(responseEntity.getStatusCodeValue(), 400);
    }

    @Test
    public void loginUserTest() {
        LoginUserView view = new LoginUserView();
        view.setLogin("user_2");
        view.setPassword("password");

        HttpEntity<LoginUserView> entity = new HttpEntity<>(view, headers);

        ResponseEntity<String> responseEntity = restTemplate.exchange(
                url + "/login",
                HttpMethod.POST, entity, String.class
        );

        Assert.assertEquals(expectedSuccess, responseEntity.getBody());
    }

    @Test
    public void loginUserTest_loginNotValidParam() {
        LoginUserView view = new LoginUserView();
        view.setPassword("password");

        HttpEntity<LoginUserView> entity = new HttpEntity<>(view, headers);

        ResponseEntity<String> responseEntity = restTemplate.exchange(
                url + "/login",
                HttpMethod.POST, entity, String.class
        );

        Assert.assertEquals(responseEntity.getStatusCodeValue(), 400);
    }

    @Test
    public void loginUserTest_loginNotFound() {
        LoginUserView view = new LoginUserView();
        view.setLogin("user_1000000");
        view.setPassword("password");

        HttpEntity<LoginUserView> entity = new HttpEntity<>(view, headers);

        ResponseEntity<String> responseEntity = restTemplate.exchange(
                url + "/login",
                HttpMethod.POST, entity, String.class
        );

        Assert.assertEquals(responseEntity.getStatusCodeValue(), 404);
    }

    @Test
    public void loginUserTest_passwordNotValidParam() {
        LoginUserView view = new LoginUserView();
        view.setLogin("user_1");

        HttpEntity<LoginUserView> entity = new HttpEntity<>(view, headers);

        ResponseEntity<String> responseEntity = restTemplate.exchange(
                url + "/login",
                HttpMethod.POST, entity, String.class
        );

        Assert.assertEquals(responseEntity.getStatusCodeValue(), 400);
    }

    @Test
    public void loginUserTest_passwordNotFound() {
        LoginUserView view = new LoginUserView();
        view.setLogin("user_1");
        view.setPassword("incorrect_password");

        HttpEntity<LoginUserView> entity = new HttpEntity<>(view, headers);

        ResponseEntity<String> responseEntity = restTemplate.exchange(
                url + "/login",
                HttpMethod.POST, entity, String.class
        );

        Assert.assertEquals(responseEntity.getStatusCodeValue(), 404);
    }
}
