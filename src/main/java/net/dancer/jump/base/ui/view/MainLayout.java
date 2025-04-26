package net.dancer.jump.base.ui.view;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.avatar.Avatar;
import com.vaadin.flow.component.avatar.AvatarVariant;
import com.vaadin.flow.component.charts.model.Label;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.component.menubar.MenuBarVariant;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.sidenav.SideNav;
import com.vaadin.flow.component.sidenav.SideNavItem;
import com.vaadin.flow.router.Layout;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.theme.lumo.Lumo;
import com.vaadin.flow.theme.Theme;

import net.dancer.jump.taskmanagement.ui.view.*;

import static com.vaadin.flow.theme.lumo.LumoUtility.*;


@Layout
@CssImport("./themes/default/styles.css")
public class MainLayout extends AppLayout {

    public MainLayout() {
        setPrimarySection(Section.DRAWER);
        addToDrawer(createHeader(), createSideNav(), createUserMenu());
        Div footer = createFooter();
        getElement().appendChild(footer.getElement());
    }

    private Div createHeader() {
        Icon logo = VaadinIcon.CUBES.create();
        logo.addClassNames(TextColor.PRIMARY, IconSize.LARGE);

        Span title = new Span("Harjoitustyö");
        title.addClassNames(FontWeight.BOLD, FontSize.XLARGE);

        Div header = new Div(logo, title);
        header.addClassNames("app-header", Display.FLEX, AlignItems.CENTER, Gap.MEDIUM, Padding.SMALL);
        return header;
    }

    private SideNav createSideNav() {
        SideNav nav = new SideNav();
        nav.addClassName("custom-nav");

        nav.addItem(new SideNavItem("Home", MainView.class, VaadinIcon.HOME.create()));
        nav.addItem(new SideNavItem("Person", PersonView.class, VaadinIcon.USER.create()));
        nav.addItem(new SideNavItem("Address", AddressView.class, VaadinIcon.MAP_MARKER.create()));
        nav.addItem(new SideNavItem("Event", EventView.class, VaadinIcon.CALENDAR.create()));
        nav.addItem(new SideNavItem("Participant", ParticipantView.class, VaadinIcon.GROUP.create()));

        return nav;
    }

    private Component createUserMenu() {
        Avatar avatar = new Avatar("Reetta Tiihonen");
        avatar.addThemeVariants(AvatarVariant.LUMO_XSMALL);
        avatar.addClassName("avatar");
        avatar.setColorIndex(5);

        MenuBar menuBar = new MenuBar();
        menuBar.addThemeVariants(MenuBarVariant.LUMO_TERTIARY_INLINE);
        menuBar.addClassName("user-menu");

        var menuItem = menuBar.addItem(avatar);
        menuItem.add("Reetta Tiihonen");
        menuItem.getSubMenu().addItem("View Profile");
        menuItem.getSubMenu().addItem("Manage Settings");
        menuItem.getSubMenu().addItem("Logout", e -> getUI().ifPresent(ui -> ui.navigate("login")));

        return menuBar;
    }
    private Div createFooter() {
        Icon icon = VaadinIcon.CHECK.create();
        icon.setSize("16px"); // Asetetaan kuvakkeen koko
        icon.getStyle().set("color", "green"); // Väri (success)

        // Luodaan FlexLayout, johon lisätään kuvake ja teksti
        FlexLayout footer = new FlexLayout(icon);
        footer.setAlignItems(FlexComponent.Alignment.CENTER); // Keskitetään sisältö

        // Lisätään välimatkaa ja täytettä
        footer.getStyle().set("padding", "10px");
        footer.getStyle().set("gap", "10px");

        // Asetetaan taustaväri ja varjo
        footer.getStyle().set("background-color", "#6200ea"); // Tummanvioletti
        footer.getStyle().set("box-shadow", "0px 4px 10px rgba(0, 0, 0, 0.1)");

        // Palautetaan footer Div-tyyppinä
        Div footerDiv = new Div(footer);
        return footerDiv;
    }
}


