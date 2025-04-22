package net.dancer.jump.base.ui.view;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Main;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.LumoUtility;
import net.dancer.jump.taskmanagement.ui.view.PersonView;

import javax.swing.*;
import java.nio.Buffer;

/**
 * This view shows up when a user navigates to the root ('/') of the application.
 */

@CssImport("./themes/default/styles.css")
@Route("")
public final class MainView extends VerticalLayout {

    public MainView() {
        // Add padding style
        addClassName("app-layout");

        // Create a welcome message
        Div welcomeMsg = new Div(new Text("Welcome to my application!"));
        welcomeMsg.addClassName("welcome-message");

        // En lisää näitä näkymää, koska sivussa on navigointipalkki.
        Button personButton = new Button("Person", e -> UI.getCurrent().navigate(net.dancer.jump.taskmanagement.ui.view.PersonView.class));
        personButton.addClassName("nav-button");
        Button addressButton = new Button("Address", e -> UI.getCurrent().navigate(net.dancer.jump.taskmanagement.ui.view.AddressView.class));
        addressButton.addClassName("nav-button");
        Button eventButton = new Button("Event", e -> UI.getCurrent().navigate(net.dancer.jump.taskmanagement.ui.view.EventView.class));
        eventButton.addClassName("nav-button");
        Button participantButton = new Button("Participant", e -> UI.getCurrent().navigate(net.dancer.jump.taskmanagement.ui.view.ParticipantView.class));
        participantButton.addClassName("nav-button");

        add(welcomeMsg);
    }

    /**
     * Navigates to the main view.
     */
    public static void showMainView() {
        UI.getCurrent().navigate(MainView.class);
    }
}

