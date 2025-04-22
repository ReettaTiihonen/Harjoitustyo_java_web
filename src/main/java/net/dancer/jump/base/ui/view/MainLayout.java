package net.dancer.jump.base.ui.view;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.avatar.Avatar;
import com.vaadin.flow.component.avatar.AvatarVariant;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.component.menubar.MenuBarVariant;
import com.vaadin.flow.component.orderedlayout.Scroller;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.sidenav.SideNav;
import com.vaadin.flow.component.sidenav.SideNavItem;
import com.vaadin.flow.router.Layout;
import com.vaadin.flow.server.menu.MenuConfiguration;
import com.vaadin.flow.server.menu.MenuEntry;
import com.vaadin.flow.theme.Theme;

import static com.vaadin.flow.theme.lumo.LumoUtility.*;


@Layout
@CssImport("./themes/default/styles.css") // Import the global styles
public final class MainLayout extends AppLayout {

    MainLayout() {
        // Set the primary section (this could be Drawer or Navbar)
        setPrimarySection(Section.DRAWER);

        // Add header, side navigation, and user menu to the drawer
        addToDrawer(createHeader(), new Scroller(createSideNav()), createUserMenu());

        VerticalLayout mainLayout = new VerticalLayout(createHeader(), new Scroller(createSideNav()), createUserMenu());
        mainLayout.setSizeFull();
        mainLayout.add(createFooter());
        setContent(mainLayout);
    }

    private Div createHeader() {
        // TODO Replace with real application logo and name
        var appLogo = VaadinIcon.CUBES.create();
        appLogo.addClassNames(TextColor.PRIMARY, IconSize.LARGE);

        var appName = new Span("Harjoitustyö");
        appName.addClassNames(FontWeight.SEMIBOLD, FontSize.LARGE);

        var header = new Div(appLogo, appName);
        header.addClassNames("app-header"); // Apply the header style
        return header;
    }

    private SideNav createSideNav() {
        var nav = new SideNav();
        nav.addClassNames("custom-nav"); // Apply the custom side nav style
        MenuConfiguration.getMenuEntries().forEach(entry -> nav.addItem(createSideNavItem(entry)));
        return nav;
    }

    private SideNavItem createSideNavItem(MenuEntry menuEntry) {
        SideNavItem item;
        if (menuEntry.icon() != null) {
            item = new SideNavItem(menuEntry.title(), menuEntry.path(), new Icon(menuEntry.icon()));
        } else {
            item = new SideNavItem(menuEntry.title(), menuEntry.path());
        }
        item.addClassName("side-nav-item"); // Apply the side nav item style
        return item;
    }

    private Div createFooter() {
        Div footer = new Div();
        footer.addClassNames("footer"); // Apply footer style
        footer.add(new Span("© 2025 Harjoitustyö"));
        return footer;
    }

    private Component createUserMenu() {
        // TODO Replace with real user information and actions
        var avatar = new Avatar("Reetta Tiihonen");
        avatar.addThemeVariants(AvatarVariant.LUMO_XSMALL);
        avatar.addClassNames("avatar"); // Apply avatar style
        avatar.setColorIndex(5);

        var userMenu = new MenuBar();
        userMenu.addThemeVariants(MenuBarVariant.LUMO_TERTIARY_INLINE);
        userMenu.addClassNames("user-menu"); // Apply the user menu style

        var userMenuItem = userMenu.addItem(avatar);
        userMenuItem.add("Reetta Tiihonen");
        userMenuItem.getSubMenu().addItem("View Profile");
        userMenuItem.getSubMenu().addItem("Manage Settings");
        userMenuItem.getSubMenu().addItem("Logout");

        return userMenu;
    }
}

