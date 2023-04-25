package etu1999.framework.servlet;

import etu1999.framework.Mapping;
import etu1999.framework.utils.ClassRetriever;
import etu1999.framework.utils.mapping.Url;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletMapping;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import etu1999.framework.process.Modelview;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.net.URISyntaxException;
import java.util.HashMap;
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
            Method method = objet.getClass().getDeclaredMethod(map.getMethod());
            if(method.getReturnType()==Modelview.class)
                modelview = (Modelview) method.invoke(objet);
            if(modelview != null){
                for (String k: modelview.getAttributes().keySet())
                    req.setAttribute(k, modelview.getAttributes().get(k));
                req.getRequestDispatcher(modelview.getView()).forward(req, resp);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
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
