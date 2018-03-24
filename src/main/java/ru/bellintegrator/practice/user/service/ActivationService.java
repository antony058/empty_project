package ru.bellintegrator.practice.user.service;

import javassist.NotFoundException;

import java.security.NoSuchAlgorithmException;

public interface ActivationService {
    void activate(String code) throws NotFoundException, NoSuchAlgorithmException;
}
