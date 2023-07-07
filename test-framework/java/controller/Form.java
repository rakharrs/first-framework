package controller;

import etu1999.framework.process.Modelview;
import etu1999.framework.utils.mapping.Url;
import etu1999.framework.process.Fileupload;

public class Form {
    String name;
    Fileupload picture;
    
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
        modelview.addItem("file_name", getPicture().getName());
        modelview.setView("liked_color.jsp");
        return modelview;
    }

    @Url("/Test-json")
    public Test json(){
        Test test = new Test();
        test.setName("name");
        return test;
    }

    public String getName(){
        return this.name;
    }

    public Fileupload getPicture() {
        return picture;
    }


    public void setName(String new_name){
        this.name = new_name;
    }

    public void setPicture(Fileupload picture) {
        this.picture = picture;
    }

}
