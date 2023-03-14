package etu1999.framework;

import java.util.HashMap;

public class Mapping {
    String className;
    String Method;

    public Mapping(String classname, String method){
        setClassName(classname);
        setMethod(method);
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getMethod() {
        return Method;
    }

    public void setMethod(String method) {
        Method = method;
    }
}
