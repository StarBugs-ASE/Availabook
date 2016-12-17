/**
 * Created by Sophie on 11/28/16.
 */


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class User {
    private String name;
    private String passwd;
    private String email;
    private Availatime availatime;


    public User(String name, String passwd, String email){
        this.name = name;
        this.passwd = passwd;
        this.email = email;
        //  this.friendList = new ArrayList<String>();
    }

    public void setUser(String name, String passwd, String email){
        this.name = name;
        this.passwd = passwd;
        this.email = email;
    }



    public void setAvailatime(Availatime availatime){
        this.availatime = availatime;
    }

    public void deleteAvailatime(Availatime availatime){
        this.availatime = null;
    }

    public String getName(){
        return name;
    }

    public String getPasswd(){
        return passwd;
    }

    public String getEmail() {
        return email;
    }

}
