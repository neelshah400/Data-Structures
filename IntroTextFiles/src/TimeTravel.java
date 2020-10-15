import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TimeTravel {

    public TimeTravel() {
        File file = new File("TravelFile.txt");
        try {
            BufferedReader input = new BufferedReader(new FileReader(file));
            String text;
            int tripNumber = 1;
            while ((text = input.readLine()) != null) {
                System.out.println("Trip " + tripNumber + ":");
                Calendar calendar = Calendar.getInstance();
                System.out.println("\tDeparture Date and Time: " + getFormattedDateTime(calendar));
                String[] travelInfo = text.split(" ");
                int days = Integer.parseInt(travelInfo[0]);
                int hours = Integer.parseInt(travelInfo[1]);
                int minutes = Integer.parseInt(travelInfo[2]);
                calendar.add(Calendar.DATE, days);
                calendar.add(Calendar.HOUR, hours);
                calendar.add(Calendar.MINUTE, minutes);
                System.out.println("\tArrival Date and Time: " + getFormattedDateTime(calendar));
                tripNumber++;
            }
        } catch (IOException e) {
            System.out.println("File not found.");
        }
    }

    public String getFormattedDateTime(Calendar calendar) {
        Date date = calendar.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("h:mm a' on 'M/d/yyyy");
        return sdf.format(date);
    }

    public static void main(String[] args) {
        TimeTravel app = new TimeTravel();
    }

}
