package ru.bellintegrator.practice.handbook.model;

import javax.persistence.*;

@Entity
@Table(name = "Doc_types")
public class DocType {

    @Id
    @Column(name = "code")
    private Integer code;

    @Basic(optional = false)
    @Column(name = "name")
    private String name;

    public Integer getCode() {
        return code;
    }

    public String getName() {
        return name;
    }
}
