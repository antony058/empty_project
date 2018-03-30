package ru.bellintegrator.practice.testdata.service.office;

import javassist.NotFoundException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.bellintegrator.practice.office.dao.OfficeDAO;
import ru.bellintegrator.practice.office.model.Office;
import ru.bellintegrator.practice.office.service.OfficeService;
import ru.bellintegrator.practice.office.service.impl.OfficeServiceImpl;
import ru.bellintegrator.practice.office.view.DeleteOfficeView;
import ru.bellintegrator.practice.office.view.ListOfficeView;
import ru.bellintegrator.practice.office.view.OfficeView;
import ru.bellintegrator.practice.office.view.UpdateOfficeView;
import ru.bellintegrator.practice.organization.dao.OrganizationDAO;
import ru.bellintegrator.practice.organization.model.Organization;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;

public class OfficeServiceTest {

    private OfficeService officeService;
    private OfficeDAO officeDAO;
    private OrganizationDAO organizationDAO;

    @Before
    public void setUp() {
        officeDAO = mock(OfficeDAO.class);
        organizationDAO = mock(OrganizationDAO.class);
        officeService = new OfficeServiceImpl(officeDAO, organizationDAO);
    }

    @Test
    public void getAllByOrgIdSuccessTest() {
        Office office = createOffice();
        ListOfficeView requestView = createListOfficeView();

        when(officeDAO.allByOrgId(1L, "some_name", null, null))
                .thenReturn(Arrays.asList(office));

        List<OfficeView> responseList = officeService.getAllByOrgId(requestView);

        Assert.assertEquals(responseList.get(0).name, "some_name");
    }

    @Test
    public void getAllByOrgIdSuccessTest_emptyList() {
        ListOfficeView requestView = createListOfficeView();

        when(officeDAO.allByOrgId(1L, "some_name", null, null))
                .thenReturn(Collections.emptyList());

        List<OfficeView> responseList = officeService.getAllByOrgId(requestView);

        Assert.assertTrue(responseList.isEmpty());
    }

    @Test
    public void getByIdSuccessTest() throws NotFoundException {
        Office office = createOffice();

        when(officeDAO.loadById(1L)).thenReturn(office);

        OfficeView responseView = officeService.getById("1");
        Assert.assertEquals(responseView.name, "some_name");
    }

    @Test(expected = NotFoundException.class)
    public void getByIdNotFoundExceptionTest() throws NotFoundException {
        when(officeDAO.loadById(1L)).thenThrow(new NotFoundException("Офис не найден"));

        officeService.getById("1");
    }

    @Test
    public void updateOfficeSuccessTest() throws NotFoundException {
        Office office = createOffice();
        UpdateOfficeView requestView = createUpdateOfficeView();

        when(officeDAO.loadById(1L)).thenReturn(office);

        officeService.update(requestView);
    }

    @Test(expected = NotFoundException.class)
    public void updateOfficeNotFoundExceptionTest() throws NotFoundException {
        UpdateOfficeView requestView = createUpdateOfficeView();

        when(officeDAO.loadById(1L)).thenThrow(new NotFoundException("Офис не найден"));

        officeService.update(requestView);
    }

    @Test
    public void saveOfficeSuccessTest() throws NotFoundException {
        when(organizationDAO.loadById(1L)).thenReturn(new Organization());

        officeService.save(createOfficeView());
        verify(officeDAO).save(any());
    }

    @Test(expected = NotFoundException.class)
    public void saveOfficeNotFoundExceptionTest() throws NotFoundException {
        when(organizationDAO.loadById(1L)).thenThrow(new NotFoundException("Организация не найдена"));

        officeService.save(createOfficeView());
    }

    @Test
    public void deleteOfficeSuccessTest() throws NotFoundException {
        Office office = createOffice();
        when(officeDAO.loadById(1L)).thenReturn(office);

        officeService.delete(createDeleteOfficeView());
        verify(officeDAO).delete(office);
    }

    @Test(expected = NotFoundException.class)
    public void deleteOfficeNotFoundExceptionTest() throws NotFoundException {
        Office office = createOffice();
        when(officeDAO.loadById(1L)).thenThrow(new NotFoundException("Офис не найден"));

        officeService.delete(createDeleteOfficeView());
    }

    private ListOfficeView createListOfficeView() {
        ListOfficeView view = new ListOfficeView();
        view.orgId = 1L;
        view.name = "some_name";

        return view;
    }

    private OfficeView createOfficeView() {
        OfficeView view = new OfficeView();
        view.name = "some_name";
        view.orgId = 1L;

        return view;
    }

    private UpdateOfficeView createUpdateOfficeView() {
        UpdateOfficeView view = new UpdateOfficeView();
        view.name = "soma_name";
        view.id = 1L;

        return view;
    }

    private DeleteOfficeView createDeleteOfficeView() {
        DeleteOfficeView view = new DeleteOfficeView();
        view.id = 1L;

        return view;
    }

    private Office createOffice() {
        Office office = new Office();
        office.setName("some_name");

        return office;
    }
}
