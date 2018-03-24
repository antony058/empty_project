package ru.bellintegrator.practice.employee.service.impl;

import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
import java.util.List;
import java.util.Set;
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
    @Transactional(readOnly = true)
    public List<EmployeeView> getAllByOfficeId(ListEmployeeView view) {
        List<Employee> employees = dao.allByOfficeId(view.officeId, view.firstName, view.lastName, view.middleName,
                view.position, view.docCode, view.citizenshipCode);

        Function<Employee, EmployeeView> mapEmployee = employee -> {
            EmployeeView v = new EmployeeView();
            v.id = employee.getId();
            v.firstName = employee.getFirstName();
            v.secondName = employee.getLastName();
            v.middleName = employee.getMiddleName();
            v.position = employee.getPosition();

            return v;
        };

        List<EmployeeView> employeeViews = employees.stream()
                .map(mapEmployee)
                .collect(Collectors.toList());

        return employeeViews;
    }

    @Override
    @Transactional(readOnly = true)
    public EmployeeView getById(String id) throws NotFoundException {
        Employee employee = dao.loadById(Long.valueOf(id));

        EmployeeView view = new EmployeeView();
        view.id = employee.getId();
        view.firstName = employee.getFirstName();
        view.secondName = employee.getLastName();
        view.middleName = employee.getMiddleName();
        view.position = employee.getPosition();
        view.phone = employee.getPhone();
        view.isIdentified = employee.getIdentified();

        Country country = employee.getCountry();
        if (country != null) {
            view.citizenshipCode = country.getCode();
            view.citizenshipName = country.getName();
        }

        Set<EmployeesDocument> employeesDocumentSet = employee.getEmployeesDocuments();
        if (!employeesDocumentSet.isEmpty()) {
            EmployeesDocument employeesDocument = employeesDocumentSet.iterator().next(); // придумать получше
            view.docNumber = employeesDocument.getDocNumber();
            view.docDate = employeesDocument.getDocDate();

            Document document = employeesDocument.getDocument();
            if (document != null)
                view.docName = document.getName();
        }

        return view;
    }

    @Override
    @Transactional
    public void update(UpdateEmployeeView view) throws NotFoundException {
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

        if (view.citizenshipCode != null && view.citizenshipName != null && !view.citizenshipName.isEmpty()) {
            Country country = countryDAO.load(view.citizenshipCode, view.citizenshipName);
            employee.setCountry(country);
        }

        boolean mustChangeDocNumber = view.docNumber != null && !view.docNumber.isEmpty();
        boolean mustChangeDocDate = view.docDate != null;
        boolean hasDocCode = view.docCode != null;
        boolean hasDocName = view.docName != null && !view.docName.isEmpty();

        /*
        * Проверяем нужно ли изменять данные связанной таблицы,
        * чтобы не делать к ней запрос впустую
        *
        * Здесь допускается возможность как изменения данных,
        * так и их добавления, если они не были добавлены ранее.
        *
        * Если нам пришел номер документа, или дата документа, или код документа вместе с его названием,
        * то условие ниже считается истиным(значит пользователь решил изменить эти данные или добавить
        * их, в случае, если они не были добавлены ранее)
        *
         */
        if (mustChangeDocNumber || mustChangeDocDate || hasDocCode && hasDocName) {
            Set<EmployeesDocument> employeesDocumentSet = employee.getEmployeesDocuments();
            EmployeesDocument employeesDocument;

            boolean isNewDocumentCreated = false;

            if (!employeesDocumentSet.isEmpty()) {
                employeesDocument = employeesDocumentSet.iterator().next();
            }
            else {
                employeesDocument = new EmployeesDocument();
                isNewDocumentCreated = true;
            }

            if (mustChangeDocNumber)
                employeesDocument.setDocNumber(view.docNumber);

            if (mustChangeDocDate)
                employeesDocument.setDocDate(view.docDate);

            if (hasDocCode && hasDocName) {
                Document document = docDAO.load(view.docCode, view.docName);
                employeesDocument.setDocument(document);
            }

            if (isNewDocumentCreated)
                employee.addEmployeesDocument(employeesDocument);
        }
    }

    @Override
    @Transactional
    public void delete(DeleteEmployeeView view) throws NotFoundException {
        Employee employee = dao.loadById(view.id);

        // см. логи
        Set<Office> offices = employee.getOffices();
        for (Office office: new HashSet<>(offices)) {

            office.removeEmployee(employee);
        }

        dao.delete(employee);
    }

    @Override
    @Transactional
    public void save(EmployeeView view) throws NotFoundException {
        Office office = officeDAO.loadById(view.officeId);

        Employee employee = new Employee();
        employee.setFirstName(view.firstName);
        employee.setLastName(view.secondName);
        employee.setMiddleName(view.middleName);
        employee.setPosition(view.position);
        employee.setPhone(view.phone);
        employee.setIdentified(view.isIdentified);

        /*
        * Если пришел код страны и ее название, то ищем такую страну в БД
        * Если такая страна найдена - добавляем ее работнику
         */
        if (view.citizenshipCode != null && view.citizenshipName != null && !view.citizenshipName.isEmpty()) {
            Country country = countryDAO.load(view.citizenshipCode, view.citizenshipName);
            employee.setCountry(country);
        }

        /*
        * Подразумевается, что у пользователя данные документа могут быть не заполнены
        * Здесь, если пришел номер документа и его дата - добавляем их к документу работника
        *
         */
        if (view.docNumber!= null && !view.docNumber.isEmpty() && view.docDate != null) {
            EmployeesDocument employeesDocument = new EmployeesDocument();
            employeesDocument.setDocNumber(view.docNumber);
            employeesDocument.setDocDate(view.docDate);

            /*
            * Если пришел код документа и его название, то такой документ ищется в БД
            * Если такой документ найден - он добавляется к документу работника
            *
             */
            if (view.docCode != null && view.docName != null && !view.docName.isEmpty()) {
                Document document = docDAO.load(view.docCode, view.docName);
                employeesDocument.setDocument(document);
            }
            employee.addEmployeesDocument(employeesDocument);
        }

        office.addEmployee(employee);
    }
}
