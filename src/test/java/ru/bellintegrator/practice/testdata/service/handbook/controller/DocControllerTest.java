package ru.bellintegrator.practice.testdata.service.handbook.controller;

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
import ru.bellintegrator.practice.ResponseView;
import ru.bellintegrator.practice.handbook.view.DocView;

import java.util.LinkedHashMap;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class DocControllerTest {

    private TestRestTemplate restTemplate = new TestRestTemplate();
    private HttpHeaders headers = new HttpHeaders();

    private final String url = "http://127.0.0.1:8888/api/docs";

    @Test
    @SuppressWarnings("unchecked")
    public void getAllDocsTest() {
        HttpEntity<String> entity = new HttpEntity<>(null, headers);

        ResponseEntity<ResponseView> responseEntity = restTemplate.exchange(
                url,
                HttpMethod.GET, entity, ResponseView.class
        );

        List<LinkedHashMap<String, Object>> responseList =
                (List<LinkedHashMap<String, Object>>) responseEntity.getBody().object;

        DocView respondedView = createDocView(
                responseList.get(0).get("code"),
                responseList.get(0).get("name")
        );

        Assert.assertEquals(respondedView, createDocView());
    }

    private DocView createDocView() {
        DocView view = new DocView();
        view.code = 3;
        view.name = "Свидетельство о рождении";

        return view;
    }

    private DocView createDocView(Object code, Object name) {
        DocView view = new DocView();
        view.code = (int) code;
        view.name = (String) name;

        return view;
    }
}
