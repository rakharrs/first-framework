# first-framework
WEB Framework java
## Setup
### To try (compile and deploy) this test:
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

### Intro
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
### Session
- There is an authentification function that could be configured in the web.xml as following:
- By adding as init-param a session_name and a session_profil where the session name will be the name of the boolean value within the session that says if it could be used or not.
- And session_profil the name of the value within the session that contain the profile type
``` xml
    <servlet>
        <servlet-name>frontservlet</servlet-name>
        <servlet-class>etu1999.framework.servlet.FrontServlet</servlet-class>
        <init-param>
            <param-name>package_src</param-name>
            <param-value>controller</param-value>
        </init-param>
        <init-param>
            <param-name>session_name</param-name>
            <param-value>isConnected</param-value>
        </init-param>
        <init-param>
            <param-name>session_profile</param-name>
            <param-value>profil</param-value>
        </init-param>
    </servlet>
```

- An example of utilisation:
``` Java
@Url("/login")
    public Modelview signin(){
        Modelview modelview = new Modelview();
        modelview.setView("login.jsp");

        return modelview;
    }
    
    @Url("/signin")
    public Modelview login(String username, String password){
        Modelview modelview = new Modelview();

        if(username.equals("rakharrs") && password.equals("pixel")){
            modelview.addSession( "isConnected" , true );
			modelview.addSession( "profil" , "admin" );
        }else if(username.equals("scott") && password.equals("tiger")){
            modelview.addSession( "isConnected" , true );
			modelview.addSession( "profil" , "guest" );
        }

        modelview.setView("authed.jsp");

        return modelview;
    }

    @Auth(user = "admin")
    @Url("/limited")
    public Modelview limited(){
        Modelview modelview = new Modelview();
        modelview.setView("admin.jsp");
        return modelview;
    }
```

- There in the login page, it's just a normal login, in the signin (which is the page processing) it verify the credentials and put in the session the value
- The if a user want to access /limited page it will only be displayed if the user is authentified as an admin profil 
