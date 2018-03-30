package ru.bellintegrator.practice.testdata.service.employee.controller;

import org.json.JSONException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import ru.bellintegrator.practice.Application;
import ru.bellintegrator.practice.ResponseView;
import ru.bellintegrator.practice.employee.view.DeleteEmployeeView;
import ru.bellintegrator.practice.employee.view.EmployeeView;
import ru.bellintegrator.practice.employee.view.ListEmployeeView;
import ru.bellintegrator.practice.employee.view.UpdateEmployeeView;

import java.sql.Date;
import java.util.LinkedHashMap;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class EmployeeControllerTest {

    private TestRestTemplate restTemplate = new TestRestTemplate();
    private HttpHeaders headers = new HttpHeaders();

    private final String url = "http://127.0.0.1:8888/api/user";
    private final String expectedSuccess = "{\"result\":\"success\"}";

    @Test
    @SuppressWarnings("unchecked")
    public void listEmployeeTest() {
        ListEmployeeView view = new ListEmployeeView();
        view.officeId = 1L;

        HttpEntity<ListEmployeeView> entity = new HttpEntity<>(view, headers);

        ResponseEntity<ResponseView> responseEntity = restTemplate.exchange(
                url + "/list",
                HttpMethod.POST, entity, ResponseView.class
        );

        List<LinkedHashMap<String, String>> viewList = (List<LinkedHashMap<String, String>>) responseEntity.getBody().object;
        Assert.assertTrue(viewList.size() > 0);
        Assert.assertEquals(viewList.get(0).get("firstName"), "Иван");
    }

    @Test
    @SuppressWarnings("unchecked")
    public void listEmployeeTest_emptyList() {
        ListEmployeeView view = new ListEmployeeView();
        view.officeId = 1L;
        view.firstName = "not exists name";

        HttpEntity<ListEmployeeView> entity = new HttpEntity<>(view, headers);

        ResponseEntity<ResponseView> responseEntity = restTemplate.exchange(
                url + "/list",
                HttpMethod.POST, entity, ResponseView.class
        );

        List<ResponseView> viewList = (List<ResponseView>) responseEntity.getBody().object;
        Assert.assertTrue(viewList.isEmpty());
    }

    @Test
    public void listEmployeeTest_officeIdNotValidParam() {
        ListEmployeeView view = new ListEmployeeView();

        HttpEntity<ListEmployeeView> entity = new HttpEntity<>(view, headers);

        ResponseEntity<ResponseView> responseEntity = restTemplate.exchange(
                url + "/list",
                HttpMethod.POST, entity, ResponseView.class
        );

        Assert.assertEquals(responseEntity.getStatusCodeValue(), 400);
    }

    @Test
    public void getEmployeeByIdTest() throws JSONException {
        HttpEntity<String> entity = new HttpEntity<>(null, headers);

        ResponseEntity<String> responseEntity = restTemplate.exchange(
                url + "/1",
                HttpMethod.GET, entity, String.class
        );

        String expected = "{\n" +
                "  \"data\": {\n" +
                "    \"id\": 1,\n" +
                "    \"firstName\": \"Иван\",\n" +
                "    \"lastName\": \"Иванов\",\n" +
                "    \"position\": \"Админ\",\n" +
                "    \"phone\": \"+78234731111\",\n" +
                "    \"docName\": \"Паспорт гражданина Российской Федерации\",\n" +
                "    \"docNumber\": \"DOC1234\",\n" +
                "    \"docDate\": \"2020-12-10\",\n" +
                "    \"citizenshipName\": \"Российская Федерация\",\n" +
                "    \"citizenshipCode\": 643,\n" +
                "    \"isIdentified\": true\n" +
                "  }\n" +
                "}";

        JSONAssert.assertEquals(expected, responseEntity.getBody(), true);
    }

    @Test
    public void getEmployeeByIdTest_notValidParam() throws JSONException {
        HttpEntity<String> entity = new HttpEntity<>(null, headers);

        ResponseEntity<String> responseEntity = restTemplate.exchange(
                url + "/asdcb",
                HttpMethod.GET, entity, String.class
        );

        Assert.assertEquals(responseEntity.getStatusCodeValue(), 400);
    }

    @Test
    public void getEmployeeByIdTest_EmployeeNotFound() throws JSONException {
        HttpEntity<String> entity = new HttpEntity<>(null, headers);

        ResponseEntity<String> responseEntity = restTemplate.exchange(
                url + "/1000000",
                HttpMethod.GET, entity, String.class
        );

        Assert.assertEquals(responseEntity.getStatusCodeValue(), 404);
    }

    @Test
    public void updateEmployeeTest() throws JSONException {
        UpdateEmployeeView view = new UpdateEmployeeView();
        view.id = 2L;
        view.firstName = "new employee firstName";

        HttpEntity<UpdateEmployeeView> entity = new HttpEntity<>(view, headers);

        ResponseEntity<String> responseEntity = restTemplate.exchange(
                url + "/update",
                HttpMethod.POST, entity, String.class
        );

        Assert.assertEquals(expectedSuccess, responseEntity.getBody());
    }

    @Test
    public void updateEmployeeTest_idNotValidParam() throws JSONException {
        UpdateEmployeeView view = new UpdateEmployeeView();
        view.firstName = "new employee firstName";

        HttpEntity<UpdateEmployeeView> entity = new HttpEntity<>(view, headers);

        ResponseEntity<String> responseEntity = restTemplate.exchange(
                url + "/update",
                HttpMethod.POST, entity, String.class
        );

        Assert.assertEquals(responseEntity.getStatusCodeValue(), 400);
    }

    @Test
    public void updateEmployeeTest_EmployeeNotFound() throws JSONException {
        UpdateEmployeeView view = new UpdateEmployeeView();
        view.id = 1000000L;
        view.firstName = "new employee firstName";

        HttpEntity<UpdateEmployeeView> entity = new HttpEntity<>(view, headers);

        ResponseEntity<String> responseEntity = restTemplate.exchange(
                url + "/update",
                HttpMethod.POST, entity, String.class
        );

        Assert.assertEquals(responseEntity.getStatusCodeValue(), 404);
    }

    @Test
    public void saveEmployeeTest() {
        EmployeeView view = new EmployeeView();
        view.firstName = "name of new employee";
        view.officeId = 1L;
        view.docCode = 21;
        view.docName = "Паспорт гражданина Российской Федерации";
        view.docDate = new Date(1);
        view.docNumber = "1234NUM";
        view.citizenshipCode = 643;
        view.citizenshipName = "Российская Федерация";

        HttpEntity<EmployeeView> entity = new HttpEntity<>(view, headers);

        ResponseEntity<String> responseEntity = restTemplate.exchange(
                url + "/save",
                HttpMethod.POST, entity, String.class
        );

        Assert.assertEquals(expectedSuccess, responseEntity.getBody());
    }


    @Test
    public void saveEmployeeTest_officeIdNotValidParam() {
        EmployeeView view = new EmployeeView();
        view.firstName = "name of new employee";
        view.docCode = 21;
        view.docName = "Паспорт гражданина Российской Федерации";
        view.docDate = new Date(1);
        view.docNumber = "1234NUM";
        view.citizenshipCode = 643;
        view.citizenshipName = "Российская Федерация";

        HttpEntity<EmployeeView> entity = new HttpEntity<>(view, headers);

        ResponseEntity<String> responseEntity = restTemplate.exchange(
                url + "/save",
                HttpMethod.POST, entity, String.class
        );

        Assert.assertEquals(responseEntity.getStatusCodeValue(), 400);
    }

    @Test
    public void saveEmployeeTest_officeIdNotFound() {
        EmployeeView view = new EmployeeView();
        view.firstName = "name of new employee";
        view.officeId = 1000000L;
        view.docCode = 21;
        view.docName = "Паспорт гражданина Российской Федерации";
        view.docDate = new Date(1);
        view.docNumber = "1234NUM";
        view.citizenshipCode = 643;
        view.citizenshipName = "Российская Федерация";

        HttpEntity<EmployeeView> entity = new HttpEntity<>(view, headers);

        ResponseEntity<String> responseEntity = restTemplate.exchange(
                url + "/save",
                HttpMethod.POST, entity, String.class
        );

        Assert.assertEquals(responseEntity.getStatusCodeValue(), 404);
    }

    @Test
    public void saveEmployeeTest_firstNameNotValidParam() {
        EmployeeView view = new EmployeeView();
        view.officeId = 1L;
        view.docCode = 21;
        view.docName = "Паспорт гражданина Российской Федерации";
        view.docDate = new Date(1);
        view.docNumber = "1234NUM";
        view.citizenshipCode = 643;
        view.citizenshipName = "Российская Федерация";

        HttpEntity<EmployeeView> entity = new HttpEntity<>(view, headers);

        ResponseEntity<String> responseEntity = restTemplate.exchange(
                url + "/save",
                HttpMethod.POST, entity, String.class
        );

        Assert.assertEquals(responseEntity.getStatusCodeValue(), 400);
    }

    @Test
    public void saveEmployeeTest_docCodeNotValidParam() {
        EmployeeView view = new EmployeeView();
        view.officeId = 1L;
        view.firstName = "name of new employee";
        view.docName = "Паспорт гражданина Российской Федерации";
        view.docDate = new Date(1);
        view.docNumber = "1234NUM";
        view.citizenshipCode = 643;
        view.citizenshipName = "Российская Федерация";

        HttpEntity<EmployeeView> entity = new HttpEntity<>(view, headers);

        ResponseEntity<String> responseEntity = restTemplate.exchange(
                url + "/save",
                HttpMethod.POST, entity, String.class
        );

        Assert.assertEquals(responseEntity.getStatusCodeValue(), 400);
    }

    @Test
    public void saveEmployeeTest_docNameNotValidParam() {
        EmployeeView view = new EmployeeView();
        view.firstName = "name of new employee";
        view.officeId = 1L;
        view.docCode = 21;
        view.docDate = new Date(1);
        view.docNumber = "1234NUM";
        view.citizenshipCode = 643;
        view.citizenshipName = "Российская Федерация";

        HttpEntity<EmployeeView> entity = new HttpEntity<>(view, headers);

        ResponseEntity<String> responseEntity = restTemplate.exchange(
                url + "/save",
                HttpMethod.POST, entity, String.class
        );

        Assert.assertEquals(responseEntity.getStatusCodeValue(), 400);
    }

    @Test
    public void saveEmployeeTest_docDateNotValidParam() {
        EmployeeView view = new EmployeeView();
        view.firstName = "name of new employee";
        view.officeId = 1L;
        view.docCode = 21;
        view.docName = "Паспорт гражданина Российской Федерации";
        view.docNumber = "1234NUM";
        view.citizenshipCode = 643;
        view.citizenshipName = "Российская Федерация";

        HttpEntity<EmployeeView> entity = new HttpEntity<>(view, headers);

        ResponseEntity<String> responseEntity = restTemplate.exchange(
                url + "/save",
                HttpMethod.POST, entity, String.class
        );

        Assert.assertEquals(responseEntity.getStatusCodeValue(), 400);
    }

    @Test
    public void saveEmployeeTest_docNumberNotValidParam() {
        EmployeeView view = new EmployeeView();
        view.firstName = "name of new employee";
        view.officeId = 1L;
        view.docCode = 21;
        view.docName = "Паспорт гражданина Российской Федерации";
        view.docDate = new Date(1);
        view.citizenshipCode = 643;
        view.citizenshipName = "Российская Федерация";

        HttpEntity<EmployeeView> entity = new HttpEntity<>(view, headers);

        ResponseEntity<String> responseEntity = restTemplate.exchange(
                url + "/save",
                HttpMethod.POST, entity, String.class
        );

        Assert.assertEquals(responseEntity.getStatusCodeValue(), 400);
    }

    @Test
    public void saveEmployeeTest_documentNotFound() {
        EmployeeView view = new EmployeeView();
        view.firstName = "name of new employee";
        view.officeId = 1L;
        view.docCode = 21000;
        view.docName = "Паспорт гражданина Российской Федерации";
        view.docDate = new Date(1);
        view.docNumber = "1234NUM";
        view.citizenshipCode = 643;
        view.citizenshipName = "Российская Федерация";

        HttpEntity<EmployeeView> entity = new HttpEntity<>(view, headers);

        ResponseEntity<String> responseEntity = restTemplate.exchange(
                url + "/save",
                HttpMethod.POST, entity, String.class
        );

        Assert.assertEquals(responseEntity.getStatusCodeValue(), 404);
    }

    @Test
    public void saveEmployeeTest_citizenshipCodeNotValidParam() {
        EmployeeView view = new EmployeeView();
        view.firstName = "name of new employee";
        view.officeId = 1L;
        view.docCode = 21;
        view.docName = "Паспорт гражданина Российской Федерации";
        view.docDate = new Date(1);
        view.docNumber = "1234NUM";
        view.citizenshipName = "Российская Федерация";

        HttpEntity<EmployeeView> entity = new HttpEntity<>(view, headers);

        ResponseEntity<String> responseEntity = restTemplate.exchange(
                url + "/save",
                HttpMethod.POST, entity, String.class
        );

        Assert.assertEquals(responseEntity.getStatusCodeValue(), 400);
    }

    @Test
    public void saveEmployeeTest_citizenshipNameNotValidParam() {
        EmployeeView view = new EmployeeView();
        view.firstName = "name of new employee";
        view.officeId = 1L;
        view.docCode = 21;
        view.docName = "Паспорт гражданина Российской Федерации";
        view.docDate = new Date(1);
        view.docNumber = "1234NUM";
        view.citizenshipCode = 643;

        HttpEntity<EmployeeView> entity = new HttpEntity<>(view, headers);

        ResponseEntity<String> responseEntity = restTemplate.exchange(
                url + "/save",
                HttpMethod.POST, entity, String.class
        );

        Assert.assertEquals(responseEntity.getStatusCodeValue(), 400);
    }

    @Test
    public void saveEmployeeTest_citizenshipNotFound() {
        EmployeeView view = new EmployeeView();
        view.firstName = "name of new employee";
        view.officeId = 1L;
        view.docCode = 21;
        view.docName = "Паспорт гражданина Российской Федерации";
        view.docDate = new Date(1);
        view.docNumber = "1234NUM";
        view.citizenshipCode = 643000;
        view.citizenshipName = "Российская Федерация";

        HttpEntity<EmployeeView> entity = new HttpEntity<>(view, headers);

        ResponseEntity<String> responseEntity = restTemplate.exchange(
                url + "/save",
                HttpMethod.POST, entity, String.class
        );

        Assert.assertEquals(responseEntity.getStatusCodeValue(), 404);
    }

    @Test
    public void deleteEmployeeTest() {
        DeleteEmployeeView view = new DeleteEmployeeView();
        view.id = 3L;

        HttpEntity<DeleteEmployeeView> entity = new HttpEntity<>(view, headers);

        ResponseEntity<String> responseEntity = restTemplate.exchange(
                url + "/delete",
                HttpMethod.POST, entity, String.class
        );

        Assert.assertEquals(expectedSuccess, responseEntity.getBody());
    }

    @Test
    public void deleteEmployeeTest_idNotValidParam() {
        DeleteEmployeeView view = new DeleteEmployeeView();

        HttpEntity<DeleteEmployeeView> entity = new HttpEntity<>(view, headers);

        ResponseEntity<String> responseEntity = restTemplate.exchange(
                url + "/delete",
                HttpMethod.POST, entity, String.class
        );

        Assert.assertEquals(responseEntity.getStatusCodeValue(), 400);
    }

    @Test
    public void deleteEmployeeTest_idNotFound() {
        DeleteEmployeeView view = new DeleteEmployeeView();
        view.id = 1000000L;

        HttpEntity<DeleteEmployeeView> entity = new HttpEntity<>(view, headers);

        ResponseEntity<String> responseEntity = restTemplate.exchange(
                url + "/delete",
                HttpMethod.POST, entity, String.class
        );

        Assert.assertEquals(responseEntity.getStatusCodeValue(), 404);
    }
}
