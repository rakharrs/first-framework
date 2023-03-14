package etu1999.framework.utils;

import java.awt.event.MouseListener;
import java.io.*;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class ClassRetriever {
    public static Set<Class> findAllClasses(String packageName){
        InputStream stream = ClassLoader.getSystemClassLoader().getResourceAsStream(packageName.replaceAll("[.]", "/"));
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
        return reader.lines()
                .filter(line -> line.endsWith(".class"))
                .map(line -> getClass(line, packageName))
                .collect(Collectors.toSet());
    }

    public static Set<Class> find_classes(String package_name) throws ClassNotFoundException {
        String path = package_name.replaceAll("[.]", "/");
        File dir = new File("path");
        FileFilter filter = file -> file.getName().endsWith(".class");
        File[] classe_files = dir.listFiles(filter);
        Set<Class> classes = new HashSet<>();
        for(File file : classe_files)
            classes.add(Class.forName(path + file.getName()));
        return classes;
    }


    private static Class getClass(String className, String packageName){
        try{
            return Class.forName(packageName + "." + className.substring(0, className.lastIndexOf('.')));
        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }
        return null;
    }
}
