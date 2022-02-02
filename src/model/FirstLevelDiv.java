package model;

/**
 * Class that creates FirstLevelDivisions that are described in the First_Level_Divisions table in the database.
 */
public class FirstLevelDiv {
private int divID;
private String division;
private int countryID;

    /**
     *
     * @param divID The unique identifier that is provided from the database.
     * @param division The name of the division.
     * @param countryID The countryID that is linked to the division.
     */
    public FirstLevelDiv(int divID, String division, int countryID) {
        this.divID = divID;
        this.division = division;
        this.countryID = countryID;
    }

    public int getDivID() {
        return divID;
    }

    public void setDivID(int divID) {
        this.divID = divID;
    }

    public String getDivision() {
        return division;
    }

    public void setDivision(String division) {
        this.division = division;
    }

    public int getCountryID() {
        return countryID;
    }

    public void setCountryID(int countryID) {
        this.countryID = countryID;
    }

    /**
     *
     * @return String to be displayed in a combobox in a legible manner.
     */
    @Override
    public String toString() {
        return "ID: " + getDivID() + " " + getDivision();
    }
}
