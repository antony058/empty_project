package ru.bellintegrator.practice.user.model;

import javax.persistence.*;

@Entity
@Table(name = "Activation")
public class Activation {

    @Id
    @Column(name = "code")
    private String code;

    @OneToOne
    @JoinColumn(name = "user_login")
    private User user;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public User getUser() {
        if (user == null)
            user = new User();
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
