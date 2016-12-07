import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Sophie on 11/28/16.
 */



@SuppressWarnings("ALL")
public class Availatime {
    private String date;
    private String startTime;
    private String endTime;
    private String tendency;
    private String userName;

    public String getStartTime(){
        return startTime;
    }
    public String getEndTime(){
        return endTime;
    }
    public String getTendency(){
        return tendency;
    }
    public String getDate(){return date;}
    public String getUserName(){return userName;}
    public Availatime(String date, String startTime, String endTime, String tendency, String userName){
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.tendency = tendency;
        this.userName = userName;
    }
    public void setAvailatime(String date, String startTime, String endTime, String tendency, String userName) {
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.tendency = tendency;
        this.userName = userName;
    }
    public boolean isValidAvailatime(){
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        DateFormat timeFormat = new SimpleDateFormat("HH:mm");
        Date date = new Date();
        Date time = new Date();
        System.out.println(dateFormat.format(date));
        System.out.println(timeFormat.format(time));
        if(this.getStartTime().compareTo(this.getEndTime()) >= 0
                || this.getDate().compareTo(String.valueOf(dateFormat.format(date))) < 0
                || (this.getDate().compareTo(dateFormat.format(date)) == 0
                && this.getStartTime().compareTo(timeFormat.format(time)) < 0)
                )
        {
            System.out.println(this.getDate().compareTo(timeFormat.format(date)));
            return false;

        }
        else {
            System.out.println("Success");
            return true;
        }
    }

    public static void main(String[] args) { //just for testing comparison function

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        DateFormat timeFormat = new SimpleDateFormat("HH:mm");
        //get current date time with Date()
        Date date = new Date();
        Date time = new Date();
        System.out.println(dateFormat.format(date));
        System.out.println(timeFormat.format(time));

        //get current date time with Calendar()
        Calendar cal = Calendar.getInstance();
        //System.out.println(dateFormat.format(cal.getTime()));

    }
}

