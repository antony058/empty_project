package ru.bellintegrator.practice.handbook.controller.impl;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ru.bellintegrator.practice.ResponseView;
import ru.bellintegrator.practice.handbook.controller.DocController;
import ru.bellintegrator.practice.handbook.service.DocService;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/api", produces = APPLICATION_JSON_VALUE)
public class DocControllerImpl implements DocController {

    private final DocService docService;

    @Autowired
    public DocControllerImpl(DocService docService) {
        this.docService = docService;
    }

    @Override
    @ApiOperation(value = "getDocs", nickname = "getDocs", httpMethod = "POST")
    @RequestMapping(value = "/docs", method = {RequestMethod.POST})
    public ResponseView getDocs() {
        return docService.getAllDocuments();
    }
}
