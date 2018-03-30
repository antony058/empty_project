package ru.bellintegrator.practice.employee.mapper;

import ru.bellintegrator.practice.employee.model.Employee;
import ru.bellintegrator.practice.employee.model.EmployeesDocument;
import ru.bellintegrator.practice.employee.view.EmployeeView;
import ru.bellintegrator.practice.employee.view.UpdateEmployeeView;
import ru.bellintegrator.practice.handbook.model.Country;
import ru.bellintegrator.practice.handbook.model.Document;

import java.util.Set;

public class EmployeeMapper {

    /*
    * Метод заполняет поля из таблицы Employee
    *
    * @param view - заполняемая вью
    * @param employee - сущность, по которой заполняется выходная view
    *
    * @return заполненная view
     */
    public static EmployeeView mapBaseDataFromEmployee(EmployeeView view, Employee employee) {
        view.id = employee.getId();
        view.firstName = employee.getFirstName();
        view.lastName = employee.getLastName();
        view.middleName = employee.getMiddleName();
        view.position = employee.getPosition();
        view.phone = employee.getPhone();
        view.isIdentified = employee.getIdentified();

        return view;
    }

    /*
    * Метод заполняет поля view из таблицы Country
    *
    * @param view - заполняемая вью
    * @param employee - сущность, по которой заполняется выходная view
    *
    * @return заполненная view
     */
    public static EmployeeView mapCountryFromEmployee(EmployeeView view, Employee employee) {
        Country country = employee.getCountry();
        if (country != null) {
            view.citizenshipCode = country.getCode();
            view.citizenshipName = country.getName();
        }

        return view;
    }

    /*
    * Метод заполняет view из таблицы EmployeeDocument и связанной с ней Document
    *
    * @param view - заполняемая вью
    * @param employee - сущность, по которой заполняется выходная view
    *
    * @return заполненная view
     */
    public static EmployeeView mapDocumentFromEmployee(EmployeeView view, Employee employee) {
        Set<EmployeesDocument> employeesDocumentSet = employee.getEmployeesDocuments();

        if (!employeesDocumentSet.isEmpty()) {

            EmployeesDocument employeesDocument = employeesDocumentSet.iterator().next();
            view.docNumber = employeesDocument.getDocNumber();
            view.docDate = employeesDocument.getDocDate();

            Document document = employeesDocument.getDocument();
            if (document != null)
                view.docName = document.getName();
        }

        return view;
    }

    /*
    * @param employee - заполняемая сущность
    * @param view - значение этой view передаются в метод инициализации полей employee
    *
     */
    public static void mapBaseDataFromUpdateEmployeeView(Employee employee, UpdateEmployeeView view) {
        initBaseEmployeeData(employee, view.firstName, view.lastName, view.middleName,
                view.position, view.phone, view.isIdentified);
    }

    /*
    * @param employee - заполняемая сущность
    * @param view - значение этой view передаются в метод инициализации полей employee
    *
     */
    public static void mapBaseDataFromEmployeeView(Employee employee, EmployeeView view) {
        initBaseEmployeeData(employee, view.firstName, view.lastName, view.middleName,
                view.position, view.phone, view.isIdentified);
    }

    private static void initBaseEmployeeData(Employee employee, String firstName, String lastName,
                                             String middleName, String position, String phone, Boolean isIdentified) {
        if (firstName != null && !firstName.isEmpty())
            employee.setFirstName(firstName);

        if (lastName != null && !lastName.isEmpty())
            employee.setLastName(lastName);

        if (middleName != null && !middleName.isEmpty())
            employee.setMiddleName(middleName);

        if (position != null && !position.isEmpty())
            employee.setPosition(position);

        if (phone != null && !phone.isEmpty())
            employee.setPhone(phone);

        if (isIdentified != null)
            employee.setIdentified(isIdentified);
    }
}
