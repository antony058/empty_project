package ru.bellintegrator.practice.handbook.controller.impl;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ru.bellintegrator.practice.ResponseView;
import ru.bellintegrator.practice.handbook.controller.CountryController;
import ru.bellintegrator.practice.handbook.model.Country;
import ru.bellintegrator.practice.handbook.service.CountryService;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.MediaType.APPLICATION_XML_VALUE;

@RestController
@RequestMapping(value = "/api", produces = APPLICATION_JSON_VALUE)
public class CountryControllerImpl implements CountryController {

    private final CountryService countryService;

    @Autowired
    public CountryControllerImpl(CountryService countryService) {
        this.countryService = countryService;
    }

    @Override
    @ApiOperation(value = "getCountries", nickname = "getCountries", httpMethod = "GET")
    @RequestMapping(value = "/countries", method = {RequestMethod.GET})
    public ResponseView getCountries() {
        List<Country> countries = countryService.getAllCountries();
        return new ResponseView().data(countries);
    }
}
