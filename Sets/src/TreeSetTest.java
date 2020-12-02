import java.util.Iterator;
import java.util.TreeSet;

public class TreeSetTest {

    public TreeSetTest() {

        TreeSet<String> set = new TreeSet<String>();
        for (int x = 65; x < 90; x++)
            set.add(new String("" + (char) x));
        Iterator<String> it = set.iterator();
        System.out.println("Iterate through the HashSet");
        while (it.hasNext()) {
            String temp = (String) it.next();
            System.out.println("HashCode: " + temp.hashCode() + " \tValue:" + temp);
        }

        set = new TreeSet<String>();
        String[] list = {"ax", "dfgs", "sdfsd", "erw", "sdf", "reret", "hfg", "xhfgx", "2rwe"};
        for (int x = 65; x < 90; x++) {
            int rand1 = (int) (Math.random() * list.length);
            int rand2 = (int) (Math.random() * list.length);
            set.add(list[rand1] + list[rand2]);
        }
        it = set.iterator();
        System.out.println("Iterate through the HashSet");
        while (it.hasNext()) {
            String temp = (String) it.next();
            System.out.println("HashCode: " + temp.hashCode() + " \tValue:" + temp);
        }

        TreeSet<Integer> intSet = new TreeSet<Integer>();
        for (int x = 0; x < 20; x++)
            intSet.add((int) (Math.random() * 100) + 1);
        Iterator<Integer> it2 = intSet.iterator();
        System.out.println("Iterate through the HashSet");
        while (it2.hasNext()) {
            Integer temp = it2.next();
            System.out.println("HashCode: " + temp.hashCode() + " \tValue:" + temp);
        }

        System.out.println(intSet.first());
        intSet.remove(intSet.first());
        System.out.println(intSet.first());

    }

    public static void main(String[] args) {
        TreeSetTest app = new TreeSetTest();
    }

}