package controller;

import etu1999.framework.utils.mapping.Url;
import etu1999.framework.utils.mapping.Arg;

import java.util.HashMap;

import etu1999.framework.process.Modelview;

public class Test {
    String name;

    @Url("/test")
    public Modelview test(){
        Modelview modelview = new Modelview();

        
        if(getName() != null){
            modelview.addItem("test1", getName());
        }else{
            modelview.addItem("test1", 22);
        }
        modelview.addItem("test2", "ok");
        modelview.setView("ox.jsp");

        System.out.println("oko");
        
        return modelview;
    }

    @Url("/tests")
    public Modelview tests(String ok){
        Modelview modelview = new Modelview();

        if(getName() != null){
            modelview.addItem("test1", getName());
        }else{
            modelview.addItem("test1", 22);
        }
        modelview.addItem("test2", ok);
        modelview.setView("ox.jsp");

        System.out.println("oko");
        
        return modelview;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getName(){
        return this.name;
    }
    
}
