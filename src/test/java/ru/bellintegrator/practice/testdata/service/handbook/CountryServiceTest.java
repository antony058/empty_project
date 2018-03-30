package ru.bellintegrator.practice.testdata.service.handbook;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.bellintegrator.practice.handbook.dao.CountryDAO;
import ru.bellintegrator.practice.handbook.model.Country;
import ru.bellintegrator.practice.handbook.service.CountryService;
import ru.bellintegrator.practice.handbook.service.impl.CountryServiceImpl;

import java.util.Arrays;
import java.util.Collections;

import static org.mockito.Mockito.*;

public class CountryServiceTest {

    private CountryService service;
    private CountryDAO dao;

    @Before
    public void setUp() {
        dao = mock(CountryDAO.class);
        service = new CountryServiceImpl(dao);
    }

    @Test
    public void getAllCountriesSuccessTest() {
        Country country = createCountry();
        when(dao.all()).thenReturn(Arrays.asList(country));

        Assert.assertTrue(service.getAllCountries().contains(country));
    }

    @Test
    public void getAllCountriesSuccessTest_emptyList() {
        when(dao.all()).thenReturn(Collections.emptyList());

        Assert.assertTrue(service.getAllCountries().isEmpty());
    }

    @Test
    public void getAllCountriesSuccessTest_verify() {
        service.getAllCountries();

        verify(dao).all();
    }

    private Country createCountry() {
        Country country = new Country();
        country.setName("some_name");

        return country;
    }
}
