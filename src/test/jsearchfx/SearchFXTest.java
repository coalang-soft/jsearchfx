package test.jsearchfx;

import java.io.File;

import io.github.coalangsoft.jsearch.JSearchEngine;
import io.github.coalangsoft.jsearchfx.NodeSearch;
import io.github.coalangsoft.jsearchfx.ui.AppSearchField;

import io.github.coalangsoft.jsearchfx.ui.AutoComplete;
import io.github.coalangsoft.jsearchfx.ui.CollectionSearchField;
import io.github.coalangsoft.jsearchfx.ui.SearchField;
import io.github.coalangsoft.lib.data.Func;
import javafx.application.Application;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.effect.Lighting;
import javafx.scene.effect.SepiaTone;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.util.Callback;

public class SearchFXTest extends Application{

	@Override
	public void start(Stage stage) throws Exception {
		Node n = FXMLLoader.load(new File("res/layout.fxml").toURI().toURL());

		SearchField<NodeSearch> search = AppSearchField.make(n, new SepiaTone(), true);
		search.setPromptText("Suche");

		final JSearchEngine<String> autoComplete = new JSearchEngine<String>();
		search.getEngine().forAllKeys(new Func<String, Object>() {
			@Override
			public Object call(String s) {
				autoComplete.add(s,s);
				return null;
			}
		});

		new AutoComplete().attach(search, autoComplete);

		BorderPane t = new BorderPane(n);
		t.setTop(search);
		
		stage.setScene(new Scene(t));
		stage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}

}
