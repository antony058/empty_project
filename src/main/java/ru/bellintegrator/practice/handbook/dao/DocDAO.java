package ru.bellintegrator.practice.handbook.dao;

import javassist.NotFoundException;
import ru.bellintegrator.practice.handbook.model.Document;

import java.util.List;

public interface DocDAO {

    List<Document> all();

    Document loadByName(String name);

    Document loadByCode(Integer code);

    Document load(Integer code, String name) throws NotFoundException;
}
