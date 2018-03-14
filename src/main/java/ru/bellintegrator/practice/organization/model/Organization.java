package ru.bellintegrator.practice.organization.model;

import ru.bellintegrator.practice.office.model.Office;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "Organization")
public class Organization {

    /*
    * id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /*
    * Служебное поле Hibernate
     */
    @Version
    private Integer version;

    /*
    * Имя
     */
    @Basic(optional = false)
    @Column(name = "name")
    private String name;

    /*
    * Полное имя
     */
    @Basic(optional = false)
    @Column(name = "full_name")
    private String fullName;

    /*
    * ИНН
     */
    @Basic(optional = false)
    @Column(name = "inn")
    private String inn;

    /*
    * КПП
     */
    @Basic(optional = false)
    @Column(name = "kpp")
    private String kpp;

    /*
    * Адрес
     */
    @Basic(optional = false)
    @Column(name = "address")
    private String address;

    /*
    * Телефон
     */
    @Basic(optional = false)
    @Column(name = "phone")
    private String phone;

    /*
    * Активна ли организация
     */
    @Basic(optional = false)
    @Column(name = "is_active")
    private Boolean isActive;

    @OneToMany(
            mappedBy = "organization",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private Set<Office> offices;

    public Organization() {

    }

    public Organization(String name, String fullName, String inn,
                        String kpp, String address, String phone, Boolean isActive) {
        this.name = name;
        this.fullName = fullName;
        this.inn = inn;
        this.kpp = kpp;
        this.address = address;
        this.phone = phone;
        this.isActive = isActive;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getInn() {
        return inn;
    }

    public void setInn(String inn) {
        this.inn = inn;
    }

    public String getKpp() {
        return kpp;
    }

    public void setKpp(String kpp) {
        this.kpp = kpp;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public Set<Office> getOffices() {
        if (offices == null)
            offices = new HashSet<>();
        return offices;
    }

    public void addOffice(Office office) {
        getOffices().add(office);
        office.setOrganization(this);
    }

    public void removeOffice(Office office) {
        getOffices().remove(office);
        office.setOrganization(null);
    }
}
