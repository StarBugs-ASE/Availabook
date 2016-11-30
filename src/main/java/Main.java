/**
 * Created by Sophie on 11/28/16.
 */


import static spark.Spark.*;


import spark.ModelAndView;
import spark.QueryParamsMap;
import spark.template.jade.JadeTemplateEngine;

import java.sql.Connection;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.sql.Time;

public class Main {

    public static void main(String[] args) {
        port(5050);

        Database sqlitemethod2 = new Database();

        sqlitemethod2.openDatabase();
        sqlitemethod2.createDatabase();
        Connection c = sqlitemethod2.c;

        HashMap<String, User> usermap = new HashMap<>();

        User user = new User("no", "no", "no");

        get("/login", (rq, rs) -> new ModelAndView(new HashMap(), "login"), new JadeTemplateEngine());

        get("/SignUp", (rq, rs) -> new ModelAndView(new HashMap(), "SignUp"), new JadeTemplateEngine());

        post("/CreateUser", (rq, rs) -> {

            HashMap<String, String> emailmap = new HashMap<>();
            QueryParamsMap body = rq.queryMap();

            String signUpInputName = body.get("name").value();
            String signUpInputPassword = body.get("password").value();
            String signUpInputEmail = body.get("email").value();
            System.out.println(signUpInputName);
            System.out.println(signUpInputPassword);
            System.out.println(signUpInputEmail);

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
            else {
                return new ModelAndView(emailmap, "SignUp");
            }
        }, new JadeTemplateEngine());


        post("/hp", (rq, rs) -> {

            QueryParamsMap body = rq.queryMap();
            String loginInputEmail = body.get("email").value();
            String loginInputName = body.get("username").value();
            String loginInputPassword = body.get("password").value();

            usermap.put(user.getName(), user);
            for (String key : usermap.keySet()) {
                System.out.println("LoginUser " + key);
            } //output login users

            String passwdInDB = sqlitemethod2.passwdQuery(c, body.get("username").value());
            System.out.println("passwdInDB " + passwdInDB);

            rs.redirect("/userHome");
            String encryptedInputPasswd = sqlitemethod2.encryptedPasswd(loginInputPassword);
            user.setUser(loginInputName, encryptedInputPasswd, loginInputEmail);
            System.out.println("encryptedInputPasswd " + encryptedInputPasswd);
            if (encryptedInputPasswd.equals(passwdInDB)) {
                System.out.println(passwdInDB);

                System.out.println("right");
            } else {
                System.out.println(passwdInDB);
                System.out.println(encryptedInputPasswd);
                System.out.println("wrong");
            }
            return null;
        });


        before("/userHome", (rq, rs) -> {
            String name = user.getName();
            String passwd = sqlitemethod2.passwdQuery(c, name);
            if (!user.getPasswd().equals(passwd)) {
                rs.redirect("/login");
            }

        });

        get("/userHome", (rq, rs) -> {
            HashMap<String, String> map = new HashMap<>();
            ArrayList<Availatime> availatimeList = sqlitemethod2.availaTimeQuery(c);
            map.put("message2", "availatimeList" + "\n");

            ArrayList<Friendship> friendshipList = sqlitemethod2.friendshipQuery(c);

            for (int i = 0; i < availatimeList.size(); i++) {
                Availatime availatime = availatimeList.get(i);
                int userID1 = sqlitemethod2.IDQuery(c, user.getName());
                int userID2 = sqlitemethod2.IDQuery(c, availatime.getUserName());
                boolean isFriend = false;
                for (int j = 0; j < friendshipList.size(); j++) {
                    isFriend = isFriend || friendshipList.get(j).isFriendOrNot(userID1, userID2);
                }
                if (isFriend) {
                    String newAvailatime = map.get("message2") + availatime.getUserName() + ": " + availatime.getDate()
                            + " " + availatime.getStartTime() + " " + availatime.getEndTime() + " "
                            + availatime.getTendency() + "\n";
                    map.put("message2", newAvailatime);
                }
                System.out.println(availatime.getUserName() + ": " + availatime.getDate() + " "
                        + availatime.getStartTime() + " " + availatime.getEndTime() + " "
                        + availatime.getTendency() + "\n");
            }
            return new ModelAndView(map, "userHome");
        }, new JadeTemplateEngine());

        get("/friend", (rq, rs) -> new ModelAndView(new HashMap<>(), "friend"), new JadeTemplateEngine());

        get("/addAvailatime", (rq, rs) -> new ModelAndView(new HashMap<>(), "addAvailatime"), new JadeTemplateEngine());

        get("/addFriend", (rq, rs) -> new ModelAndView(new HashMap<>(), "addFriend"), new JadeTemplateEngine());

        post("/userHome", (rq, rs) -> {
            HashMap<String, String> map = new HashMap<>();
            QueryParamsMap body = rq.queryMap();
            String date = body.get("Date").value();
            String start = body.get("StartTime").value();
            String end = body.get("EndTime").value();
            String tendency = body.get("Tendency").value();
            Availatime tempAvailatime = new Availatime(date,start,end,tendency,user.getName());
            System.out.println(date);
            System.out.println(start);
            System.out.println(end);
            if(!tempAvailatime.isValidAvailatime()){
                System.out.println("invalid available timetime");
                map.put("message", "Your input of available time is invalid.");
                return new ModelAndView(map, "addAvailatime");
            }

            else {
                sqlitemethod2.addAvailatime(c, date, start, end, tendency, user.getName());

                ArrayList<Availatime> availatimeList = sqlitemethod2.availaTimeQuery(c);
                map.put("message2", "availatimeList" + "\n");

                ArrayList<Friendship> friendshipList = sqlitemethod2.friendshipQuery(c);

                for (int i = 0; i < availatimeList.size(); i++) {
                    Availatime availatime = availatimeList.get(i);
                    int userID1 = sqlitemethod2.IDQuery(c, user.getName());
                    int userID2 = sqlitemethod2.IDQuery(c, availatime.getUserName());
                    boolean isFriend = false;
                    for (int j = 0; j < friendshipList.size(); j++) {
                        isFriend = isFriend || friendshipList.get(j).isFriendOrNot(userID1, userID2);
                    }
                    if (isFriend) {
                        String newAvailatime = map.get("message2") + availatime.getUserName() + ": " + availatime.getDate() + " " + availatime.getStartTime() + " " + availatime.getEndTime() + " " + availatime.getTendency() + "\n";
                        map.put("message2", newAvailatime);
                    }
                    System.out.println(availatime.getUserName() + ": " + availatime.getDate() + " " + availatime.getStartTime() + " " + availatime.getEndTime() + " " + availatime.getTendency() + "\n");

                }
                System.out.println("success!!!!");
            }
            return new ModelAndView(map, "userHome");
        }, new JadeTemplateEngine());

        post("/success ", (rq, rs) -> {
            HashMap<String, String> map = new HashMap<>();
            QueryParamsMap body = rq.queryMap();
            String friendName = body.get("name").value();
            int UserID1 = sqlitemethod2.IDQuery(c, user.getName());
            int UserID2 = sqlitemethod2.IDQuery(c, friendName);
            if (UserID1 != UserID2 && UserID1 != 0 && UserID2 != 0) {
                sqlitemethod2.addFriend(c, UserID1, UserID2);
                map.put("message", friendName + " is ur friend now!");
            } else map.put("message", "sorry, ur friend " + friendName + " hasn't joined availabook. Invite him/her!");
            return new ModelAndView(map, "addFriendOrNot");
        }, new JadeTemplateEngine());

        get("/logOut", (rq, rs) -> {
            HashMap<String, String> map = new HashMap<>();
            user.setUser("null", "null", "null");
            return new ModelAndView(map, "logOut");
        }, new JadeTemplateEngine());

    }
}