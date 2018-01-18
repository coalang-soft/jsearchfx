package io.github.coalangsoft.jsearchfx;

import io.github.coalangsoft.jsearch.ISearchEngine;
import io.github.coalangsoft.jsearch.JSearchEngine;
import io.github.coalangsoft.jsearch.JSearchResult;
import javafx.scene.effect.Effect;
import javafx.util.Callback;

public class SearchEffectChangeListener extends SearchChangeListener{

	public SearchEffectChangeListener(ISearchEngine<NodeSearch> engine, final Effect e){
		super(engine, new Callback<JSearchResult<NodeSearch>, Void>() {

			@Override
			public Void call(JSearchResult<NodeSearch> r) {
				r.getValue().getNode().setEffect(e);
				return null;
			}
			
		}, new Callback<JSearchResult<NodeSearch>, Void>() {

			@Override
			public Void call(JSearchResult<NodeSearch> r) {
				r.getValue().getNode().setEffect(null);
				return null;
			}
			
		});
	}

}
