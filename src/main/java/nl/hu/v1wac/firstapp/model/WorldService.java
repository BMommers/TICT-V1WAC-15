package nl.hu.v1wac.firstapp.model;

import nl.hu.v1wac.firstapp.persistence.CountryDao;
import nl.hu.v1wac.firstapp.persistence.CountryPostgresDaoImpl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class WorldService {
    private CountryDao dao = new CountryPostgresDaoImpl();
	
	public List<Country> getAllCountries() {
		return dao.findAll();
	}

    public List<Country> get10LargestPopulations() {
        return dao.find10LargestPopulations();
    }

	public List<Country> get10LargestSurfaces() {return dao.find10LargestSurfaces();}
	
	public Country getCountryByCode(String code) {return dao.findByCode(code);}
}
