package ru.bellintegrator.practice.employee.model;

import ru.bellintegrator.practice.handbook.model.Country;
import ru.bellintegrator.practice.handbook.model.Document;
import ru.bellintegrator.practice.office.model.Office;

import javax.persistence.*;
import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "Employee")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Version
    private Integer version;

    @Basic(optional = false)
    @Column(name = "first_name")
    private String firstName;

    @Basic(optional = false)
    @Column(name = "last_name")
    private String lastName;

    @Basic(optional = false)
    @Column(name = "middle_name")
    private String middleName;

    @Basic(optional = false)
    @Column(name = "position")
    private String position;

    @Basic(optional = false)
    @Column(name = "phone")
    private String phone;

    @Basic(optional = false)
    @Column(name = "is_identified")
    private Boolean isIdentified;

    @ManyToMany(mappedBy = "employees")
    private Set<Office> offices;

    @OneToMany(
            mappedBy = "employee",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private Set<EmployeesDocument> employeesDocuments;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "citizenship_code")
    private Country country;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Boolean getIdentified() {
        return isIdentified;
    }

    public void setIdentified(Boolean identified) {
        isIdentified = identified;
    }

    public Set<Office> getOffices() {
        if (offices == null)
            return new HashSet<>();

        return offices;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country){
        this.country = country;
    }

    public Set<EmployeesDocument> getEmployeesDocuments() {
        if (employeesDocuments == null)
            employeesDocuments = new HashSet<>();

        return employeesDocuments;
    }

    public void setEmployeesDocuments(Set<EmployeesDocument> employeesDocuments) {
        this.employeesDocuments = employeesDocuments;
    }

    public void addEmployeesDocument(EmployeesDocument employeesDocument) {
        getEmployeesDocuments().add(employeesDocument);
        employeesDocument.setEmployee(this);
    }

    public void removeEmployeesDocument(EmployeesDocument employeesDocument) {
        getEmployeesDocuments().remove(employeesDocument);
        employeesDocument.setEmployee(null);
    }
}
