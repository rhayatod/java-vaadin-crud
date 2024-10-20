package com.hayato.vaadin.ui;

import com.hayato.vaadin.backend.model.entity.User;
import com.hayato.vaadin.backend.service.NewUserService;
import com.vaadin.flow.component.button.Button;
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

@Route("user")
public class NewUserView extends VerticalLayout {
    GridCrud<User> grid = new GridCrud<>(User.class);

    @Autowired
    public NewUserView() {
        add(new H1("User List"), grid);
    }

    private void generateReport() {

    }

}
