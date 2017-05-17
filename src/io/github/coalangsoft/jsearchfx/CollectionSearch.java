package io.github.coalangsoft.jsearchfx;

import io.github.coalangsoft.jsearch.JSearchEngine;
import io.github.coalangsoft.lib.data.Func;

import java.util.List;

/**
 * Created by Matthias on 17.05.2017.
 */
public class CollectionSearch {

    public static <T> JSearchEngine<T> createEngine(List<T> list, Func<T, String> keyFactory){
        JSearchEngine<T> se = new JSearchEngine<T>();
        for(int i = 0; i < list.size(); i++){
            T item = list.get(i);
            se.add(keyFactory.call(item), item);
        }
        return se;
    }

}
