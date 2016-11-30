/**
 * Created by Sophie on 11/28/16.
 */



public class Friendship {
    private int userID1;
    private int userID2;
    public Friendship(int userID1, int userID2){
        this.userID1=userID1;
        this.userID2=userID2;
    }
    public void setFriendship(int userID1, int userID2){
        this.userID1=userID1;
        this.userID2=userID2;
    }
    public boolean isFriendOrNot(int userID1, int userID2){
        if((this.userID1==userID1 && this.userID2==userID2)||(this.userID1==userID2 && this.userID2==userID1)||(userID1==userID2)){
            return true;//the last verification is the user can see their own avaialtime
        }
        else return false;
    }

}

