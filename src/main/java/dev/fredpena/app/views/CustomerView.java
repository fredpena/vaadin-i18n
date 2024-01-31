package dev.fredpena.app.views;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.confirmdialog.ConfirmDialog;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.i18n.LocaleChangeEvent;
import com.vaadin.flow.i18n.LocaleChangeObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import com.vaadin.flow.theme.lumo.LumoUtility;

@PageTitle(TranslationConstant.TITLE_PAGE_CUSTOMER)
@Route(value = "customer", layout = MainLayout.class)
@RouteAlias(value = "", layout = MainLayout.class)
public class CustomerView extends VerticalLayout implements LocaleChangeObserver {

    private final TextField firstName = new TextField();
    private final TextField lastName = new TextField();
    private final EmailField email = new EmailField();
    private final TextField phone = new TextField();
    private final DatePicker dateOfBirth = new DatePicker();
    private final TextField occupation = new TextField();
    private final ComboBox<String> fieldRole = new ComboBox<>();
    private final Checkbox important = new Checkbox();

    private final Button delete = new Button();
    private final Button cancel = new Button();
    private final Button save = new Button();

    public CustomerView() {

        delete.addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_ERROR, ButtonVariant.LUMO_SMALL);

        cancel.addThemeVariants(ButtonVariant.LUMO_SMALL);

        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_SMALL);

        fieldRole.setItems("Worker", "Supervisor", "Manager", "External");

        save.addClickListener(this::saveOrUpdate);

        delete.addClickListener(this::delete);

        add(createFormLayout(), createButtonLayout());
    }

    private void saveOrUpdate(ClickEvent<Button> buttonClickEvent) {

            var confirmDialog = new ConfirmDialog();
            confirmDialog.setHeader(getTranslation(TranslationConstant.CONFIRM_DIALOG_UNSAVED_CHANGES));
            confirmDialog.setText(getTranslation(TranslationConstant.CONFIRM_DIALOG_DISCARD_MESSAGE));

            confirmDialog.setRejectable(true);
            confirmDialog.setRejectText(getTranslation(TranslationConstant.CONFIRM_DIALOG_DISCARD));

            confirmDialog.setConfirmText(getTranslation(TranslationConstant.CONFIRM_DIALOG_CONTINUE));
            confirmDialog.addConfirmListener(event -> notificationSuccess(getTranslation(TranslationConstant.CONFIRM_DIALOG_TRANSACTION_SUCCESSFUL)));

            confirmDialog.open();
    }

    private void delete(ClickEvent<Button> buttonClickEvent) {
        var confirmDialog = new ConfirmDialog();
        confirmDialog.setHeader(getTranslation(TranslationConstant.CONFIRM_DIALOG_DELETING_RECORD));
        confirmDialog.setText(getTranslation(TranslationConstant.CONFIRM_DIALOG_CONFIRM_DELETING_RECORD));

        confirmDialog.setRejectable(true);
        confirmDialog.setRejectText(getTranslation(TranslationConstant.CONFIRM_DIALOG_DISCARD));
        confirmDialog.setRejectButtonTheme("tertiary");

        confirmDialog.setConfirmText(getTranslation(TranslationConstant.CONFIRM_DIALOG_CONTINUE));
        confirmDialog.setConfirmButtonTheme("error primary");
        confirmDialog.addConfirmListener(event -> notificationSuccess(getTranslation(TranslationConstant.CONFIRM_DIALOG_RECORD_DELETED)));

        confirmDialog.open();

    }


    private FormLayout createFormLayout() {
        FormLayout formLayout = new FormLayout();
        formLayout.add(firstName, lastName, email, phone, dateOfBirth, occupation, fieldRole, important);
        formLayout.setResponsiveSteps(
                // Use one column by default
                new FormLayout.ResponsiveStep("0", 1),
                // Use two columns, if layout's width exceeds 500px
                new FormLayout.ResponsiveStep("500px", 2)
        );
        formLayout.addClassNames(LumoUtility.MaxWidth.SCREEN_MEDIUM, LumoUtility.AlignSelf.CENTER);
        return formLayout;
    }

    private HorizontalLayout createButtonLayout() {
        var deleteDiv = new Div();
        deleteDiv.addClassNames(LumoUtility.Margin.End.AUTO);
        deleteDiv.add(delete);

        var buttonLayout = new HorizontalLayout();
        buttonLayout.setWidthFull();
        buttonLayout.addClassNames(LumoUtility.Padding.MEDIUM, LumoUtility.FlexWrap.WRAP, LumoUtility.JustifyContent.CENTER);
        buttonLayout.addClassNames(LumoUtility.MaxWidth.SCREEN_MEDIUM, LumoUtility.AlignSelf.CENTER);
        buttonLayout.add(deleteDiv, cancel, save);
        buttonLayout.setAlignItems(FlexComponent.Alignment.END);
        return buttonLayout;
    }

    @Override
    public void localeChange(LocaleChangeEvent event) {
        firstName.setLabel(getTranslation(TranslationConstant.FIELD_FIRST_NAME));
        lastName.setLabel(getTranslation(TranslationConstant.FIELD_LAST_NAME));
        email.setLabel(getTranslation(TranslationConstant.FIELD_EMAIL));
        phone.setLabel(getTranslation(TranslationConstant.FIELD_PHONE_NUMBER));
        dateOfBirth.setLabel(getTranslation(TranslationConstant.FIELD_BIRTHDAY));
        occupation.setLabel(getTranslation(TranslationConstant.FIELD_OCCUPATION));
        fieldRole.setLabel(getTranslation(TranslationConstant.FIELD_ROLE));
        important.setLabel(getTranslation(TranslationConstant.FIELD_IS_IMPORTANT));

        delete.setText(getTranslation(TranslationConstant.BUTTON_DELETE));
        cancel.setText(getTranslation(TranslationConstant.BUTTON_CANCEL));
        save.setText(getTranslation(TranslationConstant.BUTTON_SAVE));

    }

    public static void notificationSuccess(String msg) {
        final Notification notification = new Notification();
        notification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);

        notification(notification, msg);
    }

    private static void notification(Notification notification, String msg) {
        notification.setDuration(3000);
        notification.setPosition(Notification.Position.TOP_END);

        Icon icon = VaadinIcon.CHECK_CIRCLE.create();

        final Button closeButton = new Button(new Icon("lumo", "cross"), event -> notification.close());
        closeButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY_INLINE);
        closeButton.getElement().setAttribute("aria-label", "Close");
        closeButton.getStyle().setMargin("0 0 0 var(--lumo-space-l)");

        final HorizontalLayout layout = new HorizontalLayout(icon, new Text(msg), closeButton);
        layout.setAlignItems(FlexComponent.Alignment.CENTER);

        notification.add(layout);
        notification.open();
    }
}
