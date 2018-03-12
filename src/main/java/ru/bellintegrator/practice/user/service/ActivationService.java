package ru.bellintegrator.practice.user.service;

import ru.bellintegrator.practice.user.view.ActivationView;

import java.util.List;

public interface ActivationService {
    String checkActivationCode(String code);
}
