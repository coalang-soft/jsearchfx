package io.github.coalangsoft.jsearchfx;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TitledPane;

import io.github.coalangsoft.jsearch.JSearchEngine;
import io.github.coalangsoft.lib.data.Func;
import io.github.coalangsoft.lib.data.ImutablePair;
import io.github.coalangsoft.lib.data.Pair;

public class JSearchFX {
	
	private JSearchEngine<NodeSearch> engine;
	private ArrayList<Func<Void,Void>> tabData;
	
	public JSearchEngine<NodeSearch> createSearchEngine(Node root){
		engine = new JSearchEngine<NodeSearch>();
		tabData = new ArrayList<Func<Void,Void>>();
		registerNode(root);
		return engine;
	}

	private void registerNode(Node node) {
		if(node == null){
			return;
		}
		registerProperties(node);
	}

	private void registerProperties(Node node) {
		Class<?> c = node.getClass();
		Method[] ms = c.getMethods();
		for(int i = 0; i < ms.length; i++){
			Method m = ms[i];
			if(m.getName().endsWith("Property")){
				handlePropertyMethod(m, node);
			}
			if(m.getName().equals("getStyleClass") || m.getName().equals("getId")){
				continue;
			}
			if(m.getReturnType() != ObservableList.class){
				continue;
			}
			try {
				Object res = m.invoke(node);
				if(res instanceof ObservableList){
					handleList((ObservableList<?>) res, node);
				}
			} catch (IllegalAccessException | IllegalArgumentException
					| InvocationTargetException e1) {}
		}
	}

	private void handleList(ObservableList<?> list, Node n) {
		for(int i = 0; i < list.size(); i++){
			Object o = list.get(i);
			if(o instanceof Node){
				registerNode((Node) o);
			}if(o instanceof String){
				engine.add((String) o, NodeSearch.create(n, tabData));
			}if(o instanceof Tab){
				final Tab t = (Tab) o;
				tabData.add(new Func<Void, Void>() {
					@Override
					public Void call(Void p) {
						t.getTabPane().getSelectionModel().select(t);
						return null;
					}
				});
				registerNode(t.getContent());
				tabData.remove(tabData.size() - 1);
			}if(o instanceof TitledPane){
				final TitledPane t = (TitledPane) o;
				tabData.add(new Func<Void, Void>() {
					@Override
					public Void call(Void p) {
						t.setExpanded(true);
						return null;
					}
				});
				registerNode(t.getContent());
				tabData.remove(tabData.size() - 1);
			}
		}
	}

	private void handlePropertyMethod(Method m, Node n) {
		try {
			Object res = m.invoke(n);
			if(res instanceof StringProperty){
				engine.add(((StringProperty) res).get(), NodeSearch.create(n, tabData));
			}
		} catch (IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e1) {}
	}
	
}
