package ru.bellintegrator.practice.handbook.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.bellintegrator.practice.handbook.dao.DocDAO;
import ru.bellintegrator.practice.handbook.model.Document;
import ru.bellintegrator.practice.handbook.service.DocService;

import java.util.List;

@Service
@Scope(proxyMode = ScopedProxyMode.INTERFACES)
public class DocServiceImpl implements DocService {

    private final DocDAO dao;

    @Autowired
    public DocServiceImpl(DocDAO dao) {
        this.dao = dao;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Document> getAllDocuments() {
        return dao.all();
    }
}
