package etu1999.framework.utils;

import etu1999.framework.Mapping;
import etu1999.framework.utils.*;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletMapping;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class Misc {

    // Maka anle valeur ao aorian'ny context
    public static String getMappingValue(HttpServletRequest request){
        String value = new String();
        String URI = request.getRequestURI();
        String context_path = request.getContextPath();
        value = URI.substring(context_path.length());
        return value;
    }
}
