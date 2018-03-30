package ru.bellintegrator.practice.testdata.service.employee;

import javassist.NotFoundException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.bellintegrator.practice.employee.dao.EmployeeDAO;
import ru.bellintegrator.practice.employee.model.Employee;
import ru.bellintegrator.practice.employee.model.EmployeesDocument;
import ru.bellintegrator.practice.employee.service.EmployeeService;
import ru.bellintegrator.practice.employee.service.impl.EmployeeServiceImpl;
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

import java.sql.Date;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;

public class EmployeeServiceTest {

    private EmployeeService service;
    private EmployeeDAO employeeDAO;
    private OfficeDAO officeDAO;
    private DocDAO docDAO;
    private CountryDAO countryDAO;

    @Before
    public void setUp() {
        employeeDAO = mock(EmployeeDAO.class);
        officeDAO = mock(OfficeDAO.class);
        docDAO = mock(DocDAO.class);
        countryDAO = mock(CountryDAO.class);
        service = new EmployeeServiceImpl(employeeDAO, countryDAO, docDAO, officeDAO);
    }

    @Test
    public void getAllByOfficeIdSuccessTest() {
        Employee employee = createEmployee();
        ListEmployeeView requestView = createListEmployeeView();

        when(employeeDAO.allByOfficeId(1L, "some_name", null, null,
                null, null, null)).thenReturn(Arrays.asList(employee));

        List<EmployeeView> responseViews = service.getAllByOfficeId(requestView);
        Assert.assertEquals(responseViews.get(0).firstName, "some_name");
    }

    @Test
    public void getAllByOfficeIdSuccessTest_emptyList() {
        ListEmployeeView requestView = createListEmployeeView();

        when(employeeDAO.allByOfficeId(1L, "some_name", null, null,
                null, null, null)).thenReturn(Collections.emptyList());

        List<EmployeeView> responseViews = service.getAllByOfficeId(requestView);
        Assert.assertTrue(responseViews.isEmpty());
    }

    @Test
    public void getByIdSuccessTest() throws NotFoundException {
        Employee employee = createEmployee();

        when(employeeDAO.loadById(1L)).thenReturn(employee);

        EmployeeView responseView = service.getById("1");
        Assert.assertNotNull(responseView);
        Assert.assertEquals(responseView.firstName, employee.getFirstName());
    }

    @Test
    public void getByIdSuccessTest_country() throws NotFoundException {
        Employee employee = createEmployee();
        Country country = new Country();
        country.setCode(123);
        country.setName("Some country name");

        employee.setCountry(country);

        when(employeeDAO.loadById(1L)).thenReturn(employee);

        EmployeeView responseView = service.getById("1");
        Assert.assertEquals(responseView.citizenshipCode, country.getCode());
        Assert.assertEquals(responseView.citizenshipName, country.getName());
    }

    @Test
    public void getByIdSuccessTest_employeeDocuments() throws NotFoundException {
        Employee employee = createEmployee();
        EmployeesDocument employeesDocument = new EmployeesDocument();
        employeesDocument.setDocDate(new Date(1L));
        employeesDocument.setDocNumber("1234NUM");
        employee.addEmployeesDocument(employeesDocument);

        when(employeeDAO.loadById(1L)).thenReturn(employee);

        EmployeeView responseView = service.getById("1");
        Assert.assertEquals(responseView.docDate.toString(), new Date(1L).toString());
        Assert.assertEquals(responseView.docNumber, employeesDocument.getDocNumber());
    }

    @Test
    public void getByIdSuccessTest_document() throws NotFoundException {
        Employee employee = createEmployee();
        EmployeesDocument employeesDocument = new EmployeesDocument();
        employeesDocument.setDocDate(new Date(1L));
        employeesDocument.setDocNumber("1234NUM");

        Document document = new Document();
        document.setCode(124);
        document.setName("some document");
        employeesDocument.setDocument(document);

        employee.addEmployeesDocument(employeesDocument);

        when(employeeDAO.loadById(1L)).thenReturn(employee);

        EmployeeView responseView = service.getById("1");
        Assert.assertEquals(responseView.docName, document.getName());
    }

    @Test(expected = NotFoundException.class)
    public void getByIdNotFoundExceptionTest() throws NotFoundException {
        when(employeeDAO.loadById(1L)).thenThrow(new NotFoundException("Работник не найден"));

        service.getById("1");
    }

    @Test
    public void updateEmployeeSuccessTest() throws NotFoundException {
        Employee employee = createEmployee();
        UpdateEmployeeView requestView = createUpdateEmployeeView();

        when(employeeDAO.loadById(1L)).thenReturn(employee);

        service.update(requestView);
    }

    @Test(expected = NotFoundException.class)
    public void updateEmployeeNotFoundExceptionTest() throws NotFoundException {
        when(employeeDAO.loadById(1L)).thenThrow(new NotFoundException("Работник не найден"));

        UpdateEmployeeView requestView = createUpdateEmployeeView();
        service.update(requestView);
    }

    @Test
    public void updateEmployeeTest_hasCountry() throws NotFoundException {
        Employee employee = createEmployee();

        when(employeeDAO.loadById(1L)).thenReturn(employee);
        when(countryDAO.load(123, "some_country")).thenReturn(new Country());

        UpdateEmployeeView requestView = createUpdateEmployeeView();
        requestView.citizenshipName = "some_country";
        requestView.citizenshipCode = 123;

        service.update(requestView);
    }

