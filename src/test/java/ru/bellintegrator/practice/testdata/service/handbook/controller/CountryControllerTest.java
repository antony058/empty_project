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
import ru.bellintegrator.practice.handbook.view.CountryView;

import java.util.LinkedHashMap;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class CountryControllerTest {

    private TestRestTemplate restTemplate = new TestRestTemplate();
    private HttpHeaders headers = new HttpHeaders();

    private final String url = "http://127.0.0.1:8888/api/countries";

    @Test
    @SuppressWarnings("unchecked")
    public void getAllCountriesTest() {
        HttpEntity<String> entity = new HttpEntity<>(null, headers);

        ResponseEntity<ResponseView> responseEntity = restTemplate.exchange(
                url,
                HttpMethod.GET, entity, ResponseView.class
        );

        List<LinkedHashMap<String, Object>> responseList =
                (List<LinkedHashMap<String, Object>>) responseEntity.getBody().object;

        CountryView respondedView = createCountry(
                responseList.get(0).get("code"),
                responseList.get(0).get("name")
        );

        Assert.assertEquals(respondedView, createCountry());
    }

    private CountryView createCountry() {
        CountryView view = new CountryView();
        view.code = 8;
        view.name = "Республика Албания";

        return view;
    }

    private CountryView createCountry(Object code, Object name) {
        CountryView view = new CountryView();
        view.code = (int) code;
        view.name = (String) name;

        return view;
    }
}
