package ru.bellintegrator.practice.handbook.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.bellintegrator.practice.handbook.dao.CountryDAO;
import ru.bellintegrator.practice.handbook.model.Country;
import ru.bellintegrator.practice.handbook.service.CountryService;

import java.util.List;

@Service
@Scope(proxyMode = ScopedProxyMode.INTERFACES)
public class CountryServiceImpl implements CountryService {

    private final CountryDAO dao;

    @Autowired
    public CountryServiceImpl(CountryDAO dao) {
        this.dao = dao;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Country> getAllCountries() {
        return dao.all();
    }
}
