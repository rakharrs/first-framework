package etu1999.framework.process;

import java.util.HashMap;

public class Modelview {
    
    protected String view;
    private HashMap<String, Object> data = new HashMap<>();
    HashMap<String , Object> sessions = new HashMap<String, Object>();
    boolean json;

    public String getView() {
        return view;
    }
    public void addItem(String key, Object value){
        this.getData().put(key, value);
    }

    public void addSession(String key, Object value){
        this.getSessions().put(key, value);
    }

    public HashMap<String, Object> getData(){
        return this.data;
    }

    public HashMap<String, Object> getSessions() {
        return sessions;
    }

    public boolean isJson() {
        return json;
    }

    public void setJson(boolean json) {
        this.json = json;
    }

    public void setData(HashMap<String, Object> new_attributes){
        this.data = new_attributes;
    }

    public void setSessions(HashMap<String, Object> sessions) {
        this.sessions = sessions;
    }

    public void setView(String view) {
        this.view = view;
    }
}
