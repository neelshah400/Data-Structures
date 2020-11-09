import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.*;

public class PassengerQueue {

    public PassengerQueue() {

        Queue<Passenger> q = new LinkedList<Passenger>();
        PriorityQueue<Passenger> pq = new PriorityQueue<Passenger>();

        File file = new File("PassengerInfo.txt");
        ArrayList<String> list = new ArrayList<String>();
        try {
            BufferedReader input = new BufferedReader(new FileReader(file));
            String text;
            int i = 0;
            while ((text = input.readLine()) != null) {
                if (i % 3 == 0) {
                    String[] textComponents = text.split(" ");
                    list.add(textComponents[1]);
                    list.add(textComponents[0]);
                }
                else
                    list.add(text);
                if (list.size() >= 4) {
                    Passenger p = new Passenger(list);
                    q.add(p);
                    if (p.durationInMinutes() <= 60)
                        pq.add(p);
                    list.clear();
                }
                i++;
            }
        } catch (IOException e) {
            System.out.println("File not found.");
        }

        String header = String.format("%-32s %-16s %-16s %-16s", "Last Name, First Name", "Flight City", "Flight Time", "etdCalc");

        System.out.println(header);
        while (!q.isEmpty())
            System.out.println(q.poll());
        System.out.println();

        System.out.println(header);
        while (!pq.isEmpty())
            System.out.println(pq.poll());

    }

    public static void main(String[] args) {
        PassengerQueue app = new PassengerQueue();
    }

    public class Passenger implements Comparable<Passenger> {

        private String lastName;
        private String firstName;
        private String flightCity;
        private String flightTime;

        public Passenger(ArrayList<String> list) {
            lastName = list.get(0);
            firstName = list.get(1);
            flightCity = list.get(2);
            flightTime = list.get(3);
        }

        public String getLastName() {
            return lastName;
        }

        public String getFirstName() {
            return firstName;
        }

        public String flightCity() {
            return flightCity;
        }

        public String flightTime() {
            return flightTime;
        }

        public int durationInMinutes() {
            SimpleDateFormat sdf = new SimpleDateFormat("h:mm a");
            try {
                Date current = sdf.parse("9:03 AM");
                Date departure = sdf.parse(flightTime);
                return (int) Math.abs(departure.getTime() - current.getTime()) / 60000;
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return -1;
        }

        public String etdCalc() {
            int totalMinutes = durationInMinutes();
            if (totalMinutes < 0)
                return "?";
            int hours = totalMinutes / 60;
            int minutes = totalMinutes % 60;
            return String.format("%s:%s", hours, minutes);
        }

        public String toString() {
            return String.format("%-32s %-16s %-16s %-16s", lastName + ", " + firstName, flightCity, flightTime, etdCalc());
        }

        public int compareTo(Passenger otherPassenger) {
            Integer current = durationInMinutes();
            Integer other = otherPassenger.durationInMinutes();
            return current > 60 ? 0: current.compareTo(other);
        }

    }

}