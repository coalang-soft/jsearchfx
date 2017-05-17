package io.github.coalangsoft.jsearchfx.ui;

import io.github.coalangsoft.jsearch.JSearchEngine;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.TextField;

/**
 * Created by Matthias on 17.05.2017.
 */
public abstract class SearchField<T> extends TextField {

    public SearchField(){
        setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                searchOnAction(getText());
            }
        });
        textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                searchOnChange(newValue);
            }
        });
    }

    protected abstract void searchOnChange(String newValue);
    protected abstract void searchOnAction(String text);

    public abstract JSearchEngine<T> getEngine();
    public abstract void setEngine(JSearchEngine<T> engine);

}
