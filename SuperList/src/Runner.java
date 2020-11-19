import java.util.Collections;

public class Runner {

    public Runner() {

        String f1 = "%-32s%s\n";
        String f2 = "%-32s%s";

        SuperList<Integer> list = new SuperList<Integer>();
        for (int i = 0; i < 30; i++)
            list.add((int) (Math.random() * 1000) + 1);
        System.out.printf(f1, "ArrayList version:", list);
        System.out.printf(f1, "Size:", list.size());

        SuperList<Integer> stack = new SuperList<Integer>();
        while (!list.isEmpty())
            stack.push(list.remove(0));

        SuperList<Integer> queue = new SuperList<Integer>();
        System.out.printf(f2, "Stack version:", "[");
        while (!stack.isEmpty()) {
            int value = stack.pop();
            System.out.print(value);
            System.out.print(stack.isEmpty() ? "]" : ", ");
            queue.add(value);
        }
        System.out.println();

        System.out.printf(f2, "Queue version:", "[");
        while (!queue.isEmpty()) {
            int value = queue.poll();
            System.out.print(value);
            System.out.print(queue.isEmpty() ? "]" : ", ");
            int index = (int) (Math.random() * list.size());
            list.add(index, value);
        }
        System.out.println();

        System.out.printf(f1, "Randomized ArrayList version:", list);

        int overallSum = 0;
        int evenIndexSum = 0;
        int oddIndexSum = 0;
        for (int i = 0; i < list.size(); i++) {
            int value = list.get(i);
            overallSum += value;
            if (i % 2 == 0)
                evenIndexSum += value;
            else
                oddIndexSum += value;
        }
        System.out.printf(f1, "Overall sum:", overallSum);
        System.out.printf(f1, "Even index sum:", evenIndexSum);
        System.out.printf(f1, "Odd index sum:", oddIndexSum);

        int oldSize = list.size();
        for (int i = 0; i < oldSize; i++) {
            int value = list.get(i);
            if (value % 2 == 0)
                list.add(value);
        }
        System.out.printf(f1, "Duplicate even numbers:", list);

        oldSize = list.size();
        for (int i = 0; i < oldSize; i++) {
            int value = list.get(i);
            if (value % 3 == 0)
                list.remove(i);
        }
        System.out.printf(f1, "Remove numbers divisible by 3:", list);

        list.add(4, 55555);
        System.out.printf(f1, "Insert 55555 at index 4:", list);

//        for (int i = 0; i < list.size() - 1; i++) {
//            int minIndex = i;
//            for (int j = i + 1; j < list.size(); j++) {
//                if (list.get(j) < list.get(minIndex))
//                    minIndex = j;
//            }
//            int temp = list.get(i);
//            list.remove(i);
//            list.add(i, list.get(minIndex));
//            list.remove(minIndex);
//            list.add(minIndex, temp);
//        }
//        System.out.printf(f1, "Sort in ascending order:", list);

    }

    public static void main(String[] args) {
        Runner app = new Runner();
    }

}