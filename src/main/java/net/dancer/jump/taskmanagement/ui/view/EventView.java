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
import net.dancer.jump.taskmanagement.domain.Event;
import net.dancer.jump.taskmanagement.domain.Participant;
import net.dancer.jump.taskmanagement.service.EventService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.StreamSupport;

@CssImport("./themes/default/custom-styles.css")
@Menu(order = 3, title = "Event")
@Route("event")
public class EventView extends VerticalLayout {

    private final EventService eventService;
    private final Grid<Event> grid = new Grid<>(Event.class);

    private Event selectedEvent = null;

    public EventView(EventService eventService) {
        this.eventService = eventService;

        // Grid setup
        grid.setColumns("eventName", "location");
        grid.addClassName("my-grid");
        refreshGrid();

        // Input fields for event name and location
        TextField nameField = new TextField("Event Name");
        TextField locationField = new TextField("Location");
        nameField.addClassName("my-text-field");
        locationField.addClassName("my-text-field");

        // Search field for searching by event name or location
        TextField searchField = new TextField("Search by Event Name");
        searchField.addClassName("my-text-field");
        searchField.setPlaceholder("Type to search...");
        searchField.setClearButtonVisible(true);

        // Search event handling
        searchField.addValueChangeListener(e -> {
            String searchTerm = e.getValue().toLowerCase();
            List<Event> filtered = StreamSupport
                    .stream(eventService.findAll().spliterator(), false)
                    .filter(event -> event.getEventName().toLowerCase().contains(searchTerm) ||
                            event.getLocation().toLowerCase().contains(searchTerm))
                    .toList();
            grid.setItems(filtered);
        });

        // Handle selection from the grid
        grid.asSingleSelect().addValueChangeListener(event -> {
            selectedEvent = event.getValue();
            if (selectedEvent != null) {
                nameField.setValue(selectedEvent.getEventName());
                locationField.setValue(selectedEvent.getLocation());
            } else {
                nameField.clear();
                locationField.clear();
            }
        });

        // Save button - either updates existing event or creates new one
        Button saveButton = new Button("Save", e -> {
            if (selectedEvent == null) {
                selectedEvent = new Event();
            }
            selectedEvent.setEventName(nameField.getValue());
            selectedEvent.setLocation(locationField.getValue());

            eventService.save(selectedEvent);
            selectedEvent = null;  // Reset selected event after saving
            clearFields(nameField, locationField);
            refreshGrid();
        });
        saveButton.addClassName("my-main-button");

        // Delete button - deletes the selected event
        Button deleteButton = new Button("Delete", e -> {
            if (selectedEvent != null) {
                eventService.delete(selectedEvent);
                selectedEvent = null;
                clearFields(nameField, locationField);
                refreshGrid();
            }
        });
        deleteButton.addClassName("my-highlighted-button");
        deleteButton.getStyle().set("background-color", "red");
        deleteButton.getStyle().set("color", "white");
        // Layout for the form (name, location fields and buttons)
        HorizontalLayout form = new HorizontalLayout(searchField, nameField, locationField, saveButton, deleteButton);
        add(form, grid);
    }

    // Refresh the grid to show updated data
    private void refreshGrid() {
        List<Event> events = new ArrayList<>();
        eventService.findAll().forEach(events::add);
        grid.setItems(events);
    }

    // Helper method to clear input fields
    private void clearFields(TextField... fields) {
        for (TextField f : fields) {
            f.clear();
        }
    }
}