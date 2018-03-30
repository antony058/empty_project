package ru.bellintegrator.practice.testdata.service.organization.controller;

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
import ru.bellintegrator.practice.organization.view.DeleteOrganizationView;
import ru.bellintegrator.practice.organization.view.ListOrganizationView;
import ru.bellintegrator.practice.organization.view.OrganizationView;
import ru.bellintegrator.practice.organization.view.UpdateOrganizationView;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class OrganizationControllerTest {

    private TestRestTemplate restTemplate = new TestRestTemplate();
    private HttpHeaders headers = new HttpHeaders();

    private final String url = "http://127.0.0.1:8888/api";
    private final String expectedSuccess = "{\"result\":\"success\"}";

    @Test
    public void getOrganizationByIdTest() throws JSONException {
        HttpEntity<String> entity = new HttpEntity<>(null, headers);

        ResponseEntity<String> responseEntity = restTemplate.exchange(
                url + "/organization/1",
                HttpMethod.GET, entity, String.class
        );

        String expected = "{\n" +
                "  \"data\": {\n" +
                "    \"id\": 1,\n" +
                "    \"name\": \"Пятерочка\",\n" +
                "    \"fullName\": \"Торговая сеть \\\"Пятерочка\\\"\",\n" +
                "    \"inn\": \"123456789ИНН\",\n" +
                "    \"kpp\": \"123456КПП\",\n" +
                "    \"address\": \"Россия, Москва, ул.Гоголя 23Б\",\n" +
                "    \"phone\": \"+78123123456\",\n" +
                "    \"isActive\": true\n" +
                "  }\n" +
                "}";

        JSONAssert.assertEquals(expected, responseEntity.getBody(), true);
    }

    @Test
    public void getOrganizationByIdTest_notValidParam() throws JSONException {
        HttpEntity<String> entity = new HttpEntity<>(null, headers);

        ResponseEntity<String> responseEntity = restTemplate.exchange(
                url + "/organization/abc",
                HttpMethod.GET, entity, String.class
        );

        String expected = "{\"error\": \"Параметр id должен быть числовым\"}";

        JSONAssert.assertEquals(expected, responseEntity.getBody(), true);
    }

    @Test
    public void getOrganizationByIdTest_organizationNotFound() throws JSONException {
        HttpEntity<String> entity = new HttpEntity<>(null, headers);

        ResponseEntity<String> responseEntity = restTemplate.exchange(
                url + "/organization/1000000",
                HttpMethod.GET, entity, String.class
        );

        Assert.assertEquals(responseEntity.getStatusCodeValue(), 404);
    }

    @Test
    public void deleteOrganizationTest() throws JSONException {
        DeleteOrganizationView view = new DeleteOrganizationView();
        view.id = 1L;

        HttpEntity<DeleteOrganizationView> entity = new HttpEntity<>(view, headers);

        ResponseEntity<String> responseEntity = restTemplate.exchange(
                url + "/organization/delete",
                HttpMethod.POST, entity, String.class
        );

        JSONAssert.assertEquals(expectedSuccess, responseEntity.getBody(), true);
    }

    @Test
    public void deleteOrganizationTest_notFound() throws JSONException {
        DeleteOrganizationView view = new DeleteOrganizationView();
        view.id = 10000000L;

        HttpEntity<DeleteOrganizationView> entity = new HttpEntity<>(view, headers);

        ResponseEntity<String> responseEntity = restTemplate.exchange(
                url + "/organization/delete",
                HttpMethod.POST, entity, String.class
        );

        Assert.assertEquals(responseEntity.getStatusCodeValue(), 404);
    }

    @Test
    public void saveOrganizationTest() throws JSONException {
        OrganizationView view = new OrganizationView();
        view.name = "new_org";
        view.inn = "inn";
        view.isActive = true;

        HttpEntity<OrganizationView> entity = new HttpEntity<>(view, headers);

        ResponseEntity<String> responseEntity =  restTemplate.exchange(
                url + "/organization/save",
                HttpMethod.POST, entity, String.class
        );

        JSONAssert.assertEquals(expectedSuccess, responseEntity.getBody(), true);
    }

    @Test
    public void saveOrganizationTest_innNotValidParam() throws JSONException {
        OrganizationView view = new OrganizationView();
        view.name = "new_org";
        view.isActive = true;

        HttpEntity<OrganizationView> entity = new HttpEntity<>(view, headers);

        ResponseEntity<String> responseEntity =  restTemplate.exchange(
                url + "/organization/save",
                HttpMethod.POST, entity, String.class
        );

        Assert.assertEquals(responseEntity.getStatusCodeValue(), 400);
    }

    @Test
    public void saveOrganizationTest_isActiveNotValidParam() throws JSONException {
        OrganizationView view = new OrganizationView();
        view.name = "new_org";
        view.inn = "inn";

        HttpEntity<OrganizationView> entity = new HttpEntity<>(view, headers);

        ResponseEntity<String> responseEntity =  restTemplate.exchange(
                url + "/organization/save",
                HttpMethod.POST, entity, String.class
        );

        Assert.assertEquals(responseEntity.getStatusCodeValue(), 400);
    }

    @Test
    public void saveOrganizationTest_nameNotValidParam() throws JSONException {
        OrganizationView view = new OrganizationView();
        view.inn = "inn";
        view.isActive = true;

        HttpEntity<OrganizationView> entity = new HttpEntity<>(view, headers);

        ResponseEntity<String> responseEntity =  restTemplate.exchange(
                url + "/organization/save",
                HttpMethod.POST, entity, String.class
        );

        Assert.assertEquals(responseEntity.getStatusCodeValue(), 400);
    }

    @Test
    public void updateOrganizationTest() throws JSONException {
        UpdateOrganizationView view = new UpdateOrganizationView();
        view.id = 2L;
        view.name = "new organization name";

        HttpEntity<UpdateOrganizationView> entity = new HttpEntity<>(view, headers);

        ResponseEntity<String> responseEntity = restTemplate.exchange(
                url + "/organization/update",
                HttpMethod.POST, entity, String.class
        );

        JSONAssert.assertEquals(expectedSuccess, responseEntity.getBody(), true);
    }

    @Test
    public void updateOrganizationTest_idNotValidParam() throws JSONException {
        UpdateOrganizationView view = new UpdateOrganizationView();
        view.name = "new organization name";

        HttpEntity<UpdateOrganizationView> entity = new HttpEntity<>(view, headers);

        ResponseEntity<String> responseEntity = restTemplate.exchange(
                url + "/organization/update",
                HttpMethod.POST, entity, String.class
        );

        Assert.assertEquals(responseEntity.getStatusCodeValue(), 400);
    }

    @Test
    public void updateOrganizationTest_organizationNotFound() throws JSONException {
        UpdateOrganizationView view = new UpdateOrganizationView();
        view.id = 1000000L;
        view.name = "new organization name";

        HttpEntity<UpdateOrganizationView> entity = new HttpEntity<>(view, headers);

        ResponseEntity<String> responseEntity = restTemplate.exchange(
                url + "/organization/update",
                HttpMethod.POST, entity, String.class
        );

        Assert.assertEquals(responseEntity.getStatusCodeValue(), 404);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void listOrganizationTest() {
        ListOrganizationView view = new ListOrganizationView();
        view.name = "";

        HttpEntity<ListOrganizationView> entity = new HttpEntity<>(view, headers);

        ResponseEntity<ResponseView> responseEntity = restTemplate.exchange(
                url + "/organization/list",
                HttpMethod.POST, entity, ResponseView.class
        );

        List<OrganizationView> responseList = (List<OrganizationView>) responseEntity.getBody().object;
        Assert.assertTrue(responseList.size() > 0);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void listOrganizationTest_emptyList() {
        ListOrganizationView view = new ListOrganizationView();
        view.name = "not exist organization";

        HttpEntity<ListOrganizationView> entity = new HttpEntity<>(view, headers);

        ResponseEntity<ResponseView> responseEntity = restTemplate.exchange(
                url + "/organization/list",
                HttpMethod.POST, entity, ResponseView.class
        );

        List<OrganizationView> responseList = (List<OrganizationView>) responseEntity.getBody().object;
        Assert.assertTrue(responseList.isEmpty());
    }
}
