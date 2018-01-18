package io.github.coalangsoft.jsearchfx;

import java.util.ArrayList;

import io.github.coalangsoft.jsearch.ISearchEngine;
import io.github.coalangsoft.jsearch.JSearchEngine;
import io.github.coalangsoft.jsearch.JSearchResult;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.util.Callback;

public class SearchChangeListener implements ChangeListener<String>{

	private ISearchEngine<NodeSearch> engine;
	private Callback<JSearchResult<NodeSearch>, Void> onFind;
	private ArrayList<JSearchResult<NodeSearch>> last;
	private Callback<JSearchResult<NodeSearch>, Void> onRelease;
	
	public SearchChangeListener(ISearchEngine<NodeSearch> engine, Callback<JSearchResult<NodeSearch>, Void> onFind,
			Callback<JSearchResult<NodeSearch>, Void> onRelease){
		this.engine = engine;
		this.onFind = onFind;
		this.onRelease = onRelease;
	}
	
	@Override
	public void changed(ObservableValue<? extends String> v, String o, String n) {
		if(last != null){
			for(int i = 0; i < last.size(); i++){
				onRelease.call(last.get(i));
			}
		}
		
		//find
		ArrayList<JSearchResult<NodeSearch>> list = engine.query(n);
		for(int i = 0; i < list.size(); i++){
			onFind.call(list.get(i));
		}
		last = list;
	}
	
}
