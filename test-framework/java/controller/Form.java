package controller;

import etu1999.framework.process.Modelview;
import etu1999.framework.utils.mapping.Url;
import etu1999.framework.utils.mapping.Arg;

public class Form {
    String name;

    @Url("/form")
    public Modelview form(){
        Modelview modelview = new Modelview();
        modelview.setView("form.jsp");

        return modelview;
    }

    @Url("/submit")
    public Modelview test(String[] colors, Integer[] digits){
        Modelview modelview = new Modelview();
        if(getName() != null)
            modelview.addItem("name", getName());
        else modelview.addItem("name", "unknown");
        modelview.addItem("colors", colors);
        modelview.addItem("digits", digits);
        modelview.setView("liked_color.jsp");
        return modelview;
    }

    public String getName(){
        return this.name;
    }

    public void setName(String new_name){
        this.name = new_name;
    }
}
