package nl.hu.v1wac.firstapp.persistence;

import nl.hu.v1wac.firstapp.model.Country;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
//public class CountryPostgresDaoImpl extends PostgresBaseDao implements CountryDao {
public class CountryPostgresDaoImpl extends BaseDAO implements CountryDao {
    private List<Country> selectCountries (String query) {
        List<Country> results = new ArrayList<Country>();

        try (Connection con = super.getConnection()) {
            PreparedStatement pstmt = con.prepareStatement(query);
            ResultSet dbResultSet = pstmt.executeQuery();
            while (dbResultSet.next()) {
                String code = dbResultSet.getString("code");
                String iso3 = dbResultSet.getString("Iso3");
                String name = dbResultSet.getString("name");
                String continent = dbResultSet.getString("continent");
                String region = dbResultSet.getString("region");
                String localname = dbResultSet.getString("localname");
                String governmentform = dbResultSet.getString("governmentform");
                String headofstate = dbResultSet.getString("headofstate");
                String capital = dbResultSet.getString("capital");
                Integer indepyear = dbResultSet.getInt("indepyear");
                Integer population = dbResultSet.getInt("population");
                Double surfacearea = dbResultSet.getDouble("surfacearea");
                Double lifeexpectancy = dbResultSet.getDouble("lifeexpectancy");
                Double gnp = dbResultSet.getDouble("gnp");
                Double gnpoid = dbResultSet.getDouble("gnpold");
                Double latitude = dbResultSet.getDouble("latitude");
                Double longitude = dbResultSet.getDouble("longitude");

                Country newCountry = new Country(code, iso3, name, capital, continent, region, surfacearea, population, governmentform, latitude, longitude);
                results.add(newCountry);
            }
        } catch (SQLException sqle) { sqle.printStackTrace(); }
        return results;
    }

    public List<Country> filterCountries(String filterQuery) {
        return selectCountries("SELECT * FROM country WHERE LOWER(name) LIKE LOWER('%" + filterQuery + "%')");
    }

    public boolean save(String code, String name, String capital, String surface, String population) {
        try (Connection con = super.getConnection()) {
            PreparedStatement pstmt = con.prepareStatement("INSERT INTO country (code, name, capital, surfacearea, population) VALUES ('" + code + "', '" + name + "', '" + capital + "', '" + surface + "', '" + population + "')");
            ResultSet dbResultSet = pstmt.executeQuery();
        } catch (SQLException sqle) {
            sqle.printStackTrace();
            return false;
        }

        return true;
    }
    public List<Country> findAll(){
        return selectCountries("SELECT * FROM country");
    }

    public Country findByCode(String code){
        return selectCountries("SELECT * FROM country WHERE code = '" + code + "'").get(0);
    }
    public List<Country> find10LargestPopulations(){
        return selectCountries("SELECT * FROM country ORDER BY population desc LIMIT 10");
    }
    public List<Country> find10LargestSurfaces(){
        return selectCountries("SELECT * FROM country ORDER BY surfacearea desc LIMIT 10");
    }
    public boolean update(String code, String name, String capital, String surface, String population){
        try (Connection con = super.getConnection()) {
            PreparedStatement pstmt = con.prepareStatement("UPDATE country SET (name, capital, surfacearea, population) = ('" + name + "', '" + capital + "', '" + surface + "', '" + population + "') WHERE code = '" + code + "'");
            ResultSet dbResultSet = pstmt.executeQuery();
        } catch (SQLException sqle) {
            sqle.printStackTrace();
            return false;
        }

        return true;
    }

    public boolean delete(Country country){
        try (Connection con = super.getConnection()) {
            PreparedStatement pstmt = con.prepareStatement("DELETE FROM country WHERE code = '" + country.getCode() + "'");
            ResultSet dbResultSet = pstmt.executeQuery();
        } catch (SQLException sqle) {
            sqle.printStackTrace();
            return false;
        }

        return true;
    }
}
