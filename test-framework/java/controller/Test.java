package controller;

import etu1999.framework.utils.mapping.Url;

import java.util.HashMap;

import etu1999.framework.process.Modelview;

public class Test {
    @Url("/test")
    public Modelview test(){
        Modelview modelview = new Modelview();
        HashMap<String, Object> new_attributes = new HashMap<>();
        new_attributes.put("test1", 22);
        new_attributes.put("test2", "ok");
        
        modelview.setAttributes(new_attributes);
        modelview.setView("ox.jsp");
        System.out.println("oko");
        return modelview;
    }
}
