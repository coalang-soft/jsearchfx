package test.jsearchfx;

import java.io.File;

import io.github.coalangsoft.jsearchfx.ui.AppSearchField;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.effect.Lighting;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class SearchFXTest extends Application{

	@Override
	public void start(Stage stage) throws Exception {
		BorderPane t = FXMLLoader.load(new File("res/layout.fxml").toURI().toURL());
		
		TextField search = AppSearchField.make(t, new Lighting(), true);
		search.setPromptText("Suche");
		t.setTop(search);
		
		stage.setScene(new Scene(t));		
		stage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}

}
