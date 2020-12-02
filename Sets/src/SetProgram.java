import java.util.*;

public class SetProgram {

    public SetProgram() {

        ArrayList<Integer> list = new ArrayList<Integer>();
        for (int x = 0; x < 100; x++) {
            int rand = (int) (Math.random() * 100) + 1;
            boolean inThere = false;
            for (int y = 0; y < list.size(); y++) {
                if (list.get(y) == rand)
                    inThere = true;
            }
            if (!inThere)
                list.add(rand);
        }
        Collections.sort(list);
        System.out.println("List size: " + list.size());
        System.out.println(list);
        System.out.println("\n\n");

        list = new ArrayList<Integer>();
        for (int x = 0; x < 500; x++) {
            int rand = (int) (Math.random() * 100) + 1;
            boolean inThere = false;
            for (int y = 0; y < list.size(); y++) {
                if (list.get(y) == rand)
                    inThere = true;
            }
            if (!inThere)
                list.add(rand);
        }
        Collections.sort(list);
        System.out.println("List size: " + list.size());
        System.out.println(list);
        System.out.println("\n\n");

        TreeSet<Integer> set = new TreeSet<Integer>(); // ordered (slower)
//        HashSet<Integer> set = new TreeSet<Integer>(); // unordered (faster)
        for (int x = 0; x < 100; x++) {
            int rand = (int) (Math.random() * 100) + 1;
            set.add(rand);
        }
        System.out.println("Set size: " + set.size());
        System.out.println(set);
        System.out.println("\n\n");

        //Option 1 - Walking through a set
        System.out.println("\nOption 1: Use an Iterator");
        Iterator it = set.iterator();
        System.out.println("Iterate through the Set");
        while (it.hasNext()) {
            int temp = (int) it.next();
            System.out.println("Value:" + temp);
        }

        //Option 2 - Walking through a set
        System.out.println("\nOption 2: For-Each");
        System.out.println("Walking through the Set");
        for (Integer num : set)
            System.out.println("Value: " + num);

    }

    public static void main(String[] args) {
        SetProgram app = new SetProgram();
    }

}