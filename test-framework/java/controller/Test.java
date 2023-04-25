package controller;

import etu1999.framework.utils.mapping.Url;

import java.util.HashMap;

import etu1999.framework.process.Modelview;

public class Test {
    @Url("/test")
    public Modelview test(){
        Modelview modelview = new Modelview();

        modelview.addItem("test1", 22);
        modelview.addItem("test2", "ok");
        modelview.setView("ox.jsp");
        System.out.println("oko");
        return modelview;
    }
}
