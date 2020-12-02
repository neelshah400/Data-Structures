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

        int newSize = list.size();
        for (int i = 0; i < newSize; i++) {
            int value = list.get(i);
            if (value % 3 == 0) {
                list.remove(i);
                newSize--;
                i--;
            }
        }
        System.out.printf(f1, "Remove numbers divisible by 3:", list);

        list.add(4, 55555);
        System.out.printf(f1, "Insert 55555 at index 4:", list);

        for (int i = 0; i < list.size() - 1; i++) {
            int j = i;
            while (j >= 0 && list.get(j) > list.get(j + 1)) {
                list.add(j, list.remove(j + 1));
                j--;
            }
        }
        System.out.printf(f1, "Sort in ascending order:", list);

        double median = 0.0;
        int size = list.size();
        if (size % 2 == 1)
            median = list.get(size / 2);
        else
            median = (double) (list.get(size / 2) + list.get((size / 2) - 1)) / 2.0;
        System.out.printf(f1, "Median:", median);

        int lastIndex = -1;
        System.out.printf(f2, "Values before median:", "[");
        for (int i = 0; i < list.size(); i++) {
            if ((double) list.get(i) < median)
                System.out.print(list.get(i));
            else
                break;
            if (i + 1 < list.size() && list.get(i + 1) < median)
                System.out.print(", ");
            else
                lastIndex = i;
        }
        System.out.println("]");

        System.out.printf(f2, "Values after median:", "[");
        for (int i = lastIndex + 1; i < list.size() - 1; i++) {
            if ((double) list.get(i) > median)
                System.out.print(list.get(i) + ", ");
        }
        System.out.println(list.get(list.size() - 1) + "]");
        System.out.println();

        SuperList<String> list2 = new SuperList<String>();

        String sentence = "The quick brown fox jumps over the lazy dog.";
        System.out.printf(f1, "Sentence:", sentence);

        sentence = sentence.replaceAll("[^a-zA-Z\\d\\s]", "");
        String[] words = sentence.split(" ");
        for (String word : words)
            list2.add(word);
        System.out.printf(f1, "Words:", list2);

        for (int i = 0; i < list2.size(); i++) {
            if (list2.get(i).length() <= 3)
                list2.remove(i);
        }
        System.out.printf(f1, "Remove short words:", list2);

        for (int i = 0; i < list2.size() - 1; i++) {
            int j = i;
            while (j >= 0 && list2.get(j).compareToIgnoreCase(list2.get(j + 1)) > 0) {
                list2.add(j, list2.remove(j + 1));
                j--;
            }
        }
        System.out.printf(f1, "Sort in ascending order:", list2);

        int sum = 0;
        for (int i = 0; i < list2.size(); i++)
            sum += list2.get(i).length();
        double avg = ((double) sum) / list2.size();
        System.out.printf(f1, "Average word length:", avg);

    }

    public static void main(String[] args) {
        Runner app = new Runner();
    }

}