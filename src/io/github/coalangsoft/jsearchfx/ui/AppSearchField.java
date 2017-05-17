package io.github.coalangsoft.jsearchfx.ui;

import java.util.ArrayList;

import io.github.coalangsoft.jsearch.JSearchEngine;
import io.github.coalangsoft.jsearch.JSearchResult;
import io.github.coalangsoft.jsearchfx.JSearchFX;
import io.github.coalangsoft.jsearchfx.NodeSearch;
import io.github.coalangsoft.jsearchfx.SearchEffectChangeListener;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.effect.Effect;

public class AppSearchField extends SearchField<NodeSearch>{

	private final boolean focusSearch;
	private JSearchEngine<NodeSearch> engine;

	public AppSearchField(JSearchEngine<NodeSearch> engine, Effect highlight, boolean focusSearch){
		this.engine = engine;
		this.focusSearch = focusSearch;
		
		if(highlight != null){
			textProperty().addListener(new SearchEffectChangeListener(engine, highlight));
		}
	}
	
	public static AppSearchField make(Node root, Effect highlight, boolean focusSearch){
		return new AppSearchField(new JSearchFX().createSearchEngine(root), highlight, focusSearch);
	}

	@Override
	protected void searchOnChange(String newValue) {}

	@Override
	protected void searchOnAction(String text) {
		if(focusSearch){
			ArrayList<JSearchResult<NodeSearch>> res = AppSearchField.this.engine.query(text.split(" "));
			if(res.size() >= 1){
				NodeSearch s = res.get(0).getValue();
				s.prepare();
				s.getNode().requestFocus();
			}
		}
	}

	public JSearchEngine<NodeSearch> getEngine() {
		return engine;
	}

	public void setEngine(JSearchEngine<NodeSearch> engine) {
		this.engine = engine;
	}
	
}
