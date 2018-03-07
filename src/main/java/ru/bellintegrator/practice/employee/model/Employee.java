package ru.bellintegrator.practice.employee.model;

import ru.bellintegrator.practice.handbook.model.Country;
import ru.bellintegrator.practice.handbook.model.DocType;
import ru.bellintegrator.practice.office.model.Office;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "Employee")
public class Employee {

    @Id
    @GeneratedValue
    private long id;

    @Basic(optional = false)
    @Column(name = "firstName")
    private String firstName;

    @Basic(optional = false)
    @Column(name = "lastName")
    private String lastName;

    @Basic(optional = false)
    @Column(name = "middleName")
    private String middleName;

    @Basic(optional = false)
    @Column(name = "position")
    private String position;

    @Basic(optional = false)
    @Column(name = "phone")
    private String phone;

    @Basic(optional = false)
    @Column(name = "docNumber")
    private String docNumber;

    @Basic(optional = false)
    @Column(name = "docDate")
    private Date docDate;

    @Basic(optional = false)
    @Column(name = "isIdentified")
    private Boolean isIdentified;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "officeId")
    private Office office;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "docCode")
    private DocType document;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "citizenshipCode")
    private Country country;

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

    public String getDocNumber() {
        return docNumber;
    }

    public void setDocNumber(String docNumber) {
        this.docNumber = docNumber;
    }

    public Date getDocDate() {
        return docDate;
    }

    public void setDocDate(Date docDate) {
        this.docDate = docDate;
    }

    public Boolean getIdentified() {
        return isIdentified;
    }

    public void setIdentified(Boolean identified) {
        isIdentified = identified;
    }

    public Office getOffice() {
        return office;
    }

    public void setOffice(Office office) {
        this.office = office;
    }

    public DocType getDocument() {
        return document;
    }

    public void setDocument(DocType document) {
        this.document = document;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country){
        this.country = country;
    }
}
