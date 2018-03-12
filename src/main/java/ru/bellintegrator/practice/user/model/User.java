package ru.bellintegrator.practice.user.model;

import javax.persistence.*;

@Entity
@Table(name = "User")
public class User {

    @Id
    @Column(name = "login")
    private String login;

    @Basic(optional = false)
    @Column(name = "password")
    private String password;

    @Basic(optional = false)
    @Column(name = "name")
    private String name;

    @Basic(optional = false)
    @Column(name = "is_active")
    private Boolean isActive;

    @OneToOne(
            mappedBy = "user",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL
    )
    private Activation activation;

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getUserActive() {
        return isActive;
    }

    public void setUserActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public Activation getActivation() {
        return activation;
    }

    public void setActivation(Activation activation) {
        this.activation = activation;
    }
}