    @Test(expected = NotFoundException.class)
    public void updateEmployeeNotFoundExceptionTest_countryNotFound() throws NotFoundException {
        Employee employee = createEmployee();

        when(employeeDAO.loadById(1L)).thenReturn(employee);
        when(countryDAO.load(123, "some_country")).thenThrow(new NotFoundException("Страна не найдена"));

        UpdateEmployeeView requestView = createUpdateEmployeeView();
        requestView.citizenshipName = "some_country";
        requestView.citizenshipCode = 123;

        service.update(requestView);
    }

    @Test
    public void updateEmployeeSuccessTest_hasDocument() throws NotFoundException {
        Employee employee = createEmployee();
        employee.addEmployeesDocument(new EmployeesDocument());

        when(employeeDAO.loadById(1L)).thenReturn(employee);
        when(docDAO.load(123, "some_document")).thenReturn(new Document());

        UpdateEmployeeView requestView = createUpdateEmployeeView();
        requestView.docCode = 123;
        requestView.docName = "some_document";

        service.update(requestView);
    }

    @Test(expected = NotFoundException.class)
    public void updateEmployeeNotFoundExceptionTest_documentNotFound() throws NotFoundException {
        Employee employee = createEmployee();
        employee.addEmployeesDocument(new EmployeesDocument());

        when(employeeDAO.loadById(1L)).thenReturn(employee);
        when(docDAO.load(123, "some_document")).thenThrow(new NotFoundException("Документ не найден"));

        UpdateEmployeeView requestView = createUpdateEmployeeView();
        requestView.docCode = 123;
        requestView.docName = "some_document";

        service.update(requestView);
    }

    @Test
    public void deleteEmployeeSuccessTest() throws NotFoundException {
        Employee employee = createEmployee();
        when(employeeDAO.loadById(1L)).thenReturn(employee);

        DeleteEmployeeView requestView = createDeleteEmployeeView();
        service.delete(requestView);

        verify(employeeDAO).delete(employee);
    }

    @Test(expected = NotFoundException.class)
    public void deleteEmployeeNotFoundExceptionTest() throws NotFoundException {
        when(employeeDAO.loadById(1L)).thenThrow(new NotFoundException("Работник не найден"));

        DeleteEmployeeView requestView = createDeleteEmployeeView();
        service.delete(requestView);
    }

    @Test
    public void saveEmployeeSuccessTest() throws NotFoundException {
        Office office = new Office();
        office.setName("some_office");

        when(officeDAO.loadById(1L)).thenReturn(office);

        EmployeeView requestView = createEmployeeView();
        service.save(requestView);
    }

    @Test
    public void saveEmployeeSuccessTest_hasCountry() throws NotFoundException {
        Office office = new Office();
        office.setName("some_office");

        when(officeDAO.loadById(1L)).thenReturn(office);
        when(countryDAO.load(123, "some_country")).thenReturn(new Country());

        EmployeeView requestView = createEmployeeView();
        requestView.citizenshipCode = 123;
        requestView.citizenshipName = "some_country";

        service.save(requestView);
    }

    @Test(expected = NotFoundException.class)
    public void saveEmployeeNotFoundExceptionTest_NotFoundCountry() throws NotFoundException {
        Office office = new Office();
        office.setName("some_office");

        when(officeDAO.loadById(1L)).thenReturn(office);
        when(countryDAO.load(123, "some_country")).thenThrow(new NotFoundException("Страна не найдена"));

        EmployeeView requestView = createEmployeeView();
        requestView.citizenshipCode = 123;
        requestView.citizenshipName = "some_country";

        service.save(requestView);
    }

    @Test
    public void saveEmployeeSuccessTest_hasDocument() throws NotFoundException {
        Office office = new Office();
        office.setName("some_office");

        when(officeDAO.loadById(1L)).thenReturn(office);
        when(docDAO.load(123, "some_document")).thenReturn(new Document());

        EmployeeView requestView = createEmployeeView();
        requestView.docCode = 123;
        requestView.docName = "some_document";
        requestView.docDate = new Date(1L);
        requestView.docNumber = "1234NUM";

        service.save(requestView);
    }

    @Test(expected = NotFoundException.class)
    public void saveEmployeeNotFoundExceptionTest_NotFoundDocument() throws NotFoundException {
        Office office = new Office();
        office.setName("some_office");

        when(officeDAO.loadById(1L)).thenReturn(office);
        when(docDAO.load(123, "some_document")).thenThrow(new NotFoundException("Документ не найден"));

        EmployeeView requestView = createEmployeeView();
        requestView.docCode = 123;
        requestView.docName = "some_document";
        requestView.docDate = new Date(1L);
        requestView.docNumber = "1234NUM";

        service.save(requestView);
    }

    private ListEmployeeView createListEmployeeView() {
        ListEmployeeView view = new ListEmployeeView();
        view.firstName = "some_name";
        view.officeId = 1L;

        return view;
    }

    private EmployeeView createEmployeeView() {
        EmployeeView view = new EmployeeView();
        view.firstName = "some_name";
        view.officeId = 1L;

        return view;
    }

    private UpdateEmployeeView createUpdateEmployeeView() {
        UpdateEmployeeView view = new UpdateEmployeeView();
        view.firstName = "some_name";
        view.id = 1L;

        return view;
    }

    private DeleteEmployeeView createDeleteEmployeeView() {
        DeleteEmployeeView view = new DeleteEmployeeView();
        view.id = 1L;

        return view;
    }

    private Employee createEmployee() {
        Employee employee = new Employee();
        employee.setFirstName("some_name");
        employee.setLastName("some_last_name");

        return employee;
    }
}
