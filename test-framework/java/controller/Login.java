package controller;

import etu1999.framework.process.Modelview;
import etu1999.framework.utils.mapping.Auth;
import etu1999.framework.utils.mapping.Url;

public class Login {

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
    
}
