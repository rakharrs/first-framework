package etu1999.framework.process;

import java.util.HashMap;

public class Modelview {
    
    protected String view;
    private HashMap<String, Object> attributes;

    public String getView() {
        return view;
    }

    public HashMap<String, Object> getAttributes(){
        return this.attributes;
    }

    public void setAttributes(HashMap<String, Object> new_attributes){
        this.attributes = new_attributes;
    }

    public void setView(String view) {
        this.view = view;
    }
}
