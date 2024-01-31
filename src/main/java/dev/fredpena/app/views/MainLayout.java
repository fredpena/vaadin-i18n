package dev.fredpena.app.views;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.avatar.Avatar;
import com.vaadin.flow.component.contextmenu.HasMenuItems;
import com.vaadin.flow.component.contextmenu.MenuItem;
import com.vaadin.flow.component.contextmenu.SubMenu;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.component.menubar.MenuBarVariant;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.Scroller;
import com.vaadin.flow.component.sidenav.SideNav;
import com.vaadin.flow.component.sidenav.SideNavItem;
import com.vaadin.flow.i18n.LocaleChangeEvent;
import com.vaadin.flow.i18n.LocaleChangeObserver;
import com.vaadin.flow.router.HasDynamicTitle;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.theme.lumo.LumoUtility;
import org.vaadin.lineawesome.LineAwesomeIcon;

import java.util.Locale;

/**
 * The main view is a top-level placeholder for other views.
 */
public class MainLayout extends AppLayout implements LocaleChangeObserver {

    private H2 viewTitle;

    private SideNavItem sideNavCustomer;

    private transient MenuItemComponent share;

    public MainLayout() {
        initSideNav();
        // Use the drawer for the menu
        setPrimarySection(Section.DRAWER);
        // Put the menu in the drawer
        addDrawerContent();
        // Make the nav bar a header
        addToNavbar(true, addHeaderContent());
    }


    private void addDrawerContent() {
        H1 appName = new H1("i18N");
        appName.addClassNames(LumoUtility.FontSize.LARGE, LumoUtility.Margin.NONE);

        Image logo = new Image("icons/icon.png", "i18N");
        logo.setWidth("40px");

        // Have a drawer header with an application logo
        HorizontalLayout logoLayout = new HorizontalLayout(logo, appName);
        logoLayout.setAlignItems(FlexComponent.Alignment.CENTER);

        Header header = new Header(logoLayout);

        Scroller scroller = new Scroller(createNavigation());

        addToDrawer(header, scroller, createFooter());
    }

    private void initSideNav() {
        sideNavCustomer = new SideNavItem("", CustomerView.class, LineAwesomeIcon.GLOBE_SOLID.create());
    }

    private SideNav createNavigation() {
        final SideNav nav = new SideNav();

        nav.addItem(sideNavCustomer);

        return nav;
    }

    private Component addHeaderContent() {
        // Configure the header
        HorizontalLayout layout = new HorizontalLayout();
        layout.getThemeList().set("dark", true);
        layout.setWidthFull();
        layout.setAlignItems(FlexComponent.Alignment.CENTER);
        layout.addClassNames(LumoUtility.Height.XLARGE);

        // Have the drawer toggle button on the left
        DrawerToggle toggle = new DrawerToggle();
        toggle.setAriaLabel("Menu toggle");
        layout.add(toggle);

        // Placeholder for the title of the current view.
        // The title will be set after navigation.
        viewTitle = new H2();
        viewTitle.addClassNames(LumoUtility.FontSize.LARGE, LumoUtility.Margin.NONE, LumoUtility.Margin.End.AUTO);
        layout.add(viewTitle);

        MenuBar menuBar = new MenuBar();
        menuBar.addThemeVariants(MenuBarVariant.LUMO_ICON);

        share = createIconItem(menuBar, VaadinIcon.SHARE, "", null);
        SubMenu shareSubMenu = share.menuItem().getSubMenu();

        MenuItem menuItemEnglish = createIconItem(shareSubMenu, "english", "English").menuItem();
        MenuItem menuItemSpanish = createIconItem(shareSubMenu, "spanish", "Spanish").menuItem();
        MenuItem menuItemFinnish = createIconItem(shareSubMenu, "finnish", "Finnish").menuItem();
        MenuItem menuItemFrench = createIconItem(shareSubMenu, "french", "French").menuItem();
        createIconItem(menuBar, VaadinIcon.COG, null, "duplicate");

        menuItemEnglish.addClickListener(click -> getUI().ifPresent(ui -> ui.setLocale(new Locale("en", "GB"))));
        menuItemSpanish.addClickListener(click -> getUI().ifPresent(ui -> ui.setLocale(new Locale("es", "ES"))));
        menuItemFinnish.addClickListener(click -> getUI().ifPresent(ui -> ui.setLocale(new Locale("fi", "FI"))));
        menuItemFrench.addClickListener(click -> getUI().ifPresent(ui -> ui.setLocale(new Locale("fr", "FR"))));


        // A user Avatar
        Avatar avatar = new Avatar("Fred Peña");
        avatar.addClassNames(LumoUtility.Margin.Right.MEDIUM);
        layout.add(menuBar, avatar);

        return layout;
    }


    private Footer createFooter() {
        return new Footer();
    }

    private String getCurrentPageTitle() {
        if (getContent() instanceof HasDynamicTitle title) {
            return title.getPageTitle();
        }
        PageTitle title = getContent().getClass().getAnnotation(PageTitle.class);

        return title == null ? "" : getTranslation(title.value());
    }

    @Override
    public void localeChange(LocaleChangeEvent event) {
        sideNavCustomer.setLabel(getTranslation(TranslationConstant.SIDE_NAV_CUSTOMER));

        share.text().setText(getTranslation(TranslationConstant.TRANSLATION_LANGUAGE));

        viewTitle.setText(getCurrentPageTitle());
    }

    private static MenuItemComponent createIconItem(HasMenuItems menu, VaadinIcon iconName, String label, String ariaLabel) {
        return createIconItem(menu, new Icon(iconName), label, ariaLabel, false);
    }

    private static MenuItemComponent createIconItem(HasMenuItems menu, String iconName, String label) {

        return createIconItem(menu, createIcon(iconName), label, null, true);
    }

    private static MenuItemComponent createIconItem(HasMenuItems menu, Component component, String label, String ariaLabel, boolean isChild) {
        if (isChild) {
            component.getStyle().set("margin-right", "var(--lumo-space-m)");
        }

        MenuItem item = menu.addItem(component, e -> {
        });

        if (ariaLabel != null) {
            item.setAriaLabel(ariaLabel);
        }

        Text text = new Text(label);
        if (label != null) {
            item.add(text);
        }

        return new MenuItemComponent(item, text);
    }

    private static Component createIcon(String name) {
        Image image = new Image("images/%s.png".formatted(name), "");
        image.setMaxWidth("25px");
        return image;
    }

    private record MenuItemComponent(MenuItem menuItem, Text text) {

    }
}
