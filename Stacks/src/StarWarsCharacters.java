import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Stack;

public class StarWarsCharacters {

    public StarWarsCharacters() {

        Stack<Character> males = new Stack<Character>();
        Stack<Character> females = new Stack<Character>();
        Stack<Character> droids = new Stack<Character>();
        Stack<Character> ages = new Stack<Character>();

        File file = new File("StarWarsCharacters.csv");
        try {
            BufferedReader input = new BufferedReader(new FileReader(file));
            String text;
            while ((text = input.readLine()) != null) {
                if (text.contains("\"")) {
                    int index = text.indexOf("\"");
                    text = text.substring(0, index) + text.substring(index).replaceFirst(",", "/");
                }
                String[] row = text.split(",");
                Character character = new Character(row);
                if (character.getGender().equals("male"))
                    males.push(character);
                if (character.getGender().equals("female"))
                    females.push(character);
                if (character.getSpecies().equals("Droid"))
                    droids.push(character);
                if (character.getBirthYear() >= 0.0)
                    ages.push(character);
            }
        } catch (IOException e) {
            System.out.println("File not found.");
        }

        Stack<Character>[] stacks = new Stack[]{males, females, droids, ages};
        String[] stackNames = {"Male Characters", "Female Characters", "Droids", "Ages"};
        for (int i = 0; i < stacks.length; i++) {
            System.out.println(stackNames[i]);
            if (i <= 2)
                System.out.printf("%-24s %-16s \n", "Name", "Homeworld");
            else if (i == 3)
                System.out.printf("%-24s %-16s %-16s \n", "Name", "Homeworld", "Birth Year (BBY)");
            Stack stack = stacks[i];
            while (!stack.isEmpty()) {
                Character character = (Character)stack.pop();
                if (i <= 2)
                    System.out.printf("%-24s %-16s \n", character.getName(), character.getHomeworld());
                else if (i == 3)
                    System.out.printf("%-24s %-16s %-16s \n", character.getName(), character.getHomeworld(), character.getBirthYear());
            }
            System.out.println();
        }

    }

    public static void main(String[] args) {
        StarWarsCharacters app = new StarWarsCharacters();
    }

    public class Character {

        private String name;
        private double birthYear;
        private String gender;
        private String homeworld;
        private String species;

        public Character(String[] row) {
            name = stringValidator(row[0]);
            birthYear = doubleValidator(row[5]);
            gender = stringValidator(row[6]);
            homeworld = stringValidator(row[7]);
            species = stringValidator(row[8]);
        }

        public String stringValidator(String s) {
            return s.equals("NA") ? "Unknown" : s;
        }

        public double doubleValidator(String s) {
            if (s.equals("NA") || !s.contains("BBY"))
                return -1.0;
            return Double.parseDouble(s.substring(0, s.indexOf("BBY")));
        }

        public String getName() {
            return name;
        }

        public double getBirthYear() {
            return birthYear;
        }

        public String getGender() {
            return gender;
        }

        public String getHomeworld() {
            return homeworld;
        }

        public String getSpecies() {
            return species;
        }

    }

}
