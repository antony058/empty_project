package ru.bellintegrator.practice.employee.service.impl;

import javassist.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.bellintegrator.practice.employee.dao.EmployeeDAO;
import ru.bellintegrator.practice.employee.mapper.EmployeeMapper;
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

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Scope(proxyMode = ScopedProxyMode.INTERFACES)
public class EmployeeServiceImpl implements EmployeeService {

    private final Logger log = LoggerFactory.getLogger(EmployeeServiceImpl.class);

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

        List<EmployeeView> views = new ArrayList<>(employees.size());

        for (Employee employee: employees) {
            EmployeeView employeeView = EmployeeMapper.mapBaseDataFromEmployee(new EmployeeView(), employee);
            views.add(employeeView);
        }

        return views;
    }

    @Override
    @Transactional(readOnly = true)
    public EmployeeView getById(String id) throws NotFoundException {
        Employee employee = dao.loadById(Long.valueOf(id));

        EmployeeView view = new EmployeeView();

        // заполняем view базовой информацией по работнику
        EmployeeMapper.mapBaseDataFromEmployee(view, employee);

        // заполняем view информацией о стране работника
        EmployeeMapper.mapCountryFromEmployee(view, employee);

        // заполняем view информацией о документах работника
        EmployeeMapper.mapDocumentFromEmployee(view, employee);

        return view;
    }

    @Override
    @Transactional
    public void update(UpdateEmployeeView view) throws NotFoundException {
        Employee employee = dao.loadById(view.id);
        EmployeeMapper.mapBaseDataFromUpdateEmployeeView(employee, view);

        if (view.citizenshipCode != null && view.citizenshipName != null && !view.citizenshipName.isEmpty()) {
            Country country = countryDAO.load(view.citizenshipCode, view.citizenshipName);
            employee.setCountry(country);
        }

        boolean mustChangeDocNumber = view.docNumber != null && !view.docNumber.isEmpty();
        boolean mustChangeDocDate = view.docDate != null;
        boolean hasDocCode = view.docCode != null;
        boolean hasDocName = view.docName != null && !view.docName.isEmpty();

        if (mustChangeDocNumber || mustChangeDocDate || hasDocCode && hasDocName) {
            EmployeesDocument employeesDocument = employee.getEmployeesDocuments().iterator().next();

            if (mustChangeDocNumber)
                employeesDocument.setDocNumber(view.docNumber);

            if (mustChangeDocDate)
                employeesDocument.setDocDate(view.docDate);

            if (hasDocCode && hasDocName) {
                Document document = docDAO.load(view.docCode, view.docName);
                employeesDocument.setDocument(document);
            }
        }

        log.info("Изменен работник " + employee.getFirstName() + " " + employee.getLastName());
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

        log.info("Удален работник " + employee.getFirstName() + " " + employee.getLastName());
    }

    @Override
    @Transactional
    public void save(EmployeeView view) throws NotFoundException {
        Office office = officeDAO.loadById(view.officeId);

        Employee employee = new Employee();
        EmployeeMapper.mapBaseDataFromEmployeeView(employee, view);

        Country country = countryDAO.load(view.citizenshipCode, view.citizenshipName);
        employee.setCountry(country);

        EmployeesDocument employeesDocument = new EmployeesDocument();
        employeesDocument.setDocNumber(view.docNumber);
        employeesDocument.setDocDate(view.docDate);

        Document document = docDAO.load(view.docCode, view.docName);
        employeesDocument.setDocument(document);
        employee.addEmployeesDocument(employeesDocument);

        office.addEmployee(employee);

        log.info("Добавлен работник " + employee.getFirstName() + " " + employee.getLastName() +
                " в офис " + office.getName());
    }
}
