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

public class FrontServlet extends HttpServlet {
    HashMap<String, Mapping> MappingUrls = new HashMap<>();

    @Override
    public void init() throws ServletException {
        String package_src = getInitParameter("package_src");
        retrieveMappingUrls(Objects.requireNonNullElse(package_src,
                "controller"));
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
        }catch (NullPointerException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    protected void processRequest(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpServletMapping mapping = req.getHttpServletMapping();
        //String key = mapping.getMatchValue();
        String key = getMappingValue(req);
        Mapping map = getMappingUrls().get(key);
        try {
            Class<?> process_class = Class.forName(map.getClassName());
            Object objet = process_class.newInstance();
            Method method = objet.getClass().getDeclaredMethod(map.getMethod());
            if(method.getReturnType()==ModelView.class){
                Modelview modelview = (Modelview) method.invoke(objet);
            }
            RequestDispatcher dispatcher = req.getRequestDispatcher(modelview.getView());
            dispatcher.forward(req, resp);

        } catch (Exception e) {
            e.printStackTrace();
        }
        resp.setContentType("text/html");
        // Hello
        PrintWriter out = resp.getWriter();
        out.println("<html><body>");

        out.println("<h1> URI : " + req.getRequestURI() + "</h1>");
        out.println("<h1>" + req.getQueryString() + "</h1>");
        out.println("<h1> URL : " + req.getRequestURL() + "</h1>");
        out.println("<h1>" + mapping.getPattern() + "</h1>");
        out.println("<h1>" + mapping.getMatchValue() + "</h1>");
        out.println("<p>"+req.getContextPath()+"</p>");
        out.println("<p>"+getMappingValue(req)+"</p>");
        for (String k: getMappingUrls().keySet()) {
            out.print("key : " + k);
            out.println(" value : " + getMappingUrls().get(k).getClassName());
        }
        System.gc();
        out.println("</body></html>");
    }

    public String getMappingValue(HttpServletRequest request){
        String value = new String();
        String URI = request.getRequestURI();
        String context_path = request.getContextPath();
        value = URI.substring(context_path.length());
        return value;
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
}
