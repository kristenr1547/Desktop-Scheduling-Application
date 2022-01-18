package query;

import helper.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.FirstLevelDiv;
import java.sql.*;

public class FirstLevelDivQuery {
    private static Connection conn = JDBC.getConnection();
    private static Statement mystmt = null;
    private static PreparedStatement ps = null;
    private static ResultSet result = null;
    private static ObservableList<FirstLevelDiv> allFirstLvlDivforCountry = FXCollections.observableArrayList();

  //to be used in add and update customer table and country class
    public static ObservableList<FirstLevelDiv> getAllFirstLevelDiv(int countryID){
        try{
            ps = conn.prepareStatement("SELECT * FROM first_level_divisions WHERE Country_ID = ?");
            ps.setInt(1, countryID);
            result = ps.executeQuery();
            while(result.next()){
                FirstLevelDiv flv = new FirstLevelDiv(result.getInt("Division_ID"), result.getString("Division"), result.getInt("Country_ID"));
                allFirstLvlDivforCountry.add(flv);
            }
        }catch (SQLException e){
            System.out.println(e.getErrorCode());
        }
        return allFirstLvlDivforCountry;
    }

    public static FirstLevelDiv createFirstLevelDiv(int id){
        int divID = 0;
        String divisionName = null;
        int countryID= 0;
        try{
            ps = conn.prepareStatement("SELECT * FROM first_level_Divisions WHERE Division_ID = ?");
            ps.setInt(1, id);
            result = ps.executeQuery();
            if(result.next()){
                divID = result.getInt("Division_ID");
                divisionName = result.getString("Division");
                countryID = result.getInt("Country_ID");
            }
            FirstLevelDiv fld = new FirstLevelDiv(divID,divisionName,countryID);
            return fld;

        }catch (SQLException e){
            System.out.println(" error in firstlevelqueryclass");
            return null;
        } }

}
