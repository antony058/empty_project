package ru.bellintegrator.practice.office.mapper;

import ru.bellintegrator.practice.office.model.Office;
import ru.bellintegrator.practice.office.view.OfficeView;
import ru.bellintegrator.practice.office.view.UpdateOfficeView;

public class OfficeMapper {

    public static OfficeView mapFromOffice(Office office, OfficeView view) {
        view.id = office.getId();
        view.name = office.getName();
        view.isActive = office.getActive();
        view.address = office.getAddress();
        view.phone = office.getPhone();

        return view;
    }

    public static void mapFromOfficeView(OfficeView view, Office office) {
        initBaseOfficeData(office, view.name, view.address, view.phone, view.isActive);
    }

    public static void mapFromUpdateOfficeView(UpdateOfficeView view, Office office) {
        initBaseOfficeData(office, view.name, view.address, view.phone, view.isActive);
    }

    private static void initBaseOfficeData(Office office, String name, String address, String phone, Boolean isActive) {
        if (name != null && !name.isEmpty())
            office.setName(name);

        if (address != null && !address.isEmpty())
            office.setAddress(address);

        if (phone != null && !phone.isEmpty())
            office.setPhone(phone);

        if (isActive != null)
            office.setActive(isActive);
    }
}
