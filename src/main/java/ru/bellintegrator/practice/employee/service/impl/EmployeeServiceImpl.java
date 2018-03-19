package ru.bellintegrator.practice.employee.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.bellintegrator.practice.ResponseView;
import ru.bellintegrator.practice.employee.dao.EmployeeDAO;
import ru.bellintegrator.practice.employee.model.Employee;
import ru.bellintegrator.practice.employee.model.EmployeesDocument;
import ru.bellintegrator.practice.employee.service.EmployeeService;
import ru.bellintegrator.practice.employee.view.DeleteEmployeeView;
import ru.bellintegrator.practice.employee.view.EmployeeView;
import ru.bellintegrator.practice.employee.view.ListEmployeeView;
import ru.bellintegrator.practice.employee.view.UpdateEmployeeView;
import ru.bellintegrator.practice.handbook.dao.CountryDAO;
import ru.bellintegrator.practice.handbook.dao.DocDAO;
import ru.bellintegrator.practice.handbook.model.Country;
import ru.bellintegrator.practice.handbook.model.Document;
import ru.bellintegrator.practice.office.dao.OfficeDAO;
import ru.bellintegrator.practice.office.model.Office;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Scope(proxyMode = ScopedProxyMode.INTERFACES)
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeDAO dao;
    private final CountryDAO countryDAO;
    private final DocDAO docDAO;
    private final OfficeDAO officeDAO;

    @Autowired
    public EmployeeServiceImpl(EmployeeDAO dao, CountryDAO countryDAO, DocDAO docDAO, OfficeDAO officeDAO) {
        this.dao = dao;
        this.countryDAO = countryDAO;
        this.docDAO = docDAO;
        this.officeDAO = officeDAO;
    }

    @Override
    @Transactional
    public ResponseView getAllByOfficeId(ListEmployeeView view) {
        List<Employee> employees = dao.allByOfficeId(view.officeId, view.firstName, view.lastName, view.middleName,
                view.position, view.docCode, view.citizenshipCode);

        Function<Employee, EmployeeView> mapEmployee = employee -> {
            EmployeeView v = new EmployeeView();
            v.id = String.valueOf(employee.getId());
            v.firstName = employee.getFirstName();
            v.secondName = employee.getLastName();
            v.middleName = employee.getMiddleName();
            v.position = employee.getPosition();

            return v;
        };

        List<EmployeeView> employeeViews = employees.stream()
                .map(mapEmployee)
                .collect(Collectors.toList());

        return new ResponseView(employeeViews);
    }

    @Override
    @Transactional
    public ResponseView getById(String id) {
        Employee employee = dao.loadById(id);

        EmployeeView view = new EmployeeView();
        view.id = String.valueOf(employee.getId());
        view.firstName = employee.getFirstName();
        view.secondName = employee.getLastName();
        view.middleName = employee.getMiddleName();
        view.position = employee.getPosition();
        view.phone = employee.getPhone();
        view.isIdentified = employee.getIdentified();

        Country country = employee.getCountry();
        view.citizenshipCode = country.getCode();
        view.citizenshipName = country.getName();

        EmployeesDocument employeesDocument = employee.getEmployeesDocuments().iterator().next(); // может быть ошибка
        view.docName = employeesDocument.getDocument().getName();
        view.docNumber = employeesDocument.getDocNumber();
        view.docDate = employeesDocument.getDocDate();

        return new ResponseView(view);
    }

    @Override
    @Transactional
    public ResponseView update(UpdateEmployeeView view) {
        Employee employee = dao.loadById(view.id);

        if (view.firstName != null && !view.firstName.isEmpty())
            employee.setFirstName(view.firstName);

        if (view.lastName != null && !view.lastName.isEmpty())
            employee.setLastName(view.lastName);

        if (view.middleName != null && !view.middleName.isEmpty())
            employee.setMiddleName(view.middleName);

        if (view.position != null && !view.position.isEmpty())
            employee.setPosition(view.position);

        if (view.phone != null && !view.phone.isEmpty())
            employee.setPhone(view.phone);

        if (view.isIdentified != null)
            employee.setIdentified(view.isIdentified);

        if (view.citizenshipCode != null) {
            Country country = countryDAO.loadByCode(view.citizenshipCode);
            employee.setCountry(country);
        }

        boolean mustChangeDocNumber = view.docNumber != null && !view.docNumber.isEmpty();
        boolean mustChangeDocDate = view.docDate != null;
        boolean mustChangeDocName = view.docName != null && !view.docName.isEmpty();

        /*
        * Проверяем нужно ли изменять данные связанной таблицы,
        * чтобы не делать к ней запрос в пустую
        *
         */
        if (mustChangeDocName || mustChangeDocNumber || mustChangeDocDate) {
            EmployeesDocument employeesDocument = employee.getEmployeesDocuments().iterator().next();

            if (mustChangeDocNumber)
                employeesDocument.setDocNumber(view.docNumber);

            if (mustChangeDocDate)
                employeesDocument.setDocDate(view.docDate);

            if (mustChangeDocName) {
                Document document = docDAO.loadByName(view.docName);
                employeesDocument.setDocument(document);
            }
        }

        ResponseView responseView = new ResponseView();
        responseView.setSuccess("success");

        return responseView;
    }

    @Override
    @Transactional
    public ResponseView delete(DeleteEmployeeView view) {
        Employee employee = dao.loadById(view.id);

        // см. логи
        Set<Office> offices = employee.getOffices();
        for (Office office: new HashSet<>(offices)) {

            office.removeEmployee(employee);
        }

        dao.delete(employee);

        ResponseView responseView = new ResponseView();
        responseView.setSuccess("success");

        return responseView;
    }

    @Override
    @Transactional
    public ResponseView save(EmployeeView view) {
        Office office = officeDAO.loadById(view.officeId);
        if (office == null) {
            // add exception
        }

        Employee employee = new Employee();
        employee.setFirstName(view.firstName);
        employee.setLastName(view.secondName);
        employee.setMiddleName(view.middleName);
        employee.setPosition(view.position);
        employee.setPhone(view.phone);
        employee.setIdentified(view.isIdentified);

        Country country = countryDAO.loadByCode(view.citizenshipCode);
        employee.setCountry(country);

        EmployeesDocument employeesDocument = new EmployeesDocument();
        employeesDocument.setDocNumber(view.docNumber);
        employeesDocument.setDocDate(view.docDate);

        Document document = docDAO.loadByName(view.docName);
        employeesDocument.setDocument(document);

        employee.addEmployeesDocument(employeesDocument);

        office.addEmployee(employee);

        // dao.save(employee);

        ResponseView responseView = new ResponseView();
        responseView.setSuccess("success");

        return responseView;
    }
}
