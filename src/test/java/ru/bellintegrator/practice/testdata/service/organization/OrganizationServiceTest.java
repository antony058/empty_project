package ru.bellintegrator.practice.testdata.service.organization;

import javassist.NotFoundException;
import org.junit.Assert;
import org.junit.Before;
import static org.mockito.Mockito.*;

import org.junit.Test;
import ru.bellintegrator.practice.organization.dao.OrganizationDAO;
import ru.bellintegrator.practice.organization.model.Organization;
import ru.bellintegrator.practice.organization.service.OrganizationService;
import ru.bellintegrator.practice.organization.service.impl.OrganizationServiceImpl;
import ru.bellintegrator.practice.organization.view.DeleteOrganizationView;
import ru.bellintegrator.practice.organization.view.ListOrganizationView;
import ru.bellintegrator.practice.organization.view.OrganizationView;
import ru.bellintegrator.practice.organization.view.UpdateOrganizationView;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class OrganizationServiceTest {

    private OrganizationService service;
    private OrganizationDAO dao;

    @Before
    public void setUp() {
        dao = mock(OrganizationDAO.class);
        service = new OrganizationServiceImpl(dao);
    }

    @Test
    public void getAllSuccessTest() {
        Organization organization = new Organization();
        organization.setName("some_name");

        when(dao.all("some_name", null, null)).thenReturn(Arrays.asList(organization));

        ListOrganizationView requestView = createListOrganizationView();
        List<OrganizationView> responseViews = service.getAll(requestView);

        Assert.assertNotNull(responseViews);

        Assert.assertEquals(responseViews.get(0).name, "some_name");
    }

    @Test
    public void getAllSuccessTest_emptyList() {
        Organization organization = createOrganization();

        when(dao.all("some_name", null, null)).thenReturn(Collections.emptyList());

        ListOrganizationView requestView = createListOrganizationView();
        List<OrganizationView> responseViews = service.getAll(requestView);

        Assert.assertTrue(responseViews.isEmpty());
    }

    @Test
    public void getByIdSuccessTest() throws NotFoundException {
        Organization organization = createOrganization();
        when(dao.loadById(1L)).thenReturn(organization);

        OrganizationView responseView = service.getOrganizationById("1");
        Assert.assertEquals(responseView.name, organization.getName());
    }

    @Test(expected = NotFoundException.class)
    public void getByIdNotFoundException() throws NotFoundException {
        when(dao.loadById(1L)).thenThrow(new NotFoundException("Организация не найдена"));
        service.getOrganizationById("1");
    }

    @Test
    public void updateOrganizationSuccessTest() throws NotFoundException {
        Organization organization = createOrganization();
        UpdateOrganizationView requestView = createUpdateOrganizationView();

        when(dao.loadById(1L)).thenReturn(organization);

        service.updateOrganization(requestView);
    }

    @Test(expected = NotFoundException.class)
    public void updateOrganizationNotFoundExceptionTest() throws NotFoundException {
        UpdateOrganizationView requestView = createUpdateOrganizationView();

        when(dao.loadById(1L)).thenThrow(new NotFoundException("Организация не найдена"));

        service.updateOrganization(requestView);
    }

    @Test
    public void saveOrganizationSuccessTest() {
        OrganizationView requestView = createOrganizationView();

        service.saveOrganization(requestView);
        verify(dao).save(any()); // тут под any() подразумевается объект Organization, сформированный по OrganizationView
    }

    @Test
    public void deleteOrganizationSuccessTest() throws NotFoundException {
        Organization organization = createOrganization();
        DeleteOrganizationView requestView = createDeleteOrganizationView();

        when(dao.loadById(1L)).thenReturn(organization);
        service.deleteOrganization(requestView);

        verify(dao).delete(organization);
    }

    @Test(expected = NotFoundException.class)
    public void deleteOrganizationNotFoundExceptionTest() throws NotFoundException {
        when(dao.loadById(1L)).thenThrow(new NotFoundException("Организация не найдена"));

        service.deleteOrganization(createDeleteOrganizationView());
    }

    private ListOrganizationView createListOrganizationView() {
        ListOrganizationView view = new ListOrganizationView();
        view.name = "some_name";

        return view;
    }

    private OrganizationView createOrganizationView() {
        OrganizationView view = new OrganizationView();
        view.name = "some_name";

        return view;
    }

    private UpdateOrganizationView createUpdateOrganizationView() {
        UpdateOrganizationView view = new UpdateOrganizationView();
        view.name = "some_name";
        view.id = 1L;

        return view;
    }

    private DeleteOrganizationView createDeleteOrganizationView() {
        DeleteOrganizationView view = new DeleteOrganizationView();
        view.id = 1L;

        return view;
    }

    private Organization createOrganization() {
        Organization organization = new Organization();
        organization.setName("some_name");

        return organization;
    }
}
