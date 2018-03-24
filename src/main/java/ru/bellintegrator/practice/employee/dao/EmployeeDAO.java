package ru.bellintegrator.practice.employee.dao;

import javassist.NotFoundException;
import ru.bellintegrator.practice.employee.model.Employee;

import java.util.List;

public interface EmployeeDAO {

    List<Employee> allByOfficeId(Long officeId, String firstName, String lastName, String middleName,
                                 String position, Integer docCode, Integer citizenshipCode);

    Employee loadById(Long id) throws NotFoundException;

    void update(Employee employee);

    void delete(Employee employee);

    void save(Employee employee);
}
