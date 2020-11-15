public class Runner {

    public Runner() {

        SuperList<Integer> list = new SuperList<Integer>();
        for (int i = 0; i < 30; i++)
            list.add((int) (Math.random() * 1000) + 1);
        System.out.println(list);
        System.out.println(list.size());

        SuperList<Integer> stack = new SuperList<Integer>();
        while (!list.isEmpty())
            stack.push(list.remove(0));

        while (!stack.isEmpty()) {
            int value = stack.pop();
            System.out.print("A" + value + ", ");
        }
        System.out.println();

        SuperList<Integer> list1 = new SuperList<Integer>();
        list1.add(0, 99);
        list1.add(1);
        list1.push(2);
        list1.add(3);
        list1.add(1, 99);
        System.out.println(list1);

        SuperList<Integer> list2 = new SuperList<Integer>(0);
        list2.add(0, 99);
        list2.add(1);
        list2.push(2);
        list2.add(3);
        list2.add(3, 99);
        System.out.println(list2);

    }

    public static void main(String[] args) {
        Runner app = new Runner();
    }

}