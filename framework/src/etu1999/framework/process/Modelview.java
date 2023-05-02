package etu1999.framework.process;

import java.util.HashMap;

public class Modelview {
    
    protected String view;
    private HashMap<String, Object> data = new HashMap<>();

    public String getView() {
        return view;
    }
    public void addItem(String key, Object value){
        this.getData().put(key, value);
    }

    public HashMap<String, Object> getData(){
        return this.data;
    }

    public void setData(HashMap<String, Object> new_attributes){
        this.data = new_attributes;
    }

    public void setView(String view) {
        this.view = view;
    }
}
