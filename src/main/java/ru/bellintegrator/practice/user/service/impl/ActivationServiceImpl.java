package ru.bellintegrator.practice.user.service.impl;

import javassist.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.bellintegrator.practice.user.dao.ActivationDAO;
import ru.bellintegrator.practice.user.model.Activation;
import ru.bellintegrator.practice.user.model.User;
import ru.bellintegrator.practice.user.service.ActivationService;
import ru.bellintegrator.practice.user.util.StringUtils;

import java.security.NoSuchAlgorithmException;

@Service
@Scope(proxyMode = ScopedProxyMode.INTERFACES)
public class ActivationServiceImpl implements ActivationService {
    private final Logger log = LoggerFactory.getLogger(ActivationServiceImpl.class);
    private final ActivationDAO dao;

    @Autowired
    public ActivationServiceImpl(ActivationDAO dao) {
        this.dao = dao;
    }

    @Override
    @Transactional
    public void activate(String code) throws NotFoundException, NoSuchAlgorithmException {
        String hashCode = StringUtils.sha256Hex(code);

        Activation activation = dao.loadByCode(hashCode);
        if (activation == null)
            throw new NotFoundException("Не верный код");

        User user = activation.getUser();
        user.setUserActive(true);
        user.setActivation(null);

        log.info("Активирован пользователь " + user.getLogin());
    }
}
