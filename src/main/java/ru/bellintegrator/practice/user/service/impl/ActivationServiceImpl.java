package ru.bellintegrator.practice.user.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.bellintegrator.practice.user.dao.ActivationDAO;
import ru.bellintegrator.practice.user.model.Activation;
import ru.bellintegrator.practice.user.service.ActivationService;
import ru.bellintegrator.practice.user.view.ActivationView;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Scope(proxyMode = ScopedProxyMode.INTERFACES)
public class ActivationServiceImpl implements ActivationService {
    private final ActivationDAO dao;

    @Autowired
    public ActivationServiceImpl(ActivationDAO dao) {
        this.dao = dao;
    }

    @Override
    @Transactional
    public String checkActivationCode(String code) {
        Activation activation = dao.checkActivationCode(code);

        activation.getUser().setUserActive(true);

        return "success";
    }
}
