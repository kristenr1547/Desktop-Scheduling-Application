package model;

public class FirstLevelDiv {

private int divID;
private String division;
private int countryID;

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

    @Override
    public String toString() {
        return "ID: " + getDivID() + " " + getDivision();
    }
}
