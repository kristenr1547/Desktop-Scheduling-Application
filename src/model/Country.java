package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import query.FirstLevelDivQuery;

public class Country {
    private int countryID;
    private String countryName;
//    private ObservableList<FirstLevelDiv> firstLvlDivs = FXCollections.observableArrayList();

    public Country(int countryID, String countryName) {
        this.countryID = countryID;
        this.countryName = countryName;
//        firstLvlDivs = FirstLevelDivQuery.getAllFirstLevelDiv(countryID);
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

    @Override
    public String toString() {
        return "ID: " + getCountryID() + " " + getCountryName();
    }

}
