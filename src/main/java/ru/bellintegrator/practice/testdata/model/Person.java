package ru.bellintegrator.practice.testdata.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Человек
 */
@Entity
@Table(name = "Person")
public class Person {

    @Id
    @GeneratedValue
    @Column(name = "Id")
    private Long id;

    /**
     * Служебное поле hibernate
     */
    @Version
    private Integer version;

    /**
     * Иия
     */
    @Basic(optional = false)
    @Column(name = "first_name")
    private String name;

    /**
     * Возраст
     * т.к. поле примитивного типа, оно не может быть nullable
     */
    @Basic(optional = false)
    @Column(name = "age")
    private int age;

    @OneToMany(
            mappedBy = "person",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )

    private List<House> houses;

    /**
     * Конструктор для hibernate
     */
    public Person() {

    }

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
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

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public List<House> getHouses() {
        if (houses == null) {
            houses = new ArrayList<>();
        }
        return houses;
    }

    public void addHouse(House house) {
        getHouses().add(house);
        house.setPerson(this);
    }

    public void removeHouse(House house) {
        getHouses().remove(house);
        house.setPerson(null);
    }

}
