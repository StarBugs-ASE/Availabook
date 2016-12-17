/**
 * Created by Sophie on 11/28/16.
 */


import static spark.Spark.*;
import com.google.gson.Gson;


import spark.ModelAndView;
import spark.QueryParamsMap;
import spark.template.jade.JadeTemplateEngine;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.sql.Connection;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.sql.Time;
import java.util.StringTokenizer;

public class Main {

    public static void main(String[] args) {
        port(5050);


        Database sqlitemethod2 = new Database();


        File f = new File("data.db");
        if(!f.exists()) {
            sqlitemethod2.createDatabase();
        }

        sqlitemethod2.openDatabase();


        Connection c = sqlitemethod2.c;





        get("/login", (rq, rs) -> new ModelAndView(new HashMap(), "login"), new JadeTemplateEngine());

        get("/SignUp", (rq, rs) -> new ModelAndView(new HashMap(), "SignUp"), new JadeTemplateEngine());

        post("/CreateUser", (rq, rs) -> {

            HashMap<String, String> emailmap = new HashMap<>();
            QueryParamsMap body = rq.queryMap();

            String signUpInputName = body.get("name").value();
            String signUpInputPassword = body.get("password").value();
            String signUpInputEmail = body.get("email").value();

            String signUp = sqlitemethod2.signUp(c, signUpInputName, signUpInputPassword, signUpInputEmail);
            if (signUp.equals("Success")){
                rs.redirect("/login");
                return null;
            }
            else if (signUp.equals("Name&Email&Password")) {
                emailmap.put("message3", "Your name, email and password are invalid.");
                return new ModelAndView(emailmap, "SignUp");
            }
            else if (signUp.equals("Name&Email")){
                emailmap.put("message3", "Your name and email are invalid.");
                return new ModelAndView(emailmap, "SignUp");
            }
            else if (signUp.equals("Name&Password")){
                emailmap.put("message3", "Your name and password are invalid.");
                return new ModelAndView(emailmap, "SignUp");
            }
            else if (signUp.equals("Email&Password")){
                emailmap.put("message3", "Your email and password are invalid.");
                return new ModelAndView(emailmap, "SignUp");
            }
            else if (signUp.equals("Email")){
                emailmap.put("message3", "Your email is invalid.");
                return new ModelAndView(emailmap, "SignUp");
            }
            else if (signUp.equals("Name")){
                emailmap.put("message3", "Your name is invalid.");
                return new ModelAndView(emailmap, "SignUp");
            }
            else if (signUp.equals("Password")){
                emailmap.put("message3", "Your password is invalid.");
                return new ModelAndView(emailmap, "SignUp");
            }
            else if (signUp.equals("DuplicateUsername")){
                emailmap.put("message3", "Duplicate Username, please choose another username.");
                return new ModelAndView(emailmap, "SignUp");
            }
            else if (signUp.equals("DuplicateEmail")){
                emailmap.put("message3", "Duplicate Email, please choose another email.");
                return new ModelAndView(emailmap, "SignUp");
            }
            else {
                return new ModelAndView(emailmap, "SignUp");
            }
        }, new JadeTemplateEngine());


        post("/hp", (rq, rs) -> {
            HashMap<String, String> passwordmap = new HashMap<>();
            QueryParamsMap body = rq.queryMap();
            String loginInputEmail = body.get("email").value();
            String loginInputName = body.get("username").value();
            String loginInputPassword = body.get("password").value();
            rq.session().attribute("userName", loginInputName);
            int userID = sqlitemethod2.IDQuery(c,loginInputName);
            rq.session().attribute("userID",userID);
            String userName = (String) rq.session().attribute("userName");
            String passwd = sqlitemethod2.passwdQuery(c, userName);
            HashMap<String,String> map = new HashMap<String, String>();
            String encryptedInputPasswd = sqlitemethod2.encryptedPasswd(loginInputPassword);
            if (passwd != null && passwd.equals(encryptedInputPasswd)) {
                rs.redirect("/user/home");
                return null;
            }
            else {
                passwordmap.put("message", "[{\"error\":\"Username or Password invalid!\"}]");
                return new ModelAndView(passwordmap, "login");
            }
        }, new JadeTemplateEngine());



        before("/user/*", (rq, rs) -> {
            String userName = (String)rq.session().attribute("userName");
            if(userName == null || userName.isEmpty()){
                rs.redirect("/login");
            }
        });

        get("/user/home", (rq, rs) -> {
            HashMap<String, String> map = new HashMap<>();
            ArrayList<Availatime> availatimeList = sqlitemethod2.availaTimeQuery(c);
            map.put("message2", "availatimeList" + "\n");

            Gson gson = new Gson();
            ArrayList<Availatime> visibleAvailatime = new ArrayList<Availatime>();
            ArrayList<Friendship> friendshipList = sqlitemethod2.friendshipQuery(c);
            ArrayList<Availatime> myAvailatime = new ArrayList<>();
            ArrayList<String> friendNameList = new ArrayList<String>();
            String name = (String)rq.session().attribute("userName");
            for(int i=0; i<friendshipList.size(); i++){
                if(sqlitemethod2.IDQuery(c,name)==friendshipList.get(i).getUserID1()){
                    friendNameList.add(sqlitemethod2.NameQuery(c,friendshipList.get(i).getUserID2()));
                }
                else if(sqlitemethod2.IDQuery(c,name)==friendshipList.get(i).getUserID2()){
                    friendNameList.add(sqlitemethod2.NameQuery(c,friendshipList.get(i).getUserID1()));
                }
            }
            String tempString = new String();
            String friendNames = new String();
            tempString = "[";
            if(friendNameList.size()==0){
                tempString = "[[";
            }
            for(int i=0; i<friendNameList.size();i++){
                tempString += "{\"name\":\"" + friendNameList.get(i) + "\"},";
            }
            friendNames = tempString.substring(0,tempString.length()-1);
            friendNames += "]";
            map.put("friendNameList", friendNames);



            for (int i = 0; i < availatimeList.size(); i++) {
                Availatime availatime = availatimeList.get(i);
                String userName = (String)rq.session().attribute("userName");
                int userID1 = sqlitemethod2.IDQuery(c, userName);
                int userID2 = sqlitemethod2.IDQuery(c, availatime.getUserName());
                boolean isFriend = false;
                if(userID1==userID2){
                    myAvailatime.add(availatime);
                }
                for (int j = 0; j < friendshipList.size(); j++) {
                    isFriend = isFriend || friendshipList.get(j).isFriendOrNot(userID1, userID2);
                }
                if (isFriend) {
                    visibleAvailatime.add(availatime);
                }
            }
            map.put("messagemy",gson.toJson(myAvailatime));
            map.put("messaget", gson.toJson(visibleAvailatime));
            return new ModelAndView(map, "userHome");
        }, new JadeTemplateEngine());

        get("/user/friend", (rq, rs) -> {
            HashMap<String,String> map = new HashMap<>();
            /* ############## */
            /*test friendship*/
            Gson gson = new Gson();
            ArrayList<String> friendNameList = new ArrayList<String>();
            ArrayList<Friendship> friendshipList = sqlitemethod2.friendshipQuery(c);
            String name = (String)rq.session().attribute("userName");
            for(int i=0; i<friendshipList.size(); i++){
                if(sqlitemethod2.IDQuery(c,name)==friendshipList.get(i).getUserID1()){
                    friendNameList.add(sqlitemethod2.NameQuery(c,friendshipList.get(i).getUserID2()));
                }
                else if(sqlitemethod2.IDQuery(c,name)==friendshipList.get(i).getUserID2()){
                    friendNameList.add(sqlitemethod2.NameQuery(c,friendshipList.get(i).getUserID1()));
                }
            }
            String tempString = new String();
            String friendNames = new String();
            for(int i=0; i<friendNameList.size();i++){
                tempString += "{\"name\":\"" + friendNameList.get(i) + "\"},";
            }
            friendNames = tempString.substring(0,tempString.length()-1);
            friendNames += "]";
            friendNames = "[" + friendNames;
            map.put("friendNameList", friendNames);
            /* test friendship*/
            /* ############# */
            return new ModelAndView(map, "you have success");
        }, new JadeTemplateEngine());

        get("/user/addAvailatime", (rq, rs) -> new ModelAndView(new HashMap<>(), "addAvailatime"), new JadeTemplateEngine());



        get("/user/addFriend", (rq, rs) -> new ModelAndView(new HashMap<>(), "addFriend"), new JadeTemplateEngine());

        get("/user/deleteFriend", (rq, rs) -> new ModelAndView(new HashMap<>(), "deleteFriend"), new JadeTemplateEngine());

        post("/user/deletesuccess", (rq, rs) -> {
            HashMap<String, String> map = new HashMap<>();
            QueryParamsMap body = rq.queryMap();
            String friendName = body.get("name").value();
            String userName = (String)rq.session().attribute("userName");
            ArrayList<Friendship> friendshipList = sqlitemethod2.friendshipQuery(c);
            int UserID1 = sqlitemethod2.IDQuery(c, userName);
            int UserID2 = sqlitemethod2.IDQuery(c, friendName);
            boolean isFriendOrNot = false;
            for(int i=0; i<friendshipList.size(); i++){
                if(friendshipList.get(i).isFriendOrNot(UserID1,UserID2)){
                    isFriendOrNot = true;
                }
            }
            if(!isFriendOrNot){
                map.put("message",friendName + " is not in your friend list");
            }
            else{
                sqlitemethod2.deleteFriend(c, UserID1, UserID2);
                map.put("message", friendName + " has been removed from your friend list!");
            }
            return new ModelAndView(map, "addFriendOrNot");
        }, new JadeTemplateEngine());



        post("/user/deleteavailatime", (rq, rs) -> {
            HashMap<String, String> map = new HashMap<>();
            QueryParamsMap body = rq.queryMap();
            String targetAvailatime = body.get("availatime").value();
            String userName = (String)rq.session().attribute("userName");
            String[] array = new String[4];
            ArrayList<Availatime> tempAvailatime = sqlitemethod2.availaTimeQuery(c);
            ArrayList<String> newAvaialtime = new ArrayList<String>();
            for(int i=0; i<tempAvailatime.size();i++){
                String xx = tempAvailatime.get(i).getStartTime() + " " + tempAvailatime.get(i).getEndTime()+" "
                        + tempAvailatime.get(i).getDate() +" "+ tempAvailatime.get(i).getTendency();
                if(targetAvailatime.equals(xx)){
                    sqlitemethod2.deleteAvailatime(c,tempAvailatime.get(i).getStartTime(),tempAvailatime.get(i).getEndTime(),tempAvailatime.get(i).getTendency(),userName,tempAvailatime.get(i).getDate());
                }
            }
            return new ModelAndView(map, "deleteavailatime");
        }, new JadeTemplateEngine());


        post("/user/home", (rq, rs) -> {
            HashMap<String, String> map = new HashMap<>();
            QueryParamsMap body = rq.queryMap();
            String date = body.get("Date").value();
            String start = body.get("StartTime").value();
            String end = body.get("EndTime").value();
            String tendency = body.get("Tendency").value();
            String userName = (String)rq.session().attribute("userName");
            Availatime tempAvailatime = new Availatime(date,start,end,tendency,userName);


            ArrayList<String> friendNameList = new ArrayList<String>();
            ArrayList<Friendship> friendshipList = sqlitemethod2.friendshipQuery(c);
            String name = (String)rq.session().attribute("userName");
            for(int i=0; i<friendshipList.size(); i++){
                if(sqlitemethod2.IDQuery(c,name)==friendshipList.get(i).getUserID1()){
                    friendNameList.add(sqlitemethod2.NameQuery(c,friendshipList.get(i).getUserID2()));
                }
                else if(sqlitemethod2.IDQuery(c,name)==friendshipList.get(i).getUserID2()){
                    friendNameList.add(sqlitemethod2.NameQuery(c,friendshipList.get(i).getUserID1()));
                }
            }
            String tempString = new String();
            String friendNames = new String();
            tempString = "[";
            if(friendNameList.size()==0){
                tempString = "[[";
            }
            for(int i=0; i<friendNameList.size();i++){
                tempString += "{\"name\":\"" + friendNameList.get(i) + "\"},";
            }
            friendNames = tempString.substring(0,tempString.length()-1);
            friendNames += "]";
            map.put("friendNameList", friendNames);


            if(!tempAvailatime.isValidAvailatime()){
                map.put("message", "[{\"error\":\"Your input of available time is invalid!\"}]");
                return new ModelAndView(map, "addAvailatime");

            }

            else {
                sqlitemethod2.addAvailatime(c, date, start, end, tendency, userName);

                ArrayList<Availatime> availatimeList = sqlitemethod2.availaTimeQuery(c);
                ArrayList<Availatime> visibleAvailatime = new ArrayList<Availatime>();
                ArrayList<Availatime> myAvaialtime = new ArrayList<>();
                Gson gson = new Gson();

                for (int i = 0; i < availatimeList.size(); i++) {
                    Availatime availatime = availatimeList.get(i);
                    int userID1 = sqlitemethod2.IDQuery(c, userName);
                    int userID2 = sqlitemethod2.IDQuery(c, availatime.getUserName());
                    boolean isFriend = false;
                    if(userID1==userID2){
                        myAvaialtime.add(availatime);
                    }

                    for (int j = 0; j < friendshipList.size(); j++) {
                        isFriend = isFriend || friendshipList.get(j).isFriendOrNot(userID1, userID2);
                    }
                    if (isFriend) {
                        visibleAvailatime.add(availatime);
                    }
                }
                map.put("messagemy",gson.toJson(myAvaialtime));
                map.put("messaget", gson.toJson(visibleAvailatime));
            }
            return new ModelAndView(map, "userHome");
        }, new JadeTemplateEngine());

        post("/user/success ", (rq, rs) -> {
            HashMap<String, String> map = new HashMap<>();
            QueryParamsMap body = rq.queryMap();
            String friendName = body.get("name").value();
            String userName = (String)rq.session().attribute("userName");
            ArrayList<Friendship> friendshipList = sqlitemethod2.friendshipQuery(c);
            int UserID1 = sqlitemethod2.IDQuery(c, userName);
            int UserID2 = sqlitemethod2.IDQuery(c, friendName);
            boolean alreadyFriend = false;
            for(int i=0; i<friendshipList.size(); i++){
                if(friendshipList.get(i).isFriendOrNot(UserID1,UserID2)){
                    alreadyFriend = true;
                }
            }
            if(alreadyFriend){
                map.put("message",friendName + " is already in your friend list");
            }
            else if (UserID1 != UserID2 && UserID1 != 0 && UserID2 != 0) {
                sqlitemethod2.addFriend(c, UserID1, UserID2);
                map.put("message", friendName + " is ur friend now!");
            } else map.put("message", "sorry, ur friend " + friendName + " hasn't joined availabook. Invite him/her!");
            return new ModelAndView(map, "addFriendOrNot");
        }, new JadeTemplateEngine());



        get("/user/logOut", (rq, rs) -> {
            HashMap<String, String> map = new HashMap<>();
            String userName = rq.session().attribute("userName");
            rq.session().removeAttribute(userName);
            return new ModelAndView(map, "logOut");
        }, new JadeTemplateEngine());

    }
}