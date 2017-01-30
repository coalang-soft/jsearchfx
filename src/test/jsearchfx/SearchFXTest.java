package test.jsearchfx;

import java.io.File;
import java.util.ArrayList;

import io.github.coalangsoft.jsearch.JSearchEngine;
import io.github.coalangsoft.jsearch.JSearchResult;
import io.github.coalangsoft.jsearchfx.JSearchFX;
import io.github.coalangsoft.jsearchfx.NodeSearch;
import io.github.coalangsoft.jsearchfx.SearchEffectChangeListener;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.effect.Lighting;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class SearchFXTest extends Application{

	@Override
	public void start(Stage stage) throws Exception {
		final TextField search = new TextField();
		search.setPromptText("Suche");
		
		BorderPane t = FXMLLoader.load(new File("res/layout.fxml").toURI().toURL());
		t.setTop(search);
		
		JSearchFX searchBase = new JSearchFX();
		final JSearchEngine<NodeSearch> se = searchBase.createSearchEngine(t);
		
		search.textProperty().addListener(new SearchEffectChangeListener(se, new Lighting()));
		search.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent e) {
				ArrayList<JSearchResult<NodeSearch>> res = se.query(search.getText().split(" "));
				if(res.size() >= 1){
					NodeSearch s = res.get(0).getValue();
					s.prepare();
					s.getNode().requestFocus();
				}
			}
			
		});
		
		stage.setScene(new Scene(t));		
		stage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}

}
