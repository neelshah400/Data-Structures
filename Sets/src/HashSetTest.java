import java.util.HashSet;
import java.util.Iterator;

public class HashSetTest {

    public HashSetTest() {

        HashSet<String> set = new HashSet<String>();
        for (int x = 65; x < 90; x++)
            set.add(new String("" + (char) x));
        Iterator<String> it = set.iterator();
        System.out.println("Iterate through the HashSet");
        while (it.hasNext()) {
            String temp = it.next();
            System.out.println("HashCode: " + temp.hashCode() + " \tValue:" + temp);
        }
        System.out.println(set);

        set = new HashSet<String>();
        String[] list = {"ax", "dfgs", "sdfsd", "erw", "sdf", "reret", "hfg", "xhfgx", "2rwe"};
        for (int x = 0; x < 20; x++) {
            int rand1 = (int) (Math.random() * list.length);
            int rand2 = (int) (Math.random() * list.length);
            System.out.println(list[rand1] + list[rand2]);
            set.add(list[rand1] + list[rand2]);
        }
        it = set.iterator();
        System.out.println("Iterate through the HashSet");
        int count = 0;
        while (it.hasNext()) {
            String temp = (String) it.next();
            System.out.println("Count: " + count + "\tHashCode: " + temp.hashCode() + " \tValue:" + temp);
            count++;
        }

        HashSet<Integer> intSet = new HashSet<Integer>();
        for (int x = 0; x < 20; x++)
            intSet.add((int) (Math.random() * 40) + 1);
        System.out.println("Size: " + intSet.size());
        Iterator<Integer> it2 = intSet.iterator();
        System.out.println("Iterate through the HashSet");
        while (it2.hasNext()) {
            Integer temp = it2.next();
            System.out.println("HashCode: " + temp.hashCode() + " \tValue:" + temp);
        }

    }

    public static void main(String[] args) {
        HashSetTest app = new HashSetTest();
    }

}