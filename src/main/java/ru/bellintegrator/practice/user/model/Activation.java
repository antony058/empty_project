package ru.bellintegrator.practice.user.model;

import javax.persistence.*;

@Entity
@Table(name = "Activation")
public class Activation {

    @Id
    @Column(name = "code")
    private String code;

    @OneToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_login")
    private User user;

    public String getCode() {
        return code;
    }

    public User getUser() {
        if (user == null)
            user = new User();
        return user;
    }
}
