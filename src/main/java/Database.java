/**
 * Created by Sophie on 11/28/16.
 */

import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.*;
import java.util.ArrayList;
import java.security.MessageDigest;



@SuppressWarnings("UnusedAssignment")
public class Database{
    public static void main( String args[] )
    {
        Connection c = null;
        Statement stmt = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:data.db");
            System.out.println("Opened database successfully");

            stmt = c.createStatement();

            String sql1 = "CREATE TABLE USER " +
                    "(ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                    " NAME           TEXT     NOT NULL, " +
                    " PASSWORD       CHAR(50) NOT NULL, " +
                    " EMAIL          CHAR(50) NOT NULL UNIQUE)";

            String sql2 = "CREATE TABLE FRIENDSHIP " +
                    "(UserID1 INT REFERENCES USER (ID)," +
                    "USERID2 INT REFERENCES USER (ID))";

            String sql3 = "CREATE TABLE AVAILATIME " +
                    "(startTime STRING," +
                    " endTime   STRING, " +
                    " tendency  STRING, " +
                    " userName  STRING, " +
                    " date      STRING)";
            stmt.executeUpdate(sql1);
            stmt.executeUpdate(sql2);
            stmt.executeUpdate(sql3);
            stmt.close();
            c.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        System.out.println("Table created successfully");
    }

    public boolean createDatabase(){
        Connection c = null;
        Statement stmt = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:data.db");
            System.out.println("Opened database successfully");

            stmt = c.createStatement();

            String sql1 = "CREATE TABLE USER " +
                    "(ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                    " NAME           TEXT     NOT NULL, " +
                    " PASSWORD       CHAR(50) NOT NULL, " +
                    " EMAIL          CHAR(50) NOT NULL UNIQUE)";

            String sql2 = "CREATE TABLE FRIENDSHIP " +
                    "(UserID1 INT REFERENCES USER (ID)," +
                    "USERID2 INT REFERENCES USER (ID))";

            String sql3 = "CREATE TABLE AVAILATIME " +
                    "(startTime STRING," +
                    " endTime   STRING, " +
                    " tendency  STRING, " +
                    " userName  STRING, " +
                    " date      STRING)";
            stmt.executeUpdate(sql1);
            stmt.executeUpdate(sql2);
            stmt.executeUpdate(sql3);
            stmt.close();
            c.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        System.out.println("Table created successfully");
        return true;
    }
    public Connection c = null;

