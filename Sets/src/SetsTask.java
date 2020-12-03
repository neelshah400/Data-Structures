import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

public class SetsTask {

    public SetsTask() {

        ArrayList<HashSet<Integer>> list = new ArrayList<HashSet<Integer>>();
        int size = (int) (Math.random() * 11) + 2;
        for (int i = 0; i < size; i++) {
            HashSet<Integer> set = new HashSet<Integer>();
            while (set.size() < 10) {
                int num = (int) (Math.random() * 30) + 1;
                set.add(num);
            }
            list.add(set);
        }

        System.out.println("HashSets:\n");
        for (HashSet<Integer> set : list)
            System.out.println(set);
        System.out.println();

        HashSet<Integer> intersection = list.get(0);
        HashSet<Integer> union = list.get(0);
        HashSet<Integer> evenIntersection = list.get(0);
        HashSet<Integer> evenUnion = list.get(0);

        for (int i = 1; i < list.size(); i++) {
            HashSet<Integer> set = list.get(i);
            intersection = intersection(intersection, set);
            union = union(union, set);
            evenIntersection = evenIntersection(evenIntersection, set);
            evenUnion = evenUnion(evenUnion, set);
        }

        System.out.println("Intersection:\t\t" + intersection);
        System.out.println("Union:\t\t\t\t" + union);
        System.out.println("Even Intersection:\t" + evenIntersection);
        System.out.println("Even Union:\t\t\t" + evenUnion);

    }

    public HashSet<Integer> intersection(HashSet<Integer> set1, HashSet<Integer> set2) {
        HashSet<Integer> set = new HashSet<Integer>(set1);
        set.retainAll(set2);
        return set;
    }

    public HashSet<Integer> union(HashSet<Integer> set1, HashSet<Integer> set2) {
        HashSet<Integer> set = new HashSet<Integer>(set1);
        set.addAll(set2);
        return set;
    }

    public HashSet<Integer> evenIntersection(HashSet<Integer> set1, HashSet<Integer> set2) {
        HashSet<Integer> set = intersection(set1, set2);
        Iterator<Integer> itr = set.iterator();
        while (itr.hasNext()) {
            int num = itr.next();
            if (num % 2 == 1)
                itr.remove();
        }
        return set;
    }

    public HashSet<Integer> evenUnion(HashSet<Integer> set1, HashSet<Integer> set2) {
        HashSet<Integer> set = union(set1, set2);
        Iterator<Integer> itr = set.iterator();
        while (itr.hasNext()) {
            int num = itr.next();
            if (num % 2 == 1)
                itr.remove();
        }
        return set;
    }

    public static void main(String[] args) {
        SetsTask app = new SetsTask();
    }

}