package com.hayato.vaadin.ui;

import com.hayato.vaadin.backend.model.entity.User;
import com.hayato.vaadin.backend.service.UserService;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.confirmdialog.ConfirmDialog;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;

@Route("")
public class UserView extends VerticalLayout {
    private final UserService userService;
    Grid<User> grid = new Grid<>(User.class);

    @Autowired
    public UserView(UserService userService) {
        this.userService = userService;

        Button addNewUserButton = new Button("New User", e -> newUser());
        Button generateReportButton = new Button("Generate Report", e -> generateReport());

        HorizontalLayout layout = new HorizontalLayout();
        layout.setPadding(true);
        layout.add(addNewUserButton);
        layout.add(generateReportButton);

        setupColumn();
        add(layout, grid);
        refreshGrid();
    }

    private void setupColumn() {
        grid.setItems(userService.findAll());
        grid.setColumns("userid", "username", "email", "createDate", "updateDate");

        grid.addComponentColumn(person -> {
            Button editButton = new Button("Edit");
            editButton.addClickListener(e -> editUser(person));

            Button deleteButton = new Button("Delete");
            deleteButton.addClickListener(e -> deleteUser(person));

            return new HorizontalLayout(editButton, deleteButton);
        }).setHeader("Manage");
    }

    private void newUser() {
        Dialog dialog = createUserDialog(null);
        dialog.open();
    }
    private void editUser(User user) {
        Dialog dialog = createUserDialog(user);
        dialog.open();
    }

    private void deleteUser(User user) {
        ConfirmDialog dialog = new ConfirmDialog();
        dialog.setHeader("Delete User");
        dialog.setText(
                String.format("Are you sure you want to delete user %s?", user.getUsername()));

        dialog.setCancelable(true);
        dialog.addCancelListener(event -> dialog.close());

        dialog.setConfirmText("Delete");
        dialog.setConfirmButtonTheme("error primary");
        dialog.addConfirmListener(event -> {
            userService.deleteById(user.getUserid());
            refreshGrid();
        });
        dialog.open();
    }

    @Async
    private void generateReport()  {
//        try{
////            System.out.println("start generating "+ fileName);
//            long startTime = System.nanoTime();
//            JasperPrint jasperPrint = JasperFillManager.fillReport(url, params, conn);
//            SimplePdfExporterConfiguration config = new SimplePdfExporterConfiguration();
//            JRPdfExporter exporter = new JRPdfExporter();
//            if ( (boolean) params.get("isClientStatement")){
//                String notsusUrl = new ClassPathResource("templates/jasper/ClientStatement/"+ "NotasiKhusus" + ".jasper").getURL().getPath();
////                String notsusUrl = new ClassPathResource("templates/jasper/ClientStatement/"+ "NotasiKhusus" + ".jasper").getURL().getPath();
////                JasperPrint notsusPrint = JasperFillManager.fillReport(notsusUrl, params, conn);
//                List jprintlist = new ArrayList();
//                jprintlist.add(jasperPrint);
////                jprintlist.add(notsusPrint);
//                exporter.setExporterInput(SimpleExporterInput.getInstance(jprintlist));
//            }
//
//            String filePath = pathOutput + fileName + ".pdf";
//            exporter.setConfiguration(config);
//            exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(filePath));
//
//            exporter.exportReport();
//            long endTime   = System.nanoTime();
//            double totalTime = (endTime - startTime)/1000000000;
//            System.out.println("done generating "+ fileName + " in " + totalTime + " seconds");
//
//        } catch (Exception e){
//            System.out.println("error generating sid " + params.get("sid"));
//            System.out.println(e.getMessage());
//        }
        }

    private void refreshGrid() {
        grid.setItems(userService.findAll());
    }

    private Dialog createUserDialog(User user) {
        Dialog dialog = new Dialog();
        TextField nameField = new TextField("userName");
        TextField emailField = new TextField("Email");

        if (user != null) {
            nameField.setValue(user.getUsername());
            emailField.setValue(user.getEmail());
        }

        Button saveButton = new Button("Save", e -> {

            if (user == null) {
                var isExist = userService.findByUsernameOrEmail(nameField.getValue(), emailField.getValue());

                if (isExist) {
                    showNotificationError("Username or email already exists");
                    return;
                }
            }

            var newUser = checkUser(user);
            newUser.setUsername(nameField.getValue());
            newUser.setEmail(emailField.getValue());
            newUser.setCreateUser("System");
            newUser.setUpdateUser("System");

            userService.save(newUser);
            refreshGrid();
            dialog.close();
        });

        Button cancelButton = new Button("Cancel", e -> dialog.close());

        dialog.add(nameField, emailField, saveButton, cancelButton);
        dialog.setWidth("300px");
        return dialog;
    }

    private User checkUser(User user) {
        if (user == null) {
            user = new User();
        }

        return user;
    }

    private void showNotificationError(String textLabel) {
        Notification notification = new Notification();
        notification.addThemeVariants(NotificationVariant.LUMO_ERROR);

        Div text = new Div(new Text(textLabel));

        Button closeButton = new Button(new Icon("lumo", "cross"));
        closeButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY_INLINE);
        closeButton.setAriaLabel("Close");
        closeButton.addClickListener(event -> {
            notification.close();
        });

        HorizontalLayout layout = new HorizontalLayout(text, closeButton);
        layout.setAlignItems(Alignment.CENTER);

        notification.add(layout);
        notification.open();
    }
}
