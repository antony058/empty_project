package ru.bellintegrator.practice.testdata.service.user;

import javassist.NotFoundException;
import org.junit.Before;
import org.junit.Test;
import ru.bellintegrator.practice.user.dao.ActivationDAO;
import ru.bellintegrator.practice.user.model.Activation;
import ru.bellintegrator.practice.user.service.ActivationService;
import ru.bellintegrator.practice.user.service.impl.ActivationServiceImpl;
import ru.bellintegrator.practice.user.utils.HashGenerator;

import java.security.NoSuchAlgorithmException;

import static org.mockito.Mockito.*;

public class ActivationServiceTest {

    private ActivationService activationService;
    private ActivationDAO activationDAO;

    @Before
    public void setUp() {
        activationDAO = mock(ActivationDAO.class);
        activationService = new ActivationServiceImpl(activationDAO);
    }

    @Test
    public void activateUserSuccessTest() throws NoSuchAlgorithmException, NotFoundException {
        Activation activation = createActivation();
        when(activationDAO.loadByCode(getActivationCode())).thenReturn(activation);

        activationService.activate("some_code");
    }

    @Test(expected = NotFoundException.class)
    public void activateUserNotFoundExceptionTest() throws NoSuchAlgorithmException, NotFoundException {
        when(activationDAO.loadByCode(getActivationCode())).thenReturn(null);
        activationService.activate("some_code");
    }

    private Activation createActivation() throws NoSuchAlgorithmException {
        Activation activation = new Activation();
        activation.setCode(getActivationCode());

        return activation;
    }

    private String getActivationCode() throws NoSuchAlgorithmException {
        return HashGenerator.sha256Hex("some_code");
    }
}
