package ru.bellintegrator.practice.employee.model;

import ru.bellintegrator.practice.handbook.model.Document;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "Employees_document")
public class EmployeesDocument {

    @Id
    @GeneratedValue
    private Long id;

    @Version
    private Integer version;

    @Basic(optional = false)
    @Column(name = "doc_number")
    private String docNumber;

    @Basic(optional = false)
    @Column(name = "doc_date")
    private Date docDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "doc_code")
    private Document document;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id")
    private Employee employee;

    public EmployeesDocument() {

    }

    public Long getId() {
        return id;
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

    public Document getDocument() {
        return document;
    }

    public void setDocument(Document document) {
        this.document = document;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }
}
