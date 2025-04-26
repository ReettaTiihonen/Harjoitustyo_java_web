package net.dancer.jump.taskmanagement.ui.view;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Menu;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.Theme;
import jakarta.annotation.security.RolesAllowed;
import net.dancer.jump.base.ui.view.MainLayout;
import net.dancer.jump.taskmanagement.domain.Person;
import net.dancer.jump.taskmanagement.service.PersonService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@CssImport("./themes/default/custom-styles.css")
@Route(value = "person", layout = MainLayout.class)
@Menu(order = 1, title = "Person")
@RolesAllowed({"USER", "ADMIN"})
public class PersonView extends VerticalLayout {

    private final PersonService personService;
    private final Grid<Person> grid = new Grid<>(Person.class);
    private Person selectedPerson = null;

    public PersonView(PersonService personService) {
        this.personService = personService;

        // Grid setup
        grid.setColumns("id", "name", "age");
        grid.addClassName("my-grid");
        refreshGrid();

        // Input fields
        TextField nameField = new TextField("Name");
        TextField ageField = new TextField("Age");
        nameField.addClassName("my-text-field");
        ageField.addClassName("my-text-field");

        // Search field
        TextField searchField = new TextField("Search by Name");
        searchField.addClassName("my-text-field");
        searchField.setPlaceholder("Type to search...");
        searchField.setClearButtonVisible(true);
        searchField.addValueChangeListener(e -> {
            String searchTerm = e.getValue().toLowerCase();
            List<Person> filtered = StreamSupport
                    .stream(personService.findAll().spliterator(), false)
                    .filter(p -> p.getName().toLowerCase().contains(searchTerm))
                    .collect(Collectors.toList());
            grid.setItems(filtered);
        });

        // Filter field (1 pisteen vaatimus)
        TextField nameFilter = new TextField("Filter by name");
        nameFilter.addClassName("my-text-field");
        nameFilter.setPlaceholder("Type name to filter...");
        nameFilter.setClearButtonVisible(true);
        nameFilter.addValueChangeListener(e -> {
            String filter = e.getValue().toLowerCase();
            List<Person> filtered = StreamSupport
                    .stream(personService.findAll().spliterator(), false)
                    .filter(p -> p.getName().toLowerCase().contains(filter))
                    .collect(Collectors.toList());
            grid.setItems(filtered);
        });

        // Grid selection listener
        grid.asSingleSelect().addValueChangeListener(event -> {
            selectedPerson = event.getValue();
            if (selectedPerson != null) {
                nameField.setValue(selectedPerson.getName());
                ageField.setValue(String.valueOf(selectedPerson.getAge()));
            } else {
                nameField.clear();
                ageField.clear();
            }
        });

        // Save button
        Button saveButton = new Button("Save", e -> {
            try {
                String name = nameField.getValue();
                int age = Integer.parseInt(ageField.getValue());

                if (selectedPerson != null) {
                    selectedPerson.setName(name);
                    selectedPerson.setAge(age);
                    personService.save(selectedPerson);
                } else {
                    Person person = new Person(name, age);
                    personService.save(person);
                }

                nameField.clear();
                ageField.clear();
                selectedPerson = null;
                refreshGrid();
            } catch (NumberFormatException ex) {
                ageField.setInvalid(true);
                ageField.setErrorMessage("Invalid number");
            }
        });
        saveButton.addClassName("my-main-button");
        // Delete button
        Button deleteButton = new Button("Delete", e -> {
            if (selectedPerson != null) {
                personService.delete(selectedPerson);
                selectedPerson = null;
                nameField.clear();
                ageField.clear();
                refreshGrid();
            }
        });
        deleteButton.addClassName("my-highlighted-button");
        deleteButton.getStyle().set("background-color", "red");
        deleteButton.getStyle().set("color", "white");

        // Layouts
        HorizontalLayout formLayout = new HorizontalLayout(nameField, ageField, saveButton, deleteButton);
        HorizontalLayout searchFilterLayout = new HorizontalLayout(searchField, nameFilter);

        add(formLayout, searchFilterLayout, grid);
    }

    private void refreshGrid() {
        List<Person> people = StreamSupport
                .stream(personService.findAll().spliterator(), false)
                .collect(Collectors.toList());
        grid.setItems(people);
    }
}

