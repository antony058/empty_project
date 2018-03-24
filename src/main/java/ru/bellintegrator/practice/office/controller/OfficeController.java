package ru.bellintegrator.practice.office.controller;

import javassist.NotFoundException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import ru.bellintegrator.practice.ResponseView;
import ru.bellintegrator.practice.office.view.DeleteOfficeView;
import ru.bellintegrator.practice.office.view.ListOfficeView;
import ru.bellintegrator.practice.office.view.OfficeView;
import ru.bellintegrator.practice.office.view.UpdateOfficeView;

import javax.validation.Valid;

public interface OfficeController {

    ResponseView getOffices(@Valid @RequestBody ListOfficeView view, BindingResult bindingResult);

    ResponseView getOffice(@PathVariable String officeId) throws NotFoundException;

    ResponseView updateOffice(@Valid @RequestBody UpdateOfficeView view, BindingResult bindingResult) throws NotFoundException;

    ResponseView saveOffice(@Valid @RequestBody OfficeView view, BindingResult bindingResult) throws NotFoundException;

    ResponseView deleteOffice(@Valid @RequestBody DeleteOfficeView view, BindingResult bindingResult) throws NotFoundException;
}
