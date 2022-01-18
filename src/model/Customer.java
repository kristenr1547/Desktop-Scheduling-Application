package model;

import query.CountryQuery;
import query.FirstLevelDivQuery;

public class Customer {
    private int id;
    private String name;
    private String address;
    private String postal;
    private String phone;
    private int divisionId;
    private FirstLevelDiv fld;
    private int countryId;
    private Country country;

//overloaded constructer when customers are added from db
    public Customer(int id, String name, String address, String postal, String phone, int divisionId) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.postal = postal;
        this.phone = phone;
        this.divisionId = divisionId;
        fld = FirstLevelDivQuery.createFirstLevelDiv(divisionId);
        countryId = fld.getCountryID();
        country = CountryQuery.createCountry(countryId);
    }
    //overloaded constructor for when adding customers into db from gui
    public Customer(int id, String name, String address, String postal, String phone, int divisionId, Country country, FirstLevelDiv fld){
        this.id = id;
        this.name = name;
        this.address = address;
        this.postal = postal;
        this.phone = phone;
        this.country = country;
        this.fld = fld;
        countryId = country.getCountryID();
        divisionId = fld.getDivID();
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
}
