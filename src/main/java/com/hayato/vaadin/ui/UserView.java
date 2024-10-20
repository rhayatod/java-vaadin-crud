package com.hayato.vaadin.ui;

import com.hayato.vaadin.backend.model.entity.User;
import com.hayato.vaadin.backend.service.NewUserService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.renderer.NativeButtonRenderer;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.vaadin.crudui.crud.impl.GridCrud;

import java.util.List;

@Route("")
public class UserView extends VerticalLayout {
    private final NewUserService userService;
    Grid<User> grid = new Grid<>(User.class);

    @Autowired
    public UserView(NewUserService userService) {
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
//        //var grid = new Grid<>(User.class);
//        add(new H1("User List"), grid);
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

    private void setData() {
//        List<User> people = userService.findAll();
//        grid.setItems(people);
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
        userService.deleteById(user.getUserid());
    }

    private void generateReport() {

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
            var newUser = checkUser(user);
            newUser.setUsername(nameField.getValue());
            newUser.setEmail(emailField.getValue());

            if (user == null) {
                newUser.setCreateUser("System");
                newUser.setUpdateUser("System");
            } else {
                newUser.setUpdateUser("System");
            }

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
}
