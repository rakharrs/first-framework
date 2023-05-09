# first-framework
WEB Framework java
## Setup
## To try (compile and deploy) this test:
Execute the command :
```Bash
sh run.sh <project-name>
```
But before the tomcat path have to be settedflambusard
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

## Instructions
- First the following classes havee to be imported
    ```Java
        import etu1999.framework.process.Modelview;
        import etu1999.framework.utils.mapping.Url;
    ```
- functions must return Modelview
- functions must be annoted with the annotation Url( *followed by the url_link* )
    - Example 
    ```Java
        @Url("the url_link")
        public Modelview example(){}
    ```
- Within the Modelview you can define the view *jsp file* to show
and send data to this view
    - Example
    ```Java
        Modelview modelview = new Modelview();
        modelview.setView("index.jsp");
        modelview.addItem("data", "Test");
    ```
 - You can also put argument in the function and defnie it by post or get method
 - And do the same thing with classes property
    - Example
        ```Java
            public class Test {
                String name;
                @Url("/tests")
                public Modelview tests(String ok){
                    Modelview modelview = new Modelview();
                    if(getName() != null)
                        modelview.addItem("test1", getName());
                    else modelview.addItem("test1", "unknown");
                    modelview.addItem("test2", ok);
                    modelview.setView("ox.jsp");
                    return modelview;
                }
            }
        ```

