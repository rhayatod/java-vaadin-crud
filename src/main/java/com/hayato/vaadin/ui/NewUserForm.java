package com.hayato.vaadin.ui;

import com.hayato.vaadin.backend.model.entity.User;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;

@Route("new-user-form")
public class NewUserForm extends Div {
    TextField username = new TextField("User name");
    EmailField email = new EmailField("Email");

    FormLayout formLayout = new FormLayout(username, email);
    //formLayout.add(username, email);

    //add(formLayout);
}
