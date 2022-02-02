package model;

import query.CountryQuery;
import query.FirstLevelDivQuery;

/**
 * Creates customer objects that are related to the customer table in the database.
 */
public class Customer {
    private int id;
    private String name;
    private String address;
    private String postal;
    private String phone;
    private int divisionId; //use this for searching for firstLevelDiv object
    private FirstLevelDiv fld; //this is for combobox
    private int countryId; //for selecting the country
    private Country country; //for using with the combobox

    /**
     *
     * @param id Unique customer identifier that is provided from the database.
     * @param name customer's name.
     * @param address customer's address.
     * @param postal customer's postal code.
     * @param phone customer's phone number.
     * @param divisionId customer's divisionID that relates to the country table.
     */
    public Customer(int id, String name, String address, String postal, String phone, int divisionId) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.postal = postal;
        this.phone = phone;
        this.divisionId = divisionId;
        fld = FirstLevelDivQuery.createFirstLevelDiv(divisionId); //queries db to find matching id for division
        countryId = fld.getCountryID();
        country = CountryQuery.createCountry(countryId); //queries db to find matching id for country
    }

    public FirstLevelDiv getFld() {
        return fld;
    }

    public int getCountryId() {
        return countryId;
    }

    public Country getCountry() {
        return country;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPostal() {
        return postal;
    }

    public void setPostal(String postal) {
        this.postal = postal;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getDivisionId() {
        return divisionId;
    }

    public void setDivisionId(int divisionId) {
        this.divisionId = divisionId;
    }

    /**
     *
     * @return Customer string that is to be displayed in a combobox.
     */
    @Override
    public String toString() {
        return id + " " + name;
    }
}
