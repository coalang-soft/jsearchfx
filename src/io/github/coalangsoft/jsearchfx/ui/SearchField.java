package io.github.coalangsoft.jsearchfx.ui;

import io.github.coalangsoft.jsearch.JSearchEngine;
import io.github.coalangsoft.lib.data.Func;
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
    
    public SearchField<T> autocomplete(JSearchEngine<String> autoCompletion){
    	new AutoComplete().attach(this, autoCompletion);
    	return this;
    }
    
    public SearchField<T> autocomplete(){
    	new AutoComplete().attach(this, autoCompleteEngine(getEngine()));
    	return this;
    }
    
    public static JSearchEngine<String> autoCompleteEngine(JSearchEngine<?> base){
		final JSearchEngine<String> autoComplete = new JSearchEngine<String>();
		base.forAllKeys(new Func<String,Object>(){
			public Object call(String p) {
				autoComplete.add(p, p);
				return null;
			}
		});
		return autoComplete;
	}

}
