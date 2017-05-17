package io.github.coalangsoft.jsearchfx.ui;

import javafx.beans.property.Property;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.MenuItem;

/**
 * Created by Matthias on 17.05.2017.
 */
public class AutoCompleteMenu extends MenuItem {

    public AutoCompleteMenu(String value, Property<String> property) {
        super(value);
        setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                property.setValue(value);
            }
        });
    }

}
