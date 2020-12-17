import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.TreeMap;

public class MapAndListDemo {

    public MapAndListDemo() {

        // Randomly generate 40 numbers between 1 and 200 inclusive
        // Store each number and then its respective prime factorization
        TreeMap<Integer, ArrayList<Integer>> map = new TreeMap<Integer, ArrayList<Integer>>();

        for (int i = 0; i < 40; i++) {
            int rand = (int) (Math.random() * 200) + 1;
            if (!map.containsKey(rand))
                map.put (rand, primes(rand));
        }
        System.out.println(map);

        // keys
        Iterator<Integer> itKey = map.keySet().iterator();
        while(itKey.hasNext()) {
            int key = itKey.next();
            System.out.println(key + ": " + map.get(key));
        }

        // values
        Iterator<ArrayList<Integer>> itVal = map.values().iterator();;
        while (itVal.hasNext())
            System.out.println(itVal.next());

        // keys and values
        Iterator itEntry = map.entrySet().iterator();
        while (itEntry.hasNext())
            System.out.println(itEntry.next());

    }

    public ArrayList<Integer> primes(int num) {

        ArrayList<Integer> factors = new ArrayList<Integer>();
        factors.add(1);
        int div = 2;

        // 200  /2  --> Put in factors list
        // 100  /2  --> Put in factors list
        // 50   /2  --> Put in factors list
        // 25   /2  --> Increase div by 1
        // 25   /3  --> Increase div by 1
        // 25   /4  --> Increase div by 1
        // 25   /5  --> Put in factors list
        // 5    /5  --> Put in factors list
        // Prime factorization: 1, 2, 2, 2, 5, 5

        while (num > 1) {
            if (num % div == 0) {
                factors.add(div);
                num /= div;
            }
            else
                div++;
        }

        return factors;

    }

    public static void main(String[] args) {
        MapAndListDemo app = new MapAndListDemo();
    }

}