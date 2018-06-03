package nl.hu.v1wac.firstapp.persistence;

import nl.hu.v1wac.firstapp.model.Country;
import java.util.List;

public interface CountryDao {
    public abstract boolean save(String code, String name, String capital, String surface, String population);
    public abstract List<Country> findAll();
    public abstract Country findByCode(String code);
    public abstract List<Country> filterCountries(String filterQuery);
    public abstract List<Country> find10LargestPopulations();
    public abstract List<Country> find10LargestSurfaces();
    public abstract boolean update(String code, String name, String capital, String surface, String population);
    public abstract boolean delete(Country country);

}
