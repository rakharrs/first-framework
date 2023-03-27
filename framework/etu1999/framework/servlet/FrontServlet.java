package etu1999.framework.servlet;

import etu1999.framework.Mapping;
import etu1999.framework.utils.ClassRetriever;
import etu1999.framework.utils.mapping.Url;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletMapping;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

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
                "etu1999.framework.controller"));
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
        for (String key: getMappingUrls().keySet()) {
            out.print("key : " + key);
            out.println(" value : " + getMappingUrls().get(key));
        }
        System.gc();
        out.println("</body></html>");
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
