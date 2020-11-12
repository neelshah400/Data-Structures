import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Stack;

public class CarTask {

    public CarTask() {

        boolean onFirstLine = true;
        String[] h = new String[8];
        Queue<Car> q = new LinkedList<Car>();
        Stack<Car> s = new Stack<Car>();
        PriorityQueue<Car> pq = new PriorityQueue<Car>();

        File file = new File("CarData.txt");
        try {
            BufferedReader input = new BufferedReader(new FileReader(file));
            String text;
            while ((text = input.readLine()) != null) {
                String[] data = text.split("\t");
                if (onFirstLine) {
                    h = data;
                    onFirstLine = false;
                }
                else {
                    Car car = new Car(data);
                    q.add(car);
                }
            }
        } catch (IOException e) {
            System.out.println("File not found.");
        }

        String header = String.format("%-24s %-24s %-24s %-24s %-24s %-24s %-24s %-24s", h[0], h[1], h[2], h[3], h[4], h[5], h[6], h[7]);

        System.out.println("QUEUE:\n" + header);
        while (!q.isEmpty()) {
            System.out.println(q.peek());
            s.push(q.poll());
        }

        System.out.println("\nSTACK:\n" + header);
        while(!s.isEmpty()) {
            System.out.println(s.peek());
            pq.add(s.pop());
        }

        System.out.println("\nPRIORITY QUEUE:\n" + header);
        while (!pq.isEmpty()) {
            System.out.println(pq.poll());
        }

    }

    public static void main(String[] args) {
        CarTask app = new CarTask();
    }

    public class Car implements Comparable<Car> {

        private int carID, milesPerGallon, engineSize, horsepower, weight, acceleration, countryOfOrigin, cylinders;

        public Car(String[] data) {
            int[] numbers = new int[data.length];
            for (int i = 0; i < data.length; i++)
                numbers[i] = Integer.parseInt(data[i]);
            carID = numbers[0];
            milesPerGallon = numbers[1];
            engineSize = numbers[2];
            horsepower = numbers[3];
            weight = numbers[4];
            acceleration = numbers[5];
            countryOfOrigin = numbers[6];
            cylinders = numbers[7];
        }

        public String toString() {
            return String.format("%-24s %-24s %-24s %-24s %-24s %-24s %-24s %-24s", carID, milesPerGallon, engineSize, horsepower, weight, acceleration, countryOfOrigin, cylinders);
        }

        public int compareTo(Car o) {
            int[] properties = {acceleration, milesPerGallon, horsepower, engineSize, weight, cylinders, carID};
            int[] oProperties = {o.acceleration, o.milesPerGallon, o.horsepower, o.engineSize, o.weight, o.cylinders, o.carID};
            for (int i = 0; i < properties.length; i++) {
                int property = properties[i];
                int oProperty = oProperties[i];
                int comparison = Integer.compare(property, oProperty);
                if (comparison != 0)
                    return comparison;
            }
            return 0;
        }

    }

}