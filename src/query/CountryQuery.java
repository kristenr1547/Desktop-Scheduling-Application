package query;

import helper.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Country;
import java.sql.*;

/**
 * Class that queries the country table.
 */
public class CountryQuery {
    private static Connection conn = JDBC.getConnection();
    private static Statement mystmt = null;
    private static PreparedStatement ps = null;
    private static ResultSet result = null;

    /**
     *
     * @return List of all countries.
     */
    public static ObservableList<Country> getAllCountries(){
        ObservableList<Country> allCountries = FXCollections.observableArrayList();
        try{
            mystmt = conn.createStatement();
            result = mystmt.executeQuery("SELECT * FROM countries");
            while(result.next()){
                Country country = new Country(result.getInt("Country_ID"), result.getString("Country"));
                allCountries.add(country);
            }
        } catch (SQLException e) {
            System.out.println("error in the countryquery class, getAllCountries method");
        }
        return allCountries;
    }

    /**
     *
     * @return Country object by ID to be displayed in a combo box.
     */
    public static Country createCountry(int countryID){
        try{
           ps= conn.prepareStatement("SELECT * FROM countries WHERE Country_ID = ?");
           ps.setInt(1, countryID);
           result = ps.executeQuery();
           if(result.next()){
               Country country = new Country(result.getInt("Country_ID"), result.getString("Country"));
               return country;
           }else{
               return null;
           }

        }catch (Exception e){
            System.out.println("error in createcountry method in countryQuery class");
            return null;
        }
    }

}

