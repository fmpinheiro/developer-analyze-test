package br.com.helper;

import java.util.Comparator;
import java.util.Map;

/**
 * Essa classe serve como comparador para a classe TreeMap
 * 
 * @author Deivide
 */
public class MapComparator implements Comparator<String>{

    public Map<String, Integer> map;
    public MapComparator(Map<String, Integer> map){
        this.map = map;
    }
    
    @Override
    public int compare(String o1, String o2) {
        if (map.get(o1) >= map.get(o2)) {
            return -1;
        } else {
            return 1;
        }
    }
    
}
