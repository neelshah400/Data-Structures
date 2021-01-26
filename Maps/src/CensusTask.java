import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class CensusTask {

    ArrayList<Citizen> citizens;
    String divider;

    public CensusTask() {

        citizens = new ArrayList<Citizen>();
        divider = "\n" + ("-").repeat(80) + "\n";

        File file = new File("FedCensus1930_CambriaCountyPA.txt");
        try {
            BufferedReader input = new BufferedReader(new FileReader(file));
            String text;
            while ((text = input.readLine()) != null) {
                if (text.length() > 0 && text.charAt(0) == '1') {
                    Citizen c = new Citizen(text);
                    citizens.add(c);
                }
            }
        } catch (IOException e) {
            System.out.println("File not found.");
        }

        getCitizensByStreet();
        getAgesByBirthplace();
        getNamesByMotherTongue();
        getFBirthplacesByOccupation();
        getTranscriberRemarksByGender();
        getHousingValuesByHousingStatus();
        getCanReadByStreet();

    }

    public void getCitizensByStreet() {
        System.out.println("CITIZENS BY STREET:\n");
        TreeMap<String, TreeSet<Citizen>> citizensByStreet = new TreeMap<String, TreeSet<Citizen>>();
        for (Citizen c : citizens) {
            String street = c.getStreet();
            TreeSet<Citizen> citizensForStreet = citizensByStreet.containsKey(street) ? citizensByStreet.get(street) : new TreeSet<Citizen>();
            citizensForStreet.add(c);
            citizensByStreet.put(street, citizensForStreet);
        }
        Iterator<String> itr = citizensByStreet.keySet().iterator();
        while (itr.hasNext()) {
            String street = itr.next();
            System.out.println(street + ":");
            TreeSet<Citizen> citizensForStreet = citizensByStreet.get(street);
            for (Citizen c : citizensForStreet)
                System.out.println("\t" + c);
        }
        System.out.println(divider);
    }

    public void getAgesByBirthplace() {
        System.out.println("AGES BY BIRTHPLACE:\n");
        TreeMap<String, PriorityQueue<Double>> agesByBirthplace = new TreeMap<String, PriorityQueue<Double>>();
        for (Citizen c : citizens) {
            String birthplace = c.getBirthplace();
            PriorityQueue<Double> agesForBirthplace = agesByBirthplace.containsKey(birthplace) ? agesByBirthplace.get(birthplace) : new PriorityQueue<Double>();
            agesForBirthplace.add(c.getAge());
            agesByBirthplace.put(birthplace, agesForBirthplace);
        }
        Iterator<String> itr = agesByBirthplace.keySet().iterator();
        while (itr.hasNext()) {
            String birthplace = itr.next();
            if (!birthplace.equals("")) {
                System.out.println(birthplace + ":");
                PriorityQueue<Double> agesForBirthplace = agesByBirthplace.get(birthplace);
                if (birthplace.equals("Pennsylvania")) {
                    int count = 0;
                    while (!agesForBirthplace.isEmpty()) {
                        agesForBirthplace.poll();
                        count++;
                    }
                    System.out.println("\tCount:\t" + count);
                }
                else {
                    while (!agesForBirthplace.isEmpty())
                        System.out.println("\t" + agesForBirthplace.poll());
                }
            }
        }
        System.out.println(divider);
    }

    public void getNamesByMotherTongue() {
        System.out.println("NAMES BY MOTHER TONGUE:\n");
        TreeMap<String, ArrayList<String>> namesByMotherTongue = new TreeMap<String, ArrayList<String>>();
        for (Citizen c : citizens) {
            String motherTongue = c.getMotherTongue();
            ArrayList<String> namesForMotherTongue = namesByMotherTongue.containsKey(motherTongue) ? namesByMotherTongue.get(motherTongue) : new ArrayList<String>();
            namesForMotherTongue.add(c.getLastName() + ", " + c.getFirstName());
            namesByMotherTongue.put(motherTongue, namesForMotherTongue);
        }
        System.out.printf("%-16s %-8s\n", "Mother Tongue", "Names");
        System.out.println(("-").repeat(25));
        Iterator<String> itr = namesByMotherTongue.keySet().iterator();
        while (itr.hasNext()) {
            String motherTongue = itr.next();
            ArrayList<String> namesForMotherTongue = namesByMotherTongue.get(motherTongue);
            System.out.printf("%-16s %-8s\n", motherTongue, namesForMotherTongue.size());
        }
        System.out.println(divider);
    }

    public void getFBirthplacesByOccupation() {
        System.out.println("FATHERS' BIRTHPLACES BY OCCUPATION:\n");
        TreeMap<String, HashSet<String>> fBirthplacesByOccupation = new TreeMap<String, HashSet<String>>(String.CASE_INSENSITIVE_ORDER);
        for (Citizen c : citizens) {
            String occupation = c.getOccupation();
            HashSet<String> fBirthplacesForOccupation = fBirthplacesByOccupation.containsKey(occupation) ? fBirthplacesByOccupation.get(occupation) : new HashSet<String>();
            fBirthplacesForOccupation.add(c.getFBirthplace());
            fBirthplacesByOccupation.put(occupation, fBirthplacesForOccupation);
        }
        Iterator<String> itr = fBirthplacesByOccupation.keySet().iterator();
        while (itr.hasNext()) {
            String occupation = itr.next();
            System.out.println(occupation + ":");
            HashSet<String> fBirthplacesForOccupation = fBirthplacesByOccupation.get(occupation);
            for (String fBirthplace : fBirthplacesForOccupation)
                System.out.println("\t" + fBirthplace);
        }
        System.out.println(divider);
    }

    public void getTranscriberRemarksByGender() {
        System.out.println("TRANSCRIBER REMARKS BY GENDER:\n");
        TreeMap<String, HashSet<String>> transcriberRemarksByGender = new TreeMap<String, HashSet<String>>();
        for (Citizen c : citizens) {
            String gender = c.getGender();
            HashSet<String> transcriberRemarksForGender = transcriberRemarksByGender.containsKey(gender) ? transcriberRemarksByGender.get(gender) : new HashSet<String>();
            transcriberRemarksForGender.add(c.getTranscriberRemarks());
            transcriberRemarksByGender.put(gender, transcriberRemarksForGender);
        }
        Iterator<String> itr = transcriberRemarksByGender.keySet().iterator();
        while (itr.hasNext()) {
            String gender = itr.next();
            String fullGender = gender;
            if (gender.equals("F"))
                fullGender = "Female";
            else if (gender.equals("M"))
                fullGender = "Male";
            System.out.println(fullGender + ":");
            HashSet<String> transcriberRemarksForGender = transcriberRemarksByGender.get(gender);
            for (String transcriberRemarks : transcriberRemarksForGender)
                System.out.println("\t" + transcriberRemarks);
        }
        System.out.println(divider);
    }

    public void getHousingValuesByHousingStatus() {
        System.out.println("HOUSING VALUES BY HOUSING STATUS:\n");
        TreeMap<String, TreeSet<Double>> housingValuesByHousingStatus = new TreeMap<String, TreeSet<Double>>();
        for (Citizen c : citizens) {
            String housingStatus = c.getHousingStatus();
            TreeSet<Double> housingValuesForHousingStatus = housingValuesByHousingStatus.containsKey(housingStatus) ? housingValuesByHousingStatus.get(housingStatus) : new TreeSet<Double>();
            housingValuesForHousingStatus.add(c.getHousingValue());
            housingValuesByHousingStatus.put(housingStatus, housingValuesForHousingStatus);
        }
        Iterator<String> itr = housingValuesByHousingStatus.keySet().iterator();
        while (itr.hasNext()) {
            String housingStatus = itr.next();
            String fullHousingStatus = housingStatus;
            if (housingStatus.equals("O"))
                fullHousingStatus = "Own";
            else if (housingStatus.equals("R"))
                fullHousingStatus = "Rent";
            System.out.println(fullHousingStatus + ":");
            TreeSet<Double> housingValuesForHousingStatus = housingValuesByHousingStatus.get(housingStatus);
            for (double housingValue : housingValuesForHousingStatus)
                System.out.println("\t$" + housingValue);
        }
        System.out.println(divider);
    }

    public void getCanReadByStreet() {
        System.out.println("CAN READ/WRITE BY STREET:\n");
        System.out.println("How does reading/writing ability vary by street?\n");
        TreeMap<String, ArrayList<String>> canReadByStreet = new TreeMap<String, ArrayList<String>>();
        for (Citizen c : citizens) {
            String street = c.getStreet();
            ArrayList<String> canReadForStreet = canReadByStreet.containsKey(street) ? canReadByStreet.get(street) : new ArrayList<String>();
            canReadForStreet.add(c.getCanRead());
            canReadByStreet.put(street, canReadForStreet);
        }
        System.out.printf("%-16s %-8s %-8s %-8s %-8s\n", "Street", "Yes", "No", "Unknown", "Total");
        System.out.println(("-").repeat(52));
        Iterator<String> itr = canReadByStreet.keySet().iterator();
        while (itr.hasNext()) {
            String street = itr.next();
            ArrayList<String> canReadForStreet = canReadByStreet.get(street);
            int yes = 0, no = 0, unknown = 0;
            for (String canRead : canReadForStreet) {
                if (canRead.equals("Yes"))
                    yes++;
                else if (canRead.equals("No"))
                    no++;
                else
                    unknown++;
            }
            System.out.printf("%-16s %-8s %-8s %-8s %-8s\n", street, yes, no, unknown, canReadForStreet.size());
        }
    }

    public static void main(String[] args) {
        CensusTask app = new CensusTask();
    }

}