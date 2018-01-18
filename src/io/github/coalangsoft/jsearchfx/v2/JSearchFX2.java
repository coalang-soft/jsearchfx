package io.github.coalangsoft.jsearchfx.v2;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import io.github.coalangsoft.jsearch.v2.BasicSearchEngine;
import io.github.coalangsoft.jsearchfx.NodeSearch;
import javafx.beans.property.Property;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TitledPane;

import io.github.coalangsoft.jsearch.JSearchEngine;
import io.github.coalangsoft.lib.data.Func;

public class JSearchFX2 {

    private BasicSearchEngine<NodeSearch> engine;
    private ArrayList<Func<Void,Void>> tabData;
    private ArrayList<String> parentCategories;

    public BasicSearchEngine<NodeSearch> createSearchEngine(Node root){
        engine = new BasicSearchEngine<NodeSearch>(v -> nodeList(root));
        tabData = new ArrayList<Func<Void,Void>>();
        parentCategories = new ArrayList<String>();
        return engine;
    }

    private List<NodeSearch> nodeList(Node root){
        ArrayList<NodeSearch> l = new ArrayList<>();
        registerNode(l,root);
        return l;
    }

    private void registerNode(List<NodeSearch> sceneGraphList, Node node) {
        if(node == null){
            return;
        }
        sceneGraphList.add(NodeSearch.create(node, tabData));
        registerProperties(sceneGraphList, node);
    }

    private void registerProperties(List<NodeSearch> sceneGraphList, Node node) {
        Class<?> c = node.getClass();
        Method[] ms = c.getMethods();
        for(int i = 0; i < ms.length; i++){
            Method m = ms[i];
            if(m.getName().endsWith("Property")){
                handlePropertyMethod(sceneGraphList, m, node);
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
                    handleList(sceneGraphList, (ObservableList<?>) res, node);
                }
            } catch (IllegalAccessException | IllegalArgumentException
                    | InvocationTargetException e1) {}
        }
    }

    private void handleList(List<NodeSearch> sceneGraphList, ObservableList<?> list, Node n) {
        for(int i = 0; i < list.size(); i++){
            Object o = list.get(i);
            if(o instanceof Node){
                registerNode(sceneGraphList, (Node) o);
            }if(o instanceof Tab){
                final Tab t = (Tab) o;
                tabData.add(new Func<Void, Void>() {
                    @Override
                    public Void call(Void p) {
                        t.getTabPane().getSelectionModel().select(t);
                        return null;
                    }
                });
                parentCategories.add(t.getText());
                registerNode(sceneGraphList, t.getContent());
                tabData.remove(tabData.size() - 1);
                parentCategories.remove(parentCategories.size() - 1);
            }if(o instanceof TitledPane){
                final TitledPane t = (TitledPane) o;
                tabData.add(new Func<Void, Void>() {
                    @Override
                    public Void call(Void p) {
                        t.setExpanded(true);
                        return null;
                    }
                });
                parentCategories.add(t.getText());
                registerNode(sceneGraphList, t.getContent());
                tabData.remove(tabData.size() - 1);
                parentCategories.remove(parentCategories.size() - 1);
            }
        }
    }

    private void handlePropertyMethod(List<NodeSearch> sceneGraphList, Method m, Node n) {
        try {
            Object res = m.invoke(n);
            if(res instanceof Property){
                Object v = ((Property<?>) res).getValue();
                if(v instanceof Node){
                    registerNode(sceneGraphList, (Node) v);
                }
            }
        } catch (IllegalAccessException | IllegalArgumentException
                | InvocationTargetException e1) {}
    }

}