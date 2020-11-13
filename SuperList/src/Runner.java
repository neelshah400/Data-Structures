public class Runner {

    public Runner() {

        SuperList<Integer> list1 = new SuperList<Integer>();
        list1.add(1);
        list1.add(2);
        list1.add(3);
        list1.add(1, 99);
        System.out.println(list1);

        SuperList<Integer> list2 = new SuperList<Integer>(0);
        list2.add(1);
        list2.add(2);
        list2.add(3);
        list2.add(3, 99);
        System.out.println(list2);

    }

    public static void main(String[] args) {
        Runner app = new Runner();
    }

}