package io.github.coalangsoft.jsearchfx;

import io.github.coalangsoft.lib.data.Func;
import io.github.coalangsoft.lib.data.Pair;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

import javafx.scene.Node;
import javafx.scene.control.Menu;
import javafx.scene.control.SelectionModel;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

public class NodeSearch {

	private final Node node;
	private Func<Void, Void> prepare;
	
	private NodeSearch(Node n, Func<Void, Void> prepare){
		this.node = n;
		this.prepare = prepare;
	}
	
	public static NodeSearch create(final Node n,
			final ArrayList<Func<Void,Void>> tabData) {
		final Func<Void,Void>[] tabPrepare = tabData.toArray(new Func[0]);
		Func<Void, Void> f = new Func<Void,Void>() {

			@Override
			public Void call(Void p) {
				for(int i = 0; i < tabPrepare.length; i++){
					tabPrepare[i].call(null);
				}
				return null;
			}
			
		};
		
		return new NodeSearch(n,f);
	}

	public Node getNode() {
		return node;
	}
	
	public void prepare(){
		prepare.call(null);
	}

}
