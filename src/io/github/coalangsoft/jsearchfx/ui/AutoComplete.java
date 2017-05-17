package io.github.coalangsoft.jsearchfx.ui;

import io.github.coalangsoft.jsearch.JSearchEngine;
import io.github.coalangsoft.jsearch.JSearchResult;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Side;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.TextInputControl;

import java.util.ArrayList;

/**
 * Created by Matthias on 17.05.2017.
 */
public class AutoComplete {

    private int entryMax = 10;
    private ContextMenu popup;

    public AutoComplete(){
        popup = new ContextMenu();
    }

    public void setEntryMax(int max){
        this.entryMax = max;
    }

    public void attach(TextInputControl in, JSearchEngine<String> completion){
        in.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                ArrayList<JSearchResult<String>> values = completion.query(newValue.split("\\s+"));
                popup.getItems().clear();
                for(int i = 0; i < (values.size() < entryMax ? values.size() : entryMax); i++){
                    popup.getItems().add(new AutoCompleteMenu(values.get(i).getValue(), in.textProperty()));
                }
                if(!popup.isShowing()){
                    popup.show(in, Side.BOTTOM,0,0);
                }
            }
        });
    }

}
