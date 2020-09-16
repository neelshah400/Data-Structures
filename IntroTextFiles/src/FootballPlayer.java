public class FootballPlayer implements Comparable<FootballPlayer> {

    private String avgPickLocation;
    private String name;
    private String position;
    private String team;
    private int bye;
    private double avgDraftPosition;
    private double stdDevADP;
    private String highPickLocation;
    private String lowPickLocation;
    private int timesDrafted;

    public FootballPlayer(String[] playerData) {
        avgPickLocation = playerData[0];
        name = playerData[1];
        position = playerData[2];
        team = playerData[3];
        bye = Integer.parseInt(playerData[4]);
        avgDraftPosition = Double.parseDouble(playerData[5]);
        stdDevADP = Double.parseDouble(playerData[6]);
        highPickLocation = playerData[7];
        lowPickLocation = playerData[8];
        timesDrafted = Integer.parseInt(playerData[9]);
    }

    public int pickLocationToDraftPosition(String pickLocation) {
        String[] pickComponents = pickLocation.split("[.]");
        int round = Integer.parseInt(pickComponents[0]);
        int num = Integer.parseInt(pickComponents[1]);
        if (pickComponents[1].length() == 1)
            num *= 10;
        return ((round - 1) * 12) + num;
    }

    public int getPickConsistency() {
        return pickLocationToDraftPosition(lowPickLocation) - pickLocationToDraftPosition(highPickLocation);
    }

    public int compareTo(FootballPlayer obj) {
        if (getPickConsistency() < obj.getPickConsistency())
            return -1;
        else if (getPickConsistency() > obj.getPickConsistency())
            return 1;
        else {
            if (avgDraftPosition < obj.avgDraftPosition)
                return -1;
            else if (avgDraftPosition > obj.avgDraftPosition)
                return 1;
            else
                return 0;
        }
    }

    public String toString() {
        return String.format("%-8s %-24s %-8s %-8s %-8s %-8s %-8s %-8s %-8s %-16s %-8s",
                avgPickLocation, name, position, team, bye, avgDraftPosition, stdDevADP, highPickLocation, lowPickLocation, timesDrafted, getPickConsistency());
    }

}
