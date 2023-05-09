# first-framework
WEB Framework java

## To try this test:
Execute the command :
```Bash
sh run.sh <project-name>
```
But before the tomcat path have to be setted
By changing the tomcat variable in
```Bash
./script/build_webapp.sh
```
And the project_path must be defined in
```Bash
./srcipt/compile_framework.sh
```
the test-framework folder is the project 
but the framework folder is the *framework*

the classes have to be in the same package wich is define in *web.xml* as package_src init-parameter
```Xml
    <servlet>
        <servlet-name>frontservlet</servlet-name>
        <servlet-class>etu1999.framework.servlet.FrontServlet</servlet-class>
        <init-param>
            <param-name> package_src </param-name>
            <param-value>controller</param-value>
        </init-param>
    </servlet>
    <servlet-mapping>
        <servlet-name>frontservlet</servlet-name>
        <url-pattern>/</url-pattern>
    </servlet-mapping>
```

