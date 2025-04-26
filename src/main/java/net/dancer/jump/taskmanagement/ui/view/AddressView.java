package net.dancer.jump.taskmanagement.ui.view;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Menu;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.Theme;
import jakarta.annotation.security.RolesAllowed;
import net.dancer.jump.base.ui.view.MainLayout;
import net.dancer.jump.taskmanagement.domain.Address;
import net.dancer.jump.taskmanagement.service.AddressService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.StreamSupport;

@CssImport("./themes/default/custom-styles.css")
@Route(value = "address", layout = MainLayout.class)
@Menu(order = 2, title = "Address")
@RolesAllowed({"USER", "ADMIN"})
public class AddressView extends VerticalLayout {

    private final AddressService addressService;
    private final Grid<Address> grid = new Grid<>(Address.class);

    private Address selectedAddress = null;

    public AddressView(AddressService addressService) {
        this.addressService = addressService;

        grid.setColumns("street", "city", "zipCode");
        grid.addClassName("my-grid");
        refreshGrid();

        TextField streetField = new TextField("Street");
        TextField cityField = new TextField("City");
        TextField zipField = new TextField("Zip Code");
        streetField.addClassName("my-text-field");
        cityField.addClassName("my-text-field");
        zipField.addClassName("my-text-field");

        // Search functionality
        TextField searchField = new TextField("Search by City");
        searchField.addClassName("my-text-field");
        searchField.setPlaceholder("Type to search...");
        searchField.setClearButtonVisible(true);

        searchField.addValueChangeListener(e -> {
            String searchTerm = e.getValue().toLowerCase();
            List<Address> filtered = StreamSupport
                    .stream(addressService.findAll().spliterator(), false)
                    .filter(a -> a.getCity().toLowerCase().contains(searchTerm))
                    .toList();
            grid.setItems(filtered);
        });

        grid.asSingleSelect().addValueChangeListener(event -> {
            selectedAddress = event.getValue();
            if (selectedAddress != null) {
                streetField.setValue(selectedAddress.getStreet());
                cityField.setValue(selectedAddress.getCity());
                zipField.setValue(selectedAddress.getZipCode());
            }
        });

        Button saveButton = new Button("Save", e -> {
            if (selectedAddress == null) {
                selectedAddress = new Address();
            }
            selectedAddress.setStreet(streetField.getValue());
            selectedAddress.setCity(cityField.getValue());
            selectedAddress.setZipCode(zipField.getValue());

            addressService.save(selectedAddress);
            selectedAddress = null;
            clearFields(streetField, cityField, zipField);
            refreshGrid();
        });
        saveButton.addClassName("my-main-button");

        Button deleteButton = new Button("Delete", e -> {
            if (selectedAddress != null) {
                addressService.delete(selectedAddress);
                selectedAddress = null;
                clearFields(streetField, cityField, zipField);
                refreshGrid();
            }
        });
        deleteButton.addClassName("my-highlighted-button");
        deleteButton.getStyle().set("background-color", "red");
        deleteButton.getStyle().set("color", "white");

        HorizontalLayout formLayout = new HorizontalLayout(searchField, streetField, cityField, zipField, saveButton, deleteButton);
        add(formLayout, grid);
    }

    private void refreshGrid() {
        List<Address> addresses = new ArrayList<>();
        addressService.findAll().forEach(addresses::add);
        grid.setItems(addresses);
    }

    private void clearFields(TextField... fields) {
        for (TextField f : fields) {
            f.clear();
        }
    }
}
