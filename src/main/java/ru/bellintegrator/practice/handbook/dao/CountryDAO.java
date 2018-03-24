package ru.bellintegrator.practice.handbook.dao;

import javassist.NotFoundException;
import ru.bellintegrator.practice.handbook.model.Country;

import java.util.List;

public interface CountryDAO {

    List<Country> all();

    Country loadByCode(Integer code);

    Country load(Integer code, String name) throws NotFoundException;
}
