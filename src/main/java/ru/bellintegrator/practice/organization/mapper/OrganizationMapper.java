package ru.bellintegrator.practice.organization.mapper;

import ru.bellintegrator.practice.organization.model.Organization;
import ru.bellintegrator.practice.organization.view.OrganizationView;
import ru.bellintegrator.practice.organization.view.UpdateOrganizationView;

public class OrganizationMapper {

    public static OrganizationView mapFromOrganization(Organization organization, OrganizationView view) {
        view.id = organization.getId();
        view.name = organization.getName();
        view.fullName = organization.getFullName();
        view.inn = organization.getInn();
        view.kpp = organization.getKpp();
        view.address = organization.getAddress();
        view.phone = organization.getPhone();
        view.isActive = organization.getActive();

        return view;
    }

    public static void mapFromOrganizationView(OrganizationView view, Organization organization) {
        initBaseOrganizationData(organization, view.name, view.fullName, view.inn, view.kpp,
                view.address, view.phone, view.isActive);
    }

    public static void mapFromUpdateOrganizationView(UpdateOrganizationView view, Organization organization) {
        initBaseOrganizationData(organization, view.name, view.fullName, view.inn, view.kpp,
                view.address, view.phone, view.isActive);
    }

    private static void initBaseOrganizationData(Organization organization, String name, String fullName, String inn,
                                                 String kpp, String address, String phone, Boolean isActive) {
        if (name != null && !name.isEmpty())
            organization.setName(name);

        if (fullName != null && !fullName.isEmpty())
            organization.setFullName(fullName);

        if (inn != null && !inn.isEmpty())
            organization.setInn(inn);

        if (kpp != null && !kpp.isEmpty())
            organization.setKpp(kpp);

        if (address != null && !address.isEmpty())
            organization.setAddress(address);

        if (phone != null && !phone.isEmpty())
            organization.setPhone(phone);

        if (isActive != null)
            organization.setActive(isActive);
    }
}
