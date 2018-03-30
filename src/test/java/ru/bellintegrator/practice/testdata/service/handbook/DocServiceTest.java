package ru.bellintegrator.practice.testdata.service.handbook;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.bellintegrator.practice.handbook.dao.DocDAO;
import ru.bellintegrator.practice.handbook.model.Document;
import ru.bellintegrator.practice.handbook.service.DocService;
import ru.bellintegrator.practice.handbook.service.impl.DocServiceImpl;

import java.util.Arrays;
import java.util.Collections;

import static org.mockito.Mockito.*;

public class DocServiceTest {

    private DocService service;
    private DocDAO dao;

    @Before
    public void setUp() {
        dao = mock(DocDAO.class);
        service = new DocServiceImpl(dao);
    }

    @Test
    public void getAllDocumentsSuccessTest() {
        Document document = createDocument();
        when(dao.all()).thenReturn(Arrays.asList(document));

       Assert.assertTrue(service.getAllDocuments().contains(document));
    }

    @Test
    public void getAllDocumentsSuccessTest_emptyList() {
        when(dao.all()).thenReturn(Collections.emptyList());

        Assert.assertTrue(service.getAllDocuments().isEmpty());
    }

    @Test
    public void getAllDocumentsSuccessTest_verify() {
        service.getAllDocuments();

        verify(dao).all(); // :)
    }

    private Document createDocument() {
        Document document = new Document();
        document.setName("some_name");

        return document;
    }
}
