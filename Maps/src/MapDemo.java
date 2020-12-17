import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

public class MapDemo {

    public MapDemo() {

        // Map is like a super array
        // key   --> index
        // value --> value
        HashMap<Integer, Integer> map = new HashMap<Integer, Integer>();

        // Roll the dice example: keep track of frequency of each side
        for (int i = 0; i < 60; i++) {
            int roll = (int) (Math.random() * 6) + 1;
//            System.out.println(roll);
            if (!map.containsKey(roll))
                map.put(roll, 0);
            map.put(roll, map.get(roll) + 1);
        }
        System.out.println(map);

        // Characters in String example: keep track of frequency of each char
        TreeMap<Character, Integer> charMap = new TreeMap<Character, Integer>();
        String str = "Hello. My name is Neel. I am now typing a sentence for you.";
        for (int i = 0; i < str.length(); i++) {
            if (!charMap.containsKey(str.charAt(i)))
                charMap.put(str.charAt(i), 0);
            charMap.put(str.charAt(i), charMap.get(str.charAt(i)) + 1);
        }
        System.out.println(charMap);

        // Iterating through keys, values, and entries
        // Option 1: Iterator
        // Option 2: for each loop

        Iterator<Character> keys = charMap.keySet().iterator();
        while (keys.hasNext())
            System.out.print(keys.next() + "\t");
        System.out.println();

        for (char c : charMap.keySet())
            System.out.print(c + "\t");
        System.out.println();

        Iterator<Integer> values = charMap.values().iterator();
        while (values.hasNext())
            System.out.print(values.next() + "\t");
        System.out.println();

        for (int cnt : charMap.values())
            System.out.print(cnt + "\t");
        System.out.println();

        Iterator entries = charMap.entrySet().iterator();
        while (entries.hasNext())
            System.out.print(entries.next() + ", ");
        System.out.println();

        for (Map.Entry<Character, Integer> entry : charMap.entrySet())
            System.out.print(entry + ", ");
        System.out.println();

    }

    public static void main(String[] args) {
        MapDemo app = new MapDemo();
    }

}