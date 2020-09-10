import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class AmicableNumbers {

    public AmicableNumbers() {
        File file = new File("AmicableNumsFile.txt");
        try {
            BufferedReader input = new BufferedReader(new FileReader(file));
            String text;
            while ((text = input.readLine()) != null) {
                String[] nums = text.split(" ");
                int num1 = Integer.parseInt(nums[0]);
                int num2 = Integer.parseInt(nums[1]);
                ArrayList<Integer> factors1 = getFactors(num1);
                ArrayList<Integer> factors2 = getFactors(num2);
                int sum1 = getSum(factors1);
                int sum2 = getSum(factors2);
                printOutput(num1, num2, factors1, factors2, sum1, sum2);
            }
        } catch (IOException e) {
            System.out.println("File not found.");
        }
    }

    public ArrayList<Integer> getFactors(int num) {
        ArrayList<Integer> factors = new ArrayList<Integer>();
        if (num >= 1) {
            for (int i = 1; i < num; i++) {
                if (num % i == 0)
                    factors.add(i);
            }
        }
        return factors;
    }

    public int getSum(ArrayList<Integer> factors) {
        int sum = 0;
        for (int factor : factors)
            sum += factor;
        return sum;
    }

    public String getFormattedFactors(ArrayList<Integer> factors) {
        String str = "";
        for (int i = 0; i < factors.size(); i++) {
            str += factors.get(i);
            if (i < factors.size() - 2)
                str += ", ";
            else if (i == factors.size() - 2)
                str += " and ";
        }
        return str;
    }

    public void printOutput(int num1, int num2, ArrayList<Integer> factors1, ArrayList<Integer> factors2, int sum1, int sum2) {
        System.out.print("The numbers " + num1 + " and " + num2 + " are ");
        if (num1 != sum2 || num2 != sum1)
            System.out.print("not ");
        System.out.println("amicable.");
        System.out.println("\tFactors of " + num1 + " are " + getFormattedFactors(factors1) + ". Sum is " + sum1 + ".");
        System.out.println("\tFactors of " + num2 + " are " + getFormattedFactors(factors2) + ". Sum is " + sum2 + ".");
    }

    public static void main(String[] args) {
        AmicableNumbers app = new AmicableNumbers();
    }

}
