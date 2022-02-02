package model;

/**
 * Class that describes the country objects that are described from the country table in the database.
 */

public class Country {
    private int countryID;
    private String countryName;

    /**
     *
     * @param countryID The unique country identifier that is assigned in the database.
     * @param countryName The country's name.
     */
    public Country(int countryID, String countryName) {
        this.countryID = countryID;
        this.countryName = countryName;
    }

    public int getCountryID() {
        return countryID;
    }

    public void setCountryID(int countryID) {
        this.countryID = countryID;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

//    public ObservableList<FirstLevelDiv> getFirstLvlDivs() {
//        return firstLvlDivs;
//    }

    /**
     *
     * @return Country information that is easier to comprehend and view in the combobox.
     */
    @Override
    public String toString() {
        return "ID: " + getCountryID() + " " + getCountryName();
    }

}
