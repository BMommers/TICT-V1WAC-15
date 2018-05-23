package nl.hu.v1wac.firstapp.persistence;

import nl.hu.v1wac.firstapp.model.Country;
import java.util.List;

public interface CountryDao {
    public abstract boolean save(Country country);
    public abstract List<Country> findAll();
    public abstract Country findByCode(String code);
    public abstract List<Country> find10LargestPopulations();
    public abstract List<Country> find10LargestSurfaces();
    public abstract boolean update(Country country);
    public abstract boolean delete(Country country);

}