    public boolean openDatabase(){
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:data.db");
        }catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
        System.out.println("opened database successfully");
        return true;
    }


    public String encryptedPasswd(String password){
        String generatedPassword = null;

        // Create MessageDigest instance for MD5
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("MD5");

            //Add password bytes to digest
            md.update(password.getBytes());
            //Get the hash's bytes
            byte[] bytes = md.digest();
            //This bytes[] has bytes in decimal format;
            //Convert it to hexadecimal format
            StringBuilder sb = new StringBuilder();
            for(int i=0; i< bytes.length ;i++)
            {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            //Get complete hashed password in hex format
            generatedPassword = sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return generatedPassword;


    }


    public boolean isValidEmailAddress(String email) {
        String ePattern =
                "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])" +
                        "|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(email);
        return m.matches();
    }


    public boolean isValidName(String name) {
        String ePattern =
                "[a-z|A-Z]+";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(name);
        return m.matches();
    }
    /* return true if the provided name contains only letters*/


    public boolean isValidPasswd(String passwd) {
        String ePattern =
                "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&*()_+\\-=\\[\\]{};’:”\\\\|,.<>\\/?]).{6,15}$" ;
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(passwd);
        return m.matches();
    }
    /* returns true if the provided password
  	- contains at least 1 digit
  	- contains at least 1 lowercase letter
  	- contains at least 1 uppercase letter
  	- contains at least 1 of the special characters !@#$%^&*()_+\-=[]{};':"\|,.<>/?
  	- is at least 6 character long
  	- is at most 15 characters long*/

    public boolean noDuplicateUsername(String username){
        try {
            Statement stmt = null;
            ResultSet rs = null;
            String duplicate = null;

            c.setAutoCommit(false);
            stmt = c.createStatement();
            PreparedStatement st = c.prepareStatement("SELECT * FROM USER where NAME=?");
            st.setString(1, username);
            rs = st.executeQuery();

            while(rs.next()){
                duplicate = rs.getString(1);
            }

            if(duplicate == null){
                return true;
            }
        }catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
        return false;
    }

    public boolean noDuplicateemail(String email){
        try {
            Statement stmt = null;
            ResultSet rs = null;
            String duplicate = null;

            c.setAutoCommit(false);
            stmt = c.createStatement();
            PreparedStatement st = c.prepareStatement("SELECT * FROM USER where EMAIL=?");
            st.setString(1, email);
            rs = st.executeQuery();

            while(rs.next()){
                duplicate = rs.getString(1);
            }

            if(duplicate == null){
                return true;
            }
        }catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
        return false;
    }
    public String signUp(Connection c, String name, String passwd, String email) {


        Statement stmt = null;
        if ((noDuplicateUsername(name))&& (noDuplicateemail(email))&&
                (isValidEmailAddress(email)) && (isValidName(name))&&(isValidPasswd(passwd))) {
            try {

                c.setAutoCommit(false);

                stmt = c.createStatement();

                String sql = "INSERT INTO USER (NAME,PASSWORD,EMAIL) " +
                        "VALUES ( '" + name + "','" + encryptedPasswd(passwd) + "','" + email + "');";
                stmt.executeUpdate(sql);
                stmt.close();
                c.commit();

            } catch (Exception e) {
                System.err.println(e.getClass().getName() + ": " + e.getMessage());
            }
            System.out.println("Records created successfully");
            return "Success";
        }
        else {
            if (!isValidName(name)){
                if (!isValidEmailAddress(email)){
                    if (!isValidPasswd(passwd)){
                        return "Name&Email&Password";
                    }
                    else {
                        return "Name&Email";
                    }
                }
                else if (!isValidPasswd(passwd)){
                    return "Name&Password";
                }
                else {
                    return "Name";
                }
            }
            else if (!isValidEmailAddress(email)){
                if (!isValidPasswd(passwd)){
                    return "Email&Password";
                }
                else {
                    return "Email&Password";
                }
            }
            else if (!isValidPasswd(passwd)){
                return "Password";
            }
            else if (!noDuplicateUsername(name)){
                return "DuplicateUsername";
            }
            else if (!noDuplicateemail(email)){
                return "DuplicateEmail";
            }
            else return "fail";
        }
    }

    public void addAvailatime(Connection c, String date, String start, String end, String tendency, String userName) {


        Statement stmt = null;
        try {

            c.setAutoCommit(false);

            stmt = c.createStatement();
            String sql = "INSERT INTO AVAILATIME (date,startTime,endTime,tendency,userName) " +
                    "VALUES ( '" + date + "','" + start + "','" + end + "','"+tendency+"','"+userName+"');";
            stmt.executeUpdate(sql);
            stmt.close();
            c.commit();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
        System.out.println("Records created successfully");
    }

    public void addFriend(Connection c, int UserID1, int UserID2){
        Statement stmt = null;
        try {

            c.setAutoCommit(false);

            stmt = c.createStatement();
            String sql = "INSERT INTO FRIENDSHIP (UserID1,UserID2) " +
                    "VALUES ('"+UserID1+"','"+UserID2+"');";
            stmt.executeUpdate(sql);
            stmt.close();
            c.commit();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
        System.out.println("Records created successfully");
    }

    public int IDQuery(Connection c, String name) {

        Statement stmt = null;
        ResultSet rs = null;
        try {

            c.setAutoCommit(false);

            stmt = c.createStatement();

            PreparedStatement st = c.prepareStatement("SELECT ID FROM User where name=?");

            st.setString(1, name);

            rs = st.executeQuery();
            c.commit();
            return rs.getInt("ID");

        }catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
        System.out.println("no such ID");
        return 0; //0 means that we can't find the user in database

    }
    public String NameQuery(Connection c, int userID) {

        Statement stmt = null;
        ResultSet rs = null;
        try {

            c.setAutoCommit(false);

            stmt = c.createStatement();

            PreparedStatement st = c.prepareStatement("SELECT name FROM User where ID=?");

            st.setInt(1, userID);

            rs = st.executeQuery();
            c.commit();
            return rs.getString("name");

        }catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
        System.out.println("no such ID");
        return null; //null means that we can't find the user in database

    }

    public String passwdQuery(Connection c, String name) {

        Statement stmt = null;
        ResultSet rs = null;
        try {

            c.setAutoCommit(false);

            stmt = c.createStatement();

            PreparedStatement st = c.prepareStatement("SELECT password FROM User where name=?");

            st.setString(1, name);

            rs = st.executeQuery();
            c.commit();
            stmt.close();
            return rs.getString("password");


        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
        }
        System.out.println("passwordQuery failed");
        return null;
    }
    public ArrayList<Availatime> availaTimeQuery(Connection c){
        Statement stmt = null;
        try {
            c.setAutoCommit(false);
            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM AVAILATIME;");
            ArrayList<Availatime> availatimeList = new ArrayList<>();
            while (rs.next()) {
                Availatime availatime = new Availatime(rs.getString("date"), rs.getString("startTime"),
                        rs.getString("endTime"), rs.getString("tendency"), rs.getString("userName"));
                availatimeList.add(availatime);
            }
            rs.close();
            stmt.close();
            return availatimeList;
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
        System.out.println("AvailatimeQuery failed");
        return null;
    }
    public ArrayList<Friendship> friendshipQuery(Connection c){
        Statement stmt = null;
        try{
            c.setAutoCommit(false);
            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM FRIENDSHIP;");
            ArrayList<Friendship> friendshipList = new ArrayList<>();
            while(rs.next()){
                Friendship friendship = new Friendship(rs.getInt("UserID1"),rs.getInt("UserID2"));
                friendshipList.add(friendship);
            }
            rs.close();
            stmt.close();
            return friendshipList;
        } catch (Exception e){
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
        System.out.println("FriendshipQuery failed");
        return null;
    }

    public void deleteFriend(Connection c, int userID, int friendID){
        Statement stmt = null;
        try{
            c.setAutoCommit(false);
            stmt = c.createStatement();
            //PreparedStatement st = c.prepareStatement("DELETE from FRIENDSHIP where UserID1=?;");
            //st.setInt(1,userID);
            PreparedStatement st = c.prepareStatement("DELETE from FRIENDSHIP where (UserID1=? AND UserID2=?) OR (UserID1=? AND UserID2=?)");
            st.setInt(1,userID);
            st.setInt(2,friendID);
            st.setInt(3,friendID);
            st.setInt(4,userID);
            st.executeUpdate();
            c.commit();
            stmt.close();
        }catch (Exception e){
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
    }

    public void deleteAvailatime(Connection c, String startTime, String endTime, String tendency, String userName, String date){
        Statement stmt = null;
        try{
            c.setAutoCommit(false);
            stmt = c.createStatement();
            //PreparedStatement st = c.prepareStatement("DELETE from FRIENDSHIP where UserID1=?;");
            //st.setInt(1,userID);
            PreparedStatement st = c.prepareStatement("DELETE from AVAILATIME where (startTime=? AND endTime=? AND tendency=? AND userName=? AND date=?)");
            st.setString(1,startTime);
            st.setString(2,endTime);
            st.setString(3,tendency);
            st.setString(4,userName);
            st.setString(5,date);
            st.executeUpdate();
            c.commit();
            stmt.close();
        }catch (Exception e){
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
    }



}