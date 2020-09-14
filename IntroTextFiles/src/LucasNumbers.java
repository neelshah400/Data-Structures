import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;

public class LucasNumbers {

    public LucasNumbers() {
        File file = new File("LucasNumFile.txt");
        try {
            BufferedReader input = new BufferedReader(new FileReader(file));
            String text;
            while ((text = input.readLine()) != null) {
                BigInteger n = new BigInteger(text);
                System.out.println("Term " + n + ": " + getTerm(n));
            }
        } catch (IOException e) {
            System.out.println("File not found.");
        }
    }

    public static BigInteger getTerm(BigInteger n) {
        ArrayList<BigInteger> list = new ArrayList<BigInteger>();
        for (BigInteger i = BigInteger.ZERO; i.compareTo(n) == -1 || i.compareTo(n) == 0; i = i.add(BigInteger.ONE)) {
            if (i.compareTo(BigInteger.ZERO) == 0)
                list.add(BigInteger.valueOf(2));
            else if (i.compareTo(BigInteger.ONE) == 0)
                list.add(BigInteger.valueOf(1));
            else
                list.add(list.get(i.intValue() - 2).add(list.get(i.intValue() - 1)));
        }
        return list.get(list.size() - 1);
    }

    public static void main(String[] args) {
        LucasNumbers app = new LucasNumbers();
    }

}
