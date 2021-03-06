package io.github.coalangsoft.jsearchfx.ui;

import io.github.coalangsoft.jsearch.ISearchEngine;
import io.github.coalangsoft.jsearch.JSearchEngine;
import io.github.coalangsoft.jsearch.JSearchResult;
import io.github.coalangsoft.jsearchfx.CollectionSearch;
import io.github.coalangsoft.lib.data.Func;
import javafx.beans.property.Property;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;

/**
 * Created by Matthias on 17.05.2017.
 */
public class CollectionSearchField<T> extends SearchField<T>{

    private final ObservableList<T> orig;
    private ISearchEngine<T> se;
    private final Property<ObservableList<T>> prop;
    private final boolean onChange;

    public CollectionSearchField(Property<ObservableList<T>> prop, Func<T,String> keyFactory, boolean onChange){
        this.orig = prop.getValue();
        this.se = CollectionSearch.createEngine(orig, keyFactory);
        this.prop = prop;
        this.onChange = onChange;
    }

    @Override
    protected void searchOnChange(String newValue) {
        if(onChange && newValue != null){
            act(newValue.split("\\s+"));
        }
    }

    @Override
    protected void searchOnAction(String text) {
        act(text.split("\\s+"));
    }

    private void act(String[] split) {
        if(getText().isEmpty()){
            prop.setValue(orig);
            return;
        }
        List<JSearchResult<T>> res = se.query(split);
        prop.setValue(FXCollections.<T>observableArrayList());
        for(int i = 0; i < res.size(); i++){
            prop.getValue().add(res.get(i).getValue());
        }
    }

    @Override
    public ISearchEngine<T> getEngine() {
        return se;
    }

    @Override
    public void setEngine(ISearchEngine<T> engine) {
        se = engine;
    }
}
