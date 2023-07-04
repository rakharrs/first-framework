package controller;

import java.util.HashMap;

import etu1999.framework.process.Modelview;
import etu1999.framework.utils.mapping.Scope;
import etu1999.framework.utils.mapping.Url;

@Scope("Singleton")
public class Singleton {
    Integer value = 1;
    HashMap<String, Object> session;

    @Url("/singleton")
    public Modelview singleton(){
        Modelview modelview = new Modelview();
        this.value+=1;
        modelview.addItem("value", this.value);
        modelview.setView("singleton.jsp");

        return modelview;
    }

    public HashMap<String, Object> getSession() {
        return session;
    }
    public void setSession(HashMap<String, Object> session) {
        this.session = session;
    }
}
