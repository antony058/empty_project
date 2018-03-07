package ru.bellintegrator.practice.service;

import ru.bellintegrator.practice.view.PersonView;

import java.util.List;

/**
 * Some service. Just for test
 */
public interface DummyService {

    /**
     *
     * @param person
     */
    void add(PersonView person);

    /**
     * Dummy service method
     * @return {@Person}
     */
    List<PersonView> persons();
}