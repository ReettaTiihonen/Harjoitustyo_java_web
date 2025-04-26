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
import net.dancer.jump.taskmanagement.domain.Participant;
import net.dancer.jump.taskmanagement.service.ParticipantService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.StreamSupport;

@CssImport("./themes/default/custom-styles.css")
@Route(value = "participant", layout = MainLayout.class)
@Menu(order = 4, title = "Participant")
@RolesAllowed("ADMIN")
public class ParticipantView extends VerticalLayout {

    private final ParticipantService participantService;
    private final Grid<Participant> grid = new Grid<>(Participant.class);

    private Participant selectedParticipant = null;

    public ParticipantView(ParticipantService participantService) {
        this.participantService = participantService;

        // Grid setup
        grid.setColumns("name");
        grid.addClassName("my-grid");
        refreshGrid();

        // Input field for participant name
        TextField nameField = new TextField("Name");
        nameField.addClassName("my-textfield");

        // Search field for searching by name
        TextField searchField = new TextField("Search by Name");
        searchField.addClassName("my-textfield");
        searchField.setPlaceholder("Type to search...");
        searchField.setClearButtonVisible(true);

        // Search participant handling
        searchField.addValueChangeListener(e -> {
            String searchTerm = e.getValue().toLowerCase();
            List<Participant> filtered = StreamSupport
                    .stream(participantService.findAll().spliterator(), false)
                    .filter(participant -> participant.getName().toLowerCase().contains(searchTerm))
                    .toList();
            grid.setItems(filtered);
        });

        // Handle selection from the grid
        grid.asSingleSelect().addValueChangeListener(event -> {
            selectedParticipant = event.getValue();
            if (selectedParticipant != null) {
                nameField.setValue(selectedParticipant.getName());
            } else {
                nameField.clear();
            }
        });

        // Save button - either updates existing participant or creates a new one
        Button saveButton = new Button("Save", e -> {
            if (selectedParticipant == null) {
                selectedParticipant = new Participant();
            }
            selectedParticipant.setName(nameField.getValue());

            participantService.save(selectedParticipant);
            selectedParticipant = null;  // Reset selected participant after saving
            clearFields(nameField);
            refreshGrid();
        });
        saveButton.addClassName("my-main-button");
        // Delete button - deletes the selected participant
        Button deleteButton = new Button("Delete", e -> {
            if (selectedParticipant != null) {
                participantService.delete(selectedParticipant);
                selectedParticipant = null;
                clearFields(nameField);
                refreshGrid();
            }
        });
        deleteButton.addClassName("my-highlighted-button");
        deleteButton.getStyle().set("background-color", "red");
        deleteButton.getStyle().set("color", "white");
        // Layout for the form (name field and buttons)
        HorizontalLayout form = new HorizontalLayout(searchField, nameField, saveButton, deleteButton);
        add(form, grid);
    }

    // Refresh the grid to show updated data
    private void refreshGrid() {
        List<Participant> participants = new ArrayList<>();
        participantService.findAll().forEach(participants::add);
        grid.setItems(participants);
    }

    // Helper method to clear input fields
    private void clearFields(TextField... fields) {
        for (TextField f : fields) {
            f.clear();
        }
    }
}