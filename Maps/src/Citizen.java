import java.util.ArrayList;
import java.util.Arrays;

public class Citizen implements Comparable<Citizen> {

    static ArrayList<String> exclude = new ArrayList<String>(Arrays.asList(
            ".", "ab", "un", "No #", "?", "? ", "Unknown", ""
    ));

    private String firstName;
    private String lastName;
    private String street;
    private int streetNumber;
    private String relation;
    private String housingStatus;
    private double housingValue;
    private String gender;
    private double age;
    private String maritalStatus;
    private double ageAtFirstMarriage;
    private String attendsSchool;
    private String canRead;
    private String birthplace;
    private String fBirthplace;
    private String mBirthplace;
    private String motherTongue;
    private int yearImmigrated;
    private String occupation;
    private String industry;
    private String transcriberRemarks;

    public Citizen(String text) {
        firstName = parseString(text, 71, 88);
        lastName = parseString(text, 55, 71);
        street = parseString(text, 20, 36);
        streetNumber = parseInt(parseString(text, 36, 45));
        relation = parseString(text, 88, 108);
        housingStatus = parseString(text, 108, 113);
        housingValue = parseDouble(parseString(text, 113, 121));
        gender = parseString(text, 133, 138);
        age = parseDouble(parseString(text, 143, 151));
        maritalStatus = parseString(text, 151, 156);
        ageAtFirstMarriage = parseDouble(parseString(text, 156, 162));
        attendsSchool = parseString(text, 162, 167);
        canRead = parseString(text, 167, 173);
        birthplace = parseString(text, 173, 190);
        fBirthplace = parseString(text, 190, 207);
        mBirthplace = parseString(text, 207, 224);
        motherTongue = parseString(text, 224, 235);
        yearImmigrated = parseInt(parseString(text, 235, 241));
        occupation = parseString(text, 252, 274);
        industry = parseString(text, 274, 303);
        transcriberRemarks = parseString(text, 342, -1);
    }

    public String parseString(String s, int start, int end) {
        String str = end < 0 ? s.substring(start) : s.substring(start, end);
        str = str.replaceAll("\\*", "").trim();
        if (exclude.contains(str))
            return "Unknown";
        return str;
    }

    public int parseInt(String s) {
        if (exclude.contains(s))
            return -1;
        return Integer.parseInt(s);
    }

    public double parseDouble(String s) {
        String str = s.replaceAll("[$,]", "").replaceAll("- ab", "").replaceAll(" /", "/");
        if (exclude.contains(str))
            return Double.NaN;
        if (str.contains("/")) {
            double a = 0.0, b = 0.0, c = 0.0;
            if (str.contains(" ")) {
                String[] parts = str.split(" ");
                a = Double.parseDouble(parts[0]);
                str = parts[1];
            }
            String[] fraction = str.split("/");
            b = Double.parseDouble(fraction[0]);
            c = Double.parseDouble(fraction[1]);
            double val = a + (b / c);
            return Math.round(val * 100.0) / 100.0;
        }
        return Double.parseDouble(str);
    }
    

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getStreet() {
        return street;
    }

    public int getStreetNumber() {
        return streetNumber;
    }

    public String getRelation() {
        return relation;
    }

    public String getHousingStatus() {
        return housingStatus;
    }

    public double getHousingValue() {
        return housingValue;
    }

    public String getGender() {
        return gender;
    }

    public double getAge() {
        return age;
    }

    public String getMaritalStatus() {
        return maritalStatus;
    }

    public double getAgeAtFirstMarriage() {
        return ageAtFirstMarriage;
    }

    public String getAttendsSchool() {
        return attendsSchool;
    }

    public String getCanRead() {
        return canRead;
    }

    public String getBirthplace() {
        return birthplace;
    }

    public String getFBirthplace() {
        return fBirthplace;
    }

    public String getMBirthplace() {
        return mBirthplace;
    }

    public String getMotherTongue() {
        return motherTongue;
    }

    public int getYearImmigrated() {
        return yearImmigrated;
    }

    public String getOccupation() {
        return occupation;
    }

    public String getIndustry() {
        return industry;
    }

    public String getTranscriberRemarks() {
        return transcriberRemarks;
    }

    public String toString() {
        return String.format("%-24s Age: %s", firstName + " " + lastName, age);
    }

    public int compareTo(Citizen o) {
        Object[] properties = {
                firstName, lastName, street, streetNumber, relation, housingStatus, housingValue, gender,
                age, maritalStatus, ageAtFirstMarriage, attendsSchool, canRead, birthplace, fBirthplace, 
                mBirthplace, yearImmigrated, occupation, industry, transcriberRemarks
        };
        Object[] oProperties = {
                o.firstName, o.lastName, o.street, o.streetNumber, o.relation, o.housingStatus, o.housingValue, o.gender,
                age, o.maritalStatus, o.ageAtFirstMarriage, o.attendsSchool, o.canRead, o.birthplace, o.fBirthplace,
                mBirthplace, o.yearImmigrated, o.occupation, o.industry, o.transcriberRemarks
        };
        for (int i = 0; i < properties.length; i++) {
            Object property = properties[i];
            Object oProperty = oProperties[i];
            int comparison = 0;
            if (property instanceof String)
                comparison = String.CASE_INSENSITIVE_ORDER.compare((String)property, (String)oProperty);
            if (property instanceof Integer)
                comparison = Integer.compare((int)property, (int)oProperty);
            if (property instanceof Double)
                comparison = Double.compare((double)property, (double)oProperty);
            if (comparison != 0)
                return comparison;
        }
        return 0;
    }
    
}
