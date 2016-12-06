import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class Test {
    public static void main(String[] args) {
        System.out.println("Password validation: ");
        passwordtest();
        System.out.println();
        System.out.println("Name validation: ");
        nametest();
        System.out.println();
        System.out.println("Email validation: ");
        emailtest();
        System.out.println();
        System.out.println("Time validation: ");
        timetest();
        System.out.println();
        System.out.println("Date validation: ");
        datetest();
        System.out.println();
    }

    public static void passwordtest() {
        Database test = new Database();
        String password[] = new String[7];
        password[0] = "P@ssw0rd";//valid passord
        password[1] = "P@ssword";//invalid(no digit)
        password[2] = "p@ssw0rd";//invalid(no uppercase letter)
        password[3] = "P@SSW0RD";//invalid(no lowercase letter)
        password[4] = "Passw0rd";//invalid(no special character)
        password[5] = "P@sw0";//invalid(length less than 6)
        password[6] = "P@ssw0rddddddddddddddd";//invalid(length longer than 15)
        for (String passwordcheck : password) {
            System.out.println(passwordcheck + " validation is " + test.isValidPasswd(passwordcheck));
        }
    }

    public static void nametest() {
        Database test = new Database();
        String name[] = new String[3];
        name[0] = "Name";//valid name
        name[1] = "Name1";//invalid(contains digit)
        name[2] = "Name@";//invalid(contains special character)
        for (String namecheck : name) {
            System.out.println(namecheck + " validation is " + test.isValidName(namecheck));
        }
    }
    public static void emailtest() {
        Database test = new Database();
        String email[] = new String[4];
        email[0] = "Name@mail.com";//valid email
        email[1] = "Namemail.com";//invalid(not contain '@' character)
        email[2] = "Name@mailcom";//invalid(not contain '.')
        email[3] = "Name@mail.";//invalid('.' is not followed by letters)
        for (String emailcheck : email) {
            System.out.println(emailcheck + " validation is " + test.isValidEmailAddress(emailcheck));
        }
    }
    public static void timetest() {
        DateFormat timeFormat = new SimpleDateFormat("HH:mm");
        Date time = new Date();
        String Starttime[] = new String[3];
        Starttime[0] = "10:00";
        Starttime[1] = "13:00";
        Starttime[2] = timeFormat.format(time);
        String Endtime[] = new String[3];
        Endtime[0] = "11:00";
        Endtime[1] = "12:00";
        Endtime[2] = timeFormat.format(time);
        int i=0;
        while(i<2){
            if(Starttime[i].compareTo(Endtime[i])>=0)
            {
                System.out.println("Starttime " + Starttime[i] + " cannot be equal to or later than Endtime " + Endtime[i]);
            }
            if(Endtime[i].compareTo(timeFormat.format(time))<0){
                System.out.println("Endtime " + Endtime[i] + " cannot be equal to or earlier than current time " + timeFormat.format(time));
            }
            else System.out.println("Validation passed");
            i++;
        }
    }
    public static void datetest() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        String date1[] = new String[3];
        date1[0] = "2000-01-01";
        date1[1] = dateFormat.format(date);
        date1[2] = "2100-01-01";
        for(String datecheck : date1){
            if(datecheck.compareTo(dateFormat.format(date)) < 0){
                System.out.println(datecheck +" validation is false");
            }
            else System.out.println(datecheck +" validation is true");
        }
    }
}