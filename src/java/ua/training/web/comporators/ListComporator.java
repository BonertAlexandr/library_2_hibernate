/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.training.web.comporators;

import java.util.Comparator;

/**
 *
 * @author Alexandr
 */
public class ListComporator {
    private static ObjectComporator listComporator;
    
    public static Comparator getInstance() {
        if (listComporator == null) {
            listComporator = new ObjectComporator();
        }
        return listComporator;        
    }
    
    private static class ObjectComporator implements Comparator {

        @Override
        public int compare(Object o1, Object o2) {
            return o1.toString().compareTo(o2.toString());
        }
        
    }
}
