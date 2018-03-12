package ru.bellintegrator.practice.user.dao;

import ru.bellintegrator.practice.user.model.Activation;
import ru.bellintegrator.practice.user.view.ActivationView;

import java.util.List;

public interface ActivationDAO {
    List<Activation> all();

    Activation checkActivationCode(String code);
}
