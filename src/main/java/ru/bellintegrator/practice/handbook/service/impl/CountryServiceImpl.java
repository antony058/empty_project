package ru.bellintegrator.practice.handbook.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.bellintegrator.practice.ResponseView;
import ru.bellintegrator.practice.handbook.dao.CountryDAO;
import ru.bellintegrator.practice.handbook.service.CountryService;

@Service
@Scope(proxyMode = ScopedProxyMode.INTERFACES)
public class CountryServiceImpl implements CountryService {

    private final CountryDAO dao;

    @Autowired
    public CountryServiceImpl(CountryDAO dao) {
        this.dao = dao;
    }

    @Override
    @Transactional
    public ResponseView getAllCountries() {
        return new ResponseView(dao.all());
    }
}
