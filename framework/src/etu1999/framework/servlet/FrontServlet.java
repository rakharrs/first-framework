package etu1999.framework.servlet;

import etu1999.framework.Mapping;
import etu1999.framework.utils.ClassRetriever;
import etu1999.framework.utils.mapping.Url;
import etu1999.framework.utils.mapping.Arg;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletMapping;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import etu1999.framework.process.Modelview;
import java.lang.reflect.Parameter;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import etu1999.framework.utils.*;

public class FrontServlet extends HttpServlet {
    HashMap<String, Mapping> MappingUrls = new HashMap<>();

    @Override 
    public void init() throws ServletException {
        String package_src = getInitParameter("package_src");
        retrieveMappingUrls(Objects.requireNonNullElse(package_src,
                "controller"));
    }

    protected void processRequest(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpServletMapping mapping = req.getHttpServletMapping();
        dispatch_modelview(req, resp);
        print_test(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    public HashMap<String, Mapping> getMappingUrls() {
        return MappingUrls;
    }

    public void setMappingUrls(HashMap<String, Mapping> mappingUrls) {
        MappingUrls = mappingUrls;
    }

    protected void retrieveMappingUrls(String package_name){
        try{
            System.out.println(package_name);
            Set<Class> classes = ClassRetriever.findAllClasses(package_name);
            for (Class classe : classes){
                Method[] methods = classe.getMethods();
                for (Method method : methods)
                    if(method.isAnnotationPresent(Url.class)) {
                        Url url = method.getAnnotation(Url.class);
                        this.MappingUrls.put(url.value(), new Mapping(classe.getName(), method.getName()));
                    }
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void dispatch_modelview(HttpServletRequest req, HttpServletResponse resp){
        String key = Misc.getMappingValue(req);
        Mapping map = getMappingUrls().get(key);
        Modelview modelview = null;
        try {
            Class<?> process_class = Class.forName(map.getClassName());
            Object objet = process_class.newInstance();

        // Maka an'ilay parameter avy @ requete
            Map<String, String[]> requestParameter = req.getParameterMap();

        // m'initialize anle parameter
            init_modelview_parameter(requestParameter, objet);

            /* 
            Method method = objet.getClass().getDeclaredMethod(map.getMethod());
            if(method.getReturnType()==Modelview.class)
                modelview = (Modelview) method.invoke(objet); 
            */

        // M'invoke an'ilay conntroller
            modelview = invoke_requested_method(requestParameter, objet, map.getMethod());

            if(modelview != null){
                for (String k: modelview.getData().keySet())
                    req.setAttribute(k, modelview.getData().get(k));
                req.getRequestDispatcher(modelview.getView()).forward(req, resp);
            }else{
                PrintWriter out = resp.getWriter();
                out.println("-MODELVIEW NULL-");
                out.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Method getMathingMethod(Method[] methods, String method_name) throws NoSuchMethodException{
        for(Method method : methods)
            if (method.getName().equals(method_name))
                return method;
        throw new NoSuchMethodException("No method as : "+method_name);
    }

    public Modelview invoke_requested_method(Map<String, String[]> parameters, Object objet, String method_name) {
        Modelview modelview = null;
        try {
            System.out.println("invokde meth " + method_name);
            // Method method = objet.getClass().getDeclaredMethod(method_name);
            Method method = getMathingMethod(objet.getClass().getDeclaredMethods(), method_name);
            if(method.getReturnType()==Modelview.class){
                Parameter[] params = method.getParameters();
                if(params.length > 0){
                    Class<?>[] method_parameter_class = arrayMethodParameter(method);
                    //Object[] args = new Object[params.length];
                    String[][] args = new String[params.length][];
                    for(int i = 0; i < params.length; i++){
                        String[] param = parameters.get(params[i].getName());
                        args[i] = param;
                    }
                        
                    
                    modelview = (Modelview) method.invoke(objet, dynamicCast(method_parameter_class, args));
                }else modelview = (Modelview) method.invoke(objet);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return modelview;
    }

    private Object [] dynamicCast(Class<?>[]classes,String[][]args) throws Exception{
       Object[] array = new Object[classes.length];
       int i = 0;
       for (Class<?> cl:classes) {
           //array[i] = cl.getDeclaredConstructor(String.class).newInstance(args[i]);
            if(cl == String.class)
                array[i] = cl.getDeclaredConstructor(String.class).newInstance(args[i][0]);
            else array[i] = cl.getDeclaredConstructor(String[].class).newInstance(args[i]);
           i++;
       }
       return array;
   }

    /* Invoke setters */
    public void init_modelview_parameter(Map<String, String[]> parameters, Object objet) throws Exception{
        Method[] methods = objet.getClass().getDeclaredMethods();
        for(Method method : methods){
            String method_name = method.getName();
            System.out.println("method name : "+method_name);
            if(!method_name.startsWith("set", 0))
                continue;
            String field_name = method_name.substring(3).toLowerCase();
            String[] parameter = parameters.get(field_name);
            if(parameter != null){
                Class<?>[] method_parameter_class = arrayMethodParameter(method);
                method.invoke(objet, dynamicCast(method_parameter_class, parameter));
            }
        }
    }

        /**
     * function who dynamically cast an Object with the matching classes
     * @param classes
     * @param args
     * @return Object array
     */

     private Object [] dynamicCast(Class<?>[]classes,Object[]args) throws Exception{
        Object[] array = new Object[classes.length];
        int i = 0;
        for (Class<?> cl:classes) {
            array[i] = cl.getDeclaredConstructor(String.class).newInstance(args[i]);
            i++;
        }
        return array;
    }

    private Class<?>[] arrayMethodParameter(Method method) {
        // Get the parameters of the method
        Parameter[] parameters = method.getParameters();
        // Create an array to store the classes of the parameter instances
        Class<?>[] paramClasses = new Class<?>[parameters.length];
        // Iterate through the parameters and get their classes
        for (int i = 0; i < parameters.length; i++) {
            paramClasses[i] = parameters[i].getType();
        }
        // Return the array of parameter classes
        return paramClasses;
    }

    public Method stringMatching(Method[] methods, String method_name){
        Method matching = null;
        for (Method method : methods)
            if(method.getName().equals(method_name))
                return method;
        return null;
    }

    public void print_test(HttpServletRequest req, HttpServletResponse resp) throws IOException{
        resp.setContentType("text/html");
        HttpServletMapping mapping = req.getHttpServletMapping();

        // Hello
        PrintWriter out = resp.getWriter();
        out.println("<html><body>");

        out.println("<h1> URI : " + req.getRequestURI() + "</h1>");
        out.println("<h1>" + req.getQueryString() + "</h1>");
        out.println("<h1> URL : " + req.getRequestURL() + "</h1>");
        out.println("<h1>" + mapping.getPattern() + "</h1>");
        out.println("<h1>" + mapping.getMatchValue() + "</h1>");
        out.println("<p>"+req.getContextPath()+"</p>");
        out.println("<p>"+Misc.getMappingValue(req)+"</p>");
        for (String k: getMappingUrls().keySet()) {
            out.print("key : " + k);
            out.println(" value : " + getMappingUrls().get(k).getClassName());
        }
        System.gc();
        out.println("</body></html>");
    }
}
