package controller;

import etu1999.framework.utils.mapping.Url;
import etu1999.framework.process.Modelview;

public class Test {
    @Url("test")
    public Modelview test(){
        Modelview modelview = new Modelview();
        modelview.setView("ox.jsp");
        System.out.println("oko");
        return modelview;
    }
}
