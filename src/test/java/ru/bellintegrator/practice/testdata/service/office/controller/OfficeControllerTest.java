package ru.bellintegrator.practice.testdata.service.office.controller;

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
import ru.bellintegrator.practice.office.view.DeleteOfficeView;
import ru.bellintegrator.practice.office.view.ListOfficeView;
import ru.bellintegrator.practice.office.view.OfficeView;
import ru.bellintegrator.practice.office.view.UpdateOfficeView;

import java.util.LinkedHashMap;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class OfficeControllerTest {

    private TestRestTemplate restTemplate = new TestRestTemplate();
    private HttpHeaders headers = new HttpHeaders();

    private final String url = "http://127.0.0.1:8888/api/office";
    private final String expectedSuccess = "{\"result\":\"success\"}";

    @Test
    @SuppressWarnings("unchecked")
    public void listOfficesTest() {
        ListOfficeView view = new ListOfficeView();
        view.orgId = 1L;

        HttpEntity<ListOfficeView> entity = new HttpEntity<>(view, headers);

        ResponseEntity<ResponseView> responseEntity = restTemplate.exchange(
                url + "/list",
                HttpMethod.POST, entity, ResponseView.class
        );

        List<LinkedHashMap<String, String>> responseList = (List<LinkedHashMap<String, String>>) responseEntity.getBody().object;
        Assert.assertTrue(responseList.size() > 0);
        Assert.assertEquals(responseList.get(0).get("name"), "Пятерочка Питер");
    }

    @Test
    @SuppressWarnings("unchecked")
    public void listOfficesTest_emptyList() {
        ListOfficeView view = new ListOfficeView();
        view.orgId = 1L;
        view.name = "not exists organization name";

        HttpEntity<ListOfficeView> entity = new HttpEntity<>(view, headers);

        ResponseEntity<ResponseView> responseEntity = restTemplate.exchange(
                url + "/list",
                HttpMethod.POST, entity, ResponseView.class
        );

        List<OfficeView> responseList = (List<OfficeView>) responseEntity.getBody().object;
        Assert.assertTrue(responseList.isEmpty());
    }

    @Test
    @SuppressWarnings("unchecked")
    public void listOfficesTest_orgIdNotValidParam() {
        ListOfficeView view = new ListOfficeView();

        HttpEntity<ListOfficeView> entity = new HttpEntity<>(view, headers);

        ResponseEntity<ResponseView> responseEntity = restTemplate.exchange(
                url + "/list",
                HttpMethod.POST, entity, ResponseView.class
        );

        Assert.assertEquals(responseEntity.getStatusCodeValue(), 400);
    }

    @Test
    public void getOfficeById() throws JSONException {
        HttpEntity<String> entity = new HttpEntity<>(null, headers);

        ResponseEntity<String> responseEntity = restTemplate.exchange(
                url + "/1",
                HttpMethod.GET, entity, String.class
        );

        String expected = "{\n" +
                "  \"data\": {\n" +
                "    \"id\": 1,\n" +
                "    \"name\": \"Пятерочка Питер\",\n" +
                "    \"phone\": \"+78234736215\",\n" +
                "    \"address\": \"ул. Кронштадтская 32\",\n" +
                "    \"isActive\": true\n" +
                "  }\n" +
                "}";

        JSONAssert.assertEquals(expected, responseEntity.getBody(), true);
    }

    @Test
    public void getOfficeById_idNotValidParam() throws JSONException {
        HttpEntity<String> entity = new HttpEntity<>(null, headers);

        ResponseEntity<String> responseEntity = restTemplate.exchange(
                url + "/asfaa",
                HttpMethod.GET, entity, String.class
        );

        Assert.assertEquals(responseEntity.getStatusCodeValue(), 400);
    }

    @Test
    public void getOfficeById_officeNotFound() throws JSONException {
        HttpEntity<String> entity = new HttpEntity<>(null, headers);

        ResponseEntity<String> responseEntity = restTemplate.exchange(
                url + "/1000000",
                HttpMethod.GET, entity, String.class
        );

        Assert.assertEquals(responseEntity.getStatusCodeValue(), 404);
    }

    @Test
    public void updateOfficeTest() throws JSONException {
        UpdateOfficeView view = new UpdateOfficeView();
        view.id = 2L;
        view.name = "new office name";

        HttpEntity<UpdateOfficeView> entity = new HttpEntity<>(view, headers);

        ResponseEntity<String> responseEntity = restTemplate.exchange(
                url + "/update",
                HttpMethod.POST, entity, String.class
        );

        JSONAssert.assertEquals(expectedSuccess, responseEntity.getBody(), true);
    }

    @Test
    public void updateOfficeTest_idNotValidParam() throws JSONException {
        UpdateOfficeView view = new UpdateOfficeView();
        view.name = "new office name";

        HttpEntity<UpdateOfficeView> entity = new HttpEntity<>(view, headers);

        ResponseEntity<String> responseEntity = restTemplate.exchange(
                url + "/update",
                HttpMethod.POST, entity, String.class
        );

        Assert.assertEquals(responseEntity.getStatusCodeValue(), 400);
    }

    @Test
    public void updateOfficeTest_officeNotFound() throws JSONException {
        UpdateOfficeView view = new UpdateOfficeView();
        view.id = 1000000L;
        view.name = "new office name";

        HttpEntity<UpdateOfficeView> entity = new HttpEntity<>(view, headers);

        ResponseEntity<String> responseEntity = restTemplate.exchange(
                url + "/update",
                HttpMethod.POST, entity, String.class
        );

        Assert.assertEquals(responseEntity.getStatusCodeValue(), 404);
    }

    @Test
    public void saveOfficeTest() throws JSONException {
        OfficeView view = new OfficeView();
        view.orgId = 1L;
        view.name = "new office";

        HttpEntity<OfficeView> entity = new HttpEntity<>(view, headers);

        ResponseEntity<String> responseEntity = restTemplate.exchange(
                url + "/save",
                HttpMethod.POST, entity, String.class
        );

        JSONAssert.assertEquals(expectedSuccess, responseEntity.getBody(), true);
    }

    @Test
    public void saveOfficeTest_orgIdNotValidParam() throws JSONException {
        OfficeView view = new OfficeView();
        view.name = "new office";

        HttpEntity<OfficeView> entity = new HttpEntity<>(view, headers);

        ResponseEntity<String> responseEntity = restTemplate.exchange(
                url + "/save",
                HttpMethod.POST, entity, String.class
        );

        Assert.assertEquals(responseEntity.getStatusCodeValue(), 400);
    }

    @Test
    public void deleteOfficeTest() throws JSONException {
        DeleteOfficeView view = new DeleteOfficeView();
        view.id = 3L;

        HttpEntity<DeleteOfficeView> entity = new HttpEntity<>(view, headers);

        ResponseEntity<String> responseEntity = restTemplate.exchange(
                url + "/delete",
                HttpMethod.POST, entity, String.class
        );

        JSONAssert.assertEquals(expectedSuccess, responseEntity.getBody(), true);
    }

    @Test
    public void deleteOfficeTest_idNotValidParam() throws JSONException {
        DeleteOfficeView view = new DeleteOfficeView();

        HttpEntity<DeleteOfficeView> entity = new HttpEntity<>(view, headers);

        ResponseEntity<String> responseEntity = restTemplate.exchange(
                url + "/delete",
                HttpMethod.POST, entity, String.class
        );

        Assert.assertEquals(responseEntity.getStatusCodeValue(), 400);
    }

    @Test
    public void deleteOfficeTest_idNotFound() throws JSONException {
        DeleteOfficeView view = new DeleteOfficeView();
        view.id = 1000000L;

        HttpEntity<DeleteOfficeView> entity = new HttpEntity<>(view, headers);

        ResponseEntity<String> responseEntity = restTemplate.exchange(
                url + "/delete",
                HttpMethod.POST, entity, String.class
        );

        Assert.assertEquals(responseEntity.getStatusCodeValue(), 404);
    }
}
